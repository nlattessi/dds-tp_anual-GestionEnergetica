package db;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import domain.Actuador;
import domain.Cliente;
import domain.Dispositivo;
import domain.DispositivoInteligente;
import domain.DispositivoMaestro;
import domain.Reglamentador;
import domain.Sensor;
import domain.Usuario;

public class RepositorioDispositivos implements WithGlobalEntityManager {

	public static RepositorioDispositivos instancia = new RepositorioDispositivos();

	public DispositivoMaestro buscarDispositivoMaestro(int id) {
		return entityManager().find(DispositivoMaestro.class, id);
	}

	public void agregarMaestro(DispositivoMaestro dispositivo) {
		entityManager().persist(dispositivo);
	}

	public List<DispositivoMaestro> listarMaestros() {
		return entityManager().createQuery("from DispositivoMaestro", DispositivoMaestro.class).getResultList();
	}

	public List<Dispositivo> listarCliente(String nombreUsuario) {
//		Cliente cliente = RepositorioUsuarios.instancia.buscarClientePorNombreUsuario(nombreUsuario);

//		return cliente.getDispositivos();

		return entityManager()
				.createQuery("SELECT d FROM Dispositivo d JOIN d.cliente c WHERE c.nombreUsuario = :nombreUsuario",
						Dispositivo.class)
				.setParameter("nombreUsuario", nombreUsuario).getResultList();
	}

	public Dispositivo buscarDispositivoCliente(int id) {
		return entityManager().find(Dispositivo.class, id);
	}

	public void agregar(Dispositivo dispositivo) {
		if (dispositivo instanceof DispositivoInteligente) {
			Actuador actuador = new Actuador((DispositivoInteligente) dispositivo);
			Reglamentador reglamentador = new Reglamentador(actuador);
			Sensor sensor = new Sensor();
			sensor.registerReglamentador(reglamentador);

			entityManager().persist(dispositivo);
			entityManager().persist(actuador);
			entityManager().persist(reglamentador);
			entityManager().persist(sensor);
		} else {
			entityManager().persist(dispositivo);
		}

	}

	public void borrarDispositivoCliente(int id) {
		Dispositivo dispositivo = buscarDispositivoCliente(id);
		entityManager().remove(dispositivo);
	}
}