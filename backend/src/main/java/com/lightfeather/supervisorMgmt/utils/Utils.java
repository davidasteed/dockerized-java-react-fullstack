package com.lightfeather.supervisorMgmt.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utils {
	enum sortOption {
		alphabetical
	};

	enum dataFields {
		firstName, jurisdiction, lastName
	};

	public static JSONArray createSupervisorData(String data) {
		// 1. create array of objects that have three properties:
		// a) jurisdiction, b) lastName, c) firstName
		JSONArray supervisorArray = Utils.createSupervisorArray(data);

		// 2. remove numeric jurisdictions
		JSONArray formattedArray = Utils.filterNumericValues(supervisorArray);

		// 3. sort by alphabetical, by jurisdiction, then lastName, then firstName
		JSONArray sortedArray = Utils.sortJsonArray("alphabetical", formattedArray);

		return sortedArray;
	}

	private static JSONArray createSupervisorArray(String data) {
		JSONArray jsonArray = new JSONArray(data);
		JSONArray formattedJsonArray = new JSONArray();

		for (int i = 0; i < jsonArray.length(); i++) {
			Map<String, String> map = new HashMap<String, String>();
			JSONObject item = jsonArray.getJSONObject(i);
			map.put("jurisdiction", item.getString("jurisdiction"));
			map.put("lastName", item.getString("lastName"));
			map.put("firstName", item.getString("firstName"));
			formattedJsonArray.put(map);
		}
		return formattedJsonArray;
	}

	private static boolean isNumeric(String stringOrNumeric) {
		if (stringOrNumeric == null) {
			return false;
		}
		try {
			int d = Integer.parseInt(stringOrNumeric);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	private static JSONArray filterNumericValues(JSONArray jsonArray) {
		JSONArray filteredArray = new JSONArray();

		for (int i = 0; i < jsonArray.length(); i++) {
			Map<String, String> map = new HashMap<String, String>();
			JSONObject item = jsonArray.getJSONObject(i);
			if (!isNumeric(jsonArray.getJSONObject(i).get("jurisdiction").toString())) {
				map.put("jurisdiction", item.getString("jurisdiction"));
				map.put("lastName", item.getString("lastName"));
				map.put("firstName", item.getString("firstName"));
				filteredArray.put(map);
			}
		}
		return filteredArray;
	}

	private static void compareStrings(List<JSONObject> jsonObjects) {
		Collections.sort(jsonObjects, new Comparator<JSONObject>() {
			@Override
			public int compare(JSONObject o1, JSONObject o2) {
				String jurisdictionA = new String();
				String jurisdictionB = new String();

				try {
					jurisdictionA = (String) o1.get(dataFields.jurisdiction.toString());
					jurisdictionB = (String) o2.get(dataFields.jurisdiction.toString());
				} catch (JSONException e) {
					System.out.println(
							"sort exception for jurisdictionA: " + jurisdictionA + ", jurisdictionB: " + jurisdictionB);
				}

				int sCompJurisdiction = jurisdictionA.compareTo(jurisdictionB);

				if (sCompJurisdiction != 0) {
					return sCompJurisdiction;
				}

				String lastNameA = new String();
				String lastNameB = new String();

				try {
					lastNameA = (String) o1.get(dataFields.lastName.toString());
					lastNameB = (String) o2.get(dataFields.lastName.toString());
				} catch (JSONException e) {
					System.out.println("sort exception for lastNameA: " + lastNameA + ", lastNameB: " + lastNameB);
				}

				int sCompLastName = lastNameA.compareTo(lastNameB);

				if (sCompLastName != 0) {
					return sCompLastName;
				}

				String firstNameA = new String();
				String firstNameB = new String();

				try {
					firstNameA = (String) o1.get(dataFields.firstName.toString());
					firstNameB = (String) o2.get(dataFields.firstName.toString());
				} catch (JSONException e) {
					System.out.println("sort exception for firstNameA: " + firstNameA + ", firstNameB: " + firstNameB);
				}

				return firstNameA.compareTo(firstNameB);
			}
		});
	}

	private static JSONArray sortJsonArray(String sortType, JSONArray data) {
		JSONArray sortedArray = new JSONArray();
		List<JSONObject> jsonObjects = new ArrayList<JSONObject>();

		for (int i = 0; i < data.length(); i++) {
			jsonObjects.add(data.getJSONObject(i));
		}

		if (sortType == sortOption.alphabetical.toString()) {
			compareStrings(jsonObjects);
			sortedArray = new JSONArray(jsonObjects);
		}
		return sortedArray;
	}

	public static boolean isDigit(String input) {
		for (char c : input.toCharArray()) {
			if (Character.isDigit(c)) {
				return true;
			}
		}
		return false;
	}
}
