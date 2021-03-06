package domain;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import models.ModelHelper;

public class ImportadorJson {

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private JSONArray entidades;
	private ModelHelper model;

	public ImportadorJson(JSONArray entidades) {
		this.entidades = entidades;
		this.model = new ModelHelper();
	}

	public static ImportadorJson desdeArchivo(String archivo) {
		FileReader fr = null;

		try {
			fr = new FileReader(archivo);

		} catch (IOException e) {
			System.out.println("Error leyendo el archivo: " + archivo);
			e.printStackTrace();
			System.exit(1);
		}

		JSONParser parser = new JSONParser();
		JSONArray entidades = null;

		try {
			Object obj = parser.parse(fr);
			entidades = (JSONArray) obj;
		} catch (Exception e) {
			System.out.println("Error parseando el archivo json: " + archivo);
			e.printStackTrace();
			System.exit(1);
		}

		ImportadorJson importador = new ImportadorJson(entidades);

		return importador;
	}

	public JSONArray getEntidades() {
		return entidades;
	}

	public void setEntidades(JSONArray entidades) {
		this.entidades = entidades;
	}

	public List<Cliente> importarClientes() {
		List<Cliente> clientes = new ArrayList<Cliente>();

		try {
			Iterator<JSONObject> iterator = this.entidades.iterator();
			while (iterator.hasNext()) {
				JSONObject clienteJson = iterator.next();

				clienteJson.get("id");
				String nombreYApellido = clienteJson.get("nombre_y_apellido").toString();
				String nombreDeUsuario = clienteJson.get("nombre_de_usuario").toString();
				String contraseña = clienteJson.get("contraseña").toString();
				TipoDocumento tipoDeDocumento = TipoDocumento
						.valueOf(clienteJson.get("tipo_documento").toString().toUpperCase());
				int numeroDeDocumento = (int) (long) clienteJson.get("numero_documento");
				String telefonoDeContacto = clienteJson.get("telefono").toString();
				String domicilioDelServicio = clienteJson.get("domicilio").toString();
				String fechaDeAltaDelServicioString = clienteJson.get("fecha_alta_servicio").toString();
				Date fechaDeAltaDelServicio = null;
				try {
					fechaDeAltaDelServicio = sdf.parse(fechaDeAltaDelServicioString);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Categoria categoria = Categoria.valueOf((String) clienteJson.get("categoria"));
				// Cliente cliente = new Cliente(id, nombreDeUsuario, contraseña,
				// nombreYApellido, domicilioDelServicio,
				Cliente cliente = new Cliente(nombreDeUsuario, contraseña, nombreYApellido, domicilioDelServicio,
						tipoDeDocumento, numeroDeDocumento, telefonoDeContacto, fechaDeAltaDelServicio, categoria);

				clientes.add(cliente);
			}

		} catch (Exception e) {
			System.out.println("Error parseando lista clientes en json");
			e.printStackTrace();
			System.exit(1);
		}

		return clientes;
	}

	public List<Administrador> importarAdministradores() {
		List<Administrador> administradores = new ArrayList<Administrador>();

		try {
			Iterator<JSONObject> iterator = this.entidades.iterator();
			while (iterator.hasNext()) {
				JSONObject administradorJson = iterator.next();

//				int id = (int) (long) administradorJson.get("id");
				String nombreYApellido = administradorJson.get("nombre_y_apellido").toString();
				String nombreDeUsuario = administradorJson.get("nombre_de_usuario").toString();
				String contraseña = administradorJson.get("contraseña").toString();
				String domicilio = administradorJson.get("domicilio").toString();
				String fechaDeAltaString = administradorJson.get("fecha_de_alta").toString();
				Date fechaDeAlta = null;
				try {
					fechaDeAlta = sdf.parse(fechaDeAltaString);
				} catch (ParseException e) {
					e.printStackTrace();
					System.exit(1);
				}

				Administrador administrador = new Administrador(nombreDeUsuario, contraseña, nombreYApellido, domicilio,
						fechaDeAlta);

				administradores.add(administrador);
			}

		} catch (Exception e) {
			System.out.println("Error parseando lista administradores en json");
			e.printStackTrace();
			System.exit(1);
		}

		return administradores;
	}

	public List<Dispositivo> importarDispositivos() {
		List<Dispositivo> dispositivos = new ArrayList<Dispositivo>();

		try {
			Iterator<JSONObject> iterator = this.entidades.iterator();
			while (iterator.hasNext()) {
				JSONObject dispositivoJson = iterator.next();

				Dispositivo dispositivo;

				dispositivoJson.get("id");
				String nombre = dispositivoJson.get("nombre").toString();
				int consumoPorHora = (int) (long) dispositivoJson.get("consumo_por_hora");

				Boolean inteligente = (Boolean) dispositivoJson.get("inteligente");
				if (inteligente) {
					Estados estado = Estados.valueOf(dispositivoJson.get("estado").toString().toUpperCase());
					// dispositivo = new DispositivoInteligente(id, nombre, consumoPorHora, estado);
					dispositivo = new DispositivoInteligente(nombre, consumoPorHora, estado);
				} else {
					// dispositivo = new DispositivoEstandar(id, nombre, consumoPorHora);
					dispositivo = new DispositivoEstandar(nombre, consumoPorHora);
				}

				dispositivos.add(dispositivo);
			}
		} catch (Exception e) {
			System.out.println("Error parseando lista dispositivos en json");
			e.printStackTrace();
			System.exit(1);
		}

		return dispositivos;
	}

	public List<Zona> importarZonas() {
		List<Zona> zonas = new ArrayList<Zona>();

		try {
			Iterator<JSONObject> iterator = this.entidades.iterator();
			while (iterator.hasNext()) {
				JSONObject zonaJson = iterator.next();

				Zona zona;

//				int id = (int) (long) zonaJson.get("id");
				int radio = (int) (long) zonaJson.get("radio");
				int coordenadaX = (int) (long) zonaJson.get("coordenada_x");
				int coordenadaY = (int) (long) zonaJson.get("coordenada_y");

//				zona = new Zona(id, radio, coordenadaX, coordenadaY);
				zona = new Zona(radio, coordenadaX, coordenadaY);

				zonas.add(zona);
			}
		} catch (Exception e) {
			System.out.println("Error parseando lista zonas en json");
			e.printStackTrace();
			System.exit(1);
		}

		return zonas;
	}

	public ImportadorJson limpiarTablaTransformadores() {
		this.model.eliminarTodos(Transformador.class);

		return this;
	}

	public List<Transformador> importarTransformadores(List<Zona> zonas) {
		List<Transformador> transformadores = new ArrayList<Transformador>();

		try {
			Iterator<JSONObject> iterator = this.entidades.iterator();
			while (iterator.hasNext()) {
				JSONObject transformadorJson = iterator.next();

				Transformador transformador;

//				int id = (int) (long) transformadorJson.get("id");
				int coordenadaX = ((Long) transformadorJson.get("coordenada_x")).intValue();
				int coordenadaY = ((Long) transformadorJson.get("coordenada_y")).intValue();
				int zonaId = ((Long) transformadorJson.get("zona_id")).intValue();

				Zona zona = zonas.stream().filter(item -> zonaId == item.getId()).findAny().orElse(null);

//				transformador = new Transformador(id, zona, coordenadaX, coordenadaY);
				transformador = new Transformador(zona, coordenadaX, coordenadaY);
				this.model.agregar(transformador);

				transformadores.add(transformador);
			}
		} catch (Exception e) {
			System.out.println("Error parseando lista zonas en json");
			e.printStackTrace();
			System.exit(1);
		}

		return transformadores;
	}

	public List<Map<String, Integer>> obtenerDatosDispositivos() {
		List<Map<String, Integer>> datos = new ArrayList<>();

		try {
			Iterator<JSONObject> iterator = this.entidades.iterator();
			while (iterator.hasNext()) {
				JSONObject dispositivoJson = iterator.next();

				Map<String, Integer> item = new HashMap();

				item.put("dispositivoMaestroId", (int) (long) dispositivoJson.get("dispositivo_maestro_id"));
				item.put("usoMensualMinimoHoras", (int) (long) dispositivoJson.get("uso_mensual_minimo_horas"));
				item.put("usoMensualMaximoHoras", (int) (long) dispositivoJson.get("uso_mensual_maximo_horas"));

				datos.add(item);
			}
		} catch (Exception e) {
			System.out.println("Error parseando lista dispositivos en json");
			e.printStackTrace();
			System.exit(1);
		}

		return datos;
	}

}
