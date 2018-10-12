package prueba;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import domain.Acciones;
import domain.Actuador;
import domain.Administrador;
import domain.Categoria;
import domain.Cliente;
import domain.ComparacionesReglaGenerica;
import domain.Dispositivo;
import domain.DispositivoEstandar;
import domain.DispositivoInteligente;
import domain.EncenderCommand;
import domain.EncenderRegla;
import domain.Estados;
import domain.HistorialConversionEstandarInteligente;
import domain.HistorialEstadoDispositivoInteligente;
import domain.ImportadorJson;
import domain.Periodo;
import domain.ReglaGenerica;
import domain.Reglamentador;
import domain.TipoDocumento;
import domain.Transformador;
import domain.Zona;
import models.ModelHelper;

public class Entrega3Test {
	private ModelHelper model;

	@Before
	public void inicio() {
		this.model = new ModelHelper();
	}

	@Test
	public void test() {
		// fail("Not yet implemented");
	}

	@Test
	public void persistirUsuarios() {
		String nombreUsuario = "JuanPerez";
		String contraseña = "asd123";
		String nombreYApellido = "Juan Perez";
		TipoDocumento tipoDocumento = TipoDocumento.DNI;
		int numeroDocumento = 36123894;
		String telefonoContacto = "5555-5555";
		String domicilio = "Av. Rivadavia 1111, CABA, Buenos Aires";
		Categoria categoria = Categoria.R1;
		Date fechaAltaCliente = new Date();
		Cliente cliente = new Cliente(nombreUsuario, contraseña, nombreYApellido, domicilio, tipoDocumento,
				numeroDocumento, telefonoContacto, fechaAltaCliente, categoria);
		this.model.agregar(cliente);
		int idCliente = cliente.getId();
		System.out.println("Se persistió el cliente con el id: " + idCliente);
		assertEquals(1, idCliente);

		Administrador administrador = new Administrador(1, nombreUsuario, contraseña, nombreYApellido, domicilio,
				fechaAltaCliente);
		this.model.agregar(administrador);
		int idAdministrador = administrador.getId();
		System.out.println("Se persistió el administrador con el id: " + idAdministrador);
		assertEquals(2, idAdministrador);
	}

	@Test
	public void persistirDispositivos() {
		Dispositivo plancha = new DispositivoEstandar("Plancha a vapor", 0.75);
		plancha.setUsoMensualMinimoHoras(3);
		plancha.setUsoMensualMaximoHoras(30);
		plancha.setBajoConsumo(true);
		this.model.agregar(plancha);
		int planchaId = plancha.getId();
		Dispositivo planchaRecuperada = this.model.buscar(Dispositivo.class, planchaId);
		assertEquals(0.75, planchaRecuperada.getConsumoXHora(), 0);

		Dispositivo aireAcondicionado = new DispositivoInteligente("aire acondicionado de 3500 frigorias", 1.613,
				Estados.APAGADO);
		aireAcondicionado.setUsoMensualMinimoHoras(90);
		aireAcondicionado.setUsoMensualMaximoHoras(360);
		aireAcondicionado.setBajoConsumo(false);
		this.model.agregar(aireAcondicionado);
		int aireAcondicionadoId = aireAcondicionado.getId();
		Dispositivo aireAcondicionadoRecuperado = this.model.buscar(Dispositivo.class, aireAcondicionadoId);
		assertEquals(1.613, aireAcondicionadoRecuperado.getConsumoXHora(), 0);
		
		aireAcondicionadoRecuperado.encenderse();
		HistorialEstadoDispositivoInteligente historial = this.model.buscar(HistorialEstadoDispositivoInteligente.class, new ImmutablePair<>("dispositivo_inteligente_id", aireAcondicionado.getId()));
		assertEquals(Estados.ENCENDIDO, historial.getNuevoEstado());
		
		String nombreUsuario = "JuanPerez";
		String contraseña = "asd123";
		String nombreYApellido = "Juan Perez";
		TipoDocumento tipoDocumento = TipoDocumento.DNI;
		int numeroDocumento = 36123894;
		String telefonoContacto = "5555-5555";
		String domicilio = "Av. Rivadavia 1111, CABA, Buenos Aires";
		Categoria categoria = Categoria.R1;
		Date fechaAltaCliente = new Date();
		Cliente cliente = new Cliente(nombreUsuario, contraseña, nombreYApellido, domicilio, tipoDocumento,
				numeroDocumento, telefonoContacto, fechaAltaCliente, categoria);
		this.model.agregar(cliente);
		
		cliente.conversionEstandarInteligente((DispositivoEstandar) plancha);
		HistorialConversionEstandarInteligente historialDispo = this.model.buscar(HistorialConversionEstandarInteligente.class, new ImmutablePair<>("cliente_id", cliente.getId()));
		assertEquals(plancha.getNombre(), historialDispo.getEstandar().getNombre());
		assertEquals(cliente.getNombreUsuario(), historialDispo.getCliente().getNombreUsuario());
	}

