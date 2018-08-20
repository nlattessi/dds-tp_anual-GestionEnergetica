package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Zona {
	private int id;
	private List<Transformador> transformadores = new ArrayList<Transformador>();
	private int radio; 
	
	public Zona (int id, int radio) {
		super();
		this.id = id; 
		this.radio = radio;
		
	}
	
	public List<Transformador> getTransformadores() {
		return transformadores;
	}
	public double consumoTotalEnergia() {
		double consumoTotal=0;
		for(Transformador transformador : this.transformadores) {
			consumoTotal = consumoTotal + transformador.cantidadDeEnergiaSuministrada();
		}
		return consumoTotal;
	}
	
	public void setearTransformador() {
		for(Transformador transformador: this.transformadores ) {
			transformador.setZona(this);
		}
	}
	
	public void asociarTransfCliente() {
		for(Transformador transformador :this.transformadores) {
			if(transformador.validarRadio(radio)) {
				transformador.setearClientes();
			}
			
		}
	}
}