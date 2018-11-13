package domain;

import java.util.HashMap;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity(name = "Actuador")
@Table(name = "actuador")
public class Actuador {
	// Variables
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;

	@Version
	@Column
	private Long version;

//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "dispositivo_inteligente_id", referencedColumnName = "id")
//	private DispositivoInteligente dispositivo;

	@OneToOne
    @MapsId
	private DispositivoInteligente dispositivo;

	@OneToOne(mappedBy = "actuador", cascade = CascadeType.ALL, orphanRemoval = true)
	private Reglamentador reglamentador;

	@Transient
	private HashMap<Acciones, Command> listaComandos;

	public Actuador() {

	}

	public Actuador(DispositivoInteligente dispositivo) {
		this.dispositivo = dispositivo;
		this.listaComandos = new HashMap<Acciones, Command>();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DispositivoInteligente getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(DispositivoInteligente dispositivo) {
		this.dispositivo = dispositivo;
	}

	public void agregarAccion(Acciones accion, Command command) {
		this.listaComandos.put(accion, command);
	}

	public void ejecutarAccion(Acciones accion) {
		this.listaComandos.get(accion).execute(this.dispositivo);
	}
}
