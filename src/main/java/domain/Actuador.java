package domain;

import java.util.HashMap;

public class Actuador {
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
