package domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import models.ModelHelper;

@Entity
@Table(name = "historial_conversion_estandar_inteligente")
public class HistorialConversionEstandarInteligente extends EntidadPersistente {
	private static ModelHelper model = new ModelHelper();

	public static void guardarHistorial(Cliente cliente, DispositivoEstandar estandar,
			DispositivoInteligente inteligente) {
		HistorialConversionEstandarInteligente historial = new HistorialConversionEstandarInteligente(cliente, estandar,
				inteligente);
		model.agregar(historial);
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public DispositivoEstandar getEstandar() {
		return estandar;
	}

	public void setEstandar(DispositivoEstandar estandar) {
		this.estandar = estandar;
	}

	public DispositivoInteligente getInteligente() {
		return inteligente;
	}

	public void setInteligente(DispositivoInteligente inteligente) {
		this.inteligente = inteligente;
	}

	public HistorialConversionEstandarInteligente(Cliente cliente, DispositivoEstandar estandar,
			DispositivoInteligente inteligente) {
		this.cliente = cliente;
		this.estandar = estandar;
		this.inteligente = inteligente;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cliente_id", referencedColumnName = "id")
	private Cliente cliente;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "estandar_id", referencedColumnName = "id")
	private DispositivoEstandar estandar;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "inteligente_id", referencedColumnName = "id")
	private DispositivoInteligente inteligente;
}
