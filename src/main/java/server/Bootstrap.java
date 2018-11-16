package server;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import db.RepositorioUsuarios;
import domain.Actuador;
import domain.Administrador;
import domain.Categoria;
import domain.CategoriaDispositivo;
import domain.Cliente;
import domain.Dispositivo;
import domain.DispositivoEstandar;
import domain.DispositivoInteligente;
import domain.DispositivoMaestro;
import domain.Estados;
import domain.Periodo;
import domain.TipoDocumento;

public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {
	public static void main(String[] args) {
		new Bootstrap().init();
	}

	public void init() {

		withTransaction(() -> {
			
			DispositivoInteligente d = find(DispositivoInteligente.class, 2);
			
			Actuador actuador = new Actuador(d);
			persist(actuador);
			

//			String nombreUsuario = "MartinGonzalez";
//			String contraseña = "asd123";
//			String nombreYApellido = "Martin Gonzalez";
//			TipoDocumento tipoDocumento = TipoDocumento.DNI;
//			int numeroDocumento = 36123894;
//			String telefonoContacto = "5555-5555";
//			String domicilio = "Av. Rivadavia 1111, CABA, Buenos Aires";
//			Categoria categoria = Categoria.R1;
//			Date fechaAltaCliente = new Date();
//
//			Cliente c = new Cliente(nombreUsuario, contraseña, nombreYApellido, domicilio, tipoDocumento,
//					numeroDocumento, telefonoContacto, fechaAltaCliente, categoria);
//
//			persist(c);
			
//			Cliente c = find(Cliente.class, 3);
//			
//			DispositivoMaestro maestro1 = new DispositivoMaestro();
//			maestro1.setCategoria(CategoriaDispositivo.LAVARROPAS);
//			maestro1.setNombre("Lavarropas automatico de 5kg");
//			maestro1.setConsumo(0.175);
//			maestro1.setEsInteligente(true);
//			maestro1.setEsBajoConsumo(true);
//			persist(maestro1);
//			
//			DispositivoInteligente dispositivo1 = new DispositivoInteligente();
//			dispositivo1.setMaestro(maestro1);
//			dispositivo1.setEstado(Estados.APAGADO);
//			dispositivo1.setUsoMensualMinimoHoras(6);
//			dispositivo1.setUsoMensualMaximoHoras(30);
//			
//			dispositivo1.setCliente(c);
//			persist(dispositivo1);
//			c.agregarDispositivo(dispositivo1);
//			
//			DispositivoMaestro maestro2 = new DispositivoMaestro();
//			maestro2.setCategoria(CategoriaDispositivo.VENTILADOR);
//			maestro2.setNombre("Ventilador de pie");
//			maestro2.setConsumo(0.09);
//			maestro2.setEsInteligente(false);
//			maestro2.setEsBajoConsumo(true);
//			persist(maestro2);
//			
//			DispositivoEstandar dispositivo2 = new DispositivoEstandar();
//			dispositivo2.setMaestro(maestro2);
//			dispositivo2.setUsoMensualMinimoHoras(120);
//			dispositivo2.setUsoMensualMaximoHoras(360);
//			
//			dispositivo2.setCliente(c);
//			persist(dispositivo2);
//			c.agregarDispositivo(dispositivo2);
			
//				Dispositivo dispositivo = new DispositivoEstandar("Ventilador de pie", 0.09);
//				dispositivo.setUsoMensualMinimoHoras(120);
//				dispositivo.setUsoMensualMaximoHoras(360);
//				dispositivo.setBajoConsumo(true);
////				return dispositivo;
////			}
//
//			DispositivoMaestro maestro1 = new DispositivoMaestro();
//			maestro1.setCategoria(CategoriaDispositivo.AIRE_ACONDICIONADO);
//			maestro1.setNombre("aire acondicionado de 2200 frigorias");
//			maestro1.setConsumo(1.013);
//			maestro1.setEsInteligente(true);
//			maestro1.setEsBajoConsumo(true);
//			persist(maestro1);
//			
//			DispositivoInteligente dispositivo1 = new DispositivoInteligente();
//			dispositivo1.setMaestro(maestro1);
//			dispositivo1.setEstado(Estados.APAGADO);
//			dispositivo1.setUsoMensualMinimoHoras(90);
//			dispositivo1.setUsoMensualMaximoHoras(360);
//			
//			dispositivo1.setCliente(c);
//			persist(dispositivo1);
//			c.agregarDispositivo(dispositivo1);
//			
//			DispositivoInteligente lavarropas = (DispositivoInteligente) c.generarLavarropasAuto5kg();
//			DispositivoEstandar ventiladorDePie = (DispositivoEstandar) c.generarVentiladorDePie();

//			this.cliente.agregarDispositivo(lavarropas);
//			this.cliente.agregarDispositivo(ventiladorDePie);

//			Dispositivo d = find(Dispositivo.class, 2);
//			
//			Periodo periodo = new Periodo(LocalDateTime.of(2018, 8, 1, 0, 0), LocalDateTime.of(2018, 8, 1, 10, 0));
//			periodo.setDispositivo(d);
//			persist(periodo);
//			
//			periodo = new Periodo(LocalDateTime.of(2018, 9, 1, 0, 0), LocalDateTime.of(2018, 9, 1, 10, 0));
//			periodo.setDispositivo(d);
//			persist(periodo);
//			
//			periodo = new Periodo(LocalDateTime.of(2018, 10, 1, 0, 0), LocalDateTime.of(2018, 10, 1, 10, 0));
//			periodo.setDispositivo(d);
//			persist(periodo);
//			
//			periodo = new Periodo(LocalDateTime.of(2018, 10, 12, 0, 0), LocalDateTime.of(2018, 10, 12, 10, 0));
//			periodo.setDispositivo(d);
//			persist(periodo);

//			Periodo periodo = new Periodo(LocalDateTime.of(2018, 8, 1, 0, 0), fin);
//			periodos.add(periodo);
//			periodo.setDispositivo(this);
//			
//			
//			Periodo p = new Periodo();
//			p.setInicio(LocalDateTime.of(2018, 8, 1, 0, 0));
//			p.setFin(LocalDateTime.of(2018, 8, 1, 10, 0));
//			persist(p);
//			

//			Cliente clienteExistente = RepositorioUsuarios.instancia.buscarClientePorNombreUsuario("JuanPerez");
//			if (clienteExistente == null) {
//				return;
//			}
//
//			String nombreUsuario = "JuanPerez";
//			String contraseña = "asd123";
//			String nombreYApellido = "Juan Perez";
//			TipoDocumento tipoDocumento = TipoDocumento.DNI;
//			int numeroDocumento = 36123894;
//			String telefonoContacto = "5555-5555";
//			String domicilio = "Av. Rivadavia 1111, CABA, Buenos Aires";
//			Categoria categoria = Categoria.R1;
//			Date fechaAltaCliente = new Date();
//
//			Cliente cliente = new Cliente(nombreUsuario, contraseña, nombreYApellido, domicilio, tipoDocumento,
//					numeroDocumento, telefonoContacto, fechaAltaCliente, categoria);
//			persist(cliente);
//
//			Administrador admin = new Administrador("admin1", "adminpass", nombreYApellido, domicilio,
//					fechaAltaCliente);
//			persist(admin);
//			
//			Proyecto proyecto = new Proyecto("Proyecto 1", new BigDecimal(1000));
//			persist(proyecto);
//
//			Consultora consultora = new Consultora("Nombre consultora", 10);
//			consultora.asignar(proyecto);
//			persist(consultora);
//
//			Proyecto proyecto2 = new Proyecto("Proyecto 2", new BigDecimal(2000));
//			persist(proyecto2);
//
//			Proyecto proyecto3 = new Proyecto("Proyecto 3", new BigDecimal(3000));
//			persist(proyecto3);
		});

	}
}
