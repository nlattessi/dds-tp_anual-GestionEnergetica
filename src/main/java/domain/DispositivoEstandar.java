package domain;

public class DispositivoEstandar extends Dispositivo {
	public int puntosOtorga = 0;
	public DispositivoEstandar(int dispositivo, String nombre, int consumoXHora) {
		super(dispositivo, nombre, consumoXHora);
	}

	public int estimarConsumo(int horasUsoXDia) {
		return this.getConsumoXHora() * horasUsoXDia;
	}
	public void sumarPuntos(Cliente cliente) {
		cliente.sumarPuntos(puntosOtorga);
	}
}
