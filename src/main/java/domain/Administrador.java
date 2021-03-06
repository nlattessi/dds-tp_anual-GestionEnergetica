package domain;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import db.LocalDateTimeAttributeConverter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

@Entity(name = "Administrador")
@Table(name = "administrador")
@DiscriminatorValue("2")
public class Administrador extends Usuario {

	// Variables
	@Column
	private Date fechaAltaSistema;

	// Constructores
	public Administrador() {
		super();
	}

	public Administrador(String nombreUsuario, String contraseña, String nombreYApellido, String domicilio,
			Date fechaAltaSistema) {
		super(nombreUsuario, contraseña, nombreYApellido, domicilio);
		this.fechaAltaSistema = fechaAltaSistema;
	}

	// Setters - Getters
	public Date getFechaAltaSistema() {
		return fechaAltaSistema;
	}

	public void setFechaAltaSistema(Date fechaAltaSistema) {
		this.fechaAltaSistema = fechaAltaSistema;
	}

	public int cantidadMesesComoAdministrador() {
		Calendar fechaActual = new GregorianCalendar();
		return this.calcularCantidadMeseesComoAdministrador(fechaActual);
	}

	public int cantidadMesesComoAdministrador(Date fechaActual) {
		Calendar fechaActualCalendar = new GregorianCalendar();
		fechaActualCalendar.setTime(fechaActual);
		return this.calcularCantidadMeseesComoAdministrador(fechaActualCalendar);
	}

	private int calcularCantidadMeseesComoAdministrador(Calendar fechaActual) {
		Calendar fechaAlta = new GregorianCalendar();
		fechaAlta.setTime(fechaAltaSistema);

		int anioActual = fechaActual.get(Calendar.YEAR);
		int anioAlta = fechaAlta.get(Calendar.YEAR);
		int mesActual = fechaActual.get(Calendar.MONTH);
		int mesAlta = fechaAlta.get(Calendar.MONTH);
		int diaActual = fechaActual.get(Calendar.DATE);
		int diaAlta = fechaAlta.get(Calendar.DATE);

		int meses = (anioActual * 12 + mesActual) - (anioAlta * 12 + mesAlta);
		if (diaActual < diaAlta) {
			meses = -1;
		}

		return meses;
	}
}
