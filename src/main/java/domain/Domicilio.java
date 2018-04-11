package domain;

public class Domicilio {

	private String calle;
	private int numero;
	private String barrio;
	private String ciudad;
	private String provincia;

	public Domicilio(String calle, int numero, String barrio, String ciudad, String provincia) {
		super();
		this.calle = calle;
		this.numero = numero;
		this.barrio = barrio;
		this.ciudad = ciudad;
		this.provincia = provincia;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

}
