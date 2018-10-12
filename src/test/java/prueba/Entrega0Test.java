package prueba;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import domain.Administrador;
import domain.Categoria;
import domain.Cliente;
import domain.Dispositivo;
import domain.DispositivoEstandar;
import domain.DispositivoInteligente;
import domain.Estados;
import domain.TipoDocumento;

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
		TipoDocumento tipoDocumento = TipoDocumento.DNI;
		int numeroDocumento = 36123894;
		String telefonoContacto = "5555-5555";
		String domicilio = "Av. Rivadavia 1111, CABA, Buenos Aires";
		Categoria categoria = Categoria.R1;
		Date fechaAltaCliente = new Date();
		
		//this.cliente = new Cliente(1, nombreUsuario, contraseña, nombreYApellido, domicilio, tipoDocumento,
		this.cliente = new Cliente(nombreUsuario, contraseña, nombreYApellido, domicilio, tipoDocumento,
				numeroDocumento, telefonoContacto, fechaAltaCliente, categoria);

		DispositivoInteligente heladera = new DispositivoInteligente("heladera", 2, Estados.ENCENDIDO);
		DispositivoEstandar tele = new DispositivoEstandar("tele", 3);
		DispositivoEstandar radio = new DispositivoEstandar("radio", 1);
		DispositivoInteligente aireAcondicionado = new DispositivoInteligente("aire acondicionado", 10, Estados.APAGADO);
		this.cliente.agregarDispositivo(tele);
		this.cliente.agregarDispositivo(heladera);
		this.cliente.agregarDispositivo(radio);
		this.cliente.agregarDispositivo(aireAcondicionado);

		this.administrador = new Administrador(1, nombreUsuario, contraseña, nombreYApellido, domicilio,
				fechaAltaCliente);
	}

	@Test
	public void clienteConUnDispositivoEncendido() {
		Assert.assertEquals(1, this.cliente.cantidadDispositivosEncendidos());
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
	public void clienteConCuatroDispositivosEnTotal() {
		Assert.assertEquals(4, this.cliente.cantidadTotalDispositivos());
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
