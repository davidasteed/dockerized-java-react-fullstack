package com.lightfeather.supervisorMgmt.apiError.interfaces;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Payload;

import com.lightfeather.supervisorMgmt.apiError.PhoneNumberConstraintValidatorImpl;

import jakarta.validation.Constraint;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { PhoneNumberConstraintValidatorImpl.class })
@Documented
public @interface PhoneNumberConstraintValidator {
	String message() default "Must be valid phone number";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}