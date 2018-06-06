package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Zona {
	private int id;
	private List<Transformador> transformadores = new ArrayList<Transformador>();
	private int radio; 
	private Locacion coordenadas;
	
	

	
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
	
	public void conectarCercano(Cliente cliente, int transformadorId) {
				cliente.setTransformadorId(transformadorId);
			}
		
	
	public void obtenerMasCercano(Cliente cliente) {
		double minimoDistancia = 100000;
		Transformador minimoTransf =  null;
		for (Transformador transformador : this.transformadores) {
			double distancia = coordenadas.obtenerDistancia(transformador.coordenadas.x, transformador.coordenadas.y, cliente.coordenadasDomicilio.x, cliente.coordenadasDomicilio.y);
			if( distancia<minimoDistancia) {
				minimoDistancia= distancia; 
				minimoTransf = transformador;
			}
		}this.conectarCercano(cliente, minimoTransf.getId());
	}
	
} 



	
