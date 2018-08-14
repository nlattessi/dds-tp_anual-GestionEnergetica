package prueba;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import domain.Categoria;
import domain.Cliente;
import domain.DispositivoEstandar;
import domain.DispositivoInteligente;
import domain.Estados;
import domain.TipoDocumento;

public class Entrega2Test {
	
	private DispositivoInteligente aireAcondicionado2200F;
	private DispositivoInteligente lavarropas5kg;
	private Cliente cliente;
	private DispositivoEstandar ventiladorPie;
	
	@Before
	public void inicio() 
	{
		this.aireAcondicionado2200F = new DispositivoInteligente(1, "aire acondicionado de 2200 frigorias", 0.18, Estados.APAGADO);
		this.aireAcondicionado2200F.setUsoMensualMinimoHoras(90);
		this.aireAcondicionado2200F.setUsoMensualMaximoHoras(370);
		
		this.lavarropas5kg = new DispositivoInteligente(2, "lavarropas automatico de 5 kg", 0.875, Estados.APAGADO);
		this.lavarropas5kg.setUsoMensualMinimoHoras(6);
		this.lavarropas5kg.setUsoMensualMaximoHoras(30);
		
		this.ventiladorPie = new DispositivoEstandar(3, "ventilador de pie", 0.06);
		this.ventiladorPie.setUsoMensualMinimoHoras(120);
		this.ventiladorPie.setUsoMensualMaximoHoras(360);

		String nombreUsuario = "JuanPerez";
		String contraseña = "asd123";
		String nombreYApellido = "Juan Perez";
		TipoDocumento tipoDocumento = TipoDocumento.DNI;
		int numeroDocumento = 36123894;
		String telefonoContacto = "5555-5555";
		String domicilio = "Av. Rivadavia 1111, CABA, Buenos Aires";
		Categoria categoria = Categoria.R1;
		Date fechaAltaCliente = new Date();

		this.cliente = new Cliente(1, nombreUsuario, contraseña, nombreYApellido, domicilio, tipoDocumento,
				numeroDocumento, telefonoContacto, fechaAltaCliente, categoria);
		
		this.cliente.agregarDispositivo(this.aireAcondicionado2200F);
		this.cliente.agregarDispositivo(this.lavarropas5kg);
		this.cliente.agregarDispositivo(this.ventiladorPie);
	}
	
	@Test
	public void testAlgoritmoSimplex()
	{
		cliente.calcularHogarEficiente();
		Assert.assertEquals(360, cliente.getDispositivos().get(2).getConsumoRecomendadoHoras(), 0.01); // <--- aire acondicionado de 2200 frigorias
		Assert.assertEquals(30, cliente.getDispositivos().get(1).getConsumoRecomendadoHoras(), 0.01); // <--- lavarropas automatico de 5 kg
		Assert.assertEquals(370, cliente.getDispositivos().get(0).getConsumoRecomendadoHoras(), 0.01); // <--- ventilador de pie
	}

}
