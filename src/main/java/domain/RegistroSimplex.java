package domain;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity("registros_simplex")
@Indexes(@Index(fields = @Field("clienteNombreUsuario")))
public class RegistroSimplex {

	@Id
	private ObjectId id;

	private String clienteNombreUsuario;

	private LocalDateTime fecha;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getClienteNombreUsuario() {
		return clienteNombreUsuario;
	}

	public void setClienteNombreUsuario(String clienteNombreUsuario) {
		this.clienteNombreUsuario = clienteNombreUsuario;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
}
