package domain;

import java.util.HashMap;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "actuador")
public class Actuador extends EntidadPersistente {
	public DispositivoInteligente getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(DispositivoInteligente dispositivo) {
		this.dispositivo = dispositivo;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "dispositivo_inteligente_id", referencedColumnName = "id")
	private DispositivoInteligente dispositivo;
	
	@Transient
	private HashMap<Acciones, Command> listaComandos;

	public Actuador(DispositivoInteligente dispositivo) {
		this.dispositivo = dispositivo;
		this.listaComandos = new HashMap<Acciones, Command>();
	}

	public void agregarAccion(Acciones accion, Command command) {
		this.listaComandos.put(accion, command);
	}

	public void ejecutarAccion(Acciones accion) {
		this.listaComandos.get(accion).execute(this.dispositivo);
	}
}
