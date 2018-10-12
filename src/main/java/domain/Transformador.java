package domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "transformador")
public class Transformador extends EntidadPersistente{
//	private int id;
	
	@OneToMany(mappedBy = "transformador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Cliente> clientesConectados = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "zona_id", referencedColumnName = "id")
	private Zona zona;
	
	private int geolocalizacionX = 0;
	private int geolocalizacionY = 0;
	
	@Transient
	Locacion coordenadas;

//	public Transformador(int id, Zona zona, int x, int y) {
	public Transformador(Zona zona, int x, int y) {
//		this.id = id;
		this.zona = zona;
		
		this.geolocalizacionX = x;
		this.geolocalizacionY = y;
		this.coordenadas = new Locacion(this.geolocalizacionX, this.geolocalizacionY);
	}

	public Locacion getCoordenadas() {
		return coordenadas;
	}

	public Zona getZona() {
		return zona;
	}

	public double cantidadDeEnergiaSuministrada() {
		double consumoTotal = 0;
		for (Cliente cliente : this.clientesConectados) {
			consumoTotal = consumoTotal + cliente.calcularConsumo();
		}
		return consumoTotal;
	}
	
	public double consumoPromedioEntre(LocalDateTime inicio, LocalDateTime fin) {
		double consumoPromedio = 0;
		for (Cliente cliente : this.clientesConectados) {
			consumoPromedio = consumoPromedio + cliente.calcularConsumoEntrePeriodos(inicio, fin);
		}
		return (consumoPromedio / Duration.between(inicio, fin).toDays());
	}

	public void setearCliente(Cliente cliente) {
		cliente.setTransformador(this);
	}

	public void setZona(Zona zonaElegida) {
		zona = zonaElegida;
	}

	public void agregarCliente(Cliente cliente) {
		clientesConectados.add(cliente);
	}
	
	public List<Cliente> getClientesConectados() {
		return this.clientesConectados;
	}

//	public int getId() {
//		return id;
//	}
}
