package com.loserico.json.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Pattern;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * <p>
 * Copyright: Copyright (c) 2019/10/15 9:09
 * <p>
 * Company: Sexy Uncle Inc.
 * <p>
 *
 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 */
@Slf4j
public final class DateUtils {
	/**
	 * yyyy-MM-dd
	 */
	private static final Pattern PT_ISO_DATE = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
	public static final String FMT_ISO_DATE = "yyyy-MM-dd";
	public static final DateTimeFormatter DTF_ISO_DATE = ofPattern(FMT_ISO_DATE);

	public static LocalDate toLocalDate(String source) {
		Objects.nonNull(source);
		if (PT_ISO_DATE.matcher(source).matches()) {
			return LocalDate.parse(source, DTF_ISO_DATE);
		}
		log.warn("{} does not match any LocalDate format! ", source);
		return null;
	}
}
