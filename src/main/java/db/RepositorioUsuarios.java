package db;

import java.util.List;
import javax.persistence.TypedQuery;
//import org.apache.commons.lang3.tuple.ImmutablePair;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import domain.Administrador;
import domain.Cliente;
import domain.Usuario;

public class RepositorioUsuarios implements WithGlobalEntityManager {

	public static RepositorioUsuarios instancia = new RepositorioUsuarios();

	public Usuario buscarPorNombreUsuario(String nombreUsuario) {

		return (Usuario) entityManager().createQuery("select a from Usuario a").getResultList().stream()
				.filter(u -> ((Usuario) u).getNombreUsuario().equals(nombreUsuario)).findFirst().orElse(null);
	}
	
	public Cliente buscarClientePorNombreUsuario(String nombreUsuario) {
		return entityManager().createQuery("SELECT c FROM Cliente c WHERE nombreUsuario = :nombreUsuario", Cliente.class)
				.setParameter("nombreUsuario", nombreUsuario)
				.getSingleResult();
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