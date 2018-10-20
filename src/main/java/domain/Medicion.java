package domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import models.ModelHelper;

@Entity
@Table(name = "medicion")
public class Medicion extends EntidadPersistente{
	private String magnitud;
	private int valor;
	
	private static ModelHelper model = new ModelHelper();
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "sensor_id", referencedColumnName = "id")
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
