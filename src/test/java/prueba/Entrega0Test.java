package prueba;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import domain.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class Entrega0Test {
	private Cliente cliente;
	
	@Before
	public void inicio() {
		TipoDocumento tipoDoc = new TipoDocumento(1, "Documento Nacional de Identidad");
		Documento documentoCliente = new Documento(1, tipoDoc, 36123894);
		Domicilio domicilioCliente = new Domicilio("Av. Rivadavia", 1111, 1414, "CABA", "Buenos Aires");
		Categoria categoria = new Categoria(1, "R1", 18.75, 0.644, 0, 150);
		Dispositivo heladera = new Dispositivo(1, "heladera", 2, true);
		Dispositivo tele = new Dispositivo(2, "tele", 3, false);
		Dispositivo radio = new Dispositivo(3, "radio", 1, true);
		Date fechaAltaCliente = new Date();
		List<Dispositivo> dispositivos = new ArrayList<>();
		dispositivos.add(tele);
		dispositivos.add(heladera);
		dispositivos.add(radio);
		
		this.cliente = new Cliente(1, "JuanPerez", "asd123", "Juan Perez", domicilioCliente,
							documentoCliente, fechaAltaCliente, categoria, dispositivos);
	}
	
	@Test
	public void clienteConDosDispositivoEncendido() {
		Assert.assertEquals(2, this.cliente.cantidadDispositivosEncendidos());
	}
	
	@Test
	public void clienteConAlgunDispositivoEncendido() {
		Assert.assertTrue(this.cliente.algunDispositivoEncendido());
	}
	
}
