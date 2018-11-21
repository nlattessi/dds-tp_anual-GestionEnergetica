package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import db.RepositorioDispositivos;
import db.RepositorioReglas;
import db.RepositorioUsuarios;
import domain.Acciones;
import domain.Actuador;
import domain.CategoriaDispositivo;
import domain.Cliente;
import domain.ComparacionesReglaGenerica;
import domain.Dispositivo;
import domain.DispositivoEstandar;
import domain.DispositivoInteligente;
import domain.DispositivoMaestro;
import domain.Estados;
import domain.ReglaGenerica;
import domain.Reglamentador;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.SessionHelper;

public class ReglaController {

	private RepositorioUsuarios repositorioUsuarios;
	private RepositorioReglas repositorioReglas;
	private RepositorioDispositivos repositorioDispositivos;

	public ReglaController(RepositorioUsuarios repositorioUsuarios, RepositorioReglas repositorioReglas,
			RepositorioDispositivos repositorioDispositivos) {
		this.repositorioUsuarios = repositorioUsuarios;
		this.repositorioReglas = repositorioReglas;
		this.repositorioDispositivos = repositorioDispositivos;
	}

	public ModelAndView listarReglas(Request request, Response response) {
//		SessionHelper.ensureUserIsLoggedIn(request, response);

		Map<String, List<ReglaGenerica>> model = new HashMap<>();

		int userId = request.session().attribute("userId");
		Cliente cliente = repositorioUsuarios.buscarClientePorId(userId);

		List reglas = repositorioReglas.listarPorCliente(cliente);

		model.put("reglas", reglas);

		return new ModelAndView(model, "cliente/listar-reglas-cliente.hbs");
	}

	public ModelAndView nuevaRegla(Request request, Response response) {
//		SessionHelper.ensureUserIsLoggedIn(request, response);

		Map<String, Object> model = new HashMap<>();
		model.put("comparaciones", ComparacionesReglaGenerica.values());
		model.put("acciones", Acciones.values());

		List<DispositivoInteligente> dispositivos = repositorioDispositivos
				.listarClienteInteligentes(request.session().attribute("currentUser"));
		model.put("dispositivos", dispositivos);

		return new ModelAndView(model, "cliente/nueva-regla-cliente.hbs");
	}

	public Void crearRegla(Request request, Response response) {

		String dispositivoParam = request.queryParams("dispositivo");
		int dispositivoId = Integer.parseInt(dispositivoParam);
		DispositivoInteligente dispositivo = repositorioDispositivos.buscarDispositivoInteligente(dispositivoId);

		String nombreMagnitud = request.queryParams("nombreMagnitud");

		String comparacionParam = request.queryParams("comparacion");
		ComparacionesReglaGenerica comparacion = ComparacionesReglaGenerica.valueOf(comparacionParam);

		String valorParam = request.queryParams("valor");
		int valor = Integer.parseInt(valorParam);

		String accionParam = request.queryParams("accion");
		Acciones accion = Acciones.valueOf(accionParam);

		repositorioReglas.crear(dispositivo, nombreMagnitud, comparacion, valor, accion);

		response.redirect("/cliente/reglas");
		return null;

//		String maestroParam = request.queryParams("maestro");
//		int maestroId = Integer.parseInt(maestroParam);
////		DispositivoMaestro maestro = repositorioDispositivos.buscarDispositivoMaestro(maestroId);
//
////		String estadoParam = request.queryParams("estado");
////		Estados estado = Estados.valueOf(estadoParam);
//
//		String usoMensualMinimoHorasParam = request.queryParams("usoMensualMinimoHoras");
//		int usoMensualMinimoHoras = Integer.parseInt(usoMensualMinimoHorasParam);
//
//		String usoMensualMaximoHorasParam = request.queryParams("usoMensualMaximoHoras");
//		int usoMensualMaximoHoras = Integer.parseInt(usoMensualMaximoHorasParam);
//
////		Dispositivo dispositivo;

//		if (maestro.isEsInteligente()) {
//			dispositivo = new DispositivoInteligente(maestro, Estados.APAGADO);
//		} else {
//			dispositivo = new DispositivoEstandar(maestro);
//		}

//		dispositivo.setUsoMensualMinimoHoras(usoMensualMinimoHoras);
//		dispositivo.setUsoMensualMaximoHoras(usoMensualMaximoHoras);
//		dispositivo.setCategoria(maestro.getCategoria());
//		dispositivo.setBajoConsumo(maestro.isEsBajoConsumo());

//		Cliente cliente = repositorioUsuarios.buscarClientePorNombreUsuario(request.session().attribute("currentUser"));
//
////		dispositivo.setCliente(cliente);
//
////		withTransaction(() -> {
////		repositorioDispositivos.agregar(dispositivo);
////		cliente.agregarDispositivo(dispositivo);
////		});
//
//		response.redirect("/cliente/dispositivos");
//		return null;
	}

	public Void borrarRegla(Request request, Response response) {
		String id = request.params("id");

//		withTransaction(() -> {
//		repositorioDispositivos.borrarDispositivoCliente(Integer.parseInt(id));
//		});

		repositorioReglas.borrarPorId(Integer.parseInt(id));

		response.redirect("/cliente/reglas");
		return null;
	}
}
