package domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import db.LocalDateTimeAttributeConverter;

@Entity(name = "Periodo")
@Table(name = "periodo")
public class Periodo {

	// Variables
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	protected int id;

	@Version
	@Column
	protected Long version;

//	@Column(name = "inicio", columnDefinition = "TIMESTAMP")
//	@Convert(converter = LocalDateTimeAttributeConverter.class)
	@Basic
	@Convert(converter = LocalDateTimeAttributeConverter.class)
	private LocalDateTime inicio;

	@Basic
	@Convert(converter = LocalDateTimeAttributeConverter.class)
	private LocalDateTime fin;

	@Column
	private long horas;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dispositivo_id")
	private Dispositivo dispositivo;

	public Periodo() {

	}

	public Periodo(LocalDateTime inicio, LocalDateTime fin) {
		this.setInicio(inicio);
		this.setFin(fin);

		this.setHoras(Duration.between(inicio, fin).toHours());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Dispositivo getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(Dispositivo dispositivo) {
		this.dispositivo = dispositivo;
	}

	public LocalDateTime getInicio() {
		return inicio;
	}

	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}

	public LocalDateTime getFin() {
		return fin;
	}

	public void setFin(LocalDateTime fin) {
		this.fin = fin;
	}

	public int getHoras() {
		return (int) this.horas;
	}

	public void setHoras(long horas) {
		this.horas = horas;
	}

	public boolean inicioEsDespuesDe(LocalDateTime fechaHora) {
		return this.inicio.isEqual(fechaHora) || this.inicio.isAfter(fechaHora);
	}

	public boolean finEsAntesDe(LocalDateTime fechaHora) {
		return this.fin.isEqual(fechaHora) || this.fin.isBefore(fechaHora);
	}

	public String getInicioFormateado() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

		return inicio.format(formatter);
	}

	public String getFinFormateado() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

		return fin.format(formatter);
	}
}
