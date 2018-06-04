package domain;

public interface Regla {
	public boolean cumple(Medicion medicion);
	public void dispararAccion(Actuador actuador);
}
