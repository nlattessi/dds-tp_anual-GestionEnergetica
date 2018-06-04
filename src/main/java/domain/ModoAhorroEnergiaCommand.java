package domain;

public class ModoAhorroEnergiaCommand implements Command {
	private Inteligente dispositivo;

	public ModoAhorroEnergiaCommand(Inteligente dispositivo) {
		this.dispositivo = dispositivo;
	}

	@Override
	public void execute() {
		this.dispositivo.modoAhorroEnergia();
	}

}
