package domain;

public class Documento {

	private int id;
	private TipoDocumento tipo;
	private int numero;

	public Documento(int id, TipoDocumento tipo, int numero) {
		this.id = id;
		this.tipo = tipo;
		this.numero = numero;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TipoDocumento getTipo() {
		return tipo;
	}

	public void setTipo(TipoDocumento tipo) {
		this.tipo = tipo;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

}
