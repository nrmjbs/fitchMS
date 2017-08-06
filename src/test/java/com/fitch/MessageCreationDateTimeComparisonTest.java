package com.fitch;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

public class MessageCreationDateTimeComparisonTest {

	@Test
	public void testTwoMessageCreationDateTimesForAscendingOrder() {

		LocalDateTime date1 = LocalDateTime.parse("2017-08-06T00:35:21.555");

		LocalDateTime date2 = LocalDateTime.parse("2017-08-06T00:35:21.57");

		assertTrue("Date1 is not before Date2", date1.isBefore(date2));

	}

}
