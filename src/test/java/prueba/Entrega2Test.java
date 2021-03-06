package prueba;

import java.time.LocalDateTime;
import java.util.Date;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import domain.Categoria;
import domain.Cliente;
import domain.Dispositivo;
import domain.DispositivoEstandar;
import domain.DispositivoInteligente;
import domain.Estados;
import domain.FunctionClock;
import domain.TipoDocumento;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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

		this.cliente = new Cliente(nombreUsuario, contraseña, nombreYApellido, domicilio, tipoDocumento,
				numeroDocumento, telefonoContacto, fechaAltaCliente, categoria);
	}

	@Test
	public void test1FunctionSimplex() {
		DispositivoInteligente aireAcondicionado = (DispositivoInteligente) this.cliente
				.generarAireAcondicionado2200F();
		DispositivoInteligente lavarropas = (DispositivoInteligente) this.cliente.generarLavarropasAuto5kg();
		DispositivoEstandar ventiladorDePie = (DispositivoEstandar) this.cliente.generarVentiladorDePie();

		this.cliente.agregarDispositivo(aireAcondicionado);
		this.cliente.agregarDispositivo(lavarropas);
		this.cliente.agregarDispositivo(ventiladorDePie);

		this.cliente.calcularHogarEficiente();
		System.out.println("-----------------------------------------------------------");
		System.out.println("Test function simplex:");

		Dispositivo d0 = (Dispositivo) this.cliente.getDispositivos().toArray()[0];
		Assert.assertEquals(360, d0.getConsumoRecomendadoHoras(), 0.01);
		System.out.println("El consumo recomendado para " + d0.getNombre() + " es de: "
				+ d0.getConsumoRecomendadoHoras() + " horas.");

		Dispositivo d1 = (Dispositivo) this.cliente.getDispositivos().toArray()[1];
		Assert.assertEquals(30, d1.getConsumoRecomendadoHoras(), 0.01);
		System.out.println("El consumo recomendado para " + d1.getNombre() + " es de: "
				+ d1.getConsumoRecomendadoHoras() + " horas.");

		Dispositivo d2 = (Dispositivo) this.cliente.getDispositivos().toArray()[2];
		Assert.assertEquals(360, d2.getConsumoRecomendadoHoras(), 0.01);
		System.out.println("El consumo recomendado para " + d2.getNombre() + " es de: "
				+ d2.getConsumoRecomendadoHoras() + " horas.");

		System.out.println("-----------------------------------------------------------");
	}

	@Test
	public void test2AhorroInteligente() {
		DispositivoInteligente aireAcondicionado = (DispositivoInteligente) this.cliente
				.generarAireAcondicionado2200F();
		aireAcondicionado.agregarPeriodo(LocalDateTime.of(2018, 8, 1, 0, 0), LocalDateTime.of(2018, 8, 1, 3, 0));
		aireAcondicionado.setPermiteAhorroInteligente(true);
		aireAcondicionado.encenderse();

		DispositivoInteligente lavarropas = (DispositivoInteligente) this.cliente.generarLavarropasAuto5kg();
		lavarropas.agregarPeriodo(LocalDateTime.of(2018, 10, 1, 0, 0), LocalDateTime.of(2018, 10, 1, 10, 0));
		lavarropas.agregarPeriodo(LocalDateTime.of(2018, 10, 3, 0, 0), LocalDateTime.of(2018, 10, 3, 10, 0));
		lavarropas.agregarPeriodo(LocalDateTime.of(2018, 10, 6, 0, 0), LocalDateTime.of(2018, 10, 6, 10, 0));
		lavarropas.agregarPeriodo(LocalDateTime.of(2018, 10, 7, 0, 0), LocalDateTime.of(2018, 10, 7, 10, 0));
		lavarropas.setPermiteAhorroInteligente(true);
		lavarropas.encenderse();

		DispositivoEstandar ventiladorDePie = (DispositivoEstandar) this.cliente.generarVentiladorDePie();

		this.cliente.setAhorroInteligente(true);

		this.cliente.agregarDispositivo(aireAcondicionado);
		this.cliente.agregarDispositivo(lavarropas);
		this.cliente.agregarDispositivo(ventiladorDePie);

		System.out.println("Test ahorro inteligente automatico:");

		Dispositivo d0 = (Dispositivo) this.cliente.getDispositivos().toArray()[0];
		Dispositivo d1 = (Dispositivo) this.cliente.getDispositivos().toArray()[1];

		System.out.println("El estado del " + d0.getNombre() + " ANTES de calculo de ahorro inteligente es "
				+ d0.getEstado().toString() + ".");

		System.out.println("El estado del " + d1.getNombre() + " ANTES de calculo de ahorro inteligente es "
				+ d1.getEstado().toString() + ".");

		Assert.assertTrue(Estados.ENCENDIDO == d0.getEstado());
		Assert.assertTrue(Estados.ENCENDIDO == d1.getEstado());

		cliente.calcularHogarEficiente();

		Assert.assertTrue(Estados.ENCENDIDO == d0.getEstado());
		Assert.assertTrue(Estados.APAGADO == d1.getEstado());

		System.out.println("El estado del " + d0.getNombre() + " DESPUES de calculo de ahorro inteligente es "
				+ d0.getEstado().toString() + ".");
		System.out.println("El estado del " + d1.getNombre() + " DESPUES de calculo de ahorro inteligente es "
				+ d1.getEstado().toString() + ".");

		System.out.println("-----------------------------------------------------------");
	}

