package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.SessionHelper;

public class ClienteController {
	public static ModelAndView dashboard(Request request, Response response) {
		SessionHelper.ensureUserIsLoggedIn(request, response);
		
		return new ModelAndView(null, "cliente/dashboard.hbs");
	}
}