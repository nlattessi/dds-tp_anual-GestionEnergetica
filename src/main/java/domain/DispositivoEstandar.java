package domain;

public class DispositivoEstandar extends Dispositivo {
	public DispositivoEstandar(int dispositivo, String nombre, int consumoXHora) {
		super(dispositivo, nombre, consumoXHora);
	}

	public int estimarConsumo(int horasUsoXDia) {
		return this.getConsumoXHora() * horasUsoXDia;
	}
}
