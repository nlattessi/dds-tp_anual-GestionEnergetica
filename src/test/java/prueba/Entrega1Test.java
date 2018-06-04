package prueba;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import domain.Actuador;
import domain.ApagarCommand;
import domain.ApagarRegla;
import domain.Dispositivo;
import domain.DispositivoInteligente;
import domain.EncenderCommand;
import domain.EncenderRegla;
import domain.Estados;
import domain.Medicion;
import domain.Reglamentador;
import domain.Sensor;

public class Entrega1Test {
	private DispositivoInteligente aireAcondicionado;

	@Before
	public void inicio() {
		this.aireAcondicionado = new DispositivoInteligente(1, "aire acondicionado", 2, true);

		// ApagarseCommand apagarseCommand = new ApagarseCommand(inteligente);
		// EncenderseCommand encenderseCommand = new EncenderseCommand(inteligente);
		// EntrarModoAhorroEnergiaCommand entrarModoAhorroEnergiaCommand = new
		// EntrarModoAhorroEnergiaCommand(inteligente);
		// Actuador actuador = new Actuador();
		// actuador.setApagarseCommand(apagarseCommand);
		// actuador.setEncenderseCommand(encenderseCommand);
		// actuador.setEntrarModoAhorroEnergiaCommand(entrarModoAhorroEnergiaCommand);
		//
		// Regla reglaApagarse = new ReglaApagarse();
		// Regla reglaEncenderse = new ReglaEncenderse();
		// Reglamentador reglamentador = new Reglamentador(actuador);
		// reglamentador.setRegla(reglaApagarse);
		// reglamentador.setRegla(reglaEncenderse);
		//
		// Sensor sensor = new Sensor();
		// sensor.registerObserver(reglamentador);
		//
		// sensor.setearMedicion("humedad", 80);
		// sensor.setearMedicion("temperatura", 26);
		// sensor.setearMedicion("oxigeno", 100);
		// sensor.setearMedicion("temperatura", 16);
		// sensor.setearMedicion("tension", 1000);
	}

	@Test
	public void dispositivoSeEnciendeSiSeCumpleReglaEncender() {
		Assert.assertEquals(Estados.APAGADO, this.aireAcondicionado.getEstado());

		EncenderCommand encenderCommand = new EncenderCommand(aireAcondicionado);

		Actuador actuador = new Actuador();
		actuador.setEncenderseComando(encenderCommand);

		EncenderRegla reglaEncenderse = new EncenderRegla();

		Reglamentador reglamentador = new Reglamentador(actuador);
		reglamentador.setRegla(reglaEncenderse);

		Sensor sensor = new Sensor();
		sensor.registerObserver(reglamentador);
		sensor.setMedicion(new Medicion("temperatura", 26));

		Assert.assertEquals(Estados.ENCENDIDO, this.aireAcondicionado.getEstado());
	}

	@Test
	public void dispositivoSeApagaSiSeCumpleReglaApagar() {
		this.aireAcondicionado.setEstado(Estados.ENCENDIDO);

		Assert.assertEquals(Estados.ENCENDIDO, this.aireAcondicionado.getEstado());

		ApagarCommand apagarCommand = new ApagarCommand(aireAcondicionado);

		Actuador actuador = new Actuador();
		actuador.setApagarCommand(apagarCommand);

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
		this.aireAcondicionado.setEstado(Estados.ENCENDIDO);

		Assert.assertEquals(Estados.ENCENDIDO, this.aireAcondicionado.getEstado());

		ApagarCommand apagarCommand = new ApagarCommand(aireAcondicionado);

		Actuador actuador = new Actuador();
		actuador.setApagarCommand(apagarCommand);

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

		EncenderCommand encenderCommand = new EncenderCommand(aireAcondicionado);

		Actuador actuador = new Actuador();
		actuador.setEncenderseComando(encenderCommand);

		EncenderRegla reglaEncenderse = new EncenderRegla();

		Reglamentador reglamentador = new Reglamentador(actuador);
		reglamentador.setRegla(reglaEncenderse);

		Sensor sensor = new Sensor();
		sensor.registerObserver(reglamentador);
		sensor.setMedicion(new Medicion("temperatura", 18));

		Assert.assertEquals(Estados.APAGADO, this.aireAcondicionado.getEstado());
	}

}
