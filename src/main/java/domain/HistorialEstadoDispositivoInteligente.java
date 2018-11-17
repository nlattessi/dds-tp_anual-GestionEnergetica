package domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import models.ModelHelper;

@Entity
@Table(name = "historial_estado_dispositivo_inteligente")
public class HistorialEstadoDispositivoInteligente extends EntidadPersistente {
	private static ModelHelper model = new ModelHelper();
	
	@ManyToOne
	@JoinColumn(name = "dispositivo_inteligente_id", referencedColumnName = "id")
	private DispositivoInteligente dispositivoInteligente;
	
	public static void guardarHistorial(DispositivoInteligente dispositivo, Estados estadoActual, Estados nuevoEstado) {
		HistorialEstadoDispositivoInteligente historial = new HistorialEstadoDispositivoInteligente(dispositivo,
				estadoActual, nuevoEstado);
		model.agregar(historial);
	}

	public DispositivoInteligente getDispositivo() {
		return dispositivoInteligente;
	}

	public void setDispositivo(DispositivoInteligente dispositivo) {
		this.dispositivoInteligente = dispositivo;
	}

	public Estados getEstadoActual() {
		return estadoActual;
	}

	public void setEstadoActual(Estados estadoActual) {
		this.estadoActual = estadoActual;
	}

	public Estados getNuevoEstado() {
		return nuevoEstado;
	}

	public void setNuevoEstado(Estados nuevoEstado) {
		this.nuevoEstado = nuevoEstado;
	}

	public HistorialEstadoDispositivoInteligente(DispositivoInteligente dispositivo, Estados estadoActual,
			Estados nuevoEstado) {
		this.dispositivoInteligente = dispositivo;
		this.estadoActual = estadoActual;
		this.nuevoEstado = nuevoEstado;
	}


	
	private Estados estadoActual;
	private Estados nuevoEstado;
}
