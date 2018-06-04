package domain;

import java.util.ArrayList;

public class Sensor implements Subject {
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
