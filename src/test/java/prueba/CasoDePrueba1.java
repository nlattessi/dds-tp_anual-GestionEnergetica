package prueba;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import domain.Estados;
import models.ModelHelper;
import models.entities.EntidadUsuario;

public class CasoDePrueba1 {
	
	private ModelHelper model;
	
	@Before
	public void init() {
		this.model = new ModelHelper();
	}
	
	@Test
	public void testCrearUsuario() {
		EntidadUsuario usuario = new EntidadUsuario();
		usuario.setNombreUsuario("juanPerez");
		this.model.agregar(usuario);
		
		EntidadUsuario usuarioAgregado = this.model.buscar(EntidadUsuario.class, usuario.getId());
		
		Assert.assertTrue(usuarioAgregado.getNombreUsuario() == usuario.getNombreUsuario());
	}
}
