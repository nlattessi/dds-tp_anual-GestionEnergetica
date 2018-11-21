package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import domain.Cliente;
import domain.Medicion;
import domain.Periodo;

public class RepositorioMediciones {
	private EntityManager manager;

	public RepositorioMediciones(EntityManager manager) {
		this.manager = manager;
	}

	@SuppressWarnings("unchecked")
	public List listarPorCliente(Cliente cliente) {

		String id = String.valueOf(cliente.getId());
		String sqlQuery = "SELECT DISTINCT m.* FROM medicion m INNER JOIN sensor s ON m.sensor_id = s.id INNER JOIN reglamentador r ON r.sensor_id = s.id INNER JOIN actuador a ON a.id = r.actuador_id\n"
				+ "INNER JOIN dispositivo d WHERE d.cliente_id = " + id + " ORDER BY m.id DESC";

		Query q = manager.createNativeQuery(sqlQuery, Medicion.class);
		q.setMaxResults(5);

		return q.getResultList();

	}
}

//public Periodo obtenerUltimoPeriodo(Cliente cliente) {
////	EntityManager em = emf.createEntityManager();
//	Query query = em.createQuery(
//			"SELECT p FROM Periodo p INNER JOIN p.dispositivo d WHERE d.cliente = :cliente ORDER BY p.id DESC",
//			Periodo.class);
//	query.setParameter("cliente", cliente);
//	query.setMaxResults(1);
//
//	List res = query.getResultList();
//
//	return (res.size() == 1) ? (Periodo) res.get(0) : null;
//
////	return (Periodo) query.getResultList().get(0);
//}