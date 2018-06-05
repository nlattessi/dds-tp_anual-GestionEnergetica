package domain;

public class EncenderCommand implements Command {
	@Override
	public void execute(DispositivoInteligente dispositivo) {
		dispositivo.encenderse();
	}

}
