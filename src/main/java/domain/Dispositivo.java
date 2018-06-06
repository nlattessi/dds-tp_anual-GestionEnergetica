package domain;

import java.time.LocalDateTime;

public abstract class Dispositivo {
	private int id;
	private String nombre;
	private double consumoXHora;
	private int usoMensualMinimoHoras;
	private int usoMensualMaximoHoras;
	private double consumoRecomendadoHoras;
	private boolean permiteAhorroInteligente;
	private boolean permiteCalculoAhorroInteligente;

	public Dispositivo(int dispositivo, String nombre, double consumoXHora) {
		this.id = dispositivo;
		this.nombre = nombre;
		this.consumoXHora = consumoXHora;
		this.consumoRecomendadoHoras = 0;
		this.permiteAhorroInteligente = false;
		this.permiteCalculoAhorroInteligente = true;
	}
	
	public abstract void apagarse();
	
	public abstract double consumoTotalComprendidoEntre(LocalDateTime inicio, LocalDateTime fin);
	
	public abstract Estados getEstado();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getConsumoXHora() {
		return consumoXHora;
	}

	public void setConsumoXHora(double consumoXHora) {
		this.consumoXHora = consumoXHora;
	}
	
	public int getUsoMensualMinimoHoras() {
		return usoMensualMinimoHoras;
	}

	public void setUsoMensualMinimoHoras(int usoMensualMinimoHoras) {
		this.usoMensualMinimoHoras = usoMensualMinimoHoras;
	}
	
	public int getUsoMensualMaximoHoras() {
		return usoMensualMaximoHoras;
	}

	public void setUsoMensualMaximoHoras(int usoMensualMaximoHoras) {
		this.usoMensualMaximoHoras = usoMensualMaximoHoras;
	}
	
	public double getConsumoRecomendadoHoras() {
		return consumoRecomendadoHoras;
	}

	public void setConsumoRecomendadoHoras(double consumoRecomendadoHoras) {
		this.consumoRecomendadoHoras = consumoRecomendadoHoras;
	}
	
	public boolean getPermiteAhorroInteligente() {
		return permiteAhorroInteligente;
	}

	public void setPermiteAhorroInteligente(boolean permiteAhorroInteligente) {
		this.permiteAhorroInteligente = permiteAhorroInteligente;
	}
	
	public boolean getPermiteCalculoAhorroInteligente() {
		return permiteCalculoAhorroInteligente;
	}

	public void setPermiteCalculoAhorroInteligente(boolean permiteCalculoAhorroInteligente) {
		this.permiteCalculoAhorroInteligente = permiteCalculoAhorroInteligente;
	}
}