	@Test
	public void casoDePrueba1() {
		// Crear 1 usuario nuevo.
		String nombreUsuario = "JuanPerez";
		String contraseña = "asd123";
		String nombreYApellido = "Juan Perez";
		TipoDocumento tipoDocumento = TipoDocumento.DNI;
		int numeroDocumento = 36123894;
		String telefonoContacto = "5555-5555";
		String domicilio = "Av. Rivadavia 1111, CABA, Buenos Aires";
		Categoria categoria = Categoria.R1;
		Date fechaAltaCliente = new Date();
		Cliente cliente = new Cliente(nombreUsuario, contraseña, nombreYApellido, domicilio, tipoDocumento,
				numeroDocumento, telefonoContacto, fechaAltaCliente, categoria);

		// Persistirlo.
		this.model.agregar(cliente);
		int id = cliente.getId();

		// Recuperarlo y modificar la geolocalización y grabarlo.
		Cliente clienteRecuperado = this.model.buscar(Cliente.class, id);
		cliente.setCoordenadasDomicilio(100, 200);
		this.model.modificar(clienteRecuperado);

		// Recuperarlo y evaluar que el cambio se haya realizado.
		clienteRecuperado = this.model.buscar(Cliente.class, id);

		assertEquals(clienteRecuperado.getCoordenadasDomicilio().getX(), 100);
		assertEquals(clienteRecuperado.getCoordenadasDomicilio().getY(), 200);
	}

