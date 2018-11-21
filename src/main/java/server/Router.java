package server;

import controllers.AdministradorController;
import controllers.ClienteController;
import controllers.DispositivoController;
import controllers.HomeController;
import controllers.LoginController;
import controllers.MedicionController;
import controllers.ReglaController;
import controllers.ReportesController;
import controllers.ToolsController;
import db.RepositorioDispositivos;
import db.RepositorioMediciones;
import db.RepositorioReglas;
import db.RepositorioTransformadores;
import db.RepositorioUsuarios;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;
import spark.utils.StringHelper;

import static spark.Spark.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Router {

	public static void configure() {
		HandlebarsTemplateEngine engine = HandlebarsTemplateEngineBuilder.create().withDefaultHelpers()
				.withHelper("isTrue", BooleanHelper.isTrue)
				.withHelper("isDispositivoInteligente", StringHelper.isDispositivoInteligente).build();

		Spark.staticFiles.location("/public");

		final String PERSISTENCE_UNIT_NAME = "db";
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager manager = factory.createEntityManager();

		RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios(manager);
		RepositorioDispositivos repositorioDispositivos = new RepositorioDispositivos(manager);
		RepositorioTransformadores repositorioTransformadores = new RepositorioTransformadores(manager);
		RepositorioReglas repositorioReglas = new RepositorioReglas(manager);
		RepositorioMediciones repositorioMediciones = new RepositorioMediciones(manager);

		HomeController homeController = new HomeController(repositorioTransformadores);
		LoginController loginController = new LoginController(repositorioUsuarios);
		DispositivoController dispositivoController = new DispositivoController(repositorioDispositivos,
				repositorioUsuarios);
		AdministradorController administradorController = new AdministradorController(repositorioUsuarios,
				repositorioDispositivos);
		ReportesController reportesController = new ReportesController(repositorioUsuarios, repositorioDispositivos,
				repositorioTransformadores);
		ClienteController clienteController = new ClienteController(repositorioUsuarios, repositorioDispositivos,
				repositorioMediciones, repositorioReglas);
		ReglaController reglaController = new ReglaController(repositorioUsuarios, repositorioReglas,
				repositorioDispositivos);
		ToolsController toolsController = new ToolsController(repositorioUsuarios, repositorioDispositivos,
				repositorioTransformadores, repositorioReglas, repositorioMediciones);

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

		Spark.get("/cliente/dashboard", clienteController::dashboard, engine);

		Spark.get("/cliente/dispositivos", dispositivoController::listarDispositivosCliente, engine);
		Spark.get("/cliente/dispositivos/nuevo", dispositivoController::nuevoDispositivoCliente, engine);
		Spark.post("/cliente/dispositivos", dispositivoController::crearDispositivoCliente);
		Spark.post("/cliente/dispositivos/:id/borrar", dispositivoController::borrarDispositivoCliente);

		Spark.get("/cliente/consulta-consumo-periodo", clienteController::formConsumoPeriodo, engine);
		Spark.post("/cliente/consulta-consumo-periodo", clienteController::procesarConsumoPeriodo, engine);

		Spark.get("/cliente/carga-archivo-dispositivos", clienteController::formCargaArchivoDispositivos, engine);
		Spark.post("/cliente/carga-archivo-dispositivos", clienteController::procesarCargaArchivoDispositivos);

		Spark.get("/cliente/simplex", clienteController::formSimplex, engine);
		Spark.post("/cliente/simplex", clienteController::procesarSimplex, engine);

		Spark.get("/cliente/reglas", reglaController::listarReglas, engine);
		Spark.get("/cliente/reglas/nueva", reglaController::nuevaRegla, engine);
		Spark.post("/cliente/reglas", reglaController::crearRegla);
		Spark.post("/cliente/reglas/:id/borrar", reglaController::borrarRegla);

		Spark.get("/cliente/estado-hogar", clienteController::estadoHogar, engine);

		Spark.post("/medicion", MedicionController::tomarMedicion);

		Spark.post("/tools/periodo", toolsController::cargarPeriodo);
		Spark.post("/tools/medicion", toolsController::cargarMedicion);

	}

}
