package domain;

public abstract class Dispositivo {
	private int id;
	private String nombre;
	private int consumoXHora;

	public Dispositivo(int dispositivo, String nombre, int consumoXHora) {
		this.id = dispositivo;
		this.nombre = nombre;
		this.consumoXHora = consumoXHora;
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

	public void sumarPuntos(Cliente cliente) {
		
	}
}
