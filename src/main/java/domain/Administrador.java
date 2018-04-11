package domain;

public class Administrador extends Persona {

	private int idAdministrador;
	private String fechaAltaSistema;

	public Administrador(String nombreYApellido, Domicilio domicilio, Documento documento, Usuario usuario,
			int idAdministrador, String fechaAltaSistema) {
		super(nombreYApellido, domicilio, documento, usuario);
		this.idAdministrador = idAdministrador;
		this.fechaAltaSistema = fechaAltaSistema;
	}

	public int getIdAdministrador() {
		return idAdministrador;
	}

	public void setIdAdministrador(int idAdministrador) {
		this.idAdministrador = idAdministrador;
	}

	public String getFechaAltaSistema() {
		return fechaAltaSistema;
	}

	public void setFechaAltaSistema(String fechaAltaSistema) {
		this.fechaAltaSistema = fechaAltaSistema;
	}

	public void cantidadMesesAdministrador() {

	}

}
