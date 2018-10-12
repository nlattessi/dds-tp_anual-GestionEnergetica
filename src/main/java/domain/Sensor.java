package domain;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sensor")
public class Sensor extends EntidadPersistente implements Subject {
	private ArrayList<Observer> observers;
	private Medicion medicion;

	public Sensor() {
		this.observers = new ArrayList<Observer>();
	}

	@Override
	public void registerObserver(Observer o) {
		this.observers.add(o);
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

	public void setMedicion(Medicion medicion) {
		this.medicion = medicion;
		this.notifyObservers();
	}
}
