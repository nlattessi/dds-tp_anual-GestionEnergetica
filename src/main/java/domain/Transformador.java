package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Transformador {
	private int id;
	private List <Cliente> clientes = new ArrayList<Cliente>(); 
	private List <Cliente> clientesConectados = new ArrayList<Cliente>(); 
	private Zona zona; 
		
	
	public double cantidadDeEnergiaSuministrada() {
		double consumoTotal=0;
		for(Cliente cliente : this.clientes) {
			//consumoTotal = consumoTotal + cliente.calcularConsumo();
		}
		return consumoTotal;
	}
	
	public void setearClientes() {
		for(Cliente cliente: this.clientes ) {
			//cliente.setTransformador(this);
			clientesConectados.add(cliente);
		}
	}
	
	public void setZona(Zona zonaElegida) {
		zona = zonaElegida;
	}
	
//falta implementar, lo dejo asi para que no rompa
	public boolean estaDentro(int radio, String domicilio) {
		return true;
	}
	
	public boolean validarRadio(int radio) {
		for(Cliente cliente : this.clientes) {
		return this.estaDentro(radio, cliente.getDomicilio()); 
			
		}
		return false;
	}

}

