package domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Dispositivo extends EntidadPersistente{
	//private int id;
	private String nombre;
	private double consumoXHora;
	private int usoMensualMinimoHoras;
	private int usoMensualMaximoHoras;
	private double consumoRecomendadoHoras;
	private boolean permiteAhorroInteligente;
	private boolean permiteCalculoAhorro;
	private boolean bajoConsumo;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id", referencedColumnName = "id")
	private Cliente cliente;

//	public Dispositivo(int dispositivo, String nombre, double consumoXHora) {
	public Dispositivo(String nombre, double consumoXHora) {
//		this.id = dispositivo;
		this.nombre = nombre;
		this.consumoXHora = consumoXHora;
		this.consumoRecomendadoHoras = 0;
		this.permiteAhorroInteligente = false;
		this.permiteCalculoAhorro = true;
	}
		
	public abstract double HorasTotalComprendidoEntre(LocalDateTime inicio, LocalDateTime fin);
	
	public abstract double consumoTotalComprendidoEntre(LocalDateTime inicio, LocalDateTime fin);
	
	public abstract void apagarse();
	
	public abstract void encenderse();
	
	public abstract Estados getEstado();

//	public int getId() {
//		return id;
//	}

//	public void setId(int id) {
//		this.id = id;
//	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
	
	public boolean getPermiteCalculoAhorro() {
		return permiteCalculoAhorro;
	}
	
	public void setPermiteCalculoAhorro(boolean permiteCalculoAhorro) {
		this.permiteCalculoAhorro = permiteCalculoAhorro;
	}
	
	public boolean getBajoConsumo() {
		return this.bajoConsumo;
	}
	
	public void setBajoConsumo(boolean bajoConsumo) {
		this.bajoConsumo = bajoConsumo;
	}
}
