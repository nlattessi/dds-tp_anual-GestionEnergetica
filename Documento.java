
public class Documento {
	
	private int idDocumento;
	private String tipo;
	private int numero;
		
	public Documento(int idDocumento, String tipo, int numero) {
		super();
		this.idDocumento = idDocumento;
		this.tipo = tipo;
		this.numero = numero;
	}

	public int getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(int idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}


}
