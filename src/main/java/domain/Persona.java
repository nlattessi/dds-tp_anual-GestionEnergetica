package domain;

public class Persona {

	protected String nombreYApellido;
	protected Domicilio domicilio;
	protected Documento documento;
	protected Usuario usuario;

	public Persona(String nombreYApellido, Domicilio domicilio, Documento documento, Usuario usuario) {
		super();
		this.nombreYApellido = nombreYApellido;
		this.domicilio = domicilio;
		this.documento = documento;
		this.usuario = usuario;
	}

	public String getNombreYApellido() {
		return nombreYApellido;
	}

	public void setNombreYApellido(String nombreYApellido) {
		this.nombreYApellido = nombreYApellido;
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
