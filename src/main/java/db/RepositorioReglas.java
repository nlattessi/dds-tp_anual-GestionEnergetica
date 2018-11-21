package db;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import domain.Acciones;
import domain.Cliente;
import domain.ComparacionesReglaGenerica;
import domain.DispositivoInteligente;
import domain.ReglaGenerica;
import domain.Reglamentador;

public class RepositorioReglas {
	private EntityManager manager;

	public RepositorioReglas(EntityManager manager) {
		this.manager = manager;
	}

	@SuppressWarnings("unchecked")
	public List listarPorCliente(Cliente cliente) {
		Query query = manager.createQuery(
				"SELECT rg FROM Regla rg INNER JOIN rg.reglamentador r INNER JOIN r.actuador a INNER JOIN a.dispositivo d WHERE d.cliente = :cliente");
		query.setParameter("cliente", cliente);
		return query.getResultList();
	}

	public void borrar(ReglaGenerica regla) {
		manager.getTransaction().begin();
		manager.remove(regla);
		manager.getTransaction().commit();
	}

	public void borrarPorId(int id) {
		ReglaGenerica regla = manager.find(ReglaGenerica.class, id);
		if (regla != null) {
			manager.getTransaction().begin();
//			Query query = manager.createQuery("DELETE FROM Regla rg WHERE rg = :regla");
//			query.setParameter("regla", regla);
//			query.executeUpdate();
			
			regla.getReglamentador().removeRegla(regla);
			
			manager.remove(regla);
			manager.getTransaction().commit();
		}
	}

	public void crear(DispositivoInteligente dispositivo, String nombreMagnitud, ComparacionesReglaGenerica comparacion,
			int valor, Acciones accion) {
		ReglaGenerica regla = new ReglaGenerica();
		regla.setNombreMagnitud(nombreMagnitud);
		regla.setComparacion(comparacion);
		regla.setValor(valor);
		regla.setAccion(accion);

		Reglamentador reglamentador = dispositivo.getActuador().getReglamentador();

		regla.setReglamentador(reglamentador);
		reglamentador.agregarReglaGenerica(regla);

		manager.getTransaction().begin();
		manager.persist(regla);
		manager.getTransaction().commit();
	}
}
