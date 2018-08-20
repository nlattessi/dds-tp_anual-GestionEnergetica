package domain;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
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
	
	public List<Dispositivo> calcularHogarEficiente(List<Dispositivo> dispositivos)
	{
		 List<Dispositivo> dispositivosPermitenCalculo = 
				 dispositivos.stream().filter(p -> p.getPermiteCalculoAhorroInteligente()).collect(Collectors.<Dispositivo> toList());
		 List<Dispositivo> dispositivosNoPermitenCalculo = 
				 dispositivos.stream().filter(p -> p.getPermiteCalculoAhorroInteligente() == false).collect(Collectors.<Dispositivo> toList());
		 double[] variablesFuncionEc = new double[dispositivosPermitenCalculo.size()];
		 for (int i = 0; i < dispositivosPermitenCalculo.size(); i++)
		 {
			 variablesFuncionEc[i] = 1;
		 }
		 this.crearFuncionEconomica(variablesFuncionEc);
		 
		 double[] consumoDispositivos = new double[dispositivosPermitenCalculo.size()];
		 
		 for (int i = 0; i < dispositivosPermitenCalculo.size(); i++)
		 {
			 consumoDispositivos[i] = dispositivosPermitenCalculo.get(i).getConsumoXHora();
		 }
		 
		 this.agregarRestriccion(Relationship.LEQ, 440640, consumoDispositivos);
		 
		 for (int i = 0; i < dispositivosPermitenCalculo.size(); i++)
		 {
			 double[] variablesRestricciones = new double[dispositivosPermitenCalculo.size()];
			 
			 for (int j = 0; j < dispositivosPermitenCalculo.size(); j++)
			 {
					 if (j == i){	variablesRestricciones[j] = 1;	}
					 else{	variablesRestricciones[j] = 0;	}
			 }
			 this.agregarRestriccion(Relationship.GEQ, dispositivosPermitenCalculo.get(i).getUsoMensualMinimoHoras(), variablesRestricciones);
			 this.agregarRestriccion(Relationship.LEQ, dispositivosPermitenCalculo.get(i).getUsoMensualMaximoHoras(), variablesRestricciones);
		 }
		 
		 PointValuePair solucion = this.resolver();
		 
		 for (int i = 0; i < dispositivosPermitenCalculo.size(); i++)
		 {
			 dispositivosPermitenCalculo.get(i).setConsumoRecomendadoHoras(solucion.getPoint()[i]);
		 }
		 
		 List<Dispositivo> dispositivosFinal = dispositivosPermitenCalculo;
		 
		 if (dispositivosNoPermitenCalculo != null)
		 {
			 for(Dispositivo dispositivoNoPermite : dispositivosNoPermitenCalculo)
			 {
				 dispositivosFinal.add(dispositivoNoPermite);
			 }
		 }
		 return dispositivosFinal;
	}
	
	public void crearFuncionEconomica(double ... coeficientes) {
		this.funcionEconomica = new LinearObjectiveFunction(coeficientes, 0);
	}
	
	public void agregarRestriccion(Relationship unComparador, double valorAcomprar, double ... coeficientes) {
		this.restricciones.add(new LinearConstraint(coeficientes,unComparador, valorAcomprar));
	}
	
	public PointValuePair resolver() throws TooManyIterationsException{
		return this.simplex.optimize(
										new MaxIter(100),
										this.funcionEconomica,
										new LinearConstraintSet(this.restricciones),
										this.objetivo,
										new NonNegativeConstraint(this.variablesPositivas)
				);
	}
}