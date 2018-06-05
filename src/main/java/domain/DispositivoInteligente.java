package domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DispositivoInteligente extends Dispositivo {
	private Estados estado;
	private ArrayList<Periodo> periodos;
	private LocalDateTime ultimaFechaHoraEncendido;
	public int puntosOtorga = 15; 

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

	public void agregarPeriodo(LocalDateTime inicio, LocalDateTime fin) {
		this.periodos.add(new Periodo(inicio, fin));
	}

	public int consumoDuranteUltimasNHoras(int horas) {
		LocalDateTime ahora = LocalDateTime.now();
		LocalDateTime ahoraMenosHoras = ahora.minusHours(horas);
		int cantidad = this.consumoTotalComprendidoEntre(ahoraMenosHoras, ahora);

		if (this.estaEncendido()) {
			if (this.ultimaFechaHoraEncendido.isEqual(ahoraMenosHoras)
					|| this.ultimaFechaHoraEncendido.isAfter(ahoraMenosHoras)) {
				cantidad += Duration.between(ahoraMenosHoras, this.ultimaFechaHoraEncendido).toHours()
						* this.getConsumoXHora();
			}
		}

		return cantidad;
	}

	public int consumoDuranteUltimasNHoras(int horas, LocalDateTime ahora) {
		LocalDateTime ahoraMenosHoras = ahora.minusHours(horas);
		int cantidad = this.consumoTotalComprendidoEntre(ahoraMenosHoras, ahora);

		if (this.estaEncendido()) {
			if (this.ultimaFechaHoraEncendido.isEqual(ahoraMenosHoras)
					|| this.ultimaFechaHoraEncendido.isAfter(ahoraMenosHoras)) {
				cantidad += Duration.between(ahoraMenosHoras, this.ultimaFechaHoraEncendido).toHours()
						* this.getConsumoXHora();
			}
		}

		return cantidad;
	}

	public int consumoTotalComprendidoEntre(LocalDateTime inicio, LocalDateTime fin) {
		int totalHoras = this.periodos.stream().filter(p -> p.inicioEsDespuesDe(inicio) && p.finEsAntesDe(fin))
				.map(p -> p.getHoras()).reduce(0, (x, y) -> x + y);

		return totalHoras * this.getConsumoXHora();
	}

	public void limpiarPeriodos() {
		this.periodos.clear();
	}
	
	public void sumarPuntos(Cliente cliente) {
		cliente.sumarPuntos(puntosOtorga);
	}

}
