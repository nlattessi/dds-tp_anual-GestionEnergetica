package prueba;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import domain.Administrador;
import domain.Categoria;
import domain.Cliente;
import domain.Dispositivo;
import domain.Documento;
import domain.Domicilio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Entrega0Test {
	private Cliente cliente;
	private Administrador administrador;

	@Before
	public void inicio() {
		String nombreUsuario = "JuanPerez";
		String contraseña = "asd123";
		String nombreYApellido = "Juan Perez";
		Documento documentoCliente = new Documento(Documento.Tipo.DNI, 36123894);
		String telefonoContacto = "5555-5555";
		Domicilio domicilio = new Domicilio("Av. Rivadavia", 1111, 1414, "CABA", "Buenos Aires");
		Categoria categoria = Categoria.crearR1();
		Date fechaAltaCliente = new Date();

		this.cliente = new Cliente(1, nombreUsuario, contraseña, nombreYApellido, domicilio, documentoCliente,
				telefonoContacto, fechaAltaCliente, categoria);

		Dispositivo heladera = new Dispositivo(1, "heladera", 2, true);
		Dispositivo tele = new Dispositivo(2, "tele", 3, false);
		Dispositivo radio = new Dispositivo(3, "radio", 1, true);
		this.cliente.agregarDispositivo(tele);
		this.cliente.agregarDispositivo(heladera);
		this.cliente.agregarDispositivo(radio);

		this.administrador = new Administrador(1, nombreUsuario, contraseña, nombreYApellido, domicilio,
				fechaAltaCliente);
	}

	@Test
	public void clienteConDosDispositivoEncendido() {
		Assert.assertEquals(2, this.cliente.cantidadDispositivosEncendidos());
	}

	@Test
	public void clienteConUnDispositivoApagdo() {
		Assert.assertEquals(1, this.cliente.cantidadDispositivosApagados());
	}

	@Test
	public void clienteConAlgunDispositivoEncendido() {
		Assert.assertTrue(this.cliente.algunDispositivoEncendido());
	}

	@Test
	public void administradorLleva3MesesComoAdminsitrador() throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaDeAltaEnElSistema = sdf.parse("01/01/2018");
		Date fechaActual = sdf.parse("01/04/2018");

		this.administrador.setFechaAltaSistema(fechaDeAltaEnElSistema);
		int cantidadMesesComoAdministrador = this.administrador.cantidadMesesComoAdministrador(fechaActual);

		Assert.assertEquals(3, cantidadMesesComoAdministrador);
	}

}
