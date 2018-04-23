package prueba;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import domain.Administrador;
import domain.Categoria;
import domain.Cliente;
import domain.Dispositivo;
import domain.ImportadorJson;

public class ImportadorJsonTest {

	@Test
	public void carga_archivo_correctamente() {

		ImportadorJson importadorJson = ImportadorJson.desdeArchivo("entidades_de_prueba.json");

		List<Categoria> categorias = importadorJson.importarCategorias();
		List<Cliente> clientes = importadorJson.importarClientes();
		List<Administrador> administradores = importadorJson.importarAdministradores();
		List<Dispositivo> dispositivos = importadorJson.importarDispositivos();

		Assert.assertEquals(categorias.size(), 1);
		Assert.assertEquals(clientes.size(), 1);
		Assert.assertEquals(administradores.size(), 1);
		Assert.assertEquals(dispositivos.size(), 3);
	}

}
