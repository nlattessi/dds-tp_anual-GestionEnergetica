package domain;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity(name = "Dispositivo")
@Table(name = "dispositivo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER, name = "tipo_dispositivo")
public abstract class Dispositivo {

	// Variables
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	protected int id;

	@Version
	@Column
	protected Long version;

	@Column
	private String nombre;

	@Column
	private double consumoXHora;

	@Column
	private int usoMensualMinimoHoras;

	@Column
	private int usoMensualMaximoHoras;

	@Column
	private double consumoRecomendadoHoras;

	@Column(columnDefinition = "BOOLEAN")
	private boolean permiteAhorroInteligente;

	@Column(columnDefinition = "BOOLEAN")
	private boolean permiteCalculoAhorro;

	@Column(columnDefinition = "BOOLEAN")
	private boolean bajoConsumo;

	@Column
	private CategoriaDispositivo categoria;

	@ManyToOne
	@JoinColumn(name = "cliente_id", referencedColumnName = "id")
	private Cliente cliente;

//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "reglamentador_id", referencedColumnName = "id")
//	private Reglamentador reglamentador;
	
	// Constructores
	public Dispositivo() {
	}

	public Dispositivo(String nombre, double consumoXHora) {
		this.nombre = nombre;
		this.consumoXHora = consumoXHora;
		this.consumoRecomendadoHoras = 0;
		this.permiteAhorroInteligente = false;
		this.permiteCalculoAhorro = true;
	}

	// Getters - Setters
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CategoriaDispositivo getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDispositivo categoria) {
		this.categoria = categoria;
	}

	public abstract double HorasTotalComprendidoEntre(LocalDateTime inicio, LocalDateTime fin);

	public abstract double consumoTotalComprendidoEntre(LocalDateTime inicio, LocalDateTime fin);

	public abstract void apagarse();

	public abstract void encenderse();

	public abstract Estados getEstado();

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

	public String getUrlBorrar() {
		return "/cliente/dispositivos/" + id + "/borrar";
	}

	public String getUrlEditar() {
		return "/cliente/dispositivos/" + id + "/editar";
	}
}
