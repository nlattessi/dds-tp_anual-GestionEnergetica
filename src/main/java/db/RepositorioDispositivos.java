package db;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import domain.Acciones;
import domain.Actuador;
import domain.Cliente;
import domain.ComparacionesReglaGenerica;
import domain.Dispositivo;
import domain.DispositivoEstandar;
import domain.DispositivoInteligente;
import domain.DispositivoMaestro;
import domain.Medicion;
import domain.Periodo;
import domain.ReglaGenerica;
import domain.Reglamentador;
import domain.Sensor;
import domain.Usuario;

public class RepositorioDispositivos {

//	private EntityManagerFactory emf;
//
//	public RepositorioDispositivos() {
//		emf = Persistence.createEntityManagerFactory("db");
//	}

	private EntityManager em;

	public RepositorioDispositivos(EntityManager manager) {
		this.em = manager;
	}

//	public static RepositorioDispositivos instancia = new RepositorioDispositivos();

	public DispositivoMaestro buscarDispositivoMaestro(int id) {
//		EntityManager em = emf.createEntityManager();

		DispositivoMaestro dispositivoMaestro = null;

		dispositivoMaestro = em.find(DispositivoMaestro.class, id);

//		em.close();

		return dispositivoMaestro;
	}

	public void agregarMaestro(DispositivoMaestro dispositivo) {
//		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		em.persist(dispositivo);

		em.getTransaction().commit();

//		em.close();
	}

	public List<DispositivoMaestro> listarMaestros() {
//		EntityManager em = emf.createEntityManager();

		List<DispositivoMaestro> lista = em
				.createQuery("SELECT dm FROM DispositivoMaestro dm", DispositivoMaestro.class).getResultList();

//		em.close();

		return lista;
	}

	public List<Dispositivo> listarCliente(String nombreUsuario) {
//		Cliente cliente = RepositorioUsuarios.instancia.buscarClientePorNombreUsuario(nombreUsuario);

//		return cliente.getDispositivos();

//		EntityManager em = emf.createEntityManager();

		List<Dispositivo> lista = em
				.createQuery("SELECT d FROM Dispositivo d JOIN d.cliente c WHERE c.nombreUsuario = :nombreUsuario",
						Dispositivo.class)
				.setParameter("nombreUsuario", nombreUsuario).getResultList();

//		em.close();

		return lista;
	}

	public List<DispositivoInteligente> listarClienteInteligentes(String nombreUsuario) {
//		Cliente cliente = RepositorioUsuarios.instancia.buscarClientePorNombreUsuario(nombreUsuario);

//		return cliente.getDispositivos();

//		EntityManager em = emf.createEntityManager();

		List<DispositivoInteligente> lista = em.createQuery(
				"SELECT d FROM DispositivoInteligente d JOIN d.cliente c WHERE c.nombreUsuario = :nombreUsuario",
				DispositivoInteligente.class).setParameter("nombreUsuario", nombreUsuario).getResultList();

//		em.close();

		return lista;
	}

	public Dispositivo buscarDispositivoCliente(int id) {
//		EntityManager em = emf.createEntityManager();

		Dispositivo dispositivo = em.find(Dispositivo.class, id);

//		em.close();

		return dispositivo;
	}

	public DispositivoInteligente buscarDispositivoInteligente(int id) {
//		EntityManager em = emf.createEntityManager();

		DispositivoInteligente dispositivo = em.find(DispositivoInteligente.class, id);

//		em.close();

		return dispositivo;
	}

	public void agregar(Dispositivo dispositivo) {
//		EntityManager em = emf.createEntityManager();

//		em.getTransaction().begin();

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
			em.getTransaction().commit();
		} else {
			em.getTransaction().begin();
			em.persist(dispositivo);
			em.getTransaction().commit();
		}

//		em.getTransaction().commit();

//		em.close();
	}

//	public void borrarDispositivoCliente(int id) {
////		EntityManager em = emf.createEntityManager();
//
//		em.getTransaction().begin();
//
//		Dispositivo dispositivo = em.find(Dispositivo.class, id);
//
//		em.remove(dispositivo);
//
//		em.getTransaction().commit();
//
////		em.close();
//	}

	public void borrarDispositivoCliente(int id) {
		Dispositivo dispositivo = em.find(Dispositivo.class, id);
		if (dispositivo != null) {
			em.getTransaction().begin();
//			Query query = manager.createQuery("DELETE FROM Regla rg WHERE rg = :regla");
//			query.setParameter("regla", regla);
//			query.executeUpdate();

//			Actuador a = ((DispositivoInteligente) dispositivo).getActuador();
//			Reglamentador r = a.getReglamentador();
//			em.remove(r);
//			em.remove(a);
			em.remove(dispositivo);
			em.getTransaction().commit();
		}
	}

	public Map<String, String> consumoPromedioPorTipoPorPeriodo(boolean esInteligente, LocalDateTime inicio,
			LocalDateTime fin) {
		Map<String, String> reporte = new HashMap<>();

		List<Dispositivo> dispositivos = (esInteligente) ? listarInteligente() : listarEstandar();

		double consumoTotal = 0;
		for (Dispositivo dispositivo : dispositivos) {
			consumoTotal = consumoTotal + dispositivo.consumoTotalComprendidoEntre(inicio, fin);
		}
//		return consumoTotal;

		double consumoPromedio = (consumoTotal == 0) ? 0 : (consumoTotal / dispositivos.size());

		reporte.put("cantidad_dispositivos", String.valueOf(dispositivos.size()));
		reporte.put("consumo_promedio", String.valueOf(consumoPromedio));

		return reporte;
	}

	public List<Dispositivo> listarEstandar() {
//		EntityManager em = emf.createEntityManager();

		List lista = em.createQuery("SELECT de FROM DispositivoEstandar de", DispositivoEstandar.class).getResultList();

//		em.close();

		return lista;
	}

	public List<Dispositivo> listarInteligente() {
//		EntityManager em = emf.createEntityManager();

		List lista = em.createQuery("SELECT di FROM DispositivoInteligente di", DispositivoInteligente.class)
				.getResultList();

//		em.close();

		return lista;
	}

	public Periodo obtenerUltimoPeriodo(Cliente cliente) {
//		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery(
				"SELECT p FROM Periodo p INNER JOIN p.dispositivo d WHERE d.cliente = :cliente ORDER BY p.id DESC",
				Periodo.class);
		query.setParameter("cliente", cliente);
		query.setMaxResults(1);

		List res = query.getResultList();

		return (res.size() == 1) ? (Periodo) res.get(0) : null;

//		return (Periodo) query.getResultList().get(0);
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

}