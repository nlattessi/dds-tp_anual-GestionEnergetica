package domain;

public interface Fabricante {
	public void enviarMensajeApagado();

	public void enviarMensajeEncendido();

	public void enviarMensajeModoAhorroEnergia();
	
	public String recibirMensajeStatus();
}
