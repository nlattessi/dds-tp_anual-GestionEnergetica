package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "zona")
public class Zona extends EntidadPersistente{
//	private int id;
	
	@OneToMany(mappedBy = "zona", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Transformador> transformadores = new ArrayList<>();
	
	private int radio;
	private int geolocalizacionX = 0;
	private int geolocalizacionY = 0;
	
	@Transient
	private Locacion coordenadas;
	
	@Column
	private float latitud;
	
	@Column
	private float longitud;

//	public Zona(int id, int radio, int x, int y) {
	public Zona(int radio, int x, int y) {
//		this.id = id;
		this.radio = radio;
		this.geolocalizacionX = x;
		this.geolocalizacionY = y;
		this.coordenadas = new Locacion(this.geolocalizacionX, this.geolocalizacionY);
	}
	
	public Zona() {
		
	}

//	public int getId() {
//		return id;
//	}

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
//				cliente.setTransformadorId(transformador.getId());
		cliente.setTransformador(transformador);
		transformador.agregarCliente(cliente);
	}

	public void obtenerMasCercano(Cliente cliente) {
		double minimoDistancia = 100000;
		Transformador minimoTransf =  null;
		for (Transformador transformador : this.transformadores) {
			double distancia = coordenadas.obtenerDistancia(transformador.coordenadas.x, transformador.coordenadas.y, cliente.getCoordenadasDomicilio().x, cliente.getCoordenadasDomicilio().y);
			if( distancia<minimoDistancia) {
				minimoDistancia= distancia;
				minimoTransf = transformador;
			}
		}this.conectarCercano(cliente, minimoTransf);
	
	}
}
