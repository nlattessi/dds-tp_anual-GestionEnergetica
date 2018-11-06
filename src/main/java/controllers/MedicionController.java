package controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import spark.Request;
import spark.Response;

public class MedicionController {

	public static Object tomarMedicion(Request request, Response response) {

		JSONParser parser = new JSONParser();
		Object jsonBody = null;
		try {
			jsonBody = parser.parse(request.body());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			response.type("application/json");
			response.status(500);

			JSONObject obj = new JSONObject();
			obj.put("status", "Error");

			return obj;
		}

		response.type("application/json");
		response.status(200);

		JSONObject obj = new JSONObject();
		obj.put("status", "Success");

		return obj;
	}

}