package prueba;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import domain.Categoria;
import domain.Cliente;
import domain.Dispositivo;
import domain.DispositivoEstandar;
import domain.DispositivoInteligente;
import domain.Estados;
import domain.FunctionClock;
import domain.TipoDocumento;

public class Entrega2Test {
	private Cliente cliente;
	
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

		this.cliente = new Cliente(1, nombreUsuario, contraseña, nombreYApellido, domicilio, tipoDocumento,
				numeroDocumento, telefonoContacto, fechaAltaCliente, categoria);
	}
	
	@Test
	public void testFunctionSimplex() {
		DispositivoInteligente aireAcondicionado = (DispositivoInteligente) this.cliente.generarAireAcondicionado2200F();
		DispositivoInteligente lavarropas = (DispositivoInteligente) this.cliente.generarLavarropasAuto5kg();
		DispositivoEstandar ventiladorDePie = (DispositivoEstandar) this.cliente.generarVentiladorDePie();
		
		this.cliente.agregarDispositivo(aireAcondicionado);
		this.cliente.agregarDispositivo(lavarropas);
		this.cliente.agregarDispositivo(ventiladorDePie);
		
		this.cliente.calcularHogarEficiente();
		System.out.println("-----------------------------------------------------------");
		System.out.println("Test function simplex:");
		Assert.assertEquals(360, cliente.getDispositivos().get(0).getConsumoRecomendadoHoras(), 0.01); 
		System.out.println("El consumo recomendado para " + cliente.getDispositivos().get(0).getNombre() + 
				" es de: " + cliente.getDispositivos().get(0).getConsumoRecomendadoHoras() + " horas.");
		Assert.assertEquals(30, cliente.getDispositivos().get(1).getConsumoRecomendadoHoras(), 0.01); 
		System.out.println("El consumo recomendado para " + cliente.getDispositivos().get(1).getNombre() + 
				" es de: " + cliente.getDispositivos().get(1).getConsumoRecomendadoHoras() + " horas.");
		Assert.assertEquals(360, cliente.getDispositivos().get(2).getConsumoRecomendadoHoras(), 0.01); 
		System.out.println("El consumo recomendado para " + cliente.getDispositivos().get(2).getNombre() + 
				" es de: " + cliente.getDispositivos().get(2).getConsumoRecomendadoHoras() + " horas.");
		System.out.println("-----------------------------------------------------------");
	}
	
	@Test
	public void testAhorroInteligente() {
		DispositivoInteligente aireAcondicionado = (DispositivoInteligente) this.cliente.generarAireAcondicionado2200F();
		aireAcondicionado.agregarPeriodo(LocalDateTime.of(2018, 8, 1, 0, 0), LocalDateTime.of(2018, 8, 1, 3, 0));
		aireAcondicionado.setPermiteAhorroInteligente(true);
		aireAcondicionado.encenderse();
			
		DispositivoInteligente lavarropas = (DispositivoInteligente) this.cliente.generarLavarropasAuto5kg();
		lavarropas.agregarPeriodo(LocalDateTime.of(2018, 8, 1, 0, 0), LocalDateTime.of(2018, 8, 1, 10, 0));
		lavarropas.agregarPeriodo(LocalDateTime.of(2018, 8, 3, 0, 0), LocalDateTime.of(2018, 8, 3, 10, 0));
		lavarropas.agregarPeriodo(LocalDateTime.of(2018, 8, 6, 0, 0), LocalDateTime.of(2018, 8, 6, 10, 0));
		lavarropas.agregarPeriodo(LocalDateTime.of(2018, 8, 7, 0, 0), LocalDateTime.of(2018, 8, 7, 10, 0));
		lavarropas.setPermiteAhorroInteligente(true);
		lavarropas.encenderse();
		
		DispositivoEstandar ventiladorDePie = (DispositivoEstandar) this.cliente.generarVentiladorDePie();
		
		this.cliente.setAhorroInteligente(true);
		
		this.cliente.agregarDispositivo(aireAcondicionado);
		this.cliente.agregarDispositivo(lavarropas);
		this.cliente.agregarDispositivo(ventiladorDePie);
		
		System.out.println("Test ahorro inteligente automatico:");
		System.out.println("El estado del " + this.cliente.getDispositivos().get(0).getNombre() +
				" ANTES de calculo de ahorro inteligente es " + this.cliente.getDispositivos().get(0).getEstado().toString() + ".");
		System.out.println("El estado del " + this.cliente.getDispositivos().get(0).getNombre() +
				" ANTES de calculo de ahorro inteligente es " + this.cliente.getDispositivos().get(1).getEstado().toString() + ".");
		
		cliente.calcularHogarEficiente();

		Assert.assertTrue(Estados.ENCENDIDO == cliente.getDispositivos().get(0).getEstado());
		Assert.assertTrue(Estados.APAGADO == cliente.getDispositivos().get(1).getEstado()); 
		
		System.out.println("El estado del " + this.cliente.getDispositivos().get(0).getNombre() +
				" DESPUES de calculo de ahorro inteligente es " + this.cliente.getDispositivos().get(0).getEstado().toString() + ".");
		System.out.println("El estado del " + this.cliente.getDispositivos().get(0).getNombre() +
				" DESPUES de calculo de ahorro inteligente es " + this.cliente.getDispositivos().get(1).getEstado().toString() + ".");
		System.out.println("-----------------------------------------------------------");
	}
	
//	@Test
//	public void testPeriodicidadAhorroInteligente() throws InterruptedException {
//		cliente.setAhorroInteligente(true);
//		
//		Assert.assertTrue(Estados.ENCENDIDO == cliente.getDispositivos().get(1).getEstado()); //<--- lavarropas automatico de 5 kg. Antes de calcular ahorro inteligente
//		
//		Assert.assertTrue(Estados.ENCENDIDO == cliente.getDispositivos().get(0).getEstado()); //<--- aire acondicionado de 2200 frigorias. Antes de calcular ahorro inteligente
//		
//		FunctionClock functionClock = new FunctionClock(this.cliente);
//		 
//		 functionClock.Start();
//		 
//		 Thread.sleep(5000);
//		 
//		 Assert.assertTrue(Estados.APAGADO == cliente.getDispositivos().get(1).getEstado()); //<--- lavarropas automatico de 5 kg. Antes de calcular ahorro inteligente
//			
//		 Assert.assertTrue(Estados.ENCENDIDO == cliente.getDispositivos().get(0).getEstado()); //<--- aire acondicionado de 2200 frigorias. Antes de calcular ahorro inteligente
//	}


}
