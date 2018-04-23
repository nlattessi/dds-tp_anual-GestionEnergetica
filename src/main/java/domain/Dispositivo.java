package domain;

public class Dispositivo {

	private int id;
	private String nombre;
	private int consumoXHora;
	private boolean encendido;

	public Dispositivo(int dispositivo, String nombre, int consumoXHora, boolean encendido) {
		this.id = dispositivo;
		this.nombre = nombre;
		this.consumoXHora = consumoXHora;
		this.encendido = encendido;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getConsumoXHora() {
		return consumoXHora;
	}

	public void setConsumoXHora(int consumoXHora) {
		this.consumoXHora = consumoXHora;
	}

	public boolean getEncendido() {
		return encendido;
	}

	public void setEncendido(boolean encendido) {
		this.encendido = encendido;
	}
}
