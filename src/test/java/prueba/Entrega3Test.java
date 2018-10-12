package prueba;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import domain.Acciones;
import domain.Actuador;
import domain.Categoria;
import domain.Cliente;
import domain.ComparacionesReglaGenerica;
import domain.DispositivoInteligente;
import domain.EncenderCommand;
import domain.EncenderRegla;
import domain.Estados;
import domain.Periodo;
import domain.ReglaGenerica;
import domain.Reglamentador;
import domain.TipoDocumento;
import models.ModelHelper;

public class Entrega3Test {
	private ModelHelper model;

	@Before
	public void inicio() {
		this.model = new ModelHelper();
	}

	@Test
	public void test() {
		// fail("Not yet implemented");
	}

	@Test
	public void casoDePrueba1() {
		// Crear 1 usuario nuevo.
		String nombreUsuario = "JuanPerez";
		String contraseña = "asd123";
		String nombreYApellido = "Juan Perez";
		TipoDocumento tipoDocumento = TipoDocumento.DNI;
		int numeroDocumento = 36123894;
		String telefonoContacto = "5555-5555";
		String domicilio = "Av. Rivadavia 1111, CABA, Buenos Aires";
		Categoria categoria = Categoria.R1;
		Date fechaAltaCliente = new Date();
		Cliente cliente = new Cliente(nombreUsuario, contraseña, nombreYApellido, domicilio, tipoDocumento,
				numeroDocumento, telefonoContacto, fechaAltaCliente, categoria);

		// Persistirlo.
		this.model.agregar(cliente);
		int id = cliente.getId();

		// Recuperarlo y modificar la geolocalización y grabarlo.
		Cliente clienteRecuperado = this.model.buscar(Cliente.class, id);
		cliente.setCoordenadasDomicilio(100, 200);
		this.model.modificar(clienteRecuperado);

		// Recuperarlo y evaluar que el cambio se haya realizado.
		clienteRecuperado = this.model.buscar(Cliente.class, id);

		assertEquals(clienteRecuperado.getCoordenadasDomicilio().getX(), 100);
		assertEquals(clienteRecuperado.getCoordenadasDomicilio().getY(), 200);
	}

	@Test
	public void casoDePrueba2() {
		DispositivoInteligente dispositivo = new DispositivoInteligente("aire acondicionado de 2200 frigorias", 1.013,
				Estados.APAGADO);
		dispositivo.setUsoMensualMinimoHoras(90);
		dispositivo.setUsoMensualMaximoHoras(360);
		dispositivo.setBajoConsumo(true);
		
		dispositivo.agregarPeriodo(LocalDateTime.of(2018, 8, 1, 0, 0), LocalDateTime.of(2018, 8, 1, 10, 0));
		dispositivo.agregarPeriodo(LocalDateTime.of(2018, 9, 1, 0, 0), LocalDateTime.of(2018, 9, 1, 10, 0));
		dispositivo.agregarPeriodo(LocalDateTime.of(2018, 10, 1, 0, 0), LocalDateTime.of(2018, 10, 1, 10, 0));
		dispositivo.agregarPeriodo(LocalDateTime.of(2018, 10, 12, 0, 0), LocalDateTime.of(2018, 10, 12, 10, 0));
		
		this.model.agregar(dispositivo);
		int id = dispositivo.getId();
		
		// Recuperar un dispositivo.
		DispositivoInteligente dispositivoRecuperado = this.model.buscar(DispositivoInteligente.class, id);
		
		// Mostrar por consola todos los intervalos que estuvo encendido durante el
		// último mes.
		int ultimoMes = 10; // Octubre
		List<Periodo> intervalos = dispositivoRecuperado.getPeriodosDelMes(10);
		System.out.println("Intervalos que estuvo encendido durante el ultimo mes (octubre):");
		System.out.println("-----------------------------------------------------------");
		intervalos.forEach(x -> {
			LocalDateTime inicio = x.getInicio();
			LocalDateTime fin = x.getFin();

			System.out.println("Intervalo");
			System.out.println("Desde: " + inicio.getYear() + "/" + inicio.getMonthValue() + "/" + inicio.getDayOfMonth() + " " + inicio.getHour() + ":" + inicio.getMinute() + " hs");
			System.out.println("Hasta: " + fin.getYear() + "/" + fin.getMonthValue() + "/" + fin.getDayOfMonth() + " " + fin.getHour() + ":" + fin.getMinute() + " hs");
			System.out.println("-----------------------------------------------------------");
		});

		// Modificar su nombre (o cualquier otro atributo editable) y grabarlo.
		dispositivoRecuperado.setNombre("aire acondicionado de 2200 frigorias USADO");
		this.model.modificar(dispositivoRecuperado);

		// Recuperarlo y evaluar que el nombre coincida con el esperado.
		dispositivoRecuperado = this.model.buscar(DispositivoInteligente.class, id);
		
		assertEquals(dispositivoRecuperado.getNombre(), "aire acondicionado de 2200 frigorias USADO");		
	}
	
	@Test
	public void casoDePrueba3() {
		// Crear una nueva regla.
		ReglaGenerica encenderRegla = new ReglaGenerica();
		
		// Asociarla a un dispositivo.
		DispositivoInteligente dispositivo = new DispositivoInteligente("aire acondicionado de 2200 frigorias", 1.013,
				Estados.APAGADO);
		encenderRegla.setDispositivo(dispositivo);
		
		//Agregar condiciones y acciones y persistirla.
		encenderRegla.setNombreMagnitud("temperatura");
		encenderRegla.setComparacion(ComparacionesReglaGenerica.MAYORIGUAL);
		encenderRegla.setValor(24);
		encenderRegla.setAccion(Acciones.ENCENDERSE);
		this.model.agregar(encenderRegla);
		int id = encenderRegla.getId();
		
		// Recuperarla y ejecutarla.
		ReglaGenerica encenderReglaRecuperada = this.model.buscar(ReglaGenerica.class, id);
		
		assertEquals(Estados.APAGADO, dispositivo.getEstado());
		
		Actuador actuador = new Actuador((DispositivoInteligente) encenderReglaRecuperada.getDispositivo());
		actuador.agregarAccion(Acciones.ENCENDERSE, new EncenderCommand());
		encenderReglaRecuperada.dispararAccion(actuador);
		
		assertEquals(Estados.ENCENDIDO, dispositivo.getEstado());
		
		// Modificar alguna condición y persistirla.
		encenderReglaRecuperada.setValor(22);
		this.model.modificar(encenderReglaRecuperada);
		
		// Recuperarla y evaluar que la condición modificada posea la última modificación.
		encenderReglaRecuperada = this.model.buscar(ReglaGenerica.class, id);
		
		assertEquals(22, encenderReglaRecuperada.getValor());
	}

}
