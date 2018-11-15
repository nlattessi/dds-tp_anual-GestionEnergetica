package server;

import controllers.AdministradorController;
import controllers.ClienteController;
import controllers.DispositivoController;
import controllers.HomeController;
import controllers.LoginController;
import controllers.MedicionController;
import controllers.ReportesController;
import db.RepositorioDispositivos;
import db.RepositorioTransformadores;
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
		RepositorioTransformadores repositorioTransformadores = new RepositorioTransformadores();

		HomeController homeController = new HomeController(repositorioTransformadores);
		LoginController loginController = new LoginController(repositorioUsuarios);
		DispositivoController dispositivoController = new DispositivoController(repositorioDispositivos,
				repositorioUsuarios);
		AdministradorController administradorController = new AdministradorController(repositorioUsuarios,
				repositorioDispositivos);
		ReportesController reportesController = new ReportesController(repositorioUsuarios, repositorioDispositivos, repositorioTransformadores);

		Spark.get("/", homeController::home, engine);

		Spark.get("/mapa", homeController::mostrarMapa, engine);
		Spark.get("/mapa/transformador/:id/consumo", homeController::verConsumoTransformador);

		Spark.get("/login", loginController::show, engine);
		Spark.post("/login", loginController::handleLoginPost, engine);
		Spark.get("/logout", loginController::handleLogoutPost, engine);

		Spark.get("/administrador/dashboard", administradorController::dashboard, engine);

		Spark.get("/administrador/hogares-consumos", administradorController::listarHogaresYConsumos, engine);
		Spark.get("/administrador/hogares-consumos/:id", administradorController::verConsumos, engine);

		Spark.get("/administrador/reportes/consumo-hogar-periodo", reportesController::formConsumoHogarPeriodo, engine);
		Spark.post("/administrador/reportes/consumo-hogar-periodo", reportesController::procesarConsumoHogarPeriodo,
				engine);
		Spark.get("/administrador/reportes/consumo-tipo-dispositivo-periodo",
				reportesController::formConsumoTipoDispositivoPeriodo, engine);
		Spark.post("/administrador/reportes/consumo-tipo-dispositivo-periodo",
				reportesController::procesarConsumoTipoDispositivoPeriodo, engine);
		Spark.get("/administrador/reportes/consumo-transformador-periodo",
				reportesController::formConsumoTransformadorPeriodo, engine);
		Spark.post("/administrador/reportes/consumo-transformador-periodo",
				reportesController::procesarConsumoTransformadorPeriodo, engine);

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
