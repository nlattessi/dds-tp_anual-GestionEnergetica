package prueba;

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
import domain.Dispositivo;
import domain.DispositivoEstandar;
import domain.DispositivoInteligente;
import domain.EncenderCommand;
import domain.EncenderRegla;
import domain.Estados;
import domain.Medicion;
import domain.Reglamentador;
import domain.Sensor;
import domain.TipoDocumento;

public class Entrega1Test {
	private DispositivoInteligente aireAcondicionado;

	@Before
	public void inicio() {
		this.aireAcondicionado = new DispositivoInteligente(1, "aire acondicionado", 230, Estados.APAGADO);
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

		Assert.assertEquals(consumoEstimadoEsperado, dispositivo.estimarConsumo(5));
	}

	// @Test
	// public void dispositivoInteligenteConsumoEnNHorasCorrectamente() {
	//
	//
	//
	//
	// int energianergiaConsumidaEsperada = 230 * 7;
	//
	// Assert.assertEquals(energianergiaConsumidaEsperada,
	// this.aireAcondicionado.cuantaEnergiaConsumioEnHoras(7));
	// }

}