//	@Test
//	public void test3PeriodicidadAhorroInteligente() throws InterruptedException {
//		DispositivoInteligente aireAcondicionado = (DispositivoInteligente) this.cliente.generarAireAcondicionado2200F();
//		aireAcondicionado.agregarPeriodo(LocalDateTime.of(2018, 10, 1, 0, 0), LocalDateTime.of(2018, 10, 1, 3, 0));
//		aireAcondicionado.setPermiteAhorroInteligente(true);
//		aireAcondicionado.encenderse();
//			
//		DispositivoInteligente lavarropas = (DispositivoInteligente) this.cliente.generarLavarropasAuto5kg();
//		lavarropas.agregarPeriodo(LocalDateTime.of(2018, 10, 1, 0, 0), LocalDateTime.of(2018, 10, 1, 10, 0));
//		lavarropas.agregarPeriodo(LocalDateTime.of(2018, 10, 3, 0, 0), LocalDateTime.of(2018, 10, 3, 10, 0));
//
//
//		lavarropas.setPermiteAhorroInteligente(true);
//		lavarropas.encenderse();
//		
//		DispositivoEstandar ventiladorDePie = (DispositivoEstandar) this.cliente.generarVentiladorDePie();
//		
//		this.cliente.setAhorroInteligente(true);
//		
//		this.cliente.agregarDispositivo(aireAcondicionado);
//		this.cliente.agregarDispositivo(lavarropas);
//		this.cliente.agregarDispositivo(ventiladorDePie);
//		
//		System.out.println("Test Periodicidad ahorro inteligente automatico (cada 10 segundos):");
//		System.out.println("Antes de empezar el calculo automatico: ");
//		System.out.println("El estado del " + this.cliente.getDispositivos().get(0).getNombre() +
//				" es " + this.cliente.getDispositivos().get(0).getEstado().toString() + ".");
//		System.out.println("El estado del " + this.cliente.getDispositivos().get(1).getNombre() +
//				" es " + this.cliente.getDispositivos().get(1).getEstado().toString() + ".");
//		
//		Assert.assertTrue(Estados.ENCENDIDO == this.cliente.getDispositivos().get(0).getEstado());
//		Assert.assertTrue(Estados.ENCENDIDO == this.cliente.getDispositivos().get(1).getEstado()); 
//		
//		FunctionClock functionClock = new FunctionClock(this.cliente);
//		functionClock.Start();
//		
//		Thread.sleep(10000);
//		
//		lavarropas.agregarPeriodo(LocalDateTime.of(2018, 10, 6, 0, 0), LocalDateTime.of(2018, 10, 6, 9, 0));
//		
//		System.out.println("Despues de corrido una vez el calculo y agregandole un periodo a lavarropas: ");
//		System.out.println("El estado del " + this.cliente.getDispositivos().get(0).getNombre() +
//				" es " + this.cliente.getDispositivos().get(0).getEstado().toString() + ".");
//		System.out.println("El estado del " + this.cliente.getDispositivos().get(1).getNombre() +
//				" es " + this.cliente.getDispositivos().get(1).getEstado().toString() + ".");
//		
//		Assert.assertTrue(Estados.ENCENDIDO == this.cliente.getDispositivos().get(0).getEstado());
//		Assert.assertTrue(Estados.ENCENDIDO == this.cliente.getDispositivos().get(1).getEstado()); 
//		
//		Thread.sleep(10000);
//		 
//		lavarropas.agregarPeriodo(LocalDateTime.of(2018, 10, 7, 0, 0), LocalDateTime.of(2018, 10, 7, 10, 0));
//		
//		Thread.sleep(15000);
//		
//		System.out.println("Despues de corrido varias veces el calculo y agregandole otro periodo a lavarropas que hace que se apague: ");
//		System.out.println("El estado del " + this.cliente.getDispositivos().get(0).getNombre() +
//				" es " + this.cliente.getDispositivos().get(0).getEstado().toString() + ".");
//		System.out.println("El estado del " + this.cliente.getDispositivos().get(1).getNombre() +
//				" es " + this.cliente.getDispositivos().get(1).getEstado().toString() + ".");
//		Assert.assertTrue(Estados.ENCENDIDO == this.cliente.getDispositivos().get(0).getEstado());
//		Assert.assertTrue(Estados.APAGADO == this.cliente.getDispositivos().get(1).getEstado()); 
//	}

}
