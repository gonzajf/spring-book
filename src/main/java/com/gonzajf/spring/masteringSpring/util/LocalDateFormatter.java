package com.gonzajf.spring.masteringSpring.util;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.format.Formatter;

public class LocalDateFormatter implements Formatter<LocalDate> {

	public static final String US_PATTERN = "MM/dd/yyyy";
	public static final String PATTERN = "dd/MM/yyyy";

	@Override
	public String print(LocalDate object, Locale locale) {
		return DateTimeFormatter.ofPattern(getPattern(locale)).format(object);
	}

	@Override
	public LocalDate parse(String text, Locale locale) throws ParseException {
		return LocalDate.parse(text, DateTimeFormatter.ofPattern(getPattern(locale)));
	}

	public static String getPattern(Locale locale) {
		return isUnitedStates(locale) ? US_PATTERN : PATTERN;
	}

	private static boolean isUnitedStates(Locale locale) {
		return Locale.US.getCountry().equals(locale.getCountry());
	}
}