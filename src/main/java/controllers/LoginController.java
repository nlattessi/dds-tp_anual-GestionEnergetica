package controllers;

import java.util.HashMap;
import java.util.Map;

import db.RepositorioUsuarios;
import domain.Administrador;
import domain.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginController {
	public static ModelAndView show(Request req, Response res) {
		return new ModelAndView(null, "home/login.hbs");
	}

	public static ModelAndView handleLoginPost(Request req, Response res) {
		Map<String, Object> model = new HashMap<>();

		String nombreUsuario = req.queryParams("inputNombreUsuario");
		String password = req.queryParams("inputPassword");

		if (nombreUsuario.isEmpty() || password.isEmpty()) {
			model.put("authenticationFailed", true);
			return new ModelAndView(model, "home/login.hbs");
		}

		Usuario usuario = RepositorioUsuarios.instancia.buscarPorNombreUsuario(nombreUsuario);

		if (usuario == null) {
			model.put("authenticationFailed", true);
			return new ModelAndView(model, "home/login.hbs");
		}
		
		if (!usuario.getContraseña().equals(password)) {
			model.put("authenticationFailed", true);
			return new ModelAndView(model, "home/login.hbs");
		}

		req.session().attribute("currentUser", nombreUsuario);

		if (usuario instanceof Administrador) {
			res.redirect("/administrador/dashboard");
		}

		res.redirect("/cliente/dashboard");

		return null;
	}

	public static ModelAndView handleLogoutPost(Request request, Response response) {
		request.session().removeAttribute("currentUser");
		request.session().attribute("loggedOut", true);
		response.redirect("/login");
		return null;
	};
}