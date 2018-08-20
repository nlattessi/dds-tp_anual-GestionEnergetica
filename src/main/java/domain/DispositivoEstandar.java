package domain;

public class DispositivoEstandar extends Dispositivo {
	public DispositivoEstandar(int dispositivo, String nombre, double consumoXHora) {
		super(dispositivo, nombre, consumoXHora);
	}

	public double estimarConsumo(int horasUsoXDia) {
		return this.getConsumoXHora() * horasUsoXDia;
	}
}
