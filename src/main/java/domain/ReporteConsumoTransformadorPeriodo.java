package domain;

import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity("reportes_consumo_transformador_periodo")
@Indexes(@Index(fields = @Field("reporteId")))
public class ReporteConsumoTransformadorPeriodo {
	@Id
	private ObjectId id;

	private String reporteId;

	private List<Map<String, String>> transformadores;

	private String inicio;

	private String fin;

	public ReporteConsumoTransformadorPeriodo() {

	}

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

	public List<Map<String, String>> getTransformadores() {
		return transformadores;
	}

	public void setTransformadores(List<Map<String, String>> transformadores) {
		this.transformadores = transformadores;
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

}
