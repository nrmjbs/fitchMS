package com.fitch.standalone;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fitch.dto.MessageDto;
import com.fitch.enums.MessagePriority;
import com.fitch.enums.MessageType;

public class MessageApplicationClient {

	private static final int POOL_SIZE = 20;

	private static final int START_INDEX = 1;

	private static final int DIVISOR = 2;

	private static final String DELIMETER = " : ";

	private static final String MESSAGE = "Hello Message %d";

	private static final String MESSAGE_NOT_ADDED = "Message not added";

	private static final String MISMATCH_MESSAGE = "Message DTO Mismatch Found:";

	private static final String OUT_OF_ORDER_MESSAGE = "Message response not in order by priority and FIFO:";

	private static final String EXPECTED_NUMBER_OF_RESPONSES_NOT_FOUND = "Expected number of responses %d do not match the actual %d.";

	private static final MessageType[] MESSAGE_TYPE = { MessageType.RED, MessageType.BLUE, MessageType.YELLOW };

	private static final MessagePriority[] MESSAGE_PRIORITY = { MessagePriority.HIGHEST, MessagePriority.MEDIUM,
			MessagePriority.LOWEST };

	private static final Random RANDOM_MESSAGE_TYPE = new Random();

	private static final Random RANDOM_MESSAGE_PRIORITY = new Random();

	private static final Map<Integer, MessageDto> responseMessageDtoMap = new ConcurrentHashMap<>();

	private static final IStandaloneMessageValidationService messageValidationService = new StandaloneMessageValidationService();

	////////////////////////////////////////////////////////////////////

	public static void main(String[] args) throws InterruptedException {

		MessageApplication application = new MessageApplication();

		addMessages(application);

		List<MessageDto> responseMessageDtos = getMessages(application);

		test_Messages_Received_For_A_Type_Are_In_Order_Of_Priority_And_Fifo(responseMessageDtos);

		test_Messages_That_Are_Created_And_Found_Match(responseMessageDtos);

	}

	private static void addMessages(MessageApplication application) throws InterruptedException {

		ExecutorService executorService = Executors.newFixedThreadPool(POOL_SIZE);

		// add messages concurrently
		IntStream	.rangeClosed(START_INDEX, POOL_SIZE / DIVISOR)
					.forEach(index -> {

						executorService.execute(() -> {

							application.addMessage(
									new MessageCallbackWrapper(
											getMessage(index),
											(message, statusType, statusMessage, additionalInformation) -> {

												// stateless callback
												// implementation via lambda
												// expression
												if (statusType.getValue()) {

													responseMessageDtoMap.put(index, message);

												} else {

													System.out.println(
															new StringBuilder()
																				.append(MESSAGE_NOT_ADDED)
																				.append(DELIMETER)
																				.append(statusMessage)
																				.append(DELIMETER)
																				.append(additionalInformation)
																				.append(DELIMETER)
																				.append(message)
																				.toString());

												}

											}));

						});

					});

		// await threads to finish till timeout
		executorService.awaitTermination(5000, TimeUnit.MILLISECONDS);

		// shutdown executor service
		executorService.shutdown();

	}

	private static List<MessageDto> getMessages(MessageApplication application) {

		final List<MessageDto> responseMessageDtos = new ArrayList<>();

		// get messages for each of the type from the map and store them in the
		// list where the insertion order is preserved
		IntStream	.rangeClosed(START_INDEX, POOL_SIZE / DIVISOR)
					.parallel()
					.forEach(index -> {

						if (responseMessageDtoMap.get(index) != null) {

							MessageCallbackWrapper messageCallbackWrapper = application.getMessage(
									responseMessageDtoMap	.get(index)
															.getMessageType());

							messageValidationService.validateMessageCallbackWrapper(messageCallbackWrapper);

							responseMessageDtos.add(messageCallbackWrapper.getMessage());

						}

					});

		return responseMessageDtos;

	}

	private static void test_Messages_Received_For_A_Type_Are_In_Order_Of_Priority_And_Fifo(
			final List<MessageDto> responseMessageDtos) {

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
												.ifPresent(tempMessageDto -> {
													throw new RuntimeException(
															OUT_OF_ORDER_MESSAGE +
																	System.lineSeparator() +
																	tempMessageDto);
												});

								});

							});

	}

	private static void test_Messages_That_Are_Created_And_Found_Match(final List<MessageDto> responseMessageDtos) {

		// ASSERTION AGAINST EXPECTATION

		// Match the number of responses found equal the created ones
		int expectedResponseSize = responseMessageDtoMap.size();

		int actualResponseSize = responseMessageDtos.size();

		if (expectedResponseSize != actualResponseSize)
			throw new RuntimeException(
					String.format(EXPECTED_NUMBER_OF_RESPONSES_NOT_FOUND, expectedResponseSize, actualResponseSize));

		// validate if the responses received via find and remove match the ones
		// that were created.
		responseMessageDtos.forEach(responseMessageDto -> {

			if (!responseMessageDtoMap.containsValue(responseMessageDto)) {

				throw new RuntimeException(
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

	private static MessageDto getMessage(int index) {

		return new MessageDto(
				String.format(MESSAGE, index),
				getMessageType(),
				getMessagePriority(),
				LocalDateTime	.now()
								.toString());

	}

	private static String getMessageType() {

		return MESSAGE_TYPE[RANDOM_MESSAGE_TYPE.nextInt(MESSAGE_TYPE.length)].name();

	}

	private static int getMessagePriority() {

		return MESSAGE_PRIORITY[RANDOM_MESSAGE_PRIORITY.nextInt(MESSAGE_PRIORITY.length)].getPriorityCode();

	}

}
