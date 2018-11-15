package db;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import domain.Dispositivo;
import domain.Transformador;

public class RepositorioTransformadores {
	private EntityManagerFactory emf;

	public RepositorioTransformadores() {
		emf = Persistence.createEntityManagerFactory("db");
	}

	public List<Transformador> listar() {
		EntityManager em = emf.createEntityManager();

		List<Transformador> lista = em.createQuery("SELECT t FROM Transformador t", Transformador.class)
				.getResultList();

		em.close();

		return lista;
	}

	public Transformador buscar(int id) {
		EntityManager em = emf.createEntityManager();

		Transformador t = em.find(Transformador.class, id);

		em.close();

		return t;
	}
	
	public List<Map<String, String>> consumoPromedioPorPeriodo(LocalDateTime inicio, LocalDateTime fin) {		
		List<Map<String, String>> reporte = new ArrayList<>();
		
		List<Transformador> transformadores = listar();
		
		for (Transformador transformador: transformadores) {	
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
