package com.loserico.json;

import com.loserico.json.jackson.JacksonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * Copyright: (C), 2019/12/21 19:15
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
public class JacksonUtilsTest {
	
	@Test
	public void testDeserialLocalDateTime() {
		String s = "2019-12-21 19:15:34";
		String dateStr = JacksonUtils.toJson(new DateObj(LocalDateTime.of(2019, 12, 21, 19, 15, 34)));
		DateObj dateObj = JacksonUtils.toObject(dateStr, DateObj.class);
		Assert.assertEquals(dateObj.getDatetime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), s);
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	private static class DateObj {
		private LocalDateTime datetime;
	}
}
