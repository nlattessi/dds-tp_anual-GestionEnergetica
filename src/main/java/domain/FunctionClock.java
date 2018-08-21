package domain;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FunctionClock implements Runnable{
	
	private Cliente cliente;
	
	public FunctionClock(Cliente cliente)
	{
		this.cliente = cliente;
	}
	
	public void run() {
        this.cliente.calcularHogarEficiente();
    }

    public void main(String[] args) {
        ScheduledExecutorService scheduler
                = Executors.newSingleThreadScheduledExecutor();

        Runnable task = new FunctionClock(this.cliente);
        int initialDelay = 0;
        int periodicDelay = this.cliente.getPeriodicidadAhorroInteligente();
        scheduler.scheduleAtFixedRate(task, initialDelay, periodicDelay, TimeUnit.MINUTES);
    }
    
    public Cliente getCliente() {
    	return this.cliente;
    }
    
    public void setCliente(Cliente cliente) {
    	this.cliente = cliente;
    }
}
