package domain;

public class Dispositivo {

	private int id;
	private String nombre;
	private int consumoXHora;
	private boolean estado;

	public Dispositivo(int dispositivo, String nombre, int consumoXHora, boolean estado) {
		super();
		this.id = dispositivo;
		this.nombre = nombre;
		this.consumoXHora = consumoXHora;
		this.estado = estado;
	}

	public int getIdDispositivo() {
		return id;
	}

	public void setDispositivo(int id) {
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

	public boolean getEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
}
