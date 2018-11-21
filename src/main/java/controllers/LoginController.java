package controllers;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import db.RepositorioUsuarios;
import domain.Administrador;
import domain.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class LoginController {

	private RepositorioUsuarios repositorioUsuarios;

	public LoginController(RepositorioUsuarios repositorioUsuarios) {
		this.repositorioUsuarios = repositorioUsuarios;
	}

	public ModelAndView show(Request req, Response res) {
		return new ModelAndView(null, "home/login.hbs");
	}

	public ModelAndView handleLoginPost(Request req, Response res) {
		Map<String, Object> model = new HashMap<>();

		String nombreUsuario = req.queryParams("inputNombreUsuario");
		String password = req.queryParams("inputPassword");

		if (nombreUsuario.isEmpty() || password.isEmpty()) {
			model.put("authenticationFailed", true);
			return new ModelAndView(model, "home/login.hbs");
		}

		Usuario usuario = repositorioUsuarios.buscarPorNombreUsuario(nombreUsuario);

//		Usuario usuario = RepositorioUsuarios.instancia.buscarPorNombreUsuario(nombreUsuario);

		if (usuario == null) {
			model.put("authenticationFailed", true);
			return new ModelAndView(model, "home/login.hbs");
		}

		if (!usuario.getContraseña().equals(password)) {
			model.put("authenticationFailed", true);
			return new ModelAndView(model, "home/login.hbs");
		}

		req.session().attribute("currentUser", nombreUsuario);
		req.session().attribute("userId", usuario.getId());

		if (usuario instanceof Administrador) {
			res.redirect("/administrador/dashboard");
		}

		res.redirect("/cliente/dashboard");

		return null;
	}

	public ModelAndView handleLogoutPost(Request request, Response response) {
		request.session().removeAttribute("currentUser");
		request.session().removeAttribute("userId");
		request.session().attribute("loggedOut", true);
		response.redirect("/login");
		return null;
	};
}