package domain;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;

public class Administrador extends Usuario {

	private Date fechaAltaSistema;

	public Administrador(int id, String nombreUsuario, String contrasenia, String nombreYApellido, Domicilio domicilio, Documento documento,
			Date fechaAltaSistema) {
		super(id, nombreUsuario, contrasenia, nombreYApellido, domicilio, documento);
		this.fechaAltaSistema = fechaAltaSistema;
	}

	public Date getFechaAltaSistema() {
		return fechaAltaSistema;
	}

	public void setFechaAltaSistema(Date fechaAltaSistema) {
		this.fechaAltaSistema = fechaAltaSistema;
	}

	public int cantidadMesesComoAdministrador() {
		Calendar fechaActual = new GregorianCalendar();
		Calendar fechaAlta = new GregorianCalendar();
		fechaAlta.setTime(this.fechaAltaSistema); 
		
		int anioActual = fechaActual.get(Calendar.YEAR);
		int anioAlta = fechaAlta.get(Calendar.YEAR);
		int mesActual = fechaActual.get(Calendar.MONTH);
		int mesAlta = fechaAlta.get(Calendar.MONTH);
		int diaActual = fechaActual.get(Calendar.DATE);
		int diaAlta = fechaAlta.get(Calendar.DATE);
		
		int meses = (anioActual * 12 + mesActual) - (anioAlta * 12 + mesAlta);
		if (diaActual < diaAlta) { meses =-1; }
		
		return meses;
	}

}
