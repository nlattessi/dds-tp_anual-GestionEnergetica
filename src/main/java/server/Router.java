package server;

import controllers.AdministradorController;
import controllers.ClienteController;
import controllers.HomeController;
import controllers.LoginController;
//import controllers.ProyectosController;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;
import static spark.Spark.*;

public class Router {

	public static void configure() {
		HandlebarsTemplateEngine engine = HandlebarsTemplateEngineBuilder.create().withDefaultHelpers()
				.withHelper("isTrue", BooleanHelper.isTrue).build();

		Spark.staticFiles.location("/public");

//		ProyectosController proyectosController = new ProyectosController();

		Spark.get("/", HomeController::home, engine);
		Spark.get("/login", LoginController::show, engine);
		Spark.post("/login", LoginController::handleLoginPost, engine);
		Spark.get("/logout", LoginController::handleLogoutPost, engine);
		
		Spark.get("/administrador/dashboard", AdministradorController::dashboard, engine);
		
		Spark.get("/cliente/dashboard", ClienteController::dashboard, engine);
		
//		Spark.get("/proyectos", proyectosController::listar, engine);
//		Spark.get("/proyectos/new", proyectosController::nuevo, engine);
//		Spark.get("/proyectos/:id", proyectosController::mostrar, engine);
//		Spark.post("/proyectos", proyectosController::crear);
	}

}
