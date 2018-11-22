package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import db.RepositorioTransformadores;
import domain.Transformador;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController {

	private RepositorioTransformadores repositorioTransformadores;

	public HomeController(RepositorioTransformadores repositorioTransformadores) {
		this.repositorioTransformadores = repositorioTransformadores;
	}

	public ModelAndView home(Request req, Response res) {
		return new ModelAndView(null, "home/home.hbs");
	}

	public ModelAndView mostrarMapa(Request request, Response response) {
		Map<String, List<Transformador>> model = new HashMap<>();
		List<Transformador> transformadores = repositorioTransformadores.listar();

		model.put("transformadores", transformadores);

		return new ModelAndView(model, "home/mapa.hbs");
	}

	public Object verConsumoTransformador(Request request, Response response) {
		String id = request.params("id");

		Transformador transformador = repositorioTransformadores.buscar(Integer.parseInt(id));

		JSONObject obj = new JSONObject();
		obj.put("consumo", transformador.cantidadDeEnergiaSuministrada());

		response.type("application/json");
		response.status(200);

		return obj;
	}
}