package domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import db.LocalDateTimeAttributeConverter;

@Entity(name = "DispositivoInteligente")
@Table(name = "dispositivo_inteligente")
@DiscriminatorValue("2")
public class DispositivoInteligente extends Dispositivo {

	// Variables
	@Column
	private Estados estado;

	@Basic
	@Convert(converter = LocalDateTimeAttributeConverter.class)
	private LocalDateTime ultimaFechaHoraEncendido;

	@OneToOne(mappedBy = "dispositivo", orphanRemoval = true, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Actuador actuador;

	@Transient
	private Fabricante fabricante;

	// Constructores
	public DispositivoInteligente() {
		super();
	}

	public DispositivoInteligente(String nombre, double consumoXHora, Estados estado) {
		this(nombre, consumoXHora, estado, new FabricanteGeneralAdapter());
	}

	public DispositivoInteligente(DispositivoMaestro maestro, Estados estado) {
		this(maestro, estado, new FabricanteGeneralAdapter());
		maestro.addDispositivo(this);
	}

	public DispositivoInteligente(String nombre, double consumoXHora, Estados estado, Fabricante fabricante) {
		super(nombre, consumoXHora);
		this.estado = estado;
		if (this.estado == Estados.ENCENDIDO) {
			this.ultimaFechaHoraEncendido = LocalDateTime.now();
		}
		this.fabricante = fabricante;
	}

	public DispositivoInteligente(DispositivoMaestro maestro, Estados estado, Fabricante fabricante) {
		super(maestro);
		this.estado = estado;
		if (this.estado == Estados.ENCENDIDO) {
			this.ultimaFechaHoraEncendido = LocalDateTime.now();
		}
		this.fabricante = fabricante;
	}

	// Setters - Getters

	public Actuador getActuador() {
		return actuador;
	}

	public void setActuador(Actuador actuador) {
		this.actuador = actuador;
	}

	public boolean getEsInteligente() {
		return true;
	}

	@Override
	public Estados getEstado() {
		return estado;
	}

	public void setEstado(Estados estado) {
		HistorialEstadoDispositivoInteligente.guardarHistorial(this, this.estado, estado);
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
			setEstado(Estados.APAGADO);

			Periodo p = new Periodo(this.ultimaFechaHoraEncendido, LocalDateTime.now());
			p.setDispositivo(this);

			this.periodos.add(p);

			if (this.fabricante != null) {
				this.fabricante.enviarMensajeApagado();
			}
		}
	}

	public void encenderse() {
		if (this.estado != Estados.ENCENDIDO) {
			setEstado(Estados.ENCENDIDO);
			this.ultimaFechaHoraEncendido = LocalDateTime.now();

			if (this.fabricante != null) {
				this.fabricante.enviarMensajeEncendido();
			}
		}
	}

	public void modoAhorroEnergia() {
		if (this.estado != Estados.MODO_AHORRO_ENERGIA && this.estado != Estados.APAGADO) {
			this.estado = Estados.MODO_AHORRO_ENERGIA;
			setEstado(Estados.MODO_AHORRO_ENERGIA);
			if (this.fabricante != null) {
				this.fabricante.enviarMensajeModoAhorroEnergia();
			}
		}
	}

	public void agregarPeriodo(LocalDateTime inicio, LocalDateTime fin) {
		Periodo periodo = new Periodo(inicio, fin);
		periodos.add(periodo);
		periodo.setDispositivo(this);
	}

	public List<Periodo> getPeriodosDelMes(int mes) {
		return periodos.stream().filter(p -> p.getInicio().getMonthValue() == mes).collect(Collectors.toList());
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
		int totalHoras = horasTotalComprendidoEntre(inicio, fin);

		return totalHoras * this.getConsumoXHora();
	}

	@Override
	public int horasTotalComprendidoEntre(LocalDateTime inicio, LocalDateTime fin) {
		int totalHoras = this.periodos.stream().filter(p -> p.inicioEsDespuesDe(inicio) && p.finEsAntesDe(fin))
				.map(p -> p.getHoras()).reduce(0, (x, y) -> x + y);

		if (this.estaEncendido()) {
			if (inicio.isEqual(this.ultimaFechaHoraEncendido) || inicio.isAfter(this.ultimaFechaHoraEncendido)) {
				totalHoras += Duration.between(inicio, fin).toHours();
			}
		}

		return totalHoras;
	}

	public double consumoPromedioComprendidoEntre(LocalDateTime inicio, LocalDateTime fin) {
		double totalPromedio = this.consumoTotalComprendidoEntre(inicio, fin) / Duration.between(inicio, fin).toDays();
		return totalPromedio;
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

	public boolean getEstaEncendido() {
		return estado.equals(Estados.ENCENDIDO);
	}

}
