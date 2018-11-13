package domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "regla")
public class ReglaGenerica implements Regla {

	// Variables
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;

	@Version
	@Column
	private Long version;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String nombreMagnitud;
	private int valor;
	private ComparacionesReglaGenerica comparacion;
	private Acciones accion;

//	@ManyToOne
//	@JoinColumn(name = "dispositivo_id", referencedColumnName = "id")
//	private Dispositivo dispositivo;

//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "reglamentador_id", referencedColumnName = "id")
	@ManyToOne
	private Reglamentador reglamentador;

	public static ReglaGenerica crearEncenderRegla(String nombreMagnitud, ComparacionesReglaGenerica comparacion,
			int valor) {
		ReglaGenerica encenderRegla = new ReglaGenerica();
		encenderRegla.setNombreMagnitud(nombreMagnitud);
		encenderRegla.setComparacion(comparacion);
		encenderRegla.setValor(valor);
		encenderRegla.setAccion(Acciones.ENCENDERSE);
		return encenderRegla;
	}

	public Acciones getAccion() {
		return accion;
	}

	public void setAccion(Acciones accion) {
		this.accion = accion;
	}

//	public Dispositivo getDispositivo() {
//		return dispositivo;
//	}
//
//	public void setDispositivo(Dispositivo dispositivo) {
//		this.dispositivo = dispositivo;
//	}

	public String getNombreMagnitud() {
		return nombreMagnitud;
	}

	public void setNombreMagnitud(String nombreMagnitud) {
		this.nombreMagnitud = nombreMagnitud;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public ComparacionesReglaGenerica getComparacion() {
		return comparacion;
	}

	public void setComparacion(ComparacionesReglaGenerica comparacion) {
		this.comparacion = comparacion;
	}

	@Override
	public boolean cumple(Medicion medicion) {
		return (sameMagnitud(medicion.getMagnitud()) && comparacionValor(medicion.getValor()));
	}

	@Override
	public void dispararAccion(Actuador actuador) {
		actuador.ejecutarAccion(accion);
	}

	private boolean sameMagnitud(String magnitud) {
		return magnitud.equals(nombreMagnitud);
	}

	private boolean comparacionValor(int valorMedicion) {
		if (comparacion == ComparacionesReglaGenerica.MAYORIGUAL) {
			return valorMedicion >= valor;
		}
		return false;
	}
}
