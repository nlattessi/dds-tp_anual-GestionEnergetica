package domain;

public abstract class Fabricante {
	private String nombre;
	private String identificadorDeFabrica;
	
	public Fabricante(String nombre, String identificadorDeFabrica)
	{
		this.nombre = nombre;
		this.identificadorDeFabrica = identificadorDeFabrica;
	}
	
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getIdentificadorDeFabrica() {
		return this.identificadorDeFabrica;
	}
	
	public void setIdentificadorDeFabrica(String id) {
		this.identificadorDeFabrica = id;
	}
}
