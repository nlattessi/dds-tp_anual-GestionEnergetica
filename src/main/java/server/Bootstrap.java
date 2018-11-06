package server;

import java.math.BigDecimal;
import java.util.Date;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import db.RepositorioUsuarios;
import domain.Administrador;
import domain.Categoria;
import domain.Cliente;
import domain.TipoDocumento;

public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {
	public static void main(String[] args) {
		new Bootstrap().init();
	}

	public void init() {

		withTransaction(() -> {

//			Cliente clienteExistente = RepositorioUsuarios.instancia.buscarClientePorNombreUsuario("JuanPerez");
//			if (clienteExistente == null) {
//				return;
//			}

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
			persist(cliente);

			Administrador admin = new Administrador("admin1", "adminpass", nombreYApellido, domicilio,
					fechaAltaCliente);
			persist(admin);
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
