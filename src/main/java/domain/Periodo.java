package domain;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "periodo")
public class Periodo extends EntidadPersistente{

	private LocalDateTime inicio;
	private LocalDateTime fin;
	private long horas;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dispositivo_id")
    private Dispositivo dispositivo;

	public Periodo(LocalDateTime inicio, LocalDateTime fin) {
		this.setInicio(inicio);
		this.setFin(fin);

		this.setHoras(Duration.between(inicio, fin).toHours());
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

}
