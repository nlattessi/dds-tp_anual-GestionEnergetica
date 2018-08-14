package prueba;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import domain.Acciones;
import domain.Actuador;
import domain.ApagarCommand;
import domain.ApagarRegla;
import domain.Categoria;
import domain.Cliente;
import domain.DispositivoEstandar;
import domain.DispositivoInteligente;
import domain.EncenderCommand;
import domain.EncenderRegla;
import domain.Estados;
import domain.Fabricante;
import domain.Medicion;
import domain.Reglamentador;
import domain.Sensor;
import domain.TipoDocumento;

public class Entrega1Test {
	private DispositivoInteligente aireAcondicionado;
	private Cliente cliente;
	private DispositivoEstandar tele;

	@Before
	public void inicio() {
		this.aireAcondicionado = new DispositivoInteligente(1, "aire acondicionado", 230, Estados.APAGADO);
		this.tele = new DispositivoEstandar(2, "tele", 3);

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
	public void dispositivoSeEnciendeSiSeCumpleReglaEncender() {
		Assert.assertEquals(Estados.APAGADO, this.aireAcondicionado.getEstado());

		EncenderCommand encenderCommand = new EncenderCommand();

		Actuador actuador = new Actuador(this.aireAcondicionado);
		actuador.agregarAccion(Acciones.ENCENDERSE, encenderCommand);

		Reglamentador reglamentador = new Reglamentador(actuador);
		EncenderRegla reglaEncenderse = new EncenderRegla();
		reglamentador.setRegla(reglaEncenderse);

		Sensor sensor = new Sensor();
		sensor.registerObserver(reglamentador);
		sensor.setMedicion(new Medicion("temperatura", 26));

		Assert.assertEquals(Estados.ENCENDIDO, this.aireAcondicionado.getEstado());
	}

	@Test
	public void dispositivoSeApagaSiSeCumpleReglaApagar() {
		if (this.aireAcondicionado.estaApagado())
			this.aireAcondicionado.encenderse();

		Assert.assertEquals(Estados.ENCENDIDO, this.aireAcondicionado.getEstado());

		ApagarCommand apagarCommand = new ApagarCommand();

		Actuador actuador = new Actuador(this.aireAcondicionado);
		actuador.agregarAccion(Acciones.APAGARSE, apagarCommand);

		ApagarRegla apagarRegla = new ApagarRegla();

		Reglamentador reglamentador = new Reglamentador(actuador);
		reglamentador.setRegla(apagarRegla);

		Sensor sensor = new Sensor();
		sensor.registerObserver(reglamentador);
		sensor.setMedicion(new Medicion("temperatura", 22));

		Assert.assertEquals(Estados.APAGADO, this.aireAcondicionado.getEstado());
	}

	@Test
	public void dispositivoNoSeApagaSiNoSeCumpleReglaApagar() {
		if (this.aireAcondicionado.estaApagado())
			this.aireAcondicionado.encenderse();

		Assert.assertEquals(Estados.ENCENDIDO, this.aireAcondicionado.getEstado());

		ApagarCommand apagarCommand = new ApagarCommand();

		Actuador actuador = new Actuador(this.aireAcondicionado);
		actuador.agregarAccion(Acciones.APAGARSE, apagarCommand);

		ApagarRegla apagarRegla = new ApagarRegla();

		Reglamentador reglamentador = new Reglamentador(actuador);
		reglamentador.setRegla(apagarRegla);

		Sensor sensor = new Sensor();
		sensor.registerObserver(reglamentador);
		sensor.setMedicion(new Medicion("temperatura", 25));

		Assert.assertEquals(Estados.ENCENDIDO, this.aireAcondicionado.getEstado());
	}

	@Test
	public void dispositivoNoSeEnciendeSiNoSeCumpleReglaEncender() {
		Assert.assertEquals(Estados.APAGADO, this.aireAcondicionado.getEstado());

		EncenderCommand encenderCommand = new EncenderCommand();

		Actuador actuador = new Actuador(this.aireAcondicionado);
		actuador.agregarAccion(Acciones.ENCENDERSE, encenderCommand);

		EncenderRegla reglaEncenderse = new EncenderRegla();

		Reglamentador reglamentador = new Reglamentador(actuador);
		reglamentador.setRegla(reglaEncenderse);

		Sensor sensor = new Sensor();
		sensor.registerObserver(reglamentador);
		sensor.setMedicion(new Medicion("temperatura", 18));

		Assert.assertEquals(Estados.APAGADO, this.aireAcondicionado.getEstado());
	}

	@Test
	public void dispositivoEstandarEstimaConsumoCorrectamente() {
		DispositivoEstandar dispositivo = new DispositivoEstandar(1, "radio", 23);

		int consumoEstimadoEsperado = 23 * 5;

		Assert.assertEquals(dispositivo.estimarConsumo(5),consumoEstimadoEsperado, 0.01);
	}

	@Test
	public void dispositivoInteligenteConsumoUltimasHoraCorrectamente() {
		this.aireAcondicionado.limpiarPeriodos();
		this.aireAcondicionado.encenderse();
		this.aireAcondicionado.setUltimaFechaHoraEncendido(LocalDateTime.of(2018, 06, 1, 0, 0));

		int consumoTotalEsperado = 230 * 1;

		Assert.assertEquals(
				this.aireAcondicionado.consumoDuranteUltimasNHoras(1, LocalDateTime.of(2018, 06, 1, 6, 0)),consumoTotalEsperado,0.01);
	}

	@Test
	public void dispositivoInteligenteConsumoUltimas3HorasCorrectamente() {
		this.aireAcondicionado.limpiarPeriodos();
		this.aireAcondicionado.encenderse();
		this.aireAcondicionado.setUltimaFechaHoraEncendido(LocalDateTime.of(2018, 06, 1, 0, 0));

		int consumoTotalEsperado = 230 * 3;

		Assert.assertEquals(
				this.aireAcondicionado.consumoDuranteUltimasNHoras(3, LocalDateTime.of(2018, 06, 1, 6, 0)),consumoTotalEsperado,0.01);
	}

	@Test
	public void dispositivoInteligenteConsumoUltimasHorasSiNoHuboConsumo() {
		this.aireAcondicionado.limpiarPeriodos();
		this.aireAcondicionado.encenderse();
		this.aireAcondicionado.setUltimaFechaHoraEncendido(LocalDateTime.of(2018, 06, 1, 0, 0));
		this.aireAcondicionado.apagarse();

		int consumoTotalEsperado = 0;

		Assert.assertEquals(this.aireAcondicionado.consumoDuranteUltimasNHoras(1, LocalDateTime.of(2018, 06, 1, 6, 0)),
				consumoTotalEsperado,0.01);
	}

	@Test
	public void dispositivoInteligenteConsumoTotalEntreCon1PeriodoCorrectamente() {
		this.aireAcondicionado.limpiarPeriodos();
		this.aireAcondicionado.agregarPeriodo(LocalDateTime.of(2018, 06, 1, 0, 0), LocalDateTime.of(2018, 06, 1, 3, 0));

		int consumoTotalEsperado = 230 * 3;

		Assert.assertEquals(this.aireAcondicionado.consumoTotalComprendidoEntre(
				LocalDateTime.of(2018, 06, 1, 0, 0), LocalDateTime.of(2018, 06, 1, 3, 0)), consumoTotalEsperado, 0.01);
	}

	@Test
	public void dispositivoInteligenteConsumoTotalEntreCon2PeriodoCorrectamente() {
		this.aireAcondicionado.limpiarPeriodos();
		this.aireAcondicionado.agregarPeriodo(LocalDateTime.of(2018, 05, 20, 10, 0),
				LocalDateTime.of(2018, 05, 20, 18, 0));
		this.aireAcondicionado.agregarPeriodo(LocalDateTime.of(2018, 06, 1, 0, 0), LocalDateTime.of(2018, 06, 1, 3, 0));

		int consumoTotalEsperado = 230 * (8 + 3);

		Assert.assertEquals(this.aireAcondicionado.consumoTotalComprendidoEntre(
				LocalDateTime.of(2018, 05, 1, 0, 0), LocalDateTime.of(2018, 06, 30, 0, 0)),consumoTotalEsperado, 0.01);
	}

	@Test
	public void clienteAgregaDispIntyTiene15Ptos() {
		int puntosEsperados = 15;
		this.cliente.agregarDispositivo(aireAcondicionado);
		Assert.assertEquals(puntosEsperados, this.cliente.puntosAcumulados());

	}

	@Test
	public void clienteAgregaDispEsteIntyTiene15Ptos() {
		int puntosEsperados = 15;
		this.cliente.agregarDispositivo(aireAcondicionado);
		this.cliente.agregarDispositivo(tele);
		Assert.assertEquals(puntosEsperados, this.cliente.puntosAcumulados());

	}

	@Test
	public void clienteAgregaDispEstLoTransfYtiene10ptos() {
		int puntosEsperados = 10;
		this.cliente.agregarDispositivo(tele);
		this.cliente.conversionEstandarInteligente(tele);
		Assert.assertEquals(puntosEsperados, this.cliente.puntosAcumulados());

	}

	@Test
	public void dispositivoPuedeAsociarseConFabricante() {
		class JVC {
			public void init() {
				System.out.println("Inicializando...");
			}

			public void prepararMensaje() {
				System.out.println("Preprando mensaje para enviar");
			}

			public void enviarApagarse() {
				System.out.println("Enviando mensaje para apagar dispositivo");
			}

			public void enviarEncenderse() {
				System.out.println("Enviando mensaje para encender dispositivo");
			}

			public void enviarModoAhorroEnergia() {
				System.out.println("Enviando mensaje para que dispositivo entre en modo ahorro energia");
			}

			public String obtenerStatus() {
				return "STATUS: OK";
			}
		}

		class JVCAdapter implements Fabricante {
			private JVC jvc;

			public JVCAdapter(JVC jvc) {
				this.jvc = jvc;
				this.jvc.init();
			}

			@Override
			public void enviarMensajeApagado() {
				this.jvc.prepararMensaje();
				this.jvc.enviarApagarse();
			}

			@Override
			public void enviarMensajeEncendido() {
				this.jvc.prepararMensaje();
				this.jvc.enviarEncenderse();
			}

			@Override
			public void enviarMensajeModoAhorroEnergia() {
				this.jvc.prepararMensaje();
				this.jvc.enviarModoAhorroEnergia();
			}

			@Override
			public String recibirMensajeStatus() {
				return this.jvc.obtenerStatus();
			}
		}

		JVC jvc = new JVC();
		Fabricante jvcAdapter = new JVCAdapter(jvc);
		DispositivoInteligente di = new DispositivoInteligente(1, "radio", 230, Estados.APAGADO, jvcAdapter);

		di.encenderse();
		di.apagarse();
		di.modoAhorroEnergia();

		Assert.assertEquals("STATUS: OK", di.getStatusFabricante());

	}

}
