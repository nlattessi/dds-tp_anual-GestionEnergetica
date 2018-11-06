package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import db.RepositorioDispositivos;
import db.RepositorioUsuarios;
import domain.CategoriaDispositivo;
import domain.Cliente;
import domain.Dispositivo;
import domain.DispositivoEstandar;
import domain.DispositivoInteligente;
import domain.DispositivoMaestro;
import domain.Estados;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class DispositivoController implements WithGlobalEntityManager, TransactionalOps {

	public ModelAndView listarDispositivosMaestros(Request request, Response response) {
		LoginController.ensureUserIsLoggedIn(request, response);

		Map<String, List<DispositivoMaestro>> model = new HashMap<>();
		List<DispositivoMaestro> dispositivos = RepositorioDispositivos.instancia.listarMaestros();

		model.put("dispositivos", dispositivos);
		return new ModelAndView(model, "administrador/listar_dispositivos_maestros.hbs");
	}

	public ModelAndView nuevoDispositivoMaestro(Request request, Response response) {
		LoginController.ensureUserIsLoggedIn(request, response);

		Map<String, Object> model = new HashMap<>();
		model.put("categoriaDispositivo", CategoriaDispositivo.values());

		return new ModelAndView(model, "administrador/nuevo_dispositivo_maestro.hbs");
	}

	public Void crearDispositivoMaestro(Request request, Response response) {
		String categoriaParam = request.queryParams("categoria");
		CategoriaDispositivo categoria = CategoriaDispositivo.valueOf(categoriaParam);

		String nombre = request.queryParams("nombre");

		String esInteligenteParam = request.queryParams("esInteligente");
		Boolean esInteligente = (esInteligenteParam != null) ? true : false;

		String esBajoConsumoParam = request.queryParams("esBajoConsumo");
		Boolean esBajoConsumo = (esBajoConsumoParam != null) ? true : false;

		String consumoParam = request.queryParams("consumo");
		Double consumo = Double.parseDouble(consumoParam);

		DispositivoMaestro dispositivo = new DispositivoMaestro(categoria, nombre, esInteligente, esBajoConsumo,
				consumo);

		withTransaction(() -> {
			RepositorioDispositivos.instancia.agregarMaestro(dispositivo);
		});

		response.redirect("/administrador/dispositivos");
		return null;
	}

	public ModelAndView listarDispositivosCliente(Request request, Response response) {
		LoginController.ensureUserIsLoggedIn(request, response);

		Map<String, List<Dispositivo>> model = new HashMap<>();
		withTransaction(() -> {
			List<Dispositivo> dispositivos = RepositorioDispositivos.instancia
					.listarCliente(request.session().attribute("currentUser"));
			model.put("dispositivos", dispositivos);
		});

		return new ModelAndView(model, "cliente/listar_dispositivos_cliente.hbs");
	}

	public ModelAndView nuevoDispositivoCliente(Request request, Response response) {
		LoginController.ensureUserIsLoggedIn(request, response);

		Map<String, Object> model = new HashMap<>();
		List<DispositivoMaestro> dispositivos = RepositorioDispositivos.instancia.listarMaestros();
		model.put("dispositivoMaestros", dispositivos);
//		model.put("estadosDispositivo", Estados.values());

		return new ModelAndView(model, "cliente/nuevo_dispositivo_cliente.hbs");
	}

	public Void crearDispositivoCliente(Request request, Response response) {
		String maestroParam = request.queryParams("maestro");
		int maestroId = Integer.parseInt(maestroParam);
		DispositivoMaestro maestro = RepositorioDispositivos.instancia.buscarDispositivoMaestro(maestroId);

//		String estadoParam = request.queryParams("estado");
//		Estados estado = Estados.valueOf(estadoParam);

		String usoMensualMinimoHorasParam = request.queryParams("usoMensualMinimoHoras");
		int usoMensualMinimoHoras = Integer.parseInt(usoMensualMinimoHorasParam);

		String usoMensualMaximoHorasParam = request.queryParams("usoMensualMaximoHoras");
		int usoMensualMaximoHoras = Integer.parseInt(usoMensualMaximoHorasParam);

		Dispositivo dispositivo;

		if (maestro.isEsInteligente()) {
			dispositivo = new DispositivoInteligente(maestro.getNombre(), maestro.getConsumo(), Estados.APAGADO);
		} else {
			dispositivo = new DispositivoEstandar(maestro.getNombre(), maestro.getConsumo());
		}

		dispositivo.setUsoMensualMinimoHoras(usoMensualMinimoHoras);
		dispositivo.setUsoMensualMaximoHoras(usoMensualMaximoHoras);
		dispositivo.setCategoria(maestro.getCategoria());
		dispositivo.setBajoConsumo(maestro.isEsBajoConsumo());

		Cliente cliente = RepositorioUsuarios.instancia
				.buscarClientePorNombreUsuario(request.session().attribute("currentUser"));

		dispositivo.setCliente(cliente);
		
		withTransaction(() -> {
			RepositorioDispositivos.instancia.agregar(dispositivo);
			cliente.agregarDispositivo(dispositivo);
		});

		response.redirect("/cliente/dispositivos");
		return null;
	}

	public Void borrarDispositivoCliente(Request request, Response response) {
		String id = request.params("id");

		withTransaction(() -> {
			RepositorioDispositivos.instancia.borrarDispositivoCliente(Integer.parseInt(id));
		});

		response.redirect("/cliente/dispositivos");
		return null;
	}
}
