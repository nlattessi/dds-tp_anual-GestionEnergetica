package domain;

public class Categoria {

	private int id;
	private String nombre;
	private double cargoFijo;
	private double cargoVariable;
	private int limiteInferiorConsumo;
	private int limiteSuperiorConsumo;

	public Categoria(int id, String nombre, double cargoFijo, double cargoVariable, int limiteInferiorConsumo,
			int limiteSuperiorConsumo) {
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

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getCargoFijo() {
		return cargoFijo;
	}

	public void setCargoFijo(double cargoFijo) {
		this.cargoFijo = cargoFijo;
	}

	public double getCargoVariable() {
		return cargoVariable;
	}

	public void setCargoVariable(double cargoVariable) {
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
