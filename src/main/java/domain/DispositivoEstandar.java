package domain;

import java.time.LocalDateTime;

import javax.persistence.Table;

@Table(name = "dispositivo_estandar")
public class DispositivoEstandar extends Dispositivo {
//	public DispositivoEstandar(int dispositivo, String nombre, double consumoXHora) {
//		super(dispositivo, nombre, consumoXHora);
//	}
	
	public DispositivoEstandar(String nombre, double consumoXHora) {
		super(nombre, consumoXHora);
	}

	public double estimarConsumo(int horasUsoXDia) {
		return this.getConsumoXHora() * horasUsoXDia;
	}
	
	@Override
	public void apagarse() {	}
	
	@Override
	public void encenderse() { }
	
	@Override
	public double HorasTotalComprendidoEntre(LocalDateTime inicio, LocalDateTime fin) { 
		return 0; 
	}
	
	@Override
	public Estados getEstado(){ return null; }

}
