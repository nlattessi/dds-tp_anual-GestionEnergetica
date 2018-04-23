package domain;

import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ImportadorJson {

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private JSONObject entidades;

	public ImportadorJson(JSONObject entidades) {
		this.entidades = entidades;
	}

	public static ImportadorJson desdeArchivo(String archivo) {
		JSONParser parser = new JSONParser();

		JSONObject entidades = null;

		try {
			Object obj = parser.parse(new FileReader(archivo));
			entidades = (JSONObject) obj;

		} catch (Exception e) {
			e.printStackTrace();
		}

		ImportadorJson importador = new ImportadorJson(entidades);

		return importador;
	}

	public List<Categoria> importarCategorias() {
		List<Categoria> categorias = new ArrayList<Categoria>();

		JSONArray categoriasJson = (JSONArray) this.entidades.get("categorias");

		Iterator<JSONObject> iterator = categoriasJson.iterator();
		while (iterator.hasNext()) {
			JSONObject categoriaJson = iterator.next();

			int id = (int) (long) categoriaJson.get("id");
			String nombre = (String) categoriaJson.get("nombre");
			Double cargoFijo = (Double) categoriaJson.get("cargo_fijo");
			Double cargoVariable = (Double) categoriaJson.get("cargo_variable");
			int limiteInferiorConsumo = (int) (long) categoriaJson.get("limite_inferior_consumo");
			int limiteSuperiorConsumo = (int) (long) categoriaJson.get("limite_superior_consumo");

			Categoria categoria = new Categoria(id, nombre, cargoFijo, cargoVariable, limiteInferiorConsumo,
					limiteSuperiorConsumo);

			categorias.add(categoria);
		}

		return categorias;
	}

	public List<Cliente> importarClientes() {
		List<Cliente> clientes = new ArrayList<Cliente>();

		JSONArray clientesJson = (JSONArray) this.entidades.get("clientes");

		Iterator<JSONObject> iterator = clientesJson.iterator();
		while (iterator.hasNext()) {
			JSONObject clienteJson = iterator.next();

			int id = (int) (long) clienteJson.get("id");
			String nombreYApellido = (String) clienteJson.get("nombre_y_apellido");
			String nombreDeUsuario = (String) clienteJson.get("nombre_de_usuario");
			String contraseña = (String) clienteJson.get("contraseña");

			JSONObject documentoJson = (JSONObject) clienteJson.get("documento");
			String tipoDeDocumentoString = (String) documentoJson.get("tipo");
			Documento.Tipo tipoDeDocumento = Documento.Tipo.valueOf(tipoDeDocumentoString);
			int numeroDeDocumento = (int) (long) documentoJson.get("numero");
			Documento documento = new Documento(tipoDeDocumento, numeroDeDocumento);

			String telefonoDeContacto = (String) clienteJson.get("telefono");

			JSONObject domicilioJson = (JSONObject) clienteJson.get("domicilio");
			String calle = (String) domicilioJson.get("calle");
			int numero = (int) (long) domicilioJson.get("numero");
			int codigoPostal = (int) (long) domicilioJson.get("codigo_postal");
			String ciudad = (String) domicilioJson.get("ciudad");
			String provincia = (String) domicilioJson.get("provincia");
			Domicilio domicilioDelServicio = new Domicilio(calle, numero, codigoPostal, ciudad, provincia);

			String fechaDeAltaDelServicioString = (String) clienteJson.get("fecha_alta_servicio");
			Date fechaDeAltaDelServicio = null;
			try {
				fechaDeAltaDelServicio = sdf.parse(fechaDeAltaDelServicioString);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			String categoriaString = (String) clienteJson.get("categoria");
			Categoria categoria = null;
			switch (categoriaString) {
			case "R1":
				categoria = Categoria.crearR1();
				break;
			case "R2":
				categoria = Categoria.crearR2();
				break;
			case "R3":
				categoria = Categoria.crearR3();
				break;
			case "R4":
				categoria = Categoria.crearR4();
				break;
			case "R5":
				categoria = Categoria.crearR5();
				break;
			case "R6":
				categoria = Categoria.crearR6();
				break;
			case "R7":
				categoria = Categoria.crearR7();
				break;
			case "R8":
				categoria = Categoria.crearR8();
				break;
			case "R9":
				categoria = Categoria.crearR9();
				break;
			}

			Cliente cliente = new Cliente(id, nombreDeUsuario, contraseña, nombreYApellido, domicilioDelServicio,
					documento, telefonoDeContacto, fechaDeAltaDelServicio, categoria);

			clientes.add(cliente);
		}

		return clientes;
	}

	public List<Administrador> importarAdministradores() {
		List<Administrador> administradores = new ArrayList<Administrador>();

		JSONArray administradoresJson = (JSONArray) this.entidades.get("administradores");

		Iterator<JSONObject> iterator = administradoresJson.iterator();
		while (iterator.hasNext()) {
			JSONObject administradorJson = iterator.next();

			int id = (int) (long) administradorJson.get("id");
			String nombreYApellido = (String) administradorJson.get("nombre_y_apellido");
			String nombreDeUsuario = (String) administradorJson.get("nombre_de_usuario");
			String contraseña = (String) administradorJson.get("contraseña");

			JSONObject domicilioJson = (JSONObject) administradorJson.get("domicilio");
			String calle = (String) domicilioJson.get("calle");
			int numero = (int) (long) domicilioJson.get("numero");
			int codigoPostal = (int) (long) domicilioJson.get("codigo_postal");
			String ciudad = (String) domicilioJson.get("ciudad");
			String provincia = (String) domicilioJson.get("provincia");
			Domicilio domicilio = new Domicilio(calle, numero, codigoPostal, ciudad, provincia);

			String fechaDeAltaString = (String) administradorJson.get("fecha_de_alta");
			Date fechaDeAlta = null;
			try {
				fechaDeAlta = sdf.parse(fechaDeAltaString);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			Administrador administrador = new Administrador(id, nombreYApellido, nombreDeUsuario, contraseña, domicilio,
					fechaDeAlta);

			administradores.add(administrador);

		}

		return administradores;
	}

	public List<Dispositivo> importarDispositivos() {
		List<Dispositivo> dispositivos = new ArrayList<Dispositivo>();

		JSONArray dispositivosJson = (JSONArray) this.entidades.get("dispositivos");

		Iterator<JSONObject> iterator = dispositivosJson.iterator();
		while (iterator.hasNext()) {
			JSONObject dispositivoJson = iterator.next();

			int id = (int) (long) dispositivoJson.get("id");
			String nombre = (String) dispositivoJson.get("nombre");
			int consumoPorHora = (int) (long) dispositivoJson.get("consumo_por_hora");
			Boolean encendido = (Boolean) dispositivoJson.get("encendido");

			Dispositivo dispositivo = new Dispositivo(id, nombre, consumoPorHora, encendido);

			dispositivos.add(dispositivo);
		}

		return dispositivos;
	}

}
