package com.dockerizedjavareact.supervisorMgmt.services.interfaces;

import java.io.IOException;

import org.json.JSONArray;

import com.dockerizedjavareact.supervisorMgmt.models.SupervisorNotification;

public interface SupervisorMgmtService {
	JSONArray getSupervisorData() throws IOException;

	SupervisorNotification createNotification(SupervisorNotification supervisorNotification);
}