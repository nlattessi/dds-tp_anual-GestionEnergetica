package db;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import domain.Transformador;

public class RepositorioTransformadores {

	private EntityManager em;

	public RepositorioTransformadores(EntityManager manager) {
		this.em = manager;
	}

	public List<Transformador> listar() {
		return em.createQuery("SELECT t FROM Transformador t", Transformador.class).getResultList();
	}

	public Transformador buscar(int id) {
		return em.find(Transformador.class, id);
	}

	public List<Map<String, String>> consumoPromedioPorPeriodo(LocalDateTime inicio, LocalDateTime fin) {
		List<Map<String, String>> reporte = new ArrayList<>();

		List<Transformador> transformadores = listar();

		for (Transformador transformador : transformadores) {
			Map<String, String> item = new HashMap<>();
			item.put("id", String.valueOf(transformador.getId()));
			item.put("latitud", String.valueOf(transformador.getLatitud()));
			item.put("longitud", String.valueOf(transformador.getLongitud()));
			item.put("cantidadClientesConectados", String.valueOf(transformador.getClientesConectados().size()));
			item.put("consumo", String.valueOf(transformador.consumoPromedioEntre(inicio, fin)));

			reporte.add(item);
		}

		return reporte;
	}
}
