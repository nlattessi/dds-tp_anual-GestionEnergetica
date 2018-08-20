package domain;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

public class Cliente extends Usuario implements Runnable{

	private TipoDocumento tipoDocumento;
	private int numeroDocumento;
	private String telefonoContacto;
	private Date fechaAltaServicio;
	private Categoria categoria;
	private List<Dispositivo> dispositivos = new ArrayList<Dispositivo>();
	private int puntosAcumulados = 0;
	private SimplexFacade simplex = new SimplexFacade(GoalType.MAXIMIZE, true);
	
	private boolean ahorroInteligente;
	
	
	public boolean getAhorroInteligente() {
		return ahorroInteligente;
	}

	public void setAhorroInteligente(boolean ahorroInteligente) {
		this.ahorroInteligente = ahorroInteligente;
	}

	public Thread hiloVerificadorConsumo;
	
	
	public int segundosDeEspera=5;

	public Cliente(int id, String nombreUsuario, String contraseņa, String nombreYApellido, String domicilio,
			TipoDocumento tipoDocumento, int numeroDocumento, String telefonoContacto, Date fechaAltaServicio,
			Categoria categoria) {
		super(id, nombreUsuario, contraseņa, nombreYApellido, domicilio);
		this.setTipoDocumento(tipoDocumento);
		this.setNumeroDocumento(numeroDocumento);
		this.telefonoContacto = telefonoContacto;
		this.fechaAltaServicio = fechaAltaServicio;
		this.categoria = categoria;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public int getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(int numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getTelefonoContacto() {
		return this.telefonoContacto;
	}

	public int puntosAcumulados() {
		return puntosAcumulados;
	}

	public void setTelefonoContacto(String telefonoContacto) {
		this.telefonoContacto = telefonoContacto;
	}

	public Date getFechaAltaServicio() {
		return fechaAltaServicio;
	}

	public void setFechaAltaServicio(Date fechaAltaServicio) {
		this.fechaAltaServicio = fechaAltaServicio;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Dispositivo> getDispositivos() {
		return dispositivos;
	}

	public void setDispositivos(List<Dispositivo> dispositivos) {
		this.dispositivos = dispositivos;
	}

	public void agregarDispositivo(DispositivoEstandar dispositivo) {
		this.dispositivos.add(dispositivo);
	}

	public void agregarDispositivo(DispositivoInteligente dispositivo) {
		this.dispositivos.add(dispositivo);
		this.sumarPuntos(15);
	}

	public void conversionEstandarInteligente(DispositivoEstandar dispositivo) {
		DispositivoInteligente dispInteligente = new DispositivoInteligente(dispositivo.getId(),
				dispositivo.getNombre(), dispositivo.getConsumoXHora(), Estados.APAGADO);
		this.dispositivos.add(dispInteligente);
		this.sumarPuntos(10);
	}

	public void removerDispositivo(Dispositivo dispositivo) {
		this.dispositivos.remove(dispositivo);
	}

	public boolean algunDispositivoEncendido() {
		return this.getDispositivosInteligentes().anyMatch(x -> x.estaEncendido());
	}

	public long cantidadDispositivosEncendidos() {
		return this.getDispositivosInteligentes().filter(x -> x.estaEncendido()).count();
	}

	public long cantidadDispositivosApagados() {
		return this.getDispositivosInteligentes().filter(x -> x.estaApagado()).count();
	}

	public int cantidadTotalDispositivos() {
		return this.dispositivos.size();
	}

	private void sumarPuntos(int puntos) {
		this.puntosAcumulados = puntos + this.puntosAcumulados();
	}

	private Stream<DispositivoInteligente> getDispositivosInteligentes() {
		return this.dispositivos.stream().filter(d -> d instanceof DispositivoInteligente)
				.map(d -> (DispositivoInteligente) d);
	}
	
	public void calcularHogarEficiente()
	{
		this.setDispositivos(simplex.calcularHogarEficiente(dispositivos));
	}
	
	public void start() {
		if(hiloVerificadorConsumo==null) {
	
			hiloVerificadorConsumo=new Thread(this);
			hiloVerificadorConsumo.start();
		    }
		}
	




@Override
	public void run()  
	{	
		int c=0;
		
		
		while(c<3) {//prueba solamente tres veces
		
			
		this.setDispositivos(simplex.calcularHogarEficiente(dispositivos));
		
		if (this.ahorroInteligente)
		{
			for (Dispositivo dispositivo : this.dispositivos)
			{
				if(dispositivo.permiteAhorroInteligente())
				{
					LocalDateTime fecha = LocalDateTime.now();
					
					LocalDateTime fechaInicioMes = LocalDateTime.of(fecha.getYear(), fecha.getMonth(), 1, 0, 0);
					if ((dispositivo.consumoTotalComprendidoEntre(fechaInicioMes, LocalDateTime.now()))
																				> dispositivo.getConsumoRecomendadoHoras())
					{
						dispositivo.apagarse();
					}
				}
			}
		}
		

		try {
			Thread.sleep(segundosDeEspera*1000);
		}catch(InterruptedException ex){
			Thread.currentThread().interrupt();
		}
		c++;
					}
	}

public void aguardar() {
	try {
		hiloVerificadorConsumo.join();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}



}
