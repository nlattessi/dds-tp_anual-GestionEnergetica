package domain;

public class Domicilio {

	private String calle;
	private int numero;
	private int codigoPostal;
	private String ciudad;
	private String provincia;

	public Domicilio(String calle, int numero, int codigoPostal, String ciudad, String provincia) {
		super();
		this.calle = calle;
		this.numero = numero;
		this.codigoPostal = codigoPostal;
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

	public int getCodigoPosta() {
		return codigoPostal;
	}

	public void setCodigoPostal(int codigoPosta) {
		this.codigoPostal= codigoPosta;
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
