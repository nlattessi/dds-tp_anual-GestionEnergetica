package domain;

public class Usuario {

	private int idUsuario;
	private String nombre;
	private String contrasena;

	public Usuario(int idUsuario, String nombre, String contrasena) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.contrasena = contrasena;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

}
