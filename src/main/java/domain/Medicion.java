package domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import models.ModelHelper;

@Entity(name = "Medicion")
@Table(name = "medicion")
public class Medicion {

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// Variables
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;

	@Version
	@Column
	private Long version;

	private String magnitud;
	private int valor;

	private static ModelHelper model = new ModelHelper();

//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "sensor_id", referencedColumnName = "id")
	@ManyToOne
	private Sensor sensor;

	public static void guardarMedicion(Sensor sensor, Medicion medicion) {
		medicion.setSensor(sensor);
		model.agregar(medicion);
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public Medicion(String magnitud, int valor) {
		this.magnitud = magnitud;
		this.valor = valor;
	}

	public String getMagnitud() {
		return magnitud;
	}

	public void setMagnitud(String magnitud) {
		this.magnitud = magnitud;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
}
