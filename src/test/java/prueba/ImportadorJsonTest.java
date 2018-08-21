package prueba;

import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import domain.Administrador;
import domain.Categoria;
import domain.Cliente;
import domain.Dispositivo;
import domain.DispositivoEstandar;
import domain.DispositivoInteligente;
import domain.ImportadorJson;

public class ImportadorJsonTest {

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

//	@Test
//	public void carga_clientes_correctamente() {
//		List<Cliente> clientes = ImportadorJson.desdeArchivo("clientes_de_prueba.json").importarClientes();
//
//		Assert.assertEquals(clientes.size(), 1);
//
//		Cliente primerCliente = clientes.get(0);
//		Assert.assertEquals(primerCliente.getId(), 1);
//		Assert.assertEquals(primerCliente.getNombreYApellido(), "Juan Perez");
//		Assert.assertEquals(primerCliente.getNombreUsuario(), "JuanPerez");
//		Assert.assertEquals(primerCliente.getContraseña(), "asd123");
//		Assert.assertEquals(primerCliente.getTipoDocumento().toString(), "DNI");
//		Assert.assertEquals(primerCliente.getNumeroDocumento(), 36123894);
//		Assert.assertEquals(primerCliente.getTelefonoContacto(), "5555-5555");
//		Assert.assertEquals(primerCliente.getDomicilio(), "Av. Rivadavia 1111, CABA, Buenos Aires");
//		Assert.assertEquals(sdf.format(primerCliente.getFechaAltaServicio()), "01/01/2018");
//		Assert.assertEquals(primerCliente.getCategoria().toString(), "R1");
//
//		System.out.println("Clientes:");
//		for (Cliente cliente : clientes) {
//			System.out.println("Id: " + cliente.getId());
//			System.out.println("Nombre y Apellido: " + cliente.getNombreYApellido());
//			System.out.println("Nombre de Usuario: " + cliente.getNombreUsuario());
//			System.out.println("Contraseña: " + cliente.getContraseña());
//			System.out.println("Tipo de Documento: " + cliente.getTipoDocumento().toString());
//			System.out.println("Numero de Documento: " + cliente.getNumeroDocumento());
//			System.out.println("Telefono: " + cliente.getTelefonoContacto());
//			System.out.println("Domicilio: " + cliente.getDomicilio());
//			System.out.println("Fecha de Alta: " + cliente.getFechaAltaServicio());
//			System.out.println("Categoria: " + cliente.getCategoria().toString());
//			System.out.println();
//		}
//		System.out.println("---------");
//
//	}

	@Test
	public void carga_administradores_correctamente() {
		List<Administrador> administradores = ImportadorJson.desdeArchivo("administradores_de_prueba.json")
				.importarAdministradores();

		Assert.assertEquals(administradores.size(), 1);

		Administrador primerAdministrador = administradores.get(0);
		Assert.assertEquals(primerAdministrador.getId(), 1);
		Assert.assertEquals(primerAdministrador.getNombreYApellido(), "Juan Perez");
		Assert.assertEquals(primerAdministrador.getNombreUsuario(), "JuanPerez");
		Assert.assertEquals(primerAdministrador.getContraseña(), "asd123");
		Assert.assertEquals(primerAdministrador.getDomicilio(), "Av. Rivadavia 1111, CABA, Buenos Aires");
		Assert.assertEquals(sdf.format(primerAdministrador.getFechaAltaSistema()), "01/01/2018");

		System.out.println("Administradores:");
		for (Administrador administrador : administradores) {
			System.out.println("Id: " + administrador.getId());
			System.out.println("Nombre y Apellido: " + administrador.getNombreYApellido());
			System.out.println("Nombre de Usuario: " + administrador.getNombreUsuario());
			System.out.println("Contraseña: " + administrador.getContraseña());
			System.out.println("Domicilio: " + administrador.getDomicilio());
			System.out.println("Fecha de Alta: " + administrador.getFechaAltaSistema());
			System.out.println();
		}
		System.out.println("---------");

	}

	@Test
	public void carga_dispositivos_correctamente() {
		List<Dispositivo> dispositivos = ImportadorJson.desdeArchivo("dispositivos_de_prueba.json")
				.importarDispositivos();

		Assert.assertEquals(dispositivos.size(), 3);

		DispositivoInteligente primerDispositivo = (DispositivoInteligente) dispositivos.get(0);
		Assert.assertEquals(primerDispositivo.getId(), 1);
		Assert.assertEquals(primerDispositivo.getNombre(), "heladera");
		Assert.assertEquals(2, primerDispositivo.getConsumoXHora(),0.01);
		Assert.assertEquals(primerDispositivo.estaEncendido(), true);

		DispositivoEstandar segundoDispositivo = (DispositivoEstandar) dispositivos.get(1);
		Assert.assertEquals(segundoDispositivo.getId(), 2);
		Assert.assertEquals(segundoDispositivo.getNombre(), "tele");
		Assert.assertEquals(3,segundoDispositivo.getConsumoXHora(), 0.01);

		DispositivoEstandar tercerDispositivo = (DispositivoEstandar) dispositivos.get(2);
		Assert.assertEquals(tercerDispositivo.getId(), 3);
		Assert.assertEquals(tercerDispositivo.getNombre(), "radio");
		Assert.assertEquals(1, tercerDispositivo.getConsumoXHora(), 0.01);

		System.out.println("Dispositivos:");
		for (Dispositivo dispositivo : dispositivos) {
			System.out.println("Id: " + dispositivo.getId());
			System.out.println("Nombre: " + dispositivo.getNombre());
			System.out.println("Consumo por hora: " + dispositivo.getConsumoXHora());
			if (dispositivo instanceof DispositivoInteligente) {
				System.out.println("Encendido: " + ((DispositivoInteligente) dispositivo).estaEncendido());
			}
			System.out.println();
		}
		System.out.println("---------");

	}

}
