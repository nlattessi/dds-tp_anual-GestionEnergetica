package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity(name = "Reglamentador")
@Table(name = "reglamentador")
public class Reglamentador implements Observer {

	// Variables
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;

	@Version
	@Column
	private Long version;

//	@OneToMany(mappedBy = "reglamentador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<Dispositivo> dispositivos;

	@OneToMany(mappedBy = "reglamentador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ReglaGenerica> reglasGenericas;

//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "actuador_id", referencedColumnName = "id")
//	private Actuador actuador;

	@OneToOne
//	@MapsId
    @JoinColumn(name = "actuador_id")
	private Actuador actuador;

	@ManyToOne
	private Sensor sensor;
	
//	@OneToOne(mappedBy = "reglamentador", cascade = CascadeType.ALL, orphanRemoval = true)
//	private Sensor sensor;

	public Actuador getActuador() {
		return actuador;
	}

	public void setActuador(Actuador actuador) {
		this.actuador = actuador;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	@Transient
	private List<Regla> reglas;

	public Reglamentador() {

	}

	public Reglamentador(Actuador actuador) {
		this.reglas = new ArrayList<Regla>();
		this.reglasGenericas = new ArrayList<>();
		this.actuador = actuador;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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
