package com.dockerizedjavareact.supervisorMgmt.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.springframework.stereotype.Component;

import com.dockerizedjavareact.supervisorMgmt.models.SupervisorNotification;
import com.dockerizedjavareact.supervisorMgmt.services.interfaces.SupervisorMgmtService;
import com.dockerizedjavareact.supervisorMgmt.utils.Utils;

@Component
public class SupervisorMgmtServiceImpl implements SupervisorMgmtService {
	public static final String externalSupervisorURL = "https://o3m5qixdng.execute-api.us-east-1.amazonaws.com/api/managers";
	public static final String USER_AGENT = "dockerizedjavareactService";

	public JSONArray getSupervisorData() throws IOException {
		URL supervisorUrl = new URL(externalSupervisorURL);
		JSONArray responseArray = new JSONArray();
		try {
			HttpURLConnection connection = (HttpURLConnection) supervisorUrl.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", USER_AGENT);
			int responseCode = connection.getResponseCode();
			System.out.println("getSupervisors() GET response code: " + responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream iStream = connection.getInputStream();
				StringBuffer responseStringBuffer = Utils.createInputStream(iStream);

				responseArray = Utils.createSupervisorData(responseStringBuffer.toString());
				System.out.println("getSupervisors() GET response object: " + responseArray);
				return responseArray;
			}
		} catch (Exception E) {
			System.out.println("getSupervisors() GET request failed");
		} finally {
			// provide sample supervisor data if there is a problem with the public data source
			if (responseArray.length() == 0) {
				InputStream iStream = this.getClass().getResourceAsStream("./sampleSupervisorData.json");
				StringBuffer responseStringBuffer = Utils.createInputStream(iStream);
				responseArray = Utils.createSupervisorData(responseStringBuffer.toString());
				System.out.println("getSupervisors() sample data: " + responseArray);
			}
		}
		return responseArray;
	}

	public SupervisorNotification createNotification(SupervisorNotification supervisorNotification) {
		return new SupervisorNotification(supervisorNotification);
	};

}
