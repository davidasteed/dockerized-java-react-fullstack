package com.dockerizedjavareact.supervisorMgmt.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class SupervisorNotification {
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String supervisor;

	public SupervisorNotification(SupervisorNotification supervisorNotification) {
		this.firstName = supervisorNotification.firstName;
		this.lastName = supervisorNotification.lastName;
		this.email = supervisorNotification.email;
		this.phoneNumber = supervisorNotification.phoneNumber;
		this.supervisor = supervisorNotification.supervisor;
	}
}
