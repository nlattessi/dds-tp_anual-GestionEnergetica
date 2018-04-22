package domain;

public class Categoria {

	private int id;
	private String nombre;
	private float cargoFijo;
	private float cargoVariable;
	private int limiteInferiorConsumo;
	private int limiteSuperiorConsumo;

	public Categoria(int id, String nombre, float cargoFijo, float cargoVariable, int limiteInferiorConsumo,
			int limiteSuperiorConsumo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.cargoFijo = cargoFijo;
		this.cargoVariable = cargoVariable;
		this.limiteInferiorConsumo = limiteInferiorConsumo;
		this.limiteSuperiorConsumo = limiteSuperiorConsumo;
	}

	public int getId() {
		return id;
	}

	public void setIdCategoria(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getCargoFijo() {
		return cargoFijo;
	}

	public void setCargoFijo(float cargoFijo) {
		this.cargoFijo = cargoFijo;
	}

	public float getCargoVariable() {
		return cargoVariable;
	}

	public void setCargoVariable(float cargoVariable) {
		this.cargoVariable = cargoVariable;
	}

	public int getLimiteInferiorConsumo() {
		return limiteInferiorConsumo;
	}

	public void setLimiteInferiorConsumo(int limiteInferiorConsumo) {
		this.limiteInferiorConsumo = limiteInferiorConsumo;
	}

	public int getLimiteSuperiorConsumo() {
		return limiteSuperiorConsumo;
	}

	public void setLimiteSuperiorConsumo(int limiteSuperiorConsumo) {
		this.limiteSuperiorConsumo = limiteSuperiorConsumo;
	}

}
