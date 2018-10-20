package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "reglamentador")
public class Reglamentador extends EntidadPersistente implements Observer {
//	@OneToMany(mappedBy = "reglamentador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<Dispositivo> dispositivos;

	@OneToMany(mappedBy = "reglamentador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ReglaGenerica> reglasGenericas;

	public Actuador getActuador() {
		return actuador;
	}

	public void setActuador(Actuador actuador) {
		this.actuador = actuador;
	}

	@Transient
	private List<Regla> reglas;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "actuador_id", referencedColumnName = "id")
	private Actuador actuador;
	
	@ManyToOne
	@JoinColumn(name = "sensor_id", referencedColumnName = "id")
	private Sensor sensor;

	public Reglamentador(Actuador actuador) {
		this.reglas = new ArrayList<Regla>();
		this.reglasGenericas = new ArrayList<>();
		this.actuador = actuador;
	}

	public void setRegla(Regla regla) {
		this.reglas.add(regla);
	}

	public void agregarReglaGenerica(ReglaGenerica regla) {
		this.reglasGenericas.add(regla);
	}

	@Override
	public void update(Medicion medicion) {
		Regla regla = this.reglas.stream().filter(r -> r.cumple(medicion)).findFirst().orElse(null);

		if (regla == null) {
			regla = this.reglasGenericas.stream().filter(r -> r.cumple(medicion)).findFirst().orElse(null);
		}

		if (regla != null) {
			regla.dispararAccion(this.actuador);
		}

	}

}
