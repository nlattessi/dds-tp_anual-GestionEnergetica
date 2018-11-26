package domain;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

//	@Column
//	private String nombre;

//	@Column
//	private double consumoXHora;

	@Column
	protected int usoMensualMinimoHoras;

	@Column
	protected int usoMensualMaximoHoras;

	@Column
	protected double consumoRecomendadoHoras;

	@Column(columnDefinition = "BOOLEAN")
	protected boolean permiteAhorroInteligente;

	@Column(columnDefinition = "BOOLEAN")
	protected boolean permiteCalculoAhorro;

//	@Column(columnDefinition = "BOOLEAN")
//	private boolean bajoConsumo;

//	@Column
//	private CategoriaDispositivo categoria;

	@ManyToOne
	@JoinColumn(name = "cliente_id", referencedColumnName = "id")
	protected Cliente cliente;

//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "reglamentador_id", referencedColumnName = "id")
//	private Reglamentador reglamentador;

	@ManyToOne
	@JoinColumn(name = "dispositivo_maestro_id")
	protected DispositivoMaestro maestro;

	@OneToMany(mappedBy = "dispositivo", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	protected List<Periodo> periodos = new ArrayList<>();

	// Constructores
	public Dispositivo() {
	}

	public Dispositivo(String nombre, double consumoXHora) {
//		this.nombre = nombre;
//		this.consumoXHora = consumoXHora;
		this.consumoRecomendadoHoras = 0;
		this.permiteAhorroInteligente = false;
		this.permiteCalculoAhorro = true;
	}

	public Dispositivo(DispositivoMaestro maestro) {
		this.maestro = maestro;
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

	public List<Periodo> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(List<Periodo> periodos) {
		this.periodos = periodos;
	}

	public DispositivoMaestro getMaestro() {
		return maestro;
	}

	public void setMaestro(DispositivoMaestro maestro) {
		this.maestro = maestro;
	}

	public CategoriaDispositivo getCategoria() {
//		return categoria;
		return maestro.getCategoria();
	}

	public void setCategoria(CategoriaDispositivo categoria) {
//		this.categoria = categoria;
		maestro.setCategoria(categoria);
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
//		return nombre;
		return maestro.getNombre();
	}

	public void setNombre(String nombre) {
//		this.nombre = nombre;
		maestro.setNombre(nombre);
	}

	public double getConsumoXHora() {
//		return consumoXHora;
		return maestro.getConsumo();
	}

	public void setConsumoXHora(double consumoXHora) {
//		this.consumoXHora = consumoXHora;
		maestro.setConsumo(consumoXHora);
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
//		return this.bajoConsumo;
		return maestro.isEsBajoConsumo();
	}

	public void setBajoConsumo(boolean bajoConsumo) {
//		this.bajoConsumo = bajoConsumo;
		maestro.setEsBajoConsumo(bajoConsumo);
	}

	public double getConsumoUltimoMes() {
		Calendar c = Calendar.getInstance();

		LocalDateTime fin = toLocalDateTime(c);

		c.add(Calendar.MONTH, -1);

		LocalDateTime inicio = toLocalDateTime(c);

		return consumoTotalComprendidoEntre(inicio, fin);

	}

	public String getUrlBorrar() {
		return "/cliente/dispositivos/" + id + "/borrar";
	}

	public String getUrlEditar() {
		return "/cliente/dispositivos/" + id + "/editar";
	}

	public String getUrlVerConsumo() {
		return "/administrador/hogares-consumos/" + id;
	}

	public static LocalDateTime toLocalDateTime(Calendar calendar) {
		if (calendar == null) {
			return null;
		}
		TimeZone tz = calendar.getTimeZone();
		ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
		return LocalDateTime.ofInstant(calendar.toInstant(), zid);
	}
}
