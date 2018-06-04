package domain;

public class ModoAhorroEnergiaCommand implements Command {
	@Override
	public void execute(Inteligente dispositivo) {
		dispositivo.modoAhorroEnergia();
	}

}
