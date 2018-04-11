package domain;

public class Dispositivo {// Hay q ver como se va a calcular el consumo

	private int idDispositivo;
	private String nombre;
	private int consumoXHora;// duda de interpretacion con el enunciado
	private boolean prendido_Apagado;
	private Cliente cliente;

	public Dispositivo(int dispositivo, String nombre, int consumoXHora, boolean prendido_Apagado, Cliente cliente) {
		super();
		this.idDispositivo = dispositivo;
		this.nombre = nombre;
		this.consumoXHora = consumoXHora;
		this.prendido_Apagado = prendido_Apagado;
		this.cliente = cliente;
	}

	public int getIdDispositivo() {
		return idDispositivo;
	}

	public void setDispositivo(int idDispositivo) {
		this.idDispositivo = idDispositivo;
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

	public boolean isPrendido_Apagado() {
		return prendido_Apagado;
	}

	public void setPrendido_Apagado(boolean prendido_Apagado) {
		this.prendido_Apagado = prendido_Apagado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void calcularConsumo() {

	}
}
