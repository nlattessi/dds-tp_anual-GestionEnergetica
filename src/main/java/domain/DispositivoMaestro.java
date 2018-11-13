package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity(name = "DispositivoMaestro")
@Table(name = "dispositivo_maestro")
public class DispositivoMaestro {

	// Variables
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	protected int id;

	@Version
	@Column
	protected Long version;

	@Column
	private CategoriaDispositivo categoria;

	@Column
	private String nombre;

	@Column(columnDefinition = "BOOLEAN")
	private boolean esInteligente;

	@Column(columnDefinition = "BOOLEAN")
	private boolean esBajoConsumo;

	@Column
	private double consumo;

	@OneToMany(mappedBy = "maestro", fetch = FetchType.EAGER)
	private List<Dispositivo> dispositivos = new ArrayList<Dispositivo>();

	public DispositivoMaestro(CategoriaDispositivo categoria, String nombre, boolean esInteligente,
			boolean esBajoConsumo, double consumoXHora) {
		this.categoria = categoria;
		this.nombre = nombre;
		this.esInteligente = esInteligente;
		this.esBajoConsumo = esBajoConsumo;
		this.consumo = consumoXHora;
	}

	public DispositivoMaestro() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void addDispositivo(Dispositivo dispositivo) {
		this.dispositivos.add(dispositivo);
		dispositivo.setMaestro(this);
	}

	public CategoriaDispositivo getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDispositivo categoria) {
		this.categoria = categoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isEsInteligente() {
		return esInteligente;
	}

	public void setEsInteligente(boolean esInteligente) {
		this.esInteligente = esInteligente;
	}

	public boolean isEsBajoConsumo() {
		return esBajoConsumo;
	}

	public void setEsBajoConsumo(boolean esBajoConsumo) {
		this.esBajoConsumo = esBajoConsumo;
	}

	public double getConsumo() {
		return consumo;
	}

	public void setConsumo(double consumo) {
		this.consumo = consumo;
	}
}
