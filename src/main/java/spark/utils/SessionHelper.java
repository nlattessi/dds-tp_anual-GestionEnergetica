package spark.utils;

import spark.Request;
import spark.Response;

public class SessionHelper {
	public static void ensureUserIsLoggedIn(Request request, Response response) {
		if (request.session().attribute("currentUser") == null) {
			request.session().attribute("loginRedirect", request.pathInfo());
			response.redirect("/login");
		}
	};
}