	@Test
	public void casoDePrueba2() {
		DispositivoInteligente dispositivo = new DispositivoInteligente("aire acondicionado de 2200 frigorias", 1.013,
				Estados.APAGADO);
		dispositivo.setUsoMensualMinimoHoras(90);
		dispositivo.setUsoMensualMaximoHoras(360);
		dispositivo.setBajoConsumo(true);

		dispositivo.agregarPeriodo(LocalDateTime.of(2018, 8, 1, 0, 0), LocalDateTime.of(2018, 8, 1, 10, 0));
		dispositivo.agregarPeriodo(LocalDateTime.of(2018, 9, 1, 0, 0), LocalDateTime.of(2018, 9, 1, 10, 0));
		dispositivo.agregarPeriodo(LocalDateTime.of(2018, 10, 1, 0, 0), LocalDateTime.of(2018, 10, 1, 10, 0));
		dispositivo.agregarPeriodo(LocalDateTime.of(2018, 10, 12, 0, 0), LocalDateTime.of(2018, 10, 12, 10, 0));

		this.model.agregar(dispositivo);
		int id = dispositivo.getId();

		// Recuperar un dispositivo.
		DispositivoInteligente dispositivoRecuperado = this.model.buscar(DispositivoInteligente.class, id);

		// Mostrar por consola todos los intervalos que estuvo encendido durante el
		// último mes.
		int ultimoMes = 10; // Octubre
		List<Periodo> intervalos = dispositivoRecuperado.getPeriodosDelMes(10);
		System.out.println("Intervalos que estuvo encendido durante el ultimo mes (octubre):");
		System.out.println("-----------------------------------------------------------");
		intervalos.forEach(x -> {
			LocalDateTime inicio = x.getInicio();
			LocalDateTime fin = x.getFin();

			System.out.println("Intervalo");
			System.out.println("Desde: " + inicio.getYear() + "/" + inicio.getMonthValue() + "/"
					+ inicio.getDayOfMonth() + " " + inicio.getHour() + ":" + inicio.getMinute() + " hs");
			System.out.println("Hasta: " + fin.getYear() + "/" + fin.getMonthValue() + "/" + fin.getDayOfMonth() + " "
					+ fin.getHour() + ":" + fin.getMinute() + " hs");
			System.out.println("-----------------------------------------------------------");
		});

		// Modificar su nombre (o cualquier otro atributo editable) y grabarlo.
		dispositivoRecuperado.setNombre("aire acondicionado de 2200 frigorias USADO");
		this.model.modificar(dispositivoRecuperado);

		// Recuperarlo y evaluar que el nombre coincida con el esperado.
		dispositivoRecuperado = this.model.buscar(DispositivoInteligente.class, id);

		assertEquals(dispositivoRecuperado.getNombre(), "aire acondicionado de 2200 frigorias USADO");
	}

	@Test
	public void casoDePrueba3() {
		// Crear una nueva regla.
		ReglaGenerica encenderRegla = new ReglaGenerica();

		// Asociarla a un dispositivo.
		DispositivoInteligente dispositivo = new DispositivoInteligente("aire acondicionado de 2200 frigorias", 1.013,
				Estados.APAGADO);
		this.model.agregar(dispositivo);
		encenderRegla.setDispositivo(dispositivo);

		// Agregar condiciones y acciones y persistirla.
		encenderRegla.setNombreMagnitud("temperatura");
		encenderRegla.setComparacion(ComparacionesReglaGenerica.MAYORIGUAL);
		encenderRegla.setValor(24);
		encenderRegla.setAccion(Acciones.ENCENDERSE);
		this.model.agregar(encenderRegla);
		int id = encenderRegla.getId();

		// Recuperarla y ejecutarla.
		ReglaGenerica encenderReglaRecuperada = this.model.buscar(ReglaGenerica.class, id);

		assertEquals(Estados.APAGADO, dispositivo.getEstado());

		Actuador actuador = new Actuador((DispositivoInteligente) encenderReglaRecuperada.getDispositivo());
		actuador.agregarAccion(Acciones.ENCENDERSE, new EncenderCommand());
		encenderReglaRecuperada.dispararAccion(actuador);

		assertEquals(Estados.ENCENDIDO, dispositivo.getEstado());

		// Modificar alguna condición y persistirla.
		encenderReglaRecuperada.setValor(22);
		this.model.modificar(encenderReglaRecuperada);

		// Recuperarla y evaluar que la condición modificada posea la última
		// modificación.
		encenderReglaRecuperada = this.model.buscar(ReglaGenerica.class, id);

		assertEquals(22, encenderReglaRecuperada.getValor());
	}

