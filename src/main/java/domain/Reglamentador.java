package domain;

import java.util.ArrayList;

public class Reglamentador implements Observer {
	private ArrayList<Regla> reglas;
	private Actuador actuador;

	public Reglamentador(Actuador actuador) {
		this.reglas = new ArrayList<Regla>();
		this.actuador = actuador;
	}

	public void setRegla(Regla regla) {
		this.reglas.add(regla);
	}

	@Override
	public void update(Medicion medicion) {
		Regla regla = this.reglas.stream().filter(r -> r.cumple(medicion)).findFirst().orElse(null);

		if (regla != null) {
			regla.dispararAccion(this.actuador);
		}

	}


}
