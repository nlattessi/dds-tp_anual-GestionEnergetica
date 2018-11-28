package domain;

import java.time.LocalDateTime;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "DispositivoEstandar")
@Table(name = "dispositivo_estandar")
@DiscriminatorValue("1")
public class DispositivoEstandar extends Dispositivo {

	// Constructores
	public DispositivoEstandar() {
		super();
	}

	public DispositivoEstandar(String nombre, double consumoXHora) {
		super(nombre, consumoXHora);
	}

	public DispositivoEstandar(DispositivoMaestro maestro) {
		super(maestro);
		maestro.addDispositivo(this);
	}

	// Setters - Getters

	public boolean getEsInteligente() {
		return false;
	}

	public double estimarConsumo(int horasUsoXDia) {
		return this.getConsumoXHora() * horasUsoXDia;
	}

	@Override
	public void apagarse() {
	}

	@Override
	public void encenderse() {
	}

	@Override
	public int horasTotalComprendidoEntre(LocalDateTime inicio, LocalDateTime fin) {
		return this.periodos.stream().filter(p -> p.inicioEsDespuesDe(inicio) && p.finEsAntesDe(fin))
				.map(p -> p.getHoras()).reduce(0, (x, y) -> x + y);
	}

	@Override
	public double consumoTotalComprendidoEntre(LocalDateTime inicio, LocalDateTime fin) {
		int totalHoras = horasTotalComprendidoEntre(inicio, fin);

		return totalHoras * this.getConsumoXHora();
	}

	@Override
	public Estados getEstado() {
		return null;
	}

}
