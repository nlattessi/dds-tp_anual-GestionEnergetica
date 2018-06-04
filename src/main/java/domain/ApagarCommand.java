package domain;

public class ApagarCommand implements Command {
	private Inteligente dispositivo;

	public ApagarCommand(Inteligente dispositivo) {
		this.dispositivo = dispositivo;
	}

	@Override
	public void execute() {
		this.dispositivo.apagarse();
	}
}
