package domain;
public class Locacion{
	public Locacion(int distanciaX, int distanciaY) {
	
	}
	int x;
	int y; 
	
public double obtenerDistancia(int lat1, int alt1, int lat2, int alt2) {
		double distanciaX = (lat1 - lat2);
		double distanciaY = (alt1 - lat2); 
		double hipotenusa = Math.sqrt(distanciaX*distanciaX + distanciaY*distanciaY);
		return hipotenusa;
	}

}

