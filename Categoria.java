
public class Categoria {
	
	private int idCategoria;
	private String nombre;
	private float cargoFijo;
	private float cargoVariable;
	private int limiteInferiorConsumo;
	private int limiteSuperiorConsumo;
	private float consumoEficiente;
	
	public Categoria(int idCategoria, String nombre, float cargoFijo,
			float cargoVariable, int limiteInferiorConsumo,
			int limiteSuperiorConsumo, float consumoEficiente) {
		super();
		this.idCategoria = idCategoria;
		this.nombre = nombre;
		this.cargoFijo = cargoFijo;
		this.cargoVariable = cargoVariable;
		this.limiteInferiorConsumo = limiteInferiorConsumo;
		this.limiteSuperiorConsumo = limiteSuperiorConsumo;
		this.consumoEficiente = consumoEficiente;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
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

	public float getConsumoEficiente() {
		return consumoEficiente;
	}

	public void setConsumoEficiente(float consumoEficiente) {
		this.consumoEficiente = consumoEficiente;
	}
	
	

}
