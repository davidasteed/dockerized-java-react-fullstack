package com.dockerizedjavareact.supervisorMgmt.controller;

import java.io.IOException;

import org.json.JSONArray;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dockerizedjavareact.supervisorMgmt.models.SupervisorNotification;
import com.dockerizedjavareact.supervisorMgmt.models.dtos.SupervisorNotificationDto;
import com.dockerizedjavareact.supervisorMgmt.services.interfaces.SupervisorMgmtService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
public class RestServiceController {
	private final SupervisorMgmtService supervisorMgmtService;

	public RestServiceController(SupervisorMgmtService supervisorMgmtService) {
		this.supervisorMgmtService = supervisorMgmtService;
	}

	@GetMapping("/api/supervisors")
	public String getSupervisors() {
		JSONArray returnValue = new JSONArray();
		try {
			Object supervisorData = supervisorMgmtService.getSupervisorData();
			returnValue = supervisorMgmtService.getSupervisorData();
			return returnValue.toString(); // TODO implement response entity
		} catch (IOException io) {
			System.out.println("getSupervisors() caught an IOException!");
		}
		return returnValue.toString(); // TODO: implement valid error/empty value for frontend
	}

	@PostMapping("/api/submit")
	public ResponseEntity<SupervisorNotification> createSupervisorNotification(
			@Valid @RequestBody SupervisorNotificationDto supervisorNotificationDto) {
		SupervisorNotification supervisorNotification = supervisorMgmtService
				.createNotification(supervisorNotificationDto.toSupervisorNotification());

		System.out.println("Successful createSupervisorNotification: {" 
				+ " First name: " + supervisorNotification.getFirstName() + "," 
				+ " Last name: " + supervisorNotification.getLastName() + "," 
				+ " email: " + supervisorNotification.getEmail() + "," 
				+ " Phone number: " + supervisorNotification.getPhoneNumber() + "," 
				+ " Supervisor: " + supervisorNotification.getSupervisor() 
				+ "}");

		return new ResponseEntity<>(HttpStatusCode.valueOf(200));
	}
}
