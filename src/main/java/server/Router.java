package server;

import controllers.AdministradorController;
import controllers.ClienteController;
import controllers.DispositivoController;
import controllers.HomeController;
import controllers.LoginController;
import controllers.MedicionController;
import db.RepositorioDispositivos;
import db.RepositorioUsuarios;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;
import spark.utils.StringHelper;

import static spark.Spark.*;

public class Router {

	public static void configure() {
		HandlebarsTemplateEngine engine = HandlebarsTemplateEngineBuilder.create().withDefaultHelpers()
				.withHelper("isTrue", BooleanHelper.isTrue)
				.withHelper("isDispositivoInteligente", StringHelper.isDispositivoInteligente).build();

		Spark.staticFiles.location("/public");

		RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios();
		RepositorioDispositivos repositorioDispositivos = new RepositorioDispositivos();

		LoginController loginController = new LoginController(repositorioUsuarios);
		DispositivoController dispositivoController = new DispositivoController(repositorioDispositivos,
				repositorioUsuarios);
		AdministradorController administradorController = new AdministradorController(repositorioUsuarios,
				repositorioDispositivos);

		Spark.get("/", HomeController::home, engine);
		Spark.get("/login", loginController::show, engine);
		Spark.post("/login", loginController::handleLoginPost, engine);
		Spark.get("/logout", loginController::handleLogoutPost, engine);

		Spark.get("/administrador/dashboard", administradorController::dashboard, engine);

		Spark.get("/administrador/hogares_y_consumos", administradorController::listarHogaresYConsumos, engine);
		Spark.get("/administrador/hogares_y_consumos/:id", administradorController::verConsumos, engine);

		Spark.get("/administrador/dispositivos", dispositivoController::listarDispositivosMaestros, engine);
		Spark.get("/administrador/dispositivos/nuevo", dispositivoController::nuevoDispositivoMaestro, engine);
		Spark.post("/administrador/dispositivos", dispositivoController::crearDispositivoMaestro);

		Spark.get("/cliente/dashboard", ClienteController::dashboard, engine);
		Spark.get("/cliente/dispositivos", dispositivoController::listarDispositivosCliente, engine);
		Spark.get("/cliente/dispositivos/nuevo", dispositivoController::nuevoDispositivoCliente, engine);
		Spark.post("/cliente/dispositivos", dispositivoController::crearDispositivoCliente);
		Spark.post("/cliente/dispositivos/:id/borrar", dispositivoController::borrarDispositivoCliente);
//		Spark.get("/cliente/dispositivos/:id/editar", dispositivoController::editarDispositivoCliente, engine);
//		Spark.get("/cliente/dispositivos/:id/actualizar", dispositivoController::actualizarDispositivoCliente);

		Spark.post("/medicion", MedicionController::tomarMedicion);
	}

}
