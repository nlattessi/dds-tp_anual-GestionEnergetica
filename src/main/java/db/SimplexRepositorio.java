package db;

import java.time.LocalDateTime;
import java.util.Calendar;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import domain.Cliente;
import domain.Dispositivo;
import domain.RegistroSimplex;
import domain.ReporteConsumoTransformadorPeriodo;

public class SimplexRepositorio {
	private Datastore ds;

	public SimplexRepositorio(Datastore ds) {
		this.ds = ds;
	}

	public RegistroSimplex getRegistro(Cliente cliente) {
		Query<RegistroSimplex> query = ds.createQuery(RegistroSimplex.class);
		return query.field("clienteNombreUsuario").equal(cliente.getNombreUsuario()).get();
	}

	public void guardarRegistro(Cliente cliente) {
		RegistroSimplex oldRegistro = getRegistro(cliente);

		if (oldRegistro != null) {
			ds.delete(oldRegistro);
		}

		RegistroSimplex registro = new RegistroSimplex();
		registro.setClienteNombreUsuario(cliente.getNombreUsuario());

		Calendar c = Calendar.getInstance();
		LocalDateTime fecha = Dispositivo.toLocalDateTime(c);
		registro.setFecha(fecha);

		ds.save(registro);
	}
}
