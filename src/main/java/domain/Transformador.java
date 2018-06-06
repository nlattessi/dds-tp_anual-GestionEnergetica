package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Transformador {
	private int id;

	private List <Cliente> clientesConectados = new ArrayList<Cliente>(); 
	private List <Cliente> clientes = new ArrayList<Cliente>();
	private Zona zona; 
	private Locacion coordenadas; 
	
	public Transformador(int id, Zona zona, int x, int y) {
		this.id = id;
		this.zona = zona;
		this.coordenadas.x = x;
		this.coordenadas.y = y; 
	}
		
	
	
	public double cantidadDeEnergiaSuministrada() {
		double consumoTotal=0;
		for(Cliente cliente : this.clientesConectados) {
		//consumoTotal = consumoTotal + cliente.calcularConsumo();
		}
		return consumoTotal;
	}
	
	public void setearCliente(Cliente cliente) {
		cliente.setTransformadorId(this.id);
	}
	
	public void setZona(Zona zonaElegida) {
		zona = zonaElegida;
	}
	
	
	public int getId() {
		return id;
	}
	


	



}

