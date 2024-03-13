package com.lightfeather.supervisorMgmt.models.dtos;

import com.lightfeather.supervisorMgmt.apiError.interfaces.NameConstraintValidator;
import com.lightfeather.supervisorMgmt.apiError.interfaces.PhoneNumberConstraintValidator;
import com.lightfeather.supervisorMgmt.models.SupervisorNotification;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern.Flag;
import lombok.Data;

@Data
public class SupervisorNotificationDto {
	@NameConstraintValidator
	@NotEmpty(message = "First name is required")
	private String firstName;

	@NameConstraintValidator
	@NotEmpty(message = "Last name is required")
	private String lastName;

	@Email(message = "The email address is invalid.",
			// TODO perhaps more rigorous email validation, such as (source Baeldung):
			// regexp =
			// "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$",
			flags = { Flag.CASE_INSENSITIVE })
	private String email;

	@PhoneNumberConstraintValidator
	private String phoneNumber;

	@NotEmpty(message = "Supervisor is required")
	private String supervisor;

	public SupervisorNotification toSupervisorNotification() {
		return new SupervisorNotification().setFirstName(firstName).setLastName(lastName).setEmail(email)
				.setPhoneNumber(phoneNumber).setSupervisor(supervisor);
	}
}