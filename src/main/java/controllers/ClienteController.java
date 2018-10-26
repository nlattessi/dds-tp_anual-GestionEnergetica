package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ClienteController {
	public static ModelAndView dashboard(Request req, Response res) {
		return new ModelAndView(null, "cliente/dashboard.hbs");
	}
}