	@Test
	public void casoDePrueba4() {
		List<Zona> zonas = ImportadorJson.desdeArchivo("zonas_de_prueba.json").importarZonas();

		ImportadorJson importador = ImportadorJson.desdeArchivo("transformadores_de_prueba.json");
		importador.importarTransformadores(zonas);

		// Recuperar todos los transformadores persistidos. Registrar la cantidad.
		List<Transformador> transformadores = this.model.buscarTodos(Transformador.class);
		int cantidad = transformadores.size();

		// Agregar una instancia de Transformador al JSON de entradas.
		JSONArray entidadesJson = importador.getEntidades();
		JSONObject obj = new JSONObject();
		obj.put("zona_id", 3L);
		obj.put("coordenada_x", 512L);
		obj.put("coordenada_y", 128L);
		entidadesJson.add(obj);
		importador.setEntidades(entidadesJson);

		// Ejecutar el método de lectura y persistencia.
		importador.limpiarTablaTransformadores().importarTransformadores(zonas);

		// Evaluar que la cantidad actual sea la anterior + 1.
		List<Transformador> transformadoresActual = this.model.buscarTodos(Transformador.class);

		assertEquals(transformadoresActual.size(), cantidad + 1);
	}

	@Test
	public void casoDePrueba5() {
		// Dado un hogar y un periodo mostrar el consumo total

		// Hogar => Cliente
		String nombreUsuario = "JuanPerez";
		String contraseña = "asd123";
		String nombreYApellido = "Juan Perez";
		TipoDocumento tipoDocumento = TipoDocumento.DNI;
		int numeroDocumento = 36123894;
		String telefonoContacto = "5555-5555";
		String domicilio = "Av. Rivadavia 1111, CABA, Buenos Aires";
		Categoria categoria = Categoria.R1;
		Date fechaAltaCliente = new Date();
		Cliente cliente = new Cliente(nombreUsuario, contraseña, nombreYApellido, domicilio, tipoDocumento,
				numeroDocumento, telefonoContacto, fechaAltaCliente, categoria);

		DispositivoInteligente dispositivo1 = new DispositivoInteligente("aire acondicionado de 2200 frigorias", 1.013,
				Estados.APAGADO);
		dispositivo1.setUsoMensualMinimoHoras(90);
		dispositivo1.setUsoMensualMaximoHoras(360);
		dispositivo1.setBajoConsumo(true);

		dispositivo1.agregarPeriodo(LocalDateTime.of(2018, 8, 1, 0, 0), LocalDateTime.of(2018, 8, 1, 10, 0));
		dispositivo1.agregarPeriodo(LocalDateTime.of(2018, 9, 1, 0, 0), LocalDateTime.of(2018, 9, 1, 10, 0));
		dispositivo1.agregarPeriodo(LocalDateTime.of(2018, 10, 1, 0, 0), LocalDateTime.of(2018, 10, 1, 10, 0));
		dispositivo1.agregarPeriodo(LocalDateTime.of(2018, 10, 12, 0, 0), LocalDateTime.of(2018, 10, 12, 10, 0));

		cliente.agregarDispositivo(dispositivo1);

		DispositivoInteligente dispositivo2 = new DispositivoInteligente("Lavarropas automatico de 5kg", 0.175,
				Estados.APAGADO);
		dispositivo2.setUsoMensualMinimoHoras(6);
		dispositivo2.setUsoMensualMaximoHoras(30);
		dispositivo2.setBajoConsumo(true);

		dispositivo2.agregarPeriodo(LocalDateTime.of(2018, 8, 1, 0, 0), LocalDateTime.of(2018, 8, 1, 10, 0));
		dispositivo2.agregarPeriodo(LocalDateTime.of(2018, 9, 3, 0, 0), LocalDateTime.of(2018, 9, 3, 10, 0));
		dispositivo2.agregarPeriodo(LocalDateTime.of(2018, 10, 6, 0, 0), LocalDateTime.of(2018, 10, 6, 10, 0));
		dispositivo2.agregarPeriodo(LocalDateTime.of(2018, 10, 7, 0, 0), LocalDateTime.of(2018, 10, 7, 10, 0));

		cliente.agregarDispositivo(dispositivo2);

		// Periodo
		Periodo periodo = new Periodo(LocalDateTime.of(2018, 10, 1, 0, 0), LocalDateTime.of(2018, 10, 31, 11, 59));

		System.out.println("-----------------------------------------------------------");
		System.out.println("Hogar: " + cliente.getDomicilio());
		System.out.println(
				"ConsumoTotal: " + cliente.calcularConsumoEntrePeriodos(periodo.getInicio(), periodo.getFin()));

		assertEquals(23.75, cliente.calcularConsumoEntrePeriodos(periodo.getInicio(), periodo.getFin()), 0.01);

		// Dado un dispositivo y un periodo, mostrar por consola su consumo promedio
		System.out.println("dispositivo: " + dispositivo1.getNombre());
		System.out.println("Cosumo Promedio: "
				+ dispositivo1.consumoPromedioComprendidoEntre(periodo.getInicio(), periodo.getFin()));

		// Dado un transformador y un periodo, mostrar por consola su consumo promedio
		Zona zona = new Zona(1000, -38, -58);
		this.model.agregar(zona);
		Transformador transformador = new Transformador(zona, -38, -58);
		transformador.agregarCliente(cliente);
		System.out.println("Consumo promedio Transformador: "
				+ transformador.consumoPromedioEntre(periodo.getInicio(), periodo.getFin()));

		// Recuperar un dispositivo asociado a un hogar de ese transformador e
		// incrementar un 1000% el consumo para ese periodo.
		this.model.agregar(transformador);
		int id = transformador.getId();

		Transformador transformadorRecuperado = this.model.buscar(Transformador.class, id);

		DispositivoInteligente dispositivoTransformador = (DispositivoInteligente) transformador.getClientesConectados()
				.get(0).getDispositivos().get(0);

		System.out.println("Consumo antes de incrementacion: "
				+ dispositivoTransformador.consumoTotalComprendidoEntre(periodo.getInicio(), periodo.getFin()));

		dispositivoTransformador.agregarPeriodo(LocalDateTime.of(2018, 10, 2, 0, 0),
				LocalDateTime.of(2018, 10, 2, 20, 0));
		dispositivoTransformador.agregarPeriodo(LocalDateTime.of(2018, 10, 3, 0, 0),
				LocalDateTime.of(2018, 10, 3, 20, 0));
		dispositivoTransformador.agregarPeriodo(LocalDateTime.of(2018, 10, 4, 0, 0),
				LocalDateTime.of(2018, 10, 4, 20, 0));
		dispositivoTransformador.agregarPeriodo(LocalDateTime.of(2018, 10, 5, 0, 0),
				LocalDateTime.of(2018, 10, 5, 20, 0));
		dispositivoTransformador.agregarPeriodo(LocalDateTime.of(2018, 10, 6, 0, 0),
				LocalDateTime.of(2018, 10, 6, 20, 0));
		dispositivoTransformador.agregarPeriodo(LocalDateTime.of(2018, 10, 7, 0, 0),
				LocalDateTime.of(2018, 10, 7, 20, 0));
		dispositivoTransformador.agregarPeriodo(LocalDateTime.of(2018, 10, 8, 0, 0),
				LocalDateTime.of(2018, 10, 8, 20, 0));
		dispositivoTransformador.agregarPeriodo(LocalDateTime.of(2018, 10, 9, 0, 0),
				LocalDateTime.of(2018, 10, 9, 20, 0));
		dispositivoTransformador.agregarPeriodo(LocalDateTime.of(2018, 10, 10, 0, 0),
				LocalDateTime.of(2018, 10, 10, 20, 0));

		System.out.println("Consumo despues de incrementacion: "
				+ dispositivoTransformador.consumoTotalComprendidoEntre(periodo.getInicio(), periodo.getFin()));

		// Persistir el dispositivo y mostrar consumo para ese transofrmador
		this.model.agregar(dispositivoTransformador);
		int idDispositivo = dispositivoTransformador.getId();
		System.out.println("Consumo Transformador despues de modificar dispositivo: "
				+ transformador.consumoPromedioEntre(periodo.getInicio(), periodo.getFin()));

	}

}
