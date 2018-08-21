package domain;

import java.time.LocalDateTime;

public class DispositivoEstandar extends Dispositivo {
	public DispositivoEstandar(int dispositivo, String nombre, double consumoXHora) {
		super(dispositivo, nombre, consumoXHora);
	}

	public double estimarConsumo(int horasUsoXDia) {
		return this.getConsumoXHora() * horasUsoXDia;
	}
	
	@Override
	public void apagarse(){
		
	}
	
	@Override
	public double consumoTotalComprendidoEntre(LocalDateTime inicio, LocalDateTime fin) { 
		return 0; 
	}
	
	@Override
	public Estados getEstado(){ return null; }

}
