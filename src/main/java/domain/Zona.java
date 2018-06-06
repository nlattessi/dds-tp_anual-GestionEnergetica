package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Zona {
	private int id;
	private List<Transformador> transformadores = new ArrayList<Transformador>();
	private int radio;
	private Locacion coordenadas;

	public Zona(int id, int radio, int x, int y) {
		this.id = id;
		this.radio = radio;
		this.coordenadas = new Locacion(x, y);
	}

	public int getId() {
		return id;
	}

	public int getRadio() {
		return radio;
	}

	public Locacion getCoordenadas() {
		return coordenadas;
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

	public void conectarCercano(Cliente cliente, Transformador transformador) {
				cliente.setTransformadorId(transformador.getId());
				transformador.agregarCliente(cliente);
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
		}this.conectarCercano(cliente, minimoTransf);
	
	}
}
