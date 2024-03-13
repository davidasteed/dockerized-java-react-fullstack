package com.lightfeather.supervisorMgmt.apiError;

import java.util.regex.Pattern;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Component;

import com.lightfeather.supervisorMgmt.apiError.interfaces.PhoneNumberConstraintValidator;

import jakarta.validation.ConstraintValidatorContext;

@Component
public class PhoneNumberConstraintValidatorImpl
		implements jakarta.validation.ConstraintValidator<PhoneNumberConstraintValidator, String> {

	@Override
	public void initialize(PhoneNumberConstraintValidator constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean isValid = true;

		try {
			if (value != null && !Pattern
					.compile("^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$")
					.matcher(value).matches()) {
				isValid = false;
			}

		} catch (ConstraintViolationException C) {
			isValid = false;
		}

		return isValid;
	}
}
