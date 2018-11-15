package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
//import org.apache.commons.lang3.tuple.ImmutablePair;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import domain.Administrador;
import domain.Cliente;
import domain.DispositivoMaestro;
import domain.Usuario;
import models.ModelHelper;

//public class RepositorioUsuarios implements WithGlobalEntityManager {
public class RepositorioUsuarios {

	private EntityManagerFactory emf;

	public RepositorioUsuarios() {
		emf = Persistence.createEntityManagerFactory("db");
	}

//	public static RepositorioUsuarios instancia = new RepositorioUsuarios();

	public Usuario buscarPorNombreUsuario(String nombreUsuario) {

		EntityManager em = emf.createEntityManager();

		Usuario usuario = null;

		usuario = (Usuario) em.createQuery("select u from Usuario u where nombreUsuario = :nombreUsuario")
				.setParameter("nombreUsuario", nombreUsuario).getSingleResult();

		em.close();

		return usuario;

//		return (Usuario) entityManager().createQuery("select a from Usuario a").getResultList().stream()
//				.filter(u -> ((Usuario) u).getNombreUsuario().equals(nombreUsuario)).findFirst().orElse(null);
	}

	public Cliente buscarClientePorNombreUsuario(String nombreUsuario) {
		EntityManager em = emf.createEntityManager();

		Cliente cliente = em.createQuery("SELECT c FROM Cliente c WHERE nombreUsuario = :nombreUsuario", Cliente.class)
				.setParameter("nombreUsuario", nombreUsuario).getSingleResult();

		em.close();

		return cliente;
	}

	public Cliente buscarClientePorId(int id) {
		EntityManager em = emf.createEntityManager();

		Cliente cliente = em.find(Cliente.class, id);

		em.close();

		return cliente;
	}

	public List<Cliente> listarClientes() {
		EntityManager em = emf.createEntityManager();

		List<Cliente> lista = em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();

		em.close();

		return lista;
	}

}

//	createQuery("SELECT c FROM Customer c WHERE c.name LIKE :custName").setParameter("custName",name).setMaxResults(10).getResultList();}
//
//	public <T> T buscar(Class<T> clase, ImmutablePair<Object, Object>... pair) {
//		TypedQuery<T> query = this.generarQueryPara(clase, pair);
//		List<T> resultados = query.getResultList();
//		return resultados.get(query.getFirstResult());
//	}
//
//public <T> List<T> buscarTodos(Class<T> clase) {
//	List<T> resultList = (List<T>) this.entityManager().createQuery("from "+clase.getName(), clase).getResultList();
//	return resultList;
//}