package domain;

import java.time.Duration;
import java.time.LocalDateTime;

public class Periodo {

	private LocalDateTime inicio;
	private LocalDateTime fin;
	private long horas;

	public Periodo(LocalDateTime inicio, LocalDateTime fin) {
		this.setInicio(inicio);
		this.setFin(fin);

		this.setHoras(Duration.between(inicio, fin).toHours());
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
