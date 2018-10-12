package domain;

import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "actuador")
public class Actuador extends EntidadPersistente {
	@ManyToOne
	@JoinColumn(name = "dispositivo_inteligente_id", referencedColumnName = "id")
	private DispositivoInteligente dispositivo;
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
