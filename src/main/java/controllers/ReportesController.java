package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.RepositorioDispositivos;
import db.RepositorioReportes;
import db.RepositorioTransformadores;
import db.RepositorioUsuarios;
import domain.ReporteConsumoHogarPeriodo;
import domain.ReporteConsumoTipoDispositivoPeriodo;
import domain.ReporteConsumoTransformadorPeriodo;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.SessionHelper;

public class ReportesController {

	private RepositorioUsuarios repositorioUsuarios;
	private RepositorioDispositivos repositorioDispositivos;
	private RepositorioTransformadores repositorioTransformadores;
	private RepositorioReportes repositorioReportes;

	public ReportesController(RepositorioUsuarios repositorioUsuarios, RepositorioDispositivos repositorioDispositivos,
			RepositorioTransformadores repositorioTransformadores, RepositorioReportes repositorioReportes) {
		this.repositorioUsuarios = repositorioUsuarios;
		this.repositorioDispositivos = repositorioDispositivos;
		this.repositorioTransformadores = repositorioTransformadores;
		this.repositorioReportes = repositorioReportes;
	}

	public ModelAndView formConsumoHogarPeriodo(Request request, Response response) {
		SessionHelper.ensureUserIsLoggedIn(request, response);

		List clientes = repositorioUsuarios.listarClientes();

		Map<String, List> model = new HashMap<>();
		model.put("clientes", clientes);

		return new ModelAndView(model, "administrador/reportes/consumo-hogar-periodo.hbs");
	}

	public ModelAndView procesarConsumoHogarPeriodo(Request request, Response response) {
		SessionHelper.ensureUserIsLoggedIn(request, response);

		String clienteIdParam = request.queryParams("cliente");
		String inicioParam = request.queryParams("inicio");
		String finParam = request.queryParams("fin");

		ReporteConsumoHogarPeriodo reporte = repositorioReportes.obtenerReporteConsumoHogarPeriodo(clienteIdParam,
				inicioParam, finParam);

		Map<String, String> model = new HashMap<>();
		model.put("cliente_nombre_apellido", reporte.getNombreYApellido());
		model.put("periodo_inicio", reporte.getInicio());
		model.put("periodo_fin", reporte.getFin());
		model.put("consumo", String.valueOf(reporte.getConsumo()));

		return new ModelAndView(model, "administrador/reportes/consumo-hogar-periodo-resultado.hbs");
	}

	public ModelAndView formConsumoTipoDispositivoPeriodo(Request request, Response response) {
		SessionHelper.ensureUserIsLoggedIn(request, response);

		return new ModelAndView(null, "administrador/reportes/consumo-tipo-dispositivo-periodo.hbs");
	}

	public ModelAndView procesarConsumoTipoDispositivoPeriodo(Request request, Response response) {
		SessionHelper.ensureUserIsLoggedIn(request, response);

		String tipoDispositivoParam = request.queryParams("tipo_dispositivo");
		String inicioParam = request.queryParams("inicio");
		String finParam = request.queryParams("fin");

		ReporteConsumoTipoDispositivoPeriodo reporte = repositorioReportes
				.obtenerReporteConsumoTipoDispositivoPeriodo(tipoDispositivoParam, inicioParam, finParam);

		Map<String, String> model = new HashMap<>();
		model.put("tipo_dispositivo", reporte.getTipoDispositivo());
		model.put("cantidad_dispositivos", String.valueOf(reporte.getCantidadDispositivos()));
		model.put("periodo_inicio", reporte.getInicio());
		model.put("periodo_fin", reporte.getFin());
		model.put("consumo_promedio", String.valueOf(reporte.getConsumoPromedio()));

		return new ModelAndView(model, "administrador/reportes/consumo-tipo-dispositivo-periodo-resultado.hbs");
	}

	public ModelAndView formConsumoTransformadorPeriodo(Request request, Response response) {
		SessionHelper.ensureUserIsLoggedIn(request, response);

		return new ModelAndView(null, "administrador/reportes/consumo-transformador-periodo.hbs");
	}

	public ModelAndView procesarConsumoTransformadorPeriodo(Request request, Response response) {
		SessionHelper.ensureUserIsLoggedIn(request, response);

		String inicioParam = request.queryParams("inicio");
		String finParam = request.queryParams("fin");

		ReporteConsumoTransformadorPeriodo reporte = repositorioReportes
				.obtenerReporteConsumoTransformadorPeriodo(inicioParam, finParam);

		Map<String, Object> model = new HashMap<>();
		model.put("transformadores", reporte.getTransformadores());
		model.put("periodo_inicio", reporte.getInicio());
		model.put("periodo_fin", reporte.getFin());

		return new ModelAndView(model, "administrador/reportes/consumo-transformador-periodo-resultado.hbs");
	}

}
