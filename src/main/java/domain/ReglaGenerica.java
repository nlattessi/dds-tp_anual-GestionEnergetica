package domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "regla")
public class ReglaGenerica extends EntidadPersistente implements Regla {
	private String nombreMagnitud;
	private int valor;
	private ComparacionesReglaGenerica comparacion;
	private Acciones accion;
	
//	@ManyToOne
//	@JoinColumn(name = "dispositivo_id", referencedColumnName = "id")
//	private Dispositivo dispositivo;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "reglamentador_id", referencedColumnName = "id")
	private Reglamentador reglamentador;
	
	public static ReglaGenerica crearEncenderRegla(String nombreMagnitud, ComparacionesReglaGenerica comparacion, int valor) {
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
