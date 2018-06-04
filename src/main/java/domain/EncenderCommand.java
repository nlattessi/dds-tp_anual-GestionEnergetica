package domain;

public class EncenderCommand implements Command {
	private Inteligente dispositivo;

	public EncenderCommand(Inteligente dispositivo) {
		this.dispositivo = dispositivo;
	}

	@Override
	public void execute() {
		this.dispositivo.encenderse();
	}

}
