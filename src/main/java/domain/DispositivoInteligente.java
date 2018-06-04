package domain;

public class DispositivoInteligente extends Dispositivo implements Inteligente {
	private Estados estado;

	public DispositivoInteligente(int dispositivo, String nombre, int consumoXHora, boolean encendido) {
		super(dispositivo, nombre, consumoXHora, encendido);
		this.estado = Estados.APAGADO;
	}

	public Estados getEstado() {
		return estado;
	}

	public void setEstado(Estados estado) {
		this.estado = estado;
	}

	public void apagarse() {
		if (this.estado != Estados.APAGADO) {
			this.estado = Estados.APAGADO;
		}
	}

	public void encenderse() {
		if (this.estado != Estados.ENCENDIDO) {
			this.estado = Estados.ENCENDIDO;
		}
	}

	public void modoAhorroEnergia() {
		if (this.estado != Estados.MODO_AHORRO_ENERGIA) {
			this.estado = Estados.MODO_AHORRO_ENERGIA;
		}
	}
}
