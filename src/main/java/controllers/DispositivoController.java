package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import spark.utils.SessionHelper;

public class DispositivoController {

	private RepositorioDispositivos repositorioDispositivos;
	private RepositorioUsuarios repositorioUsuarios;

	public DispositivoController(RepositorioDispositivos repositorioDispositivos,
			RepositorioUsuarios repositorioUsuarios) {
		this.repositorioDispositivos = repositorioDispositivos;
		this.repositorioUsuarios = repositorioUsuarios;
	}

	public ModelAndView listarDispositivosMaestros(Request request, Response response) {
		SessionHelper.ensureUserIsLoggedIn(request, response);

		Map<String, List<DispositivoMaestro>> model = new HashMap<>();
		List<DispositivoMaestro> dispositivos = this.repositorioDispositivos.listarMaestros();

		model.put("dispositivos", dispositivos);
		return new ModelAndView(model, "administrador/listar_dispositivos_maestros.hbs");
	}

	public ModelAndView nuevoDispositivoMaestro(Request request, Response response) {
		SessionHelper.ensureUserIsLoggedIn(request, response);

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

		this.repositorioDispositivos.agregarMaestro(dispositivo);

		response.redirect("/administrador/dispositivos");
		return null;
	}

	public ModelAndView listarDispositivosCliente(Request request, Response response) {
		SessionHelper.ensureUserIsLoggedIn(request, response);

		Map<String, List<Dispositivo>> model = new HashMap<>();

		List<Dispositivo> dispositivos = repositorioDispositivos
				.listarCliente(request.session().attribute("currentUser"));
		model.put("dispositivos", dispositivos);

		return new ModelAndView(model, "cliente/listar_dispositivos_cliente.hbs");
	}

	public ModelAndView nuevoDispositivoCliente(Request request, Response response) {
		SessionHelper.ensureUserIsLoggedIn(request, response);

		Map<String, Object> model = new HashMap<>();
		List<DispositivoMaestro> dispositivos = repositorioDispositivos.listarMaestros();
		model.put("dispositivoMaestros", dispositivos);
//		model.put("estadosDispositivo", Estados.values());

		return new ModelAndView(model, "cliente/nuevo_dispositivo_cliente.hbs");
	}

	public Void crearDispositivoCliente(Request request, Response response) {
		String maestroParam = request.queryParams("maestro");
		int maestroId = Integer.parseInt(maestroParam);
		DispositivoMaestro maestro = repositorioDispositivos.buscarDispositivoMaestro(maestroId);

		String usoMensualMinimoHorasParam = request.queryParams("usoMensualMinimoHoras");
		int usoMensualMinimoHoras = Integer.parseInt(usoMensualMinimoHorasParam);

		String usoMensualMaximoHorasParam = request.queryParams("usoMensualMaximoHoras");
		int usoMensualMaximoHoras = Integer.parseInt(usoMensualMaximoHorasParam);

		Dispositivo dispositivo;

		if (maestro.isEsInteligente()) {
			dispositivo = new DispositivoInteligente(maestro, Estados.APAGADO);
		} else {
			dispositivo = new DispositivoEstandar(maestro);
		}

		dispositivo.setUsoMensualMinimoHoras(usoMensualMinimoHoras);
		dispositivo.setUsoMensualMaximoHoras(usoMensualMaximoHoras);

		Cliente cliente = repositorioUsuarios.buscarClientePorNombreUsuario(request.session().attribute("currentUser"));

		dispositivo.setCliente(cliente);

		repositorioDispositivos.agregar(dispositivo);
		cliente.agregarDispositivo(dispositivo);

		response.redirect("/cliente/dispositivos");
		return null;
	}

	public Void borrarDispositivoCliente(Request request, Response response) {
		String id = request.params("id");

		repositorioDispositivos.borrarDispositivoCliente(Integer.parseInt(id));

		response.redirect("/cliente/dispositivos");
		return null;
	}
}
