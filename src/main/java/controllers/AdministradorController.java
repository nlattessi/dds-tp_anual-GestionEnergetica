package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.RepositorioDispositivos;
import db.RepositorioUsuarios;
import domain.Cliente;
import domain.Dispositivo;
import domain.DispositivoMaestro;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.SessionHelper;

public class AdministradorController {

	private RepositorioUsuarios repositorioUsuarios;
	private RepositorioDispositivos repositorioDispositivos;

	public AdministradorController(RepositorioUsuarios repositorioUsuarios,
			RepositorioDispositivos repositorioDispositivos) {
		this.repositorioUsuarios = repositorioUsuarios;
		this.repositorioDispositivos = repositorioDispositivos;
	}

	public ModelAndView dashboard(Request request, Response response) {
		SessionHelper.ensureUserIsLoggedIn(request, response);

		return new ModelAndView(null, "administrador/dashboard.hbs");
	}

	public ModelAndView listarHogaresYConsumos(Request request, Response response) {
//		SessionHelper.ensureUserIsLoggedIn(request, response);

		Map<String, List<Cliente>> model = new HashMap<>();
		List<Cliente> clientes = repositorioUsuarios.listarClientes();

		model.put("clientes", clientes);

		return new ModelAndView(model, "administrador/hogares_y_consumos.hbs");
	}

	public ModelAndView verConsumos(Request request, Response response) {
//		SessionHelper.ensureUserIsLoggedIn(request, response);

		String id = request.params("id");

		Map<String, Dispositivo> model = new HashMap<>();
//		List<Cliente> clientes = repositorioUsuarios.listarClientes();


		Dispositivo dispositivo = repositorioDispositivos.buscarDispositivoCliente(Integer.parseInt(id));
		model.put("dispositivo", dispositivo);

		return new ModelAndView(model, "administrador/ver_consumos.hbs");
	}

}