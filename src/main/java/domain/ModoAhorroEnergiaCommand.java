package domain;

public class ModoAhorroEnergiaCommand implements Command {
	@Override
	public void execute(DispositivoInteligente dispositivo) {
		dispositivo.modoAhorroEnergia();
	}

}
