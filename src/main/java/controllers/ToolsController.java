package controllers;

import db.RepositorioDispositivos;
import db.RepositorioMediciones;
import db.RepositorioReglas;
import db.RepositorioTransformadores;
import db.RepositorioUsuarios;
import domain.Dispositivo;
import domain.Medicion;
import domain.Periodo;
import domain.Reglamentador;
import domain.Sensor;
import java.time.LocalDateTime;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import spark.Request;
import spark.Response;

public class ToolsController {

	private RepositorioDispositivos repositorioDispositivos;

	public ToolsController(RepositorioUsuarios repositorioUsuarios, RepositorioDispositivos repositorioDispositivos,
			RepositorioTransformadores repositorioTransformadores, RepositorioReglas repositorioReglas,
			RepositorioMediciones repositorioMediciones) {
		this.repositorioDispositivos = repositorioDispositivos;
	}

	public Object cargarPeriodo(Request request, Response response) {

		JSONParser parser = new JSONParser();

		try {
			Object obj = parser.parse(request.body());

			JSONObject jsonObject = (JSONObject) obj;

			int dispositivoId = (int) (long) jsonObject.get("dispositivo_id");
			int inicioAño = (int) (long) jsonObject.get("inicio_año");
			int inicioMes = (int) (long) jsonObject.get("inicio_mes");
			int inicioDia = (int) (long) jsonObject.get("inicio_dia");
			int inicioHora = (int) (long) jsonObject.get("inicio_hora");
			int inicioMinuto = (int) (long) jsonObject.get("inicio_minuto");
			int finAño = (int) (long) jsonObject.get("fin_año");
			int finMes = (int) (long) jsonObject.get("fin_mes");
			int finDia = (int) (long) jsonObject.get("fin_dia");
			int finHora = (int) (long) jsonObject.get("fin_hora");
			int finMinuto = (int) (long) jsonObject.get("fin_minuto");

			Dispositivo dispositivo = repositorioDispositivos.buscarDispositivoCliente(dispositivoId);
			LocalDateTime inicio = LocalDateTime.of(inicioAño, inicioMes, inicioDia, inicioHora, inicioMinuto);
			LocalDateTime fin = LocalDateTime.of(finAño, finMes, finDia, finHora, finMinuto);
			Periodo periodo = new Periodo(inicio, fin);
			dispositivo.getPeriodos().add(periodo);
			periodo.setDispositivo(dispositivo);
			repositorioDispositivos.guardarPeriodo(periodo);

		} catch (ParseException e) {
			response.type("application/json");
			response.status(500);

			JSONObject obj = new JSONObject();
			obj.put("status", "Error");

			return obj;
		}

		response.type("application/json");
		response.status(200);

		JSONObject obj = new JSONObject();
		obj.put("status", "Success");

		return obj;
	}

	public Object cargarMedicion(Request request, Response response) {

		JSONParser parser = new JSONParser();

		try {
			Object obj = parser.parse(request.body());

			JSONObject jsonObject = (JSONObject) obj;

			int sensorId = (int) (long) jsonObject.get("sensor_id");
			String magnitud = (String) jsonObject.get("magnitud");
			int valor = (int) (long) jsonObject.get("valor");

			Sensor sensor = repositorioDispositivos.buscarSensorPorId(sensorId);
			Medicion medicion = new Medicion();
			medicion.setMagnitud(magnitud);
			medicion.setValor(valor);
			medicion.setSensor(sensor);
			repositorioDispositivos.guardarMedicion(medicion);
			sensor.agregarMedicion(medicion);

			for (Reglamentador r : sensor.getReglamentadores()) {
				repositorioDispositivos.actualizar(r.getActuador().getDispositivo());
			}

		} catch (ParseException e) {
			response.type("application/json");
			response.status(500);

			JSONObject obj = new JSONObject();
			obj.put("status", "Error");

			return obj;
		}

		response.type("application/json");
		response.status(200);

		JSONObject obj = new JSONObject();
		obj.put("status", "Success");

		return obj;
	}
}
