package domain;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity("reportes_consumo_hogar_periodo")
@Indexes(@Index(fields = @Field("reporteId")))
public class ReporteConsumoHogarPeriodo {

	@Id
	private ObjectId id;

	private String reporteId;

	private String nombreYApellido;

	private String inicio;

	private String fin;

	private double consumo;

//	public ReporteConsumoHogarPeriodo() {
//
//	}
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getReporteId() {
		return reporteId;
	}

	public void setReporteId(String reporteId) {
		this.reporteId = reporteId;
	}

	public String getNombreYApellido() {
		return nombreYApellido;
	}

	public void setNombreYApellido(String nombreYApellido) {
		this.nombreYApellido = nombreYApellido;
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFin() {
		return fin;
	}

	public void setFin(String fin) {
		this.fin = fin;
	}

	public double getConsumo() {
		return consumo;
	}

	public void setConsumo(double consumo) {
		this.consumo = consumo;
	}

}
