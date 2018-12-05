package db;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import domain.Actuador;
import domain.Cliente;
import domain.Dispositivo;
import domain.DispositivoEstandar;
import domain.DispositivoInteligente;
import domain.DispositivoMaestro;
import domain.Medicion;
import domain.Periodo;
import domain.Reglamentador;
import domain.Sensor;

public class RepositorioDispositivos {

	private EntityManager em;

	public RepositorioDispositivos(EntityManager manager) {
		this.em = manager;
	}

	public DispositivoMaestro buscarDispositivoMaestro(int id) {
		return em.find(DispositivoMaestro.class, id);
	}

	public void agregarMaestro(DispositivoMaestro dispositivo) {
		em.getTransaction().begin();
		em.persist(dispositivo);
		em.getTransaction().commit();
	}

	public List<DispositivoMaestro> listarMaestros() {
		return em.createQuery("SELECT dm FROM DispositivoMaestro dm", DispositivoMaestro.class).getResultList();
	}

	public List<Dispositivo> listarCliente(String nombreUsuario) {
		return em.createQuery("SELECT d FROM Dispositivo d JOIN d.cliente c WHERE c.nombreUsuario = :nombreUsuario",
				Dispositivo.class).setParameter("nombreUsuario", nombreUsuario).getResultList();
	}

	public List<DispositivoInteligente> listarClienteInteligentes(String nombreUsuario) {
		return em.createQuery(
				"SELECT d FROM DispositivoInteligente d JOIN d.cliente c WHERE c.nombreUsuario = :nombreUsuario",
				DispositivoInteligente.class).setParameter("nombreUsuario", nombreUsuario).getResultList();
	}

	public Dispositivo buscarDispositivoCliente(int id) {
		return em.find(Dispositivo.class, id);
	}

	public DispositivoInteligente buscarDispositivoInteligente(int id) {
		return em.find(DispositivoInteligente.class, id);
	}

	public void agregar(Dispositivo dispositivo) {
		if (dispositivo instanceof DispositivoInteligente) {
			Actuador actuador = new Actuador((DispositivoInteligente) dispositivo);
			Reglamentador reglamentador = new Reglamentador(actuador);
			Sensor sensor = new Sensor();
			sensor.registerReglamentador(reglamentador);

			em.getTransaction().begin();
			em.persist(sensor);
			em.persist(reglamentador);
			em.persist(actuador);
			em.persist(dispositivo);
			em.flush();
			em.getTransaction().commit();
		} else {
			em.getTransaction().begin();
			em.persist(dispositivo);
			em.flush();
			em.getTransaction().commit();
		}

		Dispositivo managedDispositivo = em.find(Dispositivo.class, dispositivo.getId());
		em.refresh(managedDispositivo);
	}

	public void borrarDispositivoCliente(int id) {
		Dispositivo dispositivo = em.find(Dispositivo.class, id);
		if (dispositivo != null) {
			em.getTransaction().begin();
			em.remove(dispositivo);
			em.getTransaction().commit();
		}
	}

	public List<Dispositivo> listarEstandar() {
		return em.createQuery("SELECT de FROM DispositivoEstandar de", Dispositivo.class).getResultList();
	}

	public List<Dispositivo> listarInteligente() {
		return em.createQuery("SELECT di FROM DispositivoInteligente di", Dispositivo.class).getResultList();
	}

	public Periodo obtenerUltimoPeriodo(Cliente cliente) {
		Query query = em.createQuery(
				"SELECT p FROM Periodo p INNER JOIN p.dispositivo d WHERE d.cliente = :cliente ORDER BY p.id DESC",
				Periodo.class);
		query.setParameter("cliente", cliente);
		query.setMaxResults(1);

		List res = query.getResultList();

		return (res.size() == 1) ? (Periodo) res.get(0) : null;
	}

	public void guardarPeriodo(Periodo periodo) {
		em.getTransaction().begin();
		em.persist(periodo);
		em.getTransaction().commit();
	}

	public Sensor buscarSensorPorId(int id) {
		Sensor sensor = em.find(Sensor.class, id);
		return sensor;
	}

	public void guardarMedicion(Medicion m) {
		em.getTransaction().begin();
		em.persist(m);
		em.getTransaction().commit();
	}

	public void actualizar(Dispositivo d) {
		em.getTransaction().begin();
		em.persist(d);
		em.getTransaction().commit();
	}

	public void flush() {
		em.getTransaction().begin();
		em.flush();
		em.getTransaction().commit();
	}

	public void encender(int id) {
		Dispositivo d = em.find(Dispositivo.class, id);
		if (d != null) {
			em.getTransaction().begin();
			d.encenderse();
			em.persist(d);
			em.getTransaction().commit();
		}
	}

	public void apagar(int id) {
		Dispositivo d = em.find(Dispositivo.class, id);
		if (d != null) {
			em.getTransaction().begin();
			d.apagarse();
			em.persist(d);
			em.getTransaction().commit();
		}
	}

}