package controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;

import db.RepositorioDispositivos;
import db.RepositorioMediciones;
import db.RepositorioReglas;
import db.RepositorioUsuarios;
import db.SimplexRepositorio;
import domain.Cliente;
import domain.Dispositivo;
import domain.DispositivoEstandar;
import domain.DispositivoInteligente;
import domain.DispositivoMaestro;
import domain.Estados;
import domain.ImportadorJson;
import domain.Periodo;
import domain.RegistroSimplex;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.SessionHelper;

public class ClienteController {

	private RepositorioUsuarios repositorioUsuarios;
	private RepositorioDispositivos repositorioDispositivos;
	private RepositorioMediciones repositorioMediciones;
	private RepositorioReglas repositorioReglas;
	private SimplexRepositorio repositorioSimplex;

	public ClienteController(RepositorioUsuarios repositorioUsuarios, RepositorioDispositivos repositorioDispositivos,
			RepositorioMediciones repositorioMediciones, RepositorioReglas repositorioReglas,
			SimplexRepositorio repositorioSimplex) {
		this.repositorioUsuarios = repositorioUsuarios;
		this.repositorioDispositivos = repositorioDispositivos;
		this.repositorioMediciones = repositorioMediciones;
		this.repositorioReglas = repositorioReglas;
		this.repositorioSimplex = repositorioSimplex;
	}

	public ModelAndView dashboard(Request request, Response response) {
		SessionHelper.ensureUserIsLoggedIn(request, response);

		return new ModelAndView(null, "cliente/dashboard.hbs");
	}

	public ModelAndView formConsumoPeriodo(Request request, Response response) {
		SessionHelper.ensureUserIsLoggedIn(request, response);

		Map<String, Object> model = new HashMap<>();

		int userId = request.session().attribute("userId");
		Cliente cliente = repositorioUsuarios.buscarClientePorId(userId);

		model.put("dispositivos", cliente.getDispositivos());

		return new ModelAndView(model, "cliente/consulta-consumo-periodo.hbs");
	}

	public ModelAndView procesarConsumoPeriodo(Request request, Response response) {
		SessionHelper.ensureUserIsLoggedIn(request, response);

		String inicioParam = request.queryParams("inicio");
		String finParam = request.queryParams("fin");

		int userId = request.session().attribute("userId");
		Cliente cliente = repositorioUsuarios.buscarClientePorId(userId);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime inicio = LocalDateTime.parse(inicioParam, formatter);
		LocalDateTime fin = LocalDateTime.parse(finParam, formatter);

		Map<String, Object> model = new HashMap<>();
		double consumo = 0f;

		String dispositivoId = request.queryParams("dispositivo");
		if (dispositivoId.isEmpty()) {
			consumo = cliente.calcularConsumoEntrePeriodos(inicio, fin);
		} else {
			Dispositivo dispositivo = repositorioDispositivos.buscarDispositivoCliente(Integer.parseInt(dispositivoId));
			consumo = dispositivo.consumoTotalComprendidoEntre(inicio, fin);
			model.put("dispositivo", dispositivo);
		}

		model.put("periodo_inicio", inicioParam);
		model.put("periodo_fin", finParam);
		model.put("consumo", String.valueOf(consumo));

		return new ModelAndView(model, "cliente/consulta-consumo-periodo-resultado.hbs");
	}

	public ModelAndView formCargaArchivoDispositivos(Request request, Response response) {
		SessionHelper.ensureUserIsLoggedIn(request, response);

		Map<String, Object> model = new HashMap<>();
		List<DispositivoMaestro> dispositivos = repositorioDispositivos.listarMaestros();
		model.put("dispositivoMaestros", dispositivos);

		return new ModelAndView(model, "cliente/carga-archivo-dispositivos.hbs");
	}

	public Void procesarCargaArchivoDispositivos(Request request, Response response)
			throws IOException, ServletException {
		SessionHelper.ensureUserIsLoggedIn(request, response);

		File uploadDir = new File("upload");
		uploadDir.mkdir();

		Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");

		request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

		ImportadorJson importador;

		try (InputStream input = request.raw().getPart("dispositivos_file").getInputStream()) {
			Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);

			importador = ImportadorJson.desdeArchivo("upload/" + tempFile.getFileName().toString());
		}

		List<Map<String, Integer>> datos = importador.obtenerDatosDispositivos();

