package domain;

public class Categoria {

	private int id;
	private String nombre;
	private double cargoFijo;
	private double cargoVariable;
	private int limiteInferiorConsumo;
	private int limiteSuperiorConsumo;

	public Categoria(int id, String nombre, double cargoFijo, double cargoVariable, int limiteInferiorConsumo,
			Integer limiteSuperiorConsumo) {
		this.id = id;
		this.nombre = nombre;
		this.cargoFijo = cargoFijo;
		this.cargoVariable = cargoVariable;
		this.limiteInferiorConsumo = limiteInferiorConsumo;
		this.limiteSuperiorConsumo = limiteSuperiorConsumo;
	}

	public static Categoria crearR1() {
		return new Categoria(0, "R1", 18.76, 0.644, 0, 150);
	}

	public static Categoria crearR2() {
		return new Categoria(0, "R2", 35.32, 0.644, 150, 325);
	}

	public static Categoria crearR3() {
		return new Categoria(0, "R3", 60.71, 0.681, 325, 400);
	}

	public static Categoria crearR4() {
		return new Categoria(0, "R4", 71.74, 0.738, 400, 450);
	}

	public static Categoria crearR5() {
		return new Categoria(0, "R5", 110.38, 0.794, 450, 500);
	}

	public static Categoria crearR6() {
		return new Categoria(0, "R6", 220.75, 0.832, 500, 600);
	}

	public static Categoria crearR7() {
		return new Categoria(0, "R7", 443.59, 0.851, 600, 700);
	}

	public static Categoria crearR8() {
		return new Categoria(0, "R8", 545.96, 0.851, 700, 1400);
	}

	public static Categoria crearR9() {
		return new Categoria(0, "R9", 887.19, 0.851, 1400, null);
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
