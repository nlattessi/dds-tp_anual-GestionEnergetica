package domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

@Entity(name = "Transformador")
@Table(name = "transformador")
public class Transformador {

	// Variables
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	protected int id;

	@Version
	@Column
	protected Long version;

	@OneToMany(mappedBy = "transformador", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Cliente> clientesConectados = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "zona_id", referencedColumnName = "id")
	private Zona zona;

	private int geolocalizacionX = 0;
	private int geolocalizacionY = 0;
	
	@Column
	private float latitud;
	
	@Column
	private float longitud;;

//	@Column(name = "locacion", nullable = false, columnDefinition = "Point")
//	private Point locacion;
	
//	@Formula("ST_ASTEXT(locacion)")
//	private String dataGeo;
	
//	public Point getLocacion() {
//		try {
//			WKTReader reader = new WKTReader();
//			return reader.read(dataGeo);
//		} catch (ParseException ex) {
//			return null;
//		}
//	}

	@Transient
	Locacion coordenadas;

public float getLatitud() {
		return latitud;
	}

	public void setLatitud(float latitud) {
		this.latitud = latitud;
	}

	public float getLongitud() {
		return longitud;
	}

	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}

	//	public Transformador(int id, Zona zona, int x, int y) {
	public Transformador(Zona zona, int x, int y) {
//		this.id = id;
		this.zona = zona;

		this.geolocalizacionX = x;
		this.geolocalizacionY = y;
		this.coordenadas = new Locacion(this.geolocalizacionX, this.geolocalizacionY);
	}

	public Transformador() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
//
//	public Point getLocacion() {
//		return locacion;
//	}
//
//	public void setLocacion(Point locacion) {
//		this.locacion = locacion;
//	}
}
