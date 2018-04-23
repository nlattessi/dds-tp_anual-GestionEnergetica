package domain;

import java.util.*;

public class Cliente extends Usuario {

	protected Documento documento;
	private String telefonoContacto;
	private Date fechaAltaServicio;
	private Categoria categoria;
	private List<Dispositivo> dispositivos = new ArrayList<Dispositivo>();

	public Cliente(int id, String nombreUsuario, String contraseña, String nombreYApellido, Domicilio domicilio,
			Documento documento, String telefonoContacto, Date fechaAltaServicio, Categoria categoria) {
		super(id, nombreUsuario, contraseña, nombreYApellido, domicilio);
		this.documento = documento;
		this.telefonoContacto = telefonoContacto;
		this.fechaAltaServicio = fechaAltaServicio;
		this.categoria = categoria;
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

	public void agregarDispositivo(Dispositivo dispositivo) {
		this.dispositivos.add(dispositivo);
	}

	public void removerDispositivo(Dispositivo dispositivo) {
		this.dispositivos.remove(dispositivo);
	}

	public boolean algunDispositivoEncendido() {
		return this.dispositivos.stream().anyMatch(x -> x.getEncendido() == true);
	}

	public long cantidadDispositivosEncendidos() {
		return this.dispositivos.stream().filter(x -> x.getEncendido() == true).count();
	}

	public long cantidadDispositivosApagados() {
		return this.dispositivos.stream().filter(x -> x.getEncendido() == false).count();
	}

	public int cantidadTotalDispositivos() {
		return this.dispositivos.size();
	}

}
