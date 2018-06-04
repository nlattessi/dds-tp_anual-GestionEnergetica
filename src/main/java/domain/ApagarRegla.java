package domain;

public class ApagarRegla implements Regla {
	@Override
	public boolean cumple(Medicion medicion) {
		return (medicion.getMagnitud().equals("temperatura") && medicion.getValor() < 24);
	}

	@Override
	public void dispararAccion(Actuador actuador) {
		actuador.ejecutarAccion(Acciones.APAGARSE);
	}
}