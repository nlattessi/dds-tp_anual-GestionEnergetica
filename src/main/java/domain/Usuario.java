package domain;

public class Usuario {

	protected int id;
	protected String nombreUsuario;
	protected String contrasenia;
	protected String nombreYApellido;
	protected Domicilio domicilio;
	protected Documento documento;

	public Usuario(int id, String nombreUsuario, String contrasenia, String nombreYApellido, Domicilio domicilio, Documento documento, Usuario usuario) {
		super();
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.contrasenia = contrasenia;
		this.nombreYApellido = nombreYApellido;
		this.domicilio = domicilio;
		this.documento = documento;

	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNombreUsuario() {
		return this.nombreUsuario;
	}
	
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	public String getContrasenia() {
		return this.contrasenia;
	}
	
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
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

}