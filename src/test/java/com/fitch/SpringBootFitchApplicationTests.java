package com.fitch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fitch.dto.MessageDto;
import com.fitch.enums.MessagePriority;
import com.fitch.enums.MessageType;
import com.fitch.resource.MessageResource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootFitchApplicationTests {

	private static final int POOL_SIZE = 20;

	private static final int START_INDEX = 1;

	private static final int DIVISOR = 2;

	private static final String MESSAGE = "Hello Message %d";

	private static final String MISMATCH_MESSAGE = "Message DTO Mismatch Found:";

	private static final String OUT_OF_ORDER_MESSAGE = "Message response not in order by priority and FIFO:";

	private static final String EXPECTED_NUMBER_OF_RESPONSES_NOT_FOUND = "Expected number of responses do not match the actual.";

	private static final MessageType[] MESSAGE_TYPE = { MessageType.RED, MessageType.BLUE, MessageType.YELLOW };

	private static final MessagePriority[] MESSAGE_PRIORITY = { MessagePriority.HIGHEST, MessagePriority.MEDIUM,
			MessagePriority.LOWEST };

	private static final Random RANDOM_MESSAGE_TYPE = new Random();

	private static final Random RANDOM_MESSAGE_PRIORITY = new Random();

	////////////////////////////////////////////////////////////////////

	private final Map<Integer, MessageDto> responseMessageDtoMap = new HashMap<>();

	////////////////////////////////////////////////////////////////////

	@Inject
	private MessageResource messageResource;

	@Before
	public void setUp() throws InterruptedException {

		ExecutorService executorService = Executors.newFixedThreadPool(POOL_SIZE);

		// add messages concurrently to the store through the service api
		IntStream	.rangeClosed(START_INDEX, POOL_SIZE / DIVISOR)
					.forEach(index -> {

						executorService.execute(() -> {

							MessageDto responseMessageDto = messageResource.createMessage(getMessageDto(index));

							responseMessageDtoMap.put(index, responseMessageDto);

						});

					});

		// await threads to finish till timeout
		executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);

		// shutdown executor service
		executorService.shutdown();

	}

	@After
	public void tearDown() {

		responseMessageDtoMap.clear();

	}

	@Test
	public void test_Messages_Received_For_A_Type_Are_In_Order_Of_Priority_And_Fifo() {

		final List<MessageDto> responseMessageDtos = findResponseMessageDtos();

		// ASSERTION AGAINST EXPECTATION

		// validate messages received for a type are in the order of priority
		// and FIFO. which means the priority and message create date time of
		// the previous message is not higher than subsequent messages.
		responseMessageDtos	.stream()
							.collect(
									Collectors.groupingBy(
											responseMessageDto -> responseMessageDto.getMessageType()))
							.entrySet()
							.forEach(entry -> {

								List<MessageDto> messageDtos = entry.getValue();

								messageDtos.forEach(messageDto -> {

									messageDtos	.stream()
												.skip(messageDtos.indexOf(messageDto) + 1)
												.filter(tempMessageDto -> messageDto.compareTo(tempMessageDto) > 1)
												.findFirst()
												.ifPresent(tempMessageDto -> fail(
														OUT_OF_ORDER_MESSAGE +
																System.lineSeparator() +
																tempMessageDto));

								});

							});

	}

	@Test
	public void test_Messages_That_Are_Created_And_Found_Match() {

		final List<MessageDto> responseMessageDtos = findResponseMessageDtos();

		// ASSERTION AGAINST EXPECTATION

		// Match the number of responses found equal the created ones
		assertEquals(EXPECTED_NUMBER_OF_RESPONSES_NOT_FOUND, responseMessageDtoMap.size(), responseMessageDtos.size());

		// validate if the responses received via find and remove match the ones
		// that were created.
		responseMessageDtos.forEach(responseMessageDto -> {

			if (!responseMessageDtoMap.containsValue(responseMessageDto)) {

				fail(
						MISMATCH_MESSAGE +
								System.lineSeparator() +
								responseMessageDtoMap +
								System.lineSeparator() +
								responseMessageDto);

			} else {

				System.out.println(responseMessageDto);

			}

		});

	}

	private List<MessageDto> findResponseMessageDtos() {

		final List<MessageDto> responseMessageDtos = new ArrayList<>();

		// get messages for each of the type from the map and store them in the
		// list where the insertion order is preserved
		IntStream	.rangeClosed(START_INDEX, POOL_SIZE / DIVISOR)
					.forEach(index -> {

						MessageDto responseMessageDto = messageResource.findAndRemoveMessage(
								responseMessageDtoMap	.get(index)
														.getMessageType());

						responseMessageDtos.add(responseMessageDto);

					});

		return responseMessageDtos;

	}

	private MessageDto getMessageDto(int index) {

		return new MessageDto(
				String.format(MESSAGE, index),
				getMessageType(),
				getMessagePriority());

	}

	private String getMessageType() {

		return MESSAGE_TYPE[RANDOM_MESSAGE_TYPE.nextInt(MESSAGE_TYPE.length)].name();

	}

	private int getMessagePriority() {

		return MESSAGE_PRIORITY[RANDOM_MESSAGE_PRIORITY.nextInt(MESSAGE_PRIORITY.length)].getPriorityCode();

	}

}
