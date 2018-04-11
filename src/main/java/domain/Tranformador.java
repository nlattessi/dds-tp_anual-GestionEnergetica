package domain;

import java.util.*;

public class Tranformador {

	private int idTranformador;
	private ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	private boolean estado;

	public Tranformador(int idTranformador, ArrayList<Cliente> clientes, boolean estado) {
		super();
		this.idTranformador = idTranformador;
		this.clientes = clientes;
		this.estado = estado;
	}

	public int getIdTranformador() {
		return idTranformador;
	}

	public void setIdTranformador(int idTranformador) {
		this.idTranformador = idTranformador;
	}

	public ArrayList<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(ArrayList<Cliente> clientes) {
		this.clientes = clientes;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public void calcularConsumoTotal() {

	}

}
