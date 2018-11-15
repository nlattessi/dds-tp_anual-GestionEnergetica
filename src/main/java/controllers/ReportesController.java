package controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.RepositorioDispositivos;
import db.RepositorioTransformadores;
import db.RepositorioUsuarios;
import domain.CategoriaDispositivo;
import domain.Cliente;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.SessionHelper;

public class ReportesController {

	private RepositorioUsuarios repositorioUsuarios;
	private RepositorioDispositivos repositorioDispositivos;
	private RepositorioTransformadores repositorioTransformadores;

	public ReportesController(RepositorioUsuarios repositorioUsuarios, RepositorioDispositivos repositorioDispositivos,
			RepositorioTransformadores repositorioTransformadores) {
		this.repositorioUsuarios = repositorioUsuarios;
		this.repositorioDispositivos = repositorioDispositivos;
		this.repositorioTransformadores = repositorioTransformadores;
	}

	public ModelAndView formConsumoHogarPeriodo(Request request, Response response) {
//		SessionHelper.ensureUserIsLoggedIn(request, response);

		List clientes = repositorioUsuarios.listarClientes();

		Map<String, List> model = new HashMap<>();
		model.put("clientes", clientes);

		return new ModelAndView(model, "administrador/reportes/consumo-hogar-periodo.hbs");
	}

	public ModelAndView procesarConsumoHogarPeriodo(Request request, Response response) {
//		SessionHelper.ensureUserIsLoggedIn(request, response);

		String clienteIdParam = request.queryParams("cliente");
		String inicioParam = request.queryParams("inicio");
		String finParam = request.queryParams("fin");

		Cliente cliente = repositorioUsuarios.buscarClientePorId(Integer.parseInt(clienteIdParam));

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime inicio = LocalDateTime.parse(inicioParam, formatter);
		LocalDateTime fin = LocalDateTime.parse(finParam, formatter);

		double consumo = cliente.calcularConsumoEntrePeriodos(inicio, fin);

		Map<String, String> model = new HashMap<>();
		model.put("cliente_nombre_apellido", cliente.getNombreYApellido());
		model.put("periodo_inicio", inicioParam);
		model.put("periodo_fin", finParam);
		model.put("consumo", String.valueOf(consumo));

		return new ModelAndView(model, "administrador/reportes/consumo-hogar-periodo-resultado.hbs");
	}

	public ModelAndView formConsumoTipoDispositivoPeriodo(Request request, Response response) {
//		SessionHelper.ensureUserIsLoggedIn(request, response);

		return new ModelAndView(null, "administrador/reportes/consumo-tipo-dispositivo-periodo.hbs");
	}

	public ModelAndView procesarConsumoTipoDispositivoPeriodo(Request request, Response response) {
//		SessionHelper.ensureUserIsLoggedIn(request, response);

		String tipoDispositivoParam = request.queryParams("tipo_dispositivo");
		String inicioParam = request.queryParams("inicio");
		String finParam = request.queryParams("fin");

		boolean esInteligente = tipoDispositivoParam.equals("1");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime inicio = LocalDateTime.parse(inicioParam, formatter);
		LocalDateTime fin = LocalDateTime.parse(finParam, formatter);

		Map<String, String> reporte = repositorioDispositivos.consumoPromedioPorTipoPorPeriodo(esInteligente, inicio,
				fin);
		String tipoDispositivo = (esInteligente) ? "Inteligente" : "Estandar";
		reporte.put("tipo_dispositivo", tipoDispositivo);
		reporte.put("periodo_inicio", inicioParam);
		reporte.put("periodo_fin", finParam);

		return new ModelAndView(reporte, "administrador/reportes/consumo-tipo-dispositivo-periodo-resultado.hbs");
	}

	public ModelAndView formConsumoTransformadorPeriodo(Request request, Response response) {
//		SessionHelper.ensureUserIsLoggedIn(request, response);

		return new ModelAndView(null, "administrador/reportes/consumo-transformador-periodo.hbs");
	}

	public ModelAndView procesarConsumoTransformadorPeriodo(Request request, Response response) {
//		SessionHelper.ensureUserIsLoggedIn(request, response);

		String inicioParam = request.queryParams("inicio");
		String finParam = request.queryParams("fin");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime inicio = LocalDateTime.parse(inicioParam, formatter);
		LocalDateTime fin = LocalDateTime.parse(finParam, formatter);
		
		Map<String, Object> reporte = new HashMap<>();

		List<Map<String, String>> transformadores = repositorioTransformadores.consumoPromedioPorPeriodo(inicio, fin);
		reporte.put("transformadores", transformadores);
		reporte.put("periodo_inicio", inicioParam);
		reporte.put("periodo_fin", finParam);

		return new ModelAndView(reporte, "administrador/reportes/consumo-transformador-periodo-resultado.hbs");
	}

}
