package domain;

public class Documento {
	
	public enum Tipo {
		DNI, CI, LE, LC
	}

	private Tipo tipo;
	private int numero;

	public Documento(Tipo tipo, int numero) {
		this.tipo = tipo;
		this.numero = numero;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

}
