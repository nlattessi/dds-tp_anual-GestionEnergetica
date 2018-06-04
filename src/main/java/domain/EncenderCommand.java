package domain;

public class EncenderCommand implements Command {
	@Override
	public void execute(Inteligente dispositivo) {
		dispositivo.encenderse();
	}

}
