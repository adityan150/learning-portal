package com.adityan150.learningportal.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {
	
	private static final String EMAIL_REGEX =
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
					"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public static boolean isValidEmail(String email) {
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(email);
        return matcher.matches();
	}
	
	public static boolean isValidInputString(String input) {
		return (input != null && input.trim().length() != 0);
	}

}
