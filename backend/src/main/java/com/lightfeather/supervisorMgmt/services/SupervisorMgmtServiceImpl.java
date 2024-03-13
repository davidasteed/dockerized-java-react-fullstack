package com.lightfeather.supervisorMgmt.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.springframework.stereotype.Component;

import com.lightfeather.supervisorMgmt.models.SupervisorNotification;
import com.lightfeather.supervisorMgmt.services.interfaces.SupervisorMgmtService;
import com.lightfeather.supervisorMgmt.utils.Utils;

@Component
public class SupervisorMgmtServiceImpl implements SupervisorMgmtService {
	public static final String externalSupervisorURL = "https://o3m5qixdng.execute-api.us-east-1.amazonaws.com/api/managers";
	public static final String USER_AGENT = "LightfeatherService";

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
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer responseStringBuffer = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					responseStringBuffer.append(inputLine);
				}
				in.close();
				responseArray = Utils.createSupervisorData(responseStringBuffer.toString());
				System.out.println("getSupervisors() GET response object: " + responseArray);
				return responseArray;
			}
		} catch (Exception E) {
			System.out.println("getSupervisors() GET request failed");
		}
		return responseArray;
	}

	public SupervisorNotification createNotification(SupervisorNotification supervisorNotification) {
		return new SupervisorNotification(supervisorNotification);
	};

}
