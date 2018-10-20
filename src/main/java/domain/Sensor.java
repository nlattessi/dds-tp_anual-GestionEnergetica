package domain;

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
@Table(name = "sensor")
public class Sensor extends EntidadPersistente implements Subject {
	@Transient
	private List<Observer> observers;
	
	@ManyToOne
	@JoinColumn(name = "sensor_id", referencedColumnName = "id")
	private Medicion medicion;
	
	@OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Reglamentador> reglamentadores;
	
	@OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Medicion> mediciones;

	public Sensor() {
		this.observers = new ArrayList<>();
		this.mediciones = new ArrayList<>();
		this.reglamentadores = new ArrayList<>();
	}

	@Override
	public void registerObserver(Observer o) {
		this.observers.add(o);
	}
	
	public void registerReglamentador(Reglamentador r) {
		this.reglamentadores.add(r);
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
		for (Reglamentador reglamentador: this.reglamentadores) {
			reglamentador.update(medicion);
		}
	}

	public void setMedicion(Medicion medicion) {
		this.medicion = medicion;
		this.notifyObservers();
	}
	
	public void agregarMedicion(Medicion medicion) {
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
