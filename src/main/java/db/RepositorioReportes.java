package db;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.mongodb.MongoClient;

import domain.Cliente;
import domain.Dispositivo;
import domain.ReporteConsumoHogarPeriodo;
import domain.ReporteConsumoTipoDispositivoPeriodo;
import domain.ReporteConsumoTransformadorPeriodo;
import domain.Transformador;

public class RepositorioReportes {

	private Datastore ds;
	private RepositorioUsuarios repositorioUsuarios;
	private RepositorioDispositivos repositorioDispositivos;
	private RepositorioTransformadores repositorioTransformadores;

	public RepositorioReportes(Datastore ds, RepositorioUsuarios repositorioUsuarios,
			RepositorioDispositivos repositorioDispositivos, RepositorioTransformadores repositorioTransformadores) {
		this.ds = ds;
		this.repositorioUsuarios = repositorioUsuarios;
		this.repositorioDispositivos = repositorioDispositivos;
		this.repositorioTransformadores = repositorioTransformadores;
	}

	public ReporteConsumoHogarPeriodo obtenerReporteConsumoHogarPeriodo(String clienteIdParam, String inicioParam,
			String finParam) {

		String reporteConsumoHogarPeriodoId = reporteConsumoHogarPeriodoId(clienteIdParam, inicioParam, finParam);

		Query<ReporteConsumoHogarPeriodo> query = ds.createQuery(ReporteConsumoHogarPeriodo.class);
		ReporteConsumoHogarPeriodo reporte = query.field("reporteId").equal(reporteConsumoHogarPeriodoId).get();

		if (reporte != null) {
			return reporte;
		}

		Cliente cliente = repositorioUsuarios.buscarClientePorId(Integer.parseInt(clienteIdParam));

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime inicio = LocalDateTime.parse(inicioParam, formatter);
		LocalDateTime fin = LocalDateTime.parse(finParam, formatter);

		double consumo = cliente.calcularConsumoEntrePeriodos(inicio, fin);

		reporte = new ReporteConsumoHogarPeriodo();
		reporte.setNombreYApellido(cliente.getNombreYApellido());
		reporte.setInicio(inicioParam);
		reporte.setFin(finParam);
		reporte.setConsumo(consumo);
		reporte.setReporteId(reporteConsumoHogarPeriodoId);

		ds.save(reporte);

		return reporte;
	}

	private String reporteConsumoHogarPeriodoId(String clienteId, String inicio, String fin) {
		return clienteId + "_" + inicio + "_" + fin;
	}

	public ReporteConsumoTipoDispositivoPeriodo obtenerReporteConsumoTipoDispositivoPeriodo(String tipoDispositivoParam,
			String inicioParam, String finParam) {

		String reporteConsumoTipoDispositivoPeriodoId = reporteConsumoTipoDispositivoPeriodoId(tipoDispositivoParam,
				inicioParam, finParam);

		Query<ReporteConsumoTipoDispositivoPeriodo> query = ds.createQuery(ReporteConsumoTipoDispositivoPeriodo.class);
		ReporteConsumoTipoDispositivoPeriodo reporte = query.field("reporteId")
				.equal(reporteConsumoTipoDispositivoPeriodoId).get();

		if (reporte != null) {
			return reporte;
		}

		boolean esInteligente = tipoDispositivoParam.equals("1");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime inicio = LocalDateTime.parse(inicioParam, formatter);
		LocalDateTime fin = LocalDateTime.parse(finParam, formatter);

		List<Dispositivo> dispositivos = (esInteligente) ? repositorioDispositivos.listarInteligente()
				: repositorioDispositivos.listarEstandar();

		double consumoTotal = 0;
		for (Dispositivo dispositivo : dispositivos) {
			consumoTotal = consumoTotal + dispositivo.consumoTotalComprendidoEntre(inicio, fin);
		}

		double consumoPromedio = (consumoTotal == 0) ? 0 : (consumoTotal / dispositivos.size());

		reporte = new ReporteConsumoTipoDispositivoPeriodo();
		reporte.setTipoDispositivo(tipoDispositivoParam);
		reporte.setInicio(inicioParam);
		reporte.setFin(finParam);
		reporte.setConsumoPromedio(consumoPromedio);
		reporte.setReporteId(reporteConsumoTipoDispositivoPeriodoId);

		ds.save(reporte);

		return reporte;
	}

	private String reporteConsumoTipoDispositivoPeriodoId(String tipoDispositivoParam, String inicioParam,
			String finParam) {
		return tipoDispositivoParam + "_" + inicioParam + "_" + finParam;
	}

	public ReporteConsumoTransformadorPeriodo obtenerReporteConsumoTransformadorPeriodo(String inicioParam,
			String finParam) {

		String reporteConsumoTransformadorPeriodoId = reporteConsumoTransformadorPeriodoId(inicioParam, finParam);

		Query<ReporteConsumoTransformadorPeriodo> query = ds.createQuery(ReporteConsumoTransformadorPeriodo.class);
		ReporteConsumoTransformadorPeriodo reporte = query.field("reporteId")
				.equal(reporteConsumoTransformadorPeriodoId).get();

		if (reporte != null) {
			return reporte;
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime inicio = LocalDateTime.parse(inicioParam, formatter);
		LocalDateTime fin = LocalDateTime.parse(finParam, formatter);

		List<Map<String, String>> transformadores = new ArrayList<>();

		List<Transformador> listaTransformadores = repositorioTransformadores.listar();

		for (Transformador transformador : listaTransformadores) {
			Map<String, String> item = new HashMap<>();
			item.put("id", String.valueOf(transformador.getId()));
			item.put("latitud", String.valueOf(transformador.getLatitud()));
			item.put("longitud", String.valueOf(transformador.getLongitud()));
			item.put("cantidadClientesConectados", String.valueOf(transformador.getClientesConectados().size()));
			item.put("consumo", String.valueOf(transformador.consumoPromedioEntre(inicio, fin)));

			transformadores.add(item);
		}

		reporte = new ReporteConsumoTransformadorPeriodo();
		reporte.setInicio(inicioParam);
		reporte.setFin(finParam);
		reporte.setTransformadores(transformadores);
		reporte.setReporteId(reporteConsumoTransformadorPeriodoId);
		
		ds.save(reporte);

		return reporte;
	}

	private String reporteConsumoTransformadorPeriodoId(String inicioParam, String finParam) {
		return inicioParam + "_" + finParam;
	}

}
