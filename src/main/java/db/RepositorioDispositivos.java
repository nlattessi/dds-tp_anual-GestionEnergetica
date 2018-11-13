package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import domain.Actuador;
import domain.Cliente;
import domain.Dispositivo;
import domain.DispositivoInteligente;
import domain.DispositivoMaestro;
import domain.Reglamentador;
import domain.Sensor;
import domain.Usuario;

public class RepositorioDispositivos {

	private EntityManagerFactory emf;

	public RepositorioDispositivos() {
		emf = Persistence.createEntityManagerFactory("db");
	}

//	public static RepositorioDispositivos instancia = new RepositorioDispositivos();

	public DispositivoMaestro buscarDispositivoMaestro(int id) {
		EntityManager em = emf.createEntityManager();

		DispositivoMaestro dispositivoMaestro = null;

		dispositivoMaestro = em.find(DispositivoMaestro.class, id);

		em.close();

		return dispositivoMaestro;
	}

	public void agregarMaestro(DispositivoMaestro dispositivo) {
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		em.persist(dispositivo);

		em.getTransaction().commit();

		em.close();
	}

	public List<DispositivoMaestro> listarMaestros() {
		EntityManager em = emf.createEntityManager();

		List<DispositivoMaestro> lista = em
				.createQuery("SELECT dm FROM DispositivoMaestro dm", DispositivoMaestro.class).getResultList();

		em.close();

		return lista;
	}

	public List<Dispositivo> listarCliente(String nombreUsuario) {
//		Cliente cliente = RepositorioUsuarios.instancia.buscarClientePorNombreUsuario(nombreUsuario);

//		return cliente.getDispositivos();

		EntityManager em = emf.createEntityManager();

		List<Dispositivo> lista = em
				.createQuery("SELECT d FROM Dispositivo d JOIN d.cliente c WHERE c.nombreUsuario = :nombreUsuario",
						Dispositivo.class)
				.setParameter("nombreUsuario", nombreUsuario).getResultList();

		em.close();

		return lista;
	}

	public Dispositivo buscarDispositivoCliente(int id) {
		EntityManager em = emf.createEntityManager();

		Dispositivo dispositivo = em.find(Dispositivo.class, id);

		em.close();

		return dispositivo;
	}

	public void agregar(Dispositivo dispositivo) {
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		if (dispositivo instanceof DispositivoInteligente) {
			Actuador actuador = new Actuador((DispositivoInteligente) dispositivo);
			Reglamentador reglamentador = new Reglamentador(actuador);
			Sensor sensor = new Sensor();
			sensor.registerReglamentador(reglamentador);

			em.persist(dispositivo);
			em.persist(actuador);
			em.persist(reglamentador);
			em.persist(sensor);
		} else {
			em.persist(dispositivo);
		}

		em.getTransaction().commit();

		em.close();

	}

	public void borrarDispositivoCliente(int id) {
		Dispositivo dispositivo = buscarDispositivoCliente(id);
	}
}