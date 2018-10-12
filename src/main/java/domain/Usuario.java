package domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Usuario extends EntidadPersistente{

	//protected int id;
	protected String nombreUsuario;
	protected String contraseña;
	protected String nombreYApellido;
	protected String domicilio;

//	public Usuario(int id, String nombreUsuario, String contraseña, String nombreYApellido, String domicilio) {
	public Usuario(String nombreUsuario, String contraseña, String nombreYApellido, String domicilio) {
//		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.contraseña = contraseña;
		this.nombreYApellido = nombreYApellido;
		this.domicilio = domicilio;
	}

//	public int getId() {
//		return this.id;
//	}

//	public void setId(int id) {
//		this.id = id;
//	}

	public String getNombreUsuario() {
		return this.nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContraseña() {
		return this.contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
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