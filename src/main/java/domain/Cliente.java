package domain;

import java.util.*;

public class Cliente extends Persona {

	private int idCliente;
	private String fechaAltaServicio;
	private Categoria categoria;
	private ArrayList<Dispositivo> dispositivos = new ArrayList<Dispositivo>();

	public Cliente(String nombreYApellido, Domicilio domicilio, Documento documento, Usuario usuario, int idCliente,
			String fechaAltaServicio, Categoria categoria, ArrayList<Dispositivo> dispositivos) {
		super(nombreYApellido, domicilio, documento, usuario);
		this.idCliente = idCliente;
		this.fechaAltaServicio = fechaAltaServicio;
		this.categoria = categoria;
		this.dispositivos = dispositivos;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getFechaAltaServicio() {
		return fechaAltaServicio;
	}

	public void setFechaAltaServicio(String fechaAltaServicio) {
		this.fechaAltaServicio = fechaAltaServicio;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public ArrayList<Dispositivo> getDispositivos() {
		return dispositivos;
	}

	public void setDispositivos(ArrayList<Dispositivo> dispositivos) {
		this.dispositivos = dispositivos;
	}

	public void conocerSiDispositivoEstaEncendido(Dispositivo dispositivo) {

	}

	public void cantidadDispositivosEncendidos() {

	}

	public void cantidadDispositivosApagados() {

	}

	public void cantidadTotalDispositivos() {

	}

	public void consumoAcumuladoMes() {

	}

	public void facturacionEstimativa() {

	}

}
