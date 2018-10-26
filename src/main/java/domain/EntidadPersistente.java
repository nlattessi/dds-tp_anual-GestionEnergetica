package domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class EntidadPersistente {
	@Id
	@GeneratedValue
	private int id;

	@Version
	private long version;

	public int getId() {
		return id;
	}
}
