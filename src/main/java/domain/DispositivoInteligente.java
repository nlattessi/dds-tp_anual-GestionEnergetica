package domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DispositivoInteligente extends Dispositivo {
	private Estados estado;
	private ArrayList<Periodo> periodos;
	private LocalDateTime ultimaFechaHoraEncendido;
	private Fabricante fabricante;

	public DispositivoInteligente(int dispositivo, String nombre, double consumoXHora, Estados estado) {
		this(dispositivo, nombre, consumoXHora, estado, new FabricanteGeneralAdapter());
	}

	public DispositivoInteligente(int dispositivo, String nombre, double consumoXHora, Estados estado,
			Fabricante fabricante) {
		super(dispositivo, nombre, consumoXHora);
		this.estado = estado;
		if (this.estado == Estados.ENCENDIDO) {
			this.ultimaFechaHoraEncendido = LocalDateTime.now();
		}
		this.periodos = new ArrayList<Periodo>();
		this.fabricante = fabricante;
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

	@Override
	public void apagarse() {
		if (this.estado != Estados.APAGADO) {
			this.estado = Estados.APAGADO;
			this.periodos.add(new Periodo(this.ultimaFechaHoraEncendido, LocalDateTime.now()));
			this.fabricante.enviarMensajeApagado();
		}
	}

	public void encenderse() {
		if (this.estado != Estados.ENCENDIDO) {
			this.estado = Estados.ENCENDIDO;
			this.ultimaFechaHoraEncendido = LocalDateTime.now();
			this.fabricante.enviarMensajeEncendido();
		}
	}

	public void modoAhorroEnergia() {
		if (this.estado != Estados.MODO_AHORRO_ENERGIA && this.estado != Estados.APAGADO) {
			this.estado = Estados.MODO_AHORRO_ENERGIA;
			this.fabricante.enviarMensajeModoAhorroEnergia();
		}
	}

	public void agregarPeriodo(LocalDateTime inicio, LocalDateTime fin) {
		this.periodos.add(new Periodo(inicio, fin));
	}

	public double consumoDuranteUltimasNHoras(int horas) {
		LocalDateTime ahora = LocalDateTime.now();
		return this.consumoDuranteUltimasNHoras(horas, ahora);
	}

	public double consumoDuranteUltimasNHoras(int horas, LocalDateTime ahora) {
		LocalDateTime ahoraMenosHoras = ahora.minusHours(horas);
		return this.consumoTotalComprendidoEntre(ahoraMenosHoras, ahora);
	}

	@Override
	public double consumoTotalComprendidoEntre(LocalDateTime inicio, LocalDateTime fin) {
		int totalHoras = this.periodos.stream().filter(p -> p.inicioEsDespuesDe(inicio) && p.finEsAntesDe(fin))
				.map(p -> p.getHoras()).reduce(0, (x, y) -> x + y);

		if (this.estaEncendido()) {
			if (inicio.isEqual(this.ultimaFechaHoraEncendido) || inicio.isAfter(this.ultimaFechaHoraEncendido)) {
				totalHoras += Duration.between(inicio, fin).toHours();
			}
		}

		return totalHoras * this.getConsumoXHora();
	}

	public void limpiarPeriodos() {
		this.ultimaFechaHoraEncendido = null;
		this.periodos.clear();
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public LocalDateTime getUltimaFechaHoraEncendido() {
		return ultimaFechaHoraEncendido;
	}

	public void setUltimaFechaHoraEncendido(LocalDateTime ultimaFechaHoraEncendido) {
		this.ultimaFechaHoraEncendido = ultimaFechaHoraEncendido;
	}

	public String getStatusFabricante() {
		return this.fabricante.recibirMensajeStatus();
	}
}
