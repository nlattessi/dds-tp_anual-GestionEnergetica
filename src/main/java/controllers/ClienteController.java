package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ClienteController {
	public static ModelAndView dashboard(Request request, Response response) {
		LoginController.ensureUserIsLoggedIn(request, response);
		return new ModelAndView(null, "cliente/dashboard.hbs");
	}
}