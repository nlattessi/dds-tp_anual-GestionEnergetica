package db;

import java.util.List;

import javax.persistence.EntityManager;
import domain.Cliente;
import domain.Usuario;

public class RepositorioUsuarios {

	private EntityManager em;

	public RepositorioUsuarios(EntityManager manager) {
		this.em = manager;
	}

	public Usuario buscarPorNombreUsuario(String nombreUsuario) {
		return (Usuario) em.createQuery("select u from Usuario u where nombreUsuario = :nombreUsuario")
				.setParameter("nombreUsuario", nombreUsuario).getSingleResult();
	}

	public Cliente buscarClientePorNombreUsuario(String nombreUsuario) {
		return em.createQuery("SELECT c FROM Cliente c WHERE nombreUsuario = :nombreUsuario", Cliente.class)
				.setParameter("nombreUsuario", nombreUsuario).getSingleResult();
	}

	public Cliente buscarClientePorId(int id) {
		return em.find(Cliente.class, id);
	}

	public List<Cliente> listarClientes() {
		return em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
	}

}
