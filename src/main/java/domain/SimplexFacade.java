package domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.*;

import org.apache.commons.math3.exception.TooManyIterationsException;
import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.NonNegativeConstraint;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.linear.SimplexSolver;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

public class SimplexFacade {
	private SimplexSolver simplex;
	private LinearObjectiveFunction funcionEconomica;
	private Collection<LinearConstraint> restricciones;
	private GoalType objetivo;
	private boolean variablesPositivas;

	public SimplexFacade(GoalType objetivo, boolean variablesPositivas) {
		this.variablesPositivas = variablesPositivas;
		this.objetivo = objetivo;
		this.restricciones = new ArrayList<LinearConstraint>();
		this.simplex = new SimplexSolver();
	}

	public List<Dispositivo> calcularHogarEficiente(List<Dispositivo> dispositivos) {
		List<Dispositivo> dispositivosPermitenAhorro = new ArrayList<Dispositivo>();
		;

		for (Dispositivo dispositivo : dispositivos) {
			if (dispositivo.getPermiteCalculoAhorro()) {
				dispositivosPermitenAhorro.add(dispositivo);
			}
		}

		double[] variablesFuncionEc = new double[dispositivosPermitenAhorro.size()];
		for (int i = 0; i < dispositivosPermitenAhorro.size(); i++) {
			variablesFuncionEc[i] = 1;
		}
		this.crearFuncionEconomica(variablesFuncionEc);

		double[] consumoDispositivos = new double[dispositivosPermitenAhorro.size()];

		for (int i = 0; i < dispositivosPermitenAhorro.size(); i++) {
			consumoDispositivos[i] = dispositivosPermitenAhorro.get(i).getConsumoXHora();
		}

		this.agregarRestriccion(Relationship.LEQ, 440640, consumoDispositivos);

		for (int i = 0; i < dispositivosPermitenAhorro.size(); i++) {
			double[] variablesRestricciones = new double[dispositivosPermitenAhorro.size()];

			for (int j = 0; j < dispositivosPermitenAhorro.size(); j++) {
				if (j == i) {
					variablesRestricciones[j] = 1;
				} else {
					variablesRestricciones[j] = 0;
				}
			}
			this.agregarRestriccion(Relationship.GEQ, dispositivosPermitenAhorro.get(i).getUsoMensualMinimoHoras(),
					variablesRestricciones);
			this.agregarRestriccion(Relationship.LEQ, dispositivosPermitenAhorro.get(i).getUsoMensualMaximoHoras(),
					variablesRestricciones);
		}

		PointValuePair solucion = this.resolver();

		for (int i = 0; i < dispositivosPermitenAhorro.size(); i++) {
			dispositivosPermitenAhorro.get(i).setConsumoRecomendadoHoras(solucion.getPoint()[i]);
		}

		for (Dispositivo dispositivoPermA : dispositivosPermitenAhorro) {
			for (Dispositivo dispositivo : dispositivos) {
				if (dispositivo.equals(dispositivoPermA)) {
					dispositivo.setConsumoRecomendadoHoras(dispositivoPermA.getConsumoRecomendadoHoras());
				}
			}
		}

		return dispositivos;
	}

	public Set<Dispositivo> calcularHogarEficiente(Set<Dispositivo> dispositivos) {
		List<Dispositivo> dispositivosPermitenAhorro = new ArrayList<Dispositivo>();
		;

		for (Dispositivo dispositivo : dispositivos) {
			if (dispositivo.getPermiteCalculoAhorro()) {
				dispositivosPermitenAhorro.add(dispositivo);
			}
		}

		double[] variablesFuncionEc = new double[dispositivosPermitenAhorro.size()];
		for (int i = 0; i < dispositivosPermitenAhorro.size(); i++) {
			variablesFuncionEc[i] = 1;
		}
		this.crearFuncionEconomica(variablesFuncionEc);

		double[] consumoDispositivos = new double[dispositivosPermitenAhorro.size()];

		for (int i = 0; i < dispositivosPermitenAhorro.size(); i++) {
			consumoDispositivos[i] = dispositivosPermitenAhorro.get(i).getConsumoXHora();
		}

		this.agregarRestriccion(Relationship.LEQ, 440640, consumoDispositivos);

		for (int i = 0; i < dispositivosPermitenAhorro.size(); i++) {
			double[] variablesRestricciones = new double[dispositivosPermitenAhorro.size()];

			for (int j = 0; j < dispositivosPermitenAhorro.size(); j++) {
				if (j == i) {
					variablesRestricciones[j] = 1;
				} else {
					variablesRestricciones[j] = 0;
				}
			}
			this.agregarRestriccion(Relationship.GEQ, dispositivosPermitenAhorro.get(i).getUsoMensualMinimoHoras(),
					variablesRestricciones);
			this.agregarRestriccion(Relationship.LEQ, dispositivosPermitenAhorro.get(i).getUsoMensualMaximoHoras(),
					variablesRestricciones);
		}

		PointValuePair solucion = this.resolver();

		for (int i = 0; i < dispositivosPermitenAhorro.size(); i++) {
			dispositivosPermitenAhorro.get(i).setConsumoRecomendadoHoras(solucion.getPoint()[i]);
		}

		for (Dispositivo dispositivoPermA : dispositivosPermitenAhorro) {
			for (Dispositivo dispositivo : dispositivos) {
				if (dispositivo.equals(dispositivoPermA)) {
					dispositivo.setConsumoRecomendadoHoras(dispositivoPermA.getConsumoRecomendadoHoras());
				}
			}
		}

		return dispositivos;
	}

	public void crearFuncionEconomica(double... coeficientes) {
		this.funcionEconomica = new LinearObjectiveFunction(coeficientes, 0);
	}

	public void agregarRestriccion(Relationship unComparador, double valorAcomprar, double... coeficientes) {
		this.restricciones.add(new LinearConstraint(coeficientes, unComparador, valorAcomprar));
	}

	public PointValuePair resolver() throws TooManyIterationsException {
		return this.simplex.optimize(new MaxIter(100), this.funcionEconomica,
				new LinearConstraintSet(this.restricciones), this.objetivo,
				new NonNegativeConstraint(this.variablesPositivas));
	}
}