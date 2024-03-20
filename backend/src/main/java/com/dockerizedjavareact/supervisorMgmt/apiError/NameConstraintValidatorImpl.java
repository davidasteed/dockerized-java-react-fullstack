package com.dockerizedjavareact.supervisorMgmt.apiError;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Component;

import com.dockerizedjavareact.supervisorMgmt.apiError.interfaces.NameConstraintValidator;
import com.dockerizedjavareact.supervisorMgmt.utils.Utils;

import jakarta.validation.ConstraintValidatorContext;

@Component
public class NameConstraintValidatorImpl
		implements jakarta.validation.ConstraintValidator<NameConstraintValidator, String> {

	@Override
	public void initialize(NameConstraintValidator constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean isValid = true;

		try {
			if (value != null && Utils.isDigit(value)) {
				isValid = false;
			}
		} catch (ConstraintViolationException C) {
			isValid = false;
		}

		return isValid;
	}
}