		for (Map<String, Integer> item : datos) {
			DispositivoMaestro maestro = repositorioDispositivos
					.buscarDispositivoMaestro(item.get("dispositivoMaestroId"));

			Dispositivo dispositivo;

			if (maestro.isEsInteligente()) {
				dispositivo = new DispositivoInteligente(maestro, Estados.APAGADO);
			} else {
				dispositivo = new DispositivoEstandar(maestro);
			}

			dispositivo.setUsoMensualMinimoHoras(item.get("usoMensualMinimoHoras"));
			dispositivo.setUsoMensualMaximoHoras(item.get("usoMensualMaximoHoras"));

			Cliente cliente = repositorioUsuarios
					.buscarClientePorNombreUsuario(request.session().attribute("currentUser"));

			dispositivo.setCliente(cliente);

			repositorioDispositivos.agregar(dispositivo);
			cliente.agregarDispositivo(dispositivo);

		}

		response.redirect("/cliente/dispositivos");
		return null;
	}

	public ModelAndView formSimplex(Request request, Response response) {
		SessionHelper.ensureUserIsLoggedIn(request, response);

		return new ModelAndView(null, "cliente/simplex.hbs");
	}

	public ModelAndView procesarSimplex(Request request, Response response) {
		SessionHelper.ensureUserIsLoggedIn(request, response);

		int userId = request.session().attribute("userId");
		Cliente cliente = repositorioUsuarios.buscarClientePorId(userId);

		Map<String, Object> model = new HashMap<>();

		cliente.calcularHogarEficiente();
		List dispositivos = new ArrayList(cliente.getDispositivos());
		model.put("dispositivosSimplex", dispositivos);

		repositorioSimplex.guardarRegistro(cliente);
		repositorioDispositivos.flush();

		calcularHogarEficiente(model, cliente);

		return new ModelAndView(model, "cliente/simplex-resultado.hbs");
	}

	public ModelAndView estadoHogar(Request request, Response response) {
		SessionHelper.ensureUserIsLoggedIn(request, response);

		Map<String, Object> model = new HashMap<>();

		int userId = request.session().attribute("userId");
		Cliente cliente = repositorioUsuarios.buscarClientePorId(userId);

		List med = repositorioMediciones.listarPorCliente(cliente);
		model.put("med", med);

		List<DispositivoInteligente> dispositivos = repositorioDispositivos
				.listarClienteInteligentes(cliente.getNombreUsuario());
		model.put("dispositivos", dispositivos);

		Periodo periodo = repositorioDispositivos.obtenerUltimoPeriodo(cliente);
		model.put("periodo", periodo);

		List reglas = repositorioReglas.listarPorCliente(cliente);
		model.put("reglas", reglas);

		calcularHogarEficiente(model, cliente);

		RegistroSimplex registro = repositorioSimplex.getRegistro(cliente);

		if (registro != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			model.put("fecha_simplex", registro.getFecha().format(formatter));
			model.put("simplex_ejecutado", true);
		} else {
			model.put("simplex_ejecutado", false);
		}

		return new ModelAndView(model, "cliente/estado-hogar.hbs");
	}

	public ModelAndView consumoUltimoMes(Request request, Response response) {
		SessionHelper.ensureUserIsLoggedIn(request, response);

		Map<String, Object> model = new HashMap<>();

		Calendar c = Calendar.getInstance();
		LocalDateTime fin = Dispositivo.toLocalDateTime(c);
		c.add(Calendar.MONTH, -1);
		LocalDateTime inicio = Dispositivo.toLocalDateTime(c);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		model.put("inicio", inicio.format(formatter));
		model.put("fin", fin.format(formatter));

		int userId = request.session().attribute("userId");
		Cliente cliente = repositorioUsuarios.buscarClientePorId(userId);
		model.put("dispositivos", cliente.getDispositivos());

		return new ModelAndView(model, "cliente/consulta-consumo-ultimo-mes.hbs");
	}

	private void calcularHogarEficiente(Map<String, Object> model, Cliente cliente) {
		Calendar c = Calendar.getInstance();
		LocalDateTime fin = Dispositivo.toLocalDateTime(c);
		LocalDateTime inicio = fin.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.of(0, 0));

		List lista = new ArrayList<>();
		boolean hogarEficiente = true;

		for (Dispositivo d : cliente.getDispositivos()) {
			int horasMes = d.horasTotalComprendidoEntre(inicio, fin);

			boolean eficiente = true;
			if (horasMes > d.getConsumoRecomendadoHoras()) {
				eficiente = false;
				hogarEficiente = false;
			}

			Map<String, Object> data = new HashMap<>();
			data.put("id", d.getId());
			data.put("categoria", d.getCategoria());
			data.put("nombre", d.getNombre());
			data.put("horas", horasMes);
			data.put("eficiente", eficiente);

			lista.add(data);
		}

		model.put("dispositivosHogarEficiente", lista);
		model.put("hogarEficiente", hogarEficiente);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		model.put("inicio", inicio.format(formatter));
		model.put("fin", fin.format(formatter));
	}
}