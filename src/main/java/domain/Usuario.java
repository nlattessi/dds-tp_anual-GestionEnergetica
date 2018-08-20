package domain;

public abstract class Usuario {

	protected int id;
	protected String nombreUsuario;
	protected String contrase�a;
	protected String nombreYApellido;
	protected String domicilio;

	public Usuario(int id, String nombreUsuario, String contrase�a, String nombreYApellido, String domicilio) {
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.contrase�a = contrase�a;
		this.nombreYApellido = nombreYApellido;
		this.domicilio = domicilio;
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

	public String getContrase�a() {
		return this.contrase�a;
	}

	public void setContrase�a(String contrase�a) {
		this.contrase�a = contrase�a;
	}

	public String getNombreYApellido() {
		return nombreYApellido;
	}

	public void setNombreYApellido(String nombreYApellido) {
		this.nombreYApellido = nombreYApellido;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
}