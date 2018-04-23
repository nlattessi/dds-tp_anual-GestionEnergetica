package domain;

import java.util.*;

public class Cliente extends Usuario {

	private String telefonoContacto;
	private Date fechaAltaServicio;
	private Categoria categoria;
	private List<Dispositivo> dispositivos;

	public Cliente(int id, String nombreUsuario, String contrasenia, String nombreYApellido, Domicilio domicilio, Documento documento,
			Date fechaAltaServicio, Categoria categoria, List<Dispositivo> dispositivos) {
		super(id, nombreUsuario, contrasenia, nombreYApellido, domicilio, documento);
		this.fechaAltaServicio = fechaAltaServicio;
		this.categoria = categoria;
		this.dispositivos = dispositivos;
	}
	
	public String getTelefonoContacto() {
		return this.telefonoContacto;
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

	public boolean algunDispositivoEncendido() {
		long cantDispositivosEncendidos = this.dispositivos.stream().filter(x -> x.getEstado() == true).count();
		if (cantDispositivosEncendidos >= 1) { return true; }
		else { return false; }
	}

	public long cantidadDispositivosEncendidos() {
		return this.dispositivos.stream().filter(x -> x.getEstado() == true).count();
	}

	public long cantidadDispositivosApagados() {
		return this.dispositivos.stream().filter(x -> x.getEstado() == false).count();
	}

	public long cantidadTotalDispositivos() {
		return this.dispositivos.stream().count();
	}
	
	
}
