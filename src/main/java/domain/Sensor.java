package domain;

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

@Entity(name = "Sensor")
@Table(name = "sensor")
public class Sensor implements Subject {

	// Variables
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	protected int id;

	@Version
	@Column
	protected Long version;

	@Transient
	private List<Observer> observers = new ArrayList<Observer>();

//	@ManyToOne
//	@JoinColumn(name = "sensor_id", referencedColumnName = "id")
	@Transient
	private Medicion medicion;

	@OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Reglamentador> reglamentadores = new ArrayList<Reglamentador>();

	@OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Medicion> mediciones = new ArrayList<Medicion>();

	public Sensor() {

	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void registerObserver(Observer o) {
		this.observers.add(o);
	}

	public void registerReglamentador(Reglamentador r) {
		this.reglamentadores.add(r);
		r.setSensor(this);
	}

	@Override
	public void removeObserver(Observer o) {
		this.observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for (Observer observer : this.observers) {
			observer.update(this.medicion);
		}
	}

	public void notifyReglamentadores(Medicion medicion) {
		for (Reglamentador reglamentador : this.reglamentadores) {
			reglamentador.update(medicion);
		}
	}

	public void setMedicion(Medicion medicion) {
		this.medicion = medicion;
		this.notifyObservers();
	}

	public void agregarMedicion(Medicion medicion) {
		medicion.setSensor(this);
		Medicion.guardarMedicion(this, medicion);
		this.mediciones.add(medicion);
		this.notifyReglamentadores(medicion);
	}

	public List<Reglamentador> getReglamentadores() {
		return reglamentadores;
	}

	public void setReglamentadores(List<Reglamentador> reglamentadores) {
		this.reglamentadores = reglamentadores;
	}
}
