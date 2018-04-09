package prueba;

import org.junit.Assert;
import org.junit.Test;

import domain.Pajaro;

public class PajaroTest {
	
	@Test
	public void pichonDeberianNacerConMuchaEnergia() {
		Pajaro pichon = new Pajaro();
		Assert.assertEquals(500, pichon.getEnergia());
	}

}
