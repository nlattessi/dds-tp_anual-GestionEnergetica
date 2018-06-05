package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class DispositivoInteligente extends Dispositivo {
	private Estados estado;
	private ArrayList<Periodo> periodos;
	private LocalDateTime ultimaFechaHoraEncendido;

	public DispositivoInteligente(int dispositivo, String nombre, int consumoXHora, Estados estado) {
		super(dispositivo, nombre, consumoXHora);
		this.estado = estado;
		if (this.estado == Estados.ENCENDIDO) {
			this.ultimaFechaHoraEncendido = LocalDateTime.now();
		}
		this.periodos = new ArrayList<Periodo>();
	}

	public Estados getEstado() {
		return estado;
	}

	public void setEstado(Estados estado) {
		this.estado = estado;
	}

	public boolean estaEncendido() {
		return this.estado == Estados.ENCENDIDO;
	}

	public boolean estaApagado() {
		return this.estado == Estados.APAGADO;
	}

	public void apagarse() {
		if (this.estado != Estados.APAGADO) {
			this.estado = Estados.APAGADO;
			this.periodos.add(new Periodo(this.ultimaFechaHoraEncendido, LocalDateTime.now()));
		}
	}

	public void encenderse() {
		if (this.estado != Estados.ENCENDIDO) {
			this.estado = Estados.ENCENDIDO;
			this.ultimaFechaHoraEncendido = LocalDateTime.now();
		}
	}

	public void modoAhorroEnergia() {
		if (this.estado != Estados.MODO_AHORRO_ENERGIA) {
			this.estado = Estados.MODO_AHORRO_ENERGIA;
		}
	}

	public int cuantaEnergiaConsumioEnHoras(int horas) {
		return this.getConsumoXHora() * horas;
	}

	public void agregarPeriodo(LocalDateTime inicio, LocalDateTime fin) {
		this.periodos.add(new Periodo(inicio, fin));
	}

	// public int consumoTotalEnUltimosPeriodos(int periodos) {
	// int startValue = 0;
	// int sumaCantidadHoras = this.periodos.stream().skip(Math.max(0,
	// this.periodos.size() - periodos)).map(p -> p.getHoras()).reduce(startValue,
	// (x,y) -> x+y);
	//
	//
	//
	//
	// }
}
