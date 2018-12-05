package domain;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity("reportes_consumo_tipo_dispositivo_periodo")
@Indexes(@Index(fields = @Field("reporteId")))
public class ReporteConsumoTipoDispositivoPeriodo {
	@Id
	private ObjectId id;

	private String reporteId;

	private String tipoDispositivo;

	private String inicio;

	private String fin;

	private int cantidadDispositivos;

	private double consumoPromedio;

	public ReporteConsumoTipoDispositivoPeriodo() {

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

	public String getTipoDispositivo() {
		return tipoDispositivo;
	}

	public void setTipoDispositivo(String tipoDispositivo) {
		this.tipoDispositivo = tipoDispositivo;
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

	public int getCantidadDispositivos() {
		return cantidadDispositivos;
	}

	public void setCantidadDispositivos(int cantidadDispositivos) {
		this.cantidadDispositivos = cantidadDispositivos;
	}

	public double getConsumoPromedio() {
		return consumoPromedio;
	}

	public void setConsumoPromedio(double consumoPromedio) {
		this.consumoPromedio = consumoPromedio;
	}

}
