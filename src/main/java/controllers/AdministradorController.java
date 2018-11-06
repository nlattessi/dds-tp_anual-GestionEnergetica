package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class AdministradorController {
	public static ModelAndView dashboard(Request request, Response response) {
	    LoginController.ensureUserIsLoggedIn(request, response);
		return new ModelAndView(null, "administrador/dashboard.hbs");
	}
}