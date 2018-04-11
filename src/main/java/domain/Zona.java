package domain;

import java.util.*;

public class Zona {

	private int nroZona;
	private String nombre;
	private ArrayList<Tranformador> transformadores = new ArrayList<Tranformador>();

	public Zona(int nroZona, String nombre, ArrayList<Tranformador> transformadores) {
		super();
		this.nroZona = nroZona;
		this.nombre = nombre;
		this.transformadores = transformadores;
	}

	public int getNroZona() {
		return nroZona;
	}

	public void setNroZona(int nroZona) {
		this.nroZona = nroZona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Tranformador> getTransformadores() {
		return transformadores;
	}

	public void setTransformadores(ArrayList<Tranformador> transformadores) {
		this.transformadores = transformadores;
	}

	public void calcularConsumoTotalZona() {

	}

}
