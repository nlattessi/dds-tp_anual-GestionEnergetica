package domain;

public class Medicion {
	private String magnitud;
	private int valor;

	public Medicion(String magnitud, int valor) {
		this.magnitud = magnitud;
		this.valor = valor;
	}

	public String getMagnitud() {
		return magnitud;
	}

	public void setMagnitud(String magnitud) {
		this.magnitud = magnitud;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
}
