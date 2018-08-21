package prueba;

import java.time.LocalDateTime;
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
		this.aireAcondicionado2200F.agregarPeriodo(LocalDateTime.of(2018, 8, 1, 0, 0), LocalDateTime.of(2018, 8, 1, 3, 0));
		this.aireAcondicionado2200F.encenderse();
		this.aireAcondicionado2200F.setPermiteAhorroInteligente(true);
		
		this.lavarropas5kg = new DispositivoInteligente(2, "lavarropas automatico de 5 kg", 0.875, Estados.APAGADO);
		this.lavarropas5kg.setUsoMensualMinimoHoras(6);
		this.lavarropas5kg.setUsoMensualMaximoHoras(30);
		this.lavarropas5kg.agregarPeriodo(LocalDateTime.of(2018, 8, 1, 0, 0), LocalDateTime.of(2018, 8, 1, 10, 0));
		this.lavarropas5kg.agregarPeriodo(LocalDateTime.of(2018, 8, 3, 0, 0), LocalDateTime.of(2018, 8, 3, 10, 0));
		this.lavarropas5kg.agregarPeriodo(LocalDateTime.of(2018, 8, 6, 0, 0), LocalDateTime.of(2018, 8, 6, 10, 0));
		this.lavarropas5kg.agregarPeriodo(LocalDateTime.of(2018, 8, 7, 0, 0), LocalDateTime.of(2018, 8, 7, 10, 0));
		this.lavarropas5kg.encenderse();
		this.lavarropas5kg.setPermiteAhorroInteligente(true);
		
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
		Assert.assertEquals(370, cliente.getDispositivos().get(0).getConsumoRecomendadoHoras(), 0.01); // <--- aire acondicionado de 2200 frigorias
		System.out.println("El consumo recomendado para " + cliente.getDispositivos().get(0).getNombre() + 
				" es de: " + cliente.getDispositivos().get(0).getConsumoRecomendadoHoras() + " horas.");
		Assert.assertEquals(30, cliente.getDispositivos().get(1).getConsumoRecomendadoHoras(), 0.01); // <--- lavarropas automatico de 5 kg
		System.out.println("El consumo recomendado para " + cliente.getDispositivos().get(1).getNombre() + 
				" es de: " + cliente.getDispositivos().get(1).getConsumoRecomendadoHoras() + " horas.");
		Assert.assertEquals(360, cliente.getDispositivos().get(2).getConsumoRecomendadoHoras(), 0.01); // <--- ventilador de pie
		System.out.println("El consumo recomendado para " + cliente.getDispositivos().get(2).getNombre() + 
				" es de: " + cliente.getDispositivos().get(2).getConsumoRecomendadoHoras() + " horas.");
	}
	
	@Test
	public void testAhorroInteligente()
	{
		cliente.setAhorroInteligente(true);
		
		cliente.calcularHogarEficiente();

		Assert.assertTrue(Estados.APAGADO == cliente.getDispositivos().get(1).getEstado()); //<--- lavarropas automatico de 5 kg. Antes de calcular ahorro inteligente
		
		Assert.assertTrue(Estados.ENCENDIDO == cliente.getDispositivos().get(0).getEstado()); //<--- aire acondicionado de 2200 frigorias. Antes de calcular ahorro inteligente
	}
//
//
//		cliente.start();
//		
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		cliente.calcularHogarEficiente();
//
//		
//		Assert.assertTrue(Estados.APAGADO == cliente.getDispositivos().get(1).getEstado()); //<--- lavarropas automatico de 5 kg Desp de calcular ahorro inteligente
//		
//		Assert.assertTrue(Estados.ENCENDIDO == cliente.getDispositivos().get(0).getEstado()); //<--- aire acondicionado de 2200 frigorias. Desp de calcular ahorro inteligente
//		
//
//				 
//		
//		
//
//		cliente.aguardar();//el main espera hasta que finalice de ejecutarse el hilo
//
//	}


}
