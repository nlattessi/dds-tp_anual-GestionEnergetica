package domain;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

public class Cliente extends Usuario {

	private TipoDocumento tipoDocumento;
	private int numeroDocumento;
	private String telefonoContacto;
	private Date fechaAltaServicio;
	private Categoria categoria;
	private List<Dispositivo> dispositivos = new ArrayList<Dispositivo>();
	private int puntosAcumulados = 0;
	private SimplexFacade simplex = new SimplexFacade(GoalType.MAXIMIZE, true);
	private boolean ahorroInteligente;
	private int periodicidadAhorroInt;	
	private int contadorId;
	private int transformadorId;
	private Locacion coordenadasDomicilio; 
	
	public boolean getAhorroInteligente() {
		return ahorroInteligente;
	}

	public void setAhorroInteligente(boolean ahorroInteligente) {
		this.ahorroInteligente = ahorroInteligente;
	}

	public Thread hiloVerificadorConsumo;
	
	
	public int segundosDeEspera=5;

	public Cliente(int id, String nombreUsuario, String contraseña, String nombreYApellido, String domicilio,
			TipoDocumento tipoDocumento, int numeroDocumento, String telefonoContacto, Date fechaAltaServicio,
			Categoria categoria) {
		super(id, nombreUsuario, contraseña, nombreYApellido, domicilio);
		this.setTipoDocumento(tipoDocumento);
		this.setNumeroDocumento(numeroDocumento);
		this.telefonoContacto = telefonoContacto;
		this.fechaAltaServicio = fechaAltaServicio;
		this.categoria = categoria;
		this.ahorroInteligente = false;
		this.periodicidadAhorroInt = 10; 
		this.contadorId = 0;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTransformadorId(int transfId) {
		transformadorId=transfId;
	}
	public int getTransformadorId() {
		return transformadorId;
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
	
	public void setCoordenadasDomicilio(int lat, int alt) {
		this.coordenadasDomicilio.x = lat;
		this.coordenadasDomicilio.y = alt;
	}
	public Locacion getCoordenadasDomicilio() {
		return coordenadasDomicilio;
	}
	

	public void removerDispositivo(Dispositivo dispositivo) {
		this.dispositivos.remove(dispositivo);
	}
	
	public void limpiarDispositivos() {
		this.dispositivos.clear();
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
	
	public int getPeriodicidadAhorroInteligente() {
		return this.periodicidadAhorroInt;
	}
	
	public void setPeriodicidadAhorroInteligente(int periodicidad) {
		this.periodicidadAhorroInt = periodicidad;
	}

	private Stream<DispositivoInteligente> getDispositivosInteligentes() {
		return this.dispositivos.stream().filter(d -> d instanceof DispositivoInteligente)
				.map(d -> (DispositivoInteligente) d);
	}
	
	
	public void calcularHogarEficiente()
	{
		this.simplex.calcularHogarEficiente(this.dispositivos);
		
		if (this.ahorroInteligente)
		{
			for (Dispositivo dispositivo : this.dispositivos)
			{
				if(dispositivo.getPermiteAhorroInteligente())
				{
					LocalDateTime fecha = LocalDateTime.now();
					
					LocalDateTime fechaInicioMes = LocalDateTime.of(fecha.getYear(), fecha.getMonth(), 1, 0, 0);
					if ((dispositivo.HorasTotalComprendidoEntre(fechaInicioMes, LocalDateTime.now()))
																				> dispositivo.getConsumoRecomendadoHoras())
					{
						dispositivo.apagarse();
					}
				}
			}
		}
	}
	
	public Dispositivo generarAireAcondicionado3500F() {
		contadorId++;
		Dispositivo dispositivo = new DispositivoInteligente(contadorId, "aire acondicionado de 3500 frigorias", 1.613, Estados.APAGADO);
		dispositivo.setUsoMensualMinimoHoras(90);
		dispositivo.setUsoMensualMaximoHoras(360);
		dispositivo.setBajoConsumo(false);
		return dispositivo;
	}
	
	public Dispositivo generarAireAcondicionado2200F() {
		contadorId++;
		Dispositivo dispositivo = new DispositivoInteligente(contadorId, "aire acondicionado de 2200 frigorias", 1.013, Estados.APAGADO);
		dispositivo.setUsoMensualMinimoHoras(90);
		dispositivo.setUsoMensualMaximoHoras(360);
		dispositivo.setBajoConsumo(true);
		return dispositivo;
	}
	
	public Dispositivo generarTelevisorTubo21P() {
		contadorId++;
		Dispositivo dispositivo = new DispositivoEstandar(contadorId, "Color de tubo fluorescente de 21 pulgadas", 0.075);
		dispositivo.setUsoMensualMinimoHoras(90);
		dispositivo.setUsoMensualMaximoHoras(360);
		dispositivo.setBajoConsumo(false);
		return dispositivo;
	}
	
	public Dispositivo generarTelevisorTubo29A34P() {
		contadorId++;
		Dispositivo dispositivo = new DispositivoEstandar(contadorId, "Color de tubo fluorescente de 29 a 34 pulgadas", 0.175);
		dispositivo.setUsoMensualMinimoHoras(90);
		dispositivo.setUsoMensualMaximoHoras(360);
		dispositivo.setBajoConsumo(false);
		return dispositivo;
	}
	
	public Dispositivo generarTelevisorLCD40P() {
		contadorId++;
		Dispositivo dispositivo = new DispositivoEstandar(contadorId, "LCD 40 pulgadas", 0.18);
		dispositivo.setUsoMensualMinimoHoras(90);
		dispositivo.setUsoMensualMaximoHoras(360);
		dispositivo.setBajoConsumo(false);
		return dispositivo;
	}
	
	public Dispositivo generarLED24P() {
		contadorId++;
		Dispositivo dispositivo = new DispositivoInteligente(contadorId, "LED 24 pulgadas", 0.04, Estados.APAGADO);
		dispositivo.setUsoMensualMinimoHoras(90);
		dispositivo.setUsoMensualMaximoHoras(360);
		dispositivo.setBajoConsumo(true);
		return dispositivo;
	}
	
	public Dispositivo generarLED32P() {
		contadorId++;
		Dispositivo dispositivo = new DispositivoInteligente(contadorId, "LED 32 pulgadas", 0.055, Estados.APAGADO);
		dispositivo.setUsoMensualMinimoHoras(90);
		dispositivo.setUsoMensualMaximoHoras(360);
		dispositivo.setBajoConsumo(true);
		return dispositivo;
	}
	
	public Dispositivo generarLED40P() {
		contadorId++;
		Dispositivo dispositivo = new DispositivoInteligente(contadorId, "LED 40 pulgadas", 0.08, Estados.APAGADO);
		dispositivo.setUsoMensualMinimoHoras(90);
		dispositivo.setUsoMensualMaximoHoras(360);
		dispositivo.setBajoConsumo(true);
		return dispositivo;
	}
	
	public Dispositivo generarHeladeraConFreezer() {
		contadorId++;
		Dispositivo dispositivo = new DispositivoInteligente(contadorId, "Heladera con freezer", 0.09, Estados.APAGADO);
		dispositivo.setUsoMensualMinimoHoras(0);
		dispositivo.setUsoMensualMaximoHoras(0);
		dispositivo.setBajoConsumo(true);
		dispositivo.setPermiteCalculoAhorro(false);
		return dispositivo;
	}
	
	public Dispositivo generarHeladeraSinFreezer() {
		contadorId++;
		Dispositivo dispositivo = new DispositivoInteligente(contadorId, "Heladera sin freezer", 0.075, Estados.APAGADO);
		dispositivo.setUsoMensualMinimoHoras(0);
		dispositivo.setUsoMensualMaximoHoras(0);
		dispositivo.setBajoConsumo(true);
		dispositivo.setPermiteCalculoAhorro(false);
		return dispositivo;
	}
	
	public Dispositivo generarLavarropasAuto5KgCA() {
		contadorId++;
		Dispositivo dispositivo = new DispositivoEstandar(contadorId, "Lavarropas automatico de 5kg con calentamiento de agua ", 0.875);
		dispositivo.setUsoMensualMinimoHoras(6);
		dispositivo.setUsoMensualMaximoHoras(30);
		dispositivo.setBajoConsumo(false);
		return dispositivo;
	}
	
	public Dispositivo generarLavarropasAuto5kg() {
		contadorId++;
		Dispositivo dispositivo = new DispositivoInteligente(contadorId, "Lavarropas automatico de 5kg", 0.175, Estados.APAGADO);
		dispositivo.setUsoMensualMinimoHoras(6);
		dispositivo.setUsoMensualMaximoHoras(30);
		dispositivo.setBajoConsumo(true);
		return dispositivo;
	}
	
	public Dispositivo generarLavarropasSemiAuto5Kg() {
		contadorId++;
		Dispositivo dispositivo = new DispositivoEstandar(contadorId, "Lavarropas semi-automatico de 5kg", 0.1275);
		dispositivo.setUsoMensualMinimoHoras(6);
		dispositivo.setUsoMensualMaximoHoras(30);
		dispositivo.setBajoConsumo(true);
		return dispositivo;
	}
	
	public Dispositivo generarVentiladorDePie() {
		contadorId++;
		Dispositivo dispositivo = new DispositivoEstandar(contadorId, "Ventilador de pie", 0.09);
		dispositivo.setUsoMensualMinimoHoras(120);
		dispositivo.setUsoMensualMaximoHoras(360);
		dispositivo.setBajoConsumo(true);
		return dispositivo;
	}
	
	public Dispositivo generarVentiladorDeTecho() {
		contadorId++;
		Dispositivo dispositivo = new DispositivoInteligente(contadorId, "Ventilador de techo", 0.06, Estados.APAGADO);
		dispositivo.setUsoMensualMinimoHoras(120);
		dispositivo.setUsoMensualMaximoHoras(360);
		dispositivo.setBajoConsumo(true);
		return dispositivo;
	}
	
	public Dispositivo generarLamparaHalogena40W() {
		contadorId++;
		Dispositivo dispositivo = new DispositivoInteligente(contadorId, "Lampara Halogena de 40 W", 0.04, Estados.APAGADO);
		dispositivo.setUsoMensualMinimoHoras(90);
		dispositivo.setUsoMensualMaximoHoras(360);
		dispositivo.setBajoConsumo(false);
		return dispositivo;
	}
	
	public Dispositivo generarLamparaHalogena60W() {
		contadorId++;
		Dispositivo dispositivo = new DispositivoInteligente(contadorId, "Lampara Halogena de 60 W", 0.06, Estados.APAGADO);
		dispositivo.setUsoMensualMinimoHoras(90);
		dispositivo.setUsoMensualMaximoHoras(360);
		dispositivo.setBajoConsumo(false);
		return dispositivo;
	}
	
	public Dispositivo generarLamparaHalogena100W() {
		contadorId++;
		Dispositivo dispositivo = new DispositivoInteligente(contadorId, "Lampara Halogena de 100 W", 0.015, Estados.APAGADO);
		dispositivo.setUsoMensualMinimoHoras(90);
		dispositivo.setUsoMensualMaximoHoras(360);
		dispositivo.setBajoConsumo(false);
		return dispositivo;
	}
	
	public Dispositivo generarLampara11W() {
		contadorId++;
		Dispositivo dispositivo = new DispositivoInteligente(contadorId, "Lampara de 11 W", 0.011, Estados.APAGADO);
		dispositivo.setUsoMensualMinimoHoras(90);
		dispositivo.setUsoMensualMaximoHoras(360);
		dispositivo.setBajoConsumo(true);
		return dispositivo;
	}
	
	public Dispositivo generarLampara15W() {
		contadorId++;
		Dispositivo dispositivo = new DispositivoInteligente(contadorId, "Lampara de 15 W", 0.015, Estados.APAGADO);
		dispositivo.setUsoMensualMinimoHoras(90);
		dispositivo.setUsoMensualMaximoHoras(360);
		dispositivo.setBajoConsumo(true);
		return dispositivo;
	}
	
	public Dispositivo generarLampara20W() {
		contadorId++;
		Dispositivo dispositivo = new DispositivoInteligente(contadorId, "Lampara de 20 W", 0.02, Estados.APAGADO);
		dispositivo.setUsoMensualMinimoHoras(90);
		dispositivo.setUsoMensualMaximoHoras(360);
		dispositivo.setBajoConsumo(true);
		return dispositivo;
	}
	
	public Dispositivo generarPC() {
		contadorId++;
		Dispositivo dispositivo = new DispositivoInteligente(contadorId, "PC de escritorio", 0.4, Estados.APAGADO);
		dispositivo.setUsoMensualMinimoHoras(60);
		dispositivo.setUsoMensualMaximoHoras(360);
		dispositivo.setBajoConsumo(true);
		return dispositivo;
	}
	
	public Dispositivo generarMicroondas() {
		contadorId++;
		Dispositivo dispositivo = new DispositivoEstandar(contadorId, "Microondas convencional", 0.64);
		dispositivo.setUsoMensualMinimoHoras(3);
		dispositivo.setUsoMensualMaximoHoras(15);
		dispositivo.setBajoConsumo(true);
		return dispositivo;
	}
	
	public Dispositivo generarPlancha() {
		contadorId++;
		Dispositivo dispositivo = new DispositivoEstandar(contadorId, "Plancha a vapor", 0.75);
		dispositivo.setUsoMensualMinimoHoras(3);
		dispositivo.setUsoMensualMaximoHoras(30);
		dispositivo.setBajoConsumo(true);
		return dispositivo;
	}
	

}
