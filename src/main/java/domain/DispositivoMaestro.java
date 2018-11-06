package domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class DispositivoMaestro extends EntidadPersistente {

	private CategoriaDispositivo categoria;
	private String nombre;

	@Column(columnDefinition = "BOOLEAN")
	private boolean esInteligente;

	@Column(columnDefinition = "BOOLEAN")
	private boolean esBajoConsumo;

	private double consumo;

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
