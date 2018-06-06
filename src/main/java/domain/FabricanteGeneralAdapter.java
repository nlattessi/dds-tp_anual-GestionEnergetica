package domain;

public class FabricanteGeneralAdapter implements Fabricante {

	private String nombre;
	private int idFabricante;

	public FabricanteGeneralAdapter() {
		this.nombre = "generico";
		this.idFabricante = 0;
	}

	@Override
	public void enviarMensajeApagado() {
	}

	@Override
	public void enviarMensajeEncendido() {
	}

	@Override
	public void enviarMensajeModoAhorroEnergia() {
	}

	@Override
	public String recibirMensajeStatus() {
		return null;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getIdFabricante() {
		return idFabricante;
	}

	public void setIdFabricante(int idFabricante) {
		this.idFabricante = idFabricante;
	}

}
