package com.lightfeather.supervisorMgmt.apiError.interfaces;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Payload;

import com.lightfeather.supervisorMgmt.apiError.NameConstraintValidatorImpl;

import jakarta.validation.Constraint;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { NameConstraintValidatorImpl.class })
@Documented
public @interface NameConstraintValidator {
	String message() default "Must only contain letters, no numbers";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}