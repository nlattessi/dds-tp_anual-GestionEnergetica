package domain;

public class Actuador {
	private EncenderCommand encenderCommand;
	private ApagarCommand apagarCommand;
	private ModoAhorroEnergiaCommand modoAhorroEnergiaCommand;

	public void setEncenderseComando(EncenderCommand encenderCommand) {
		this.encenderCommand = encenderCommand;
	}

	public void setApagarCommand(ApagarCommand apagarCommand) {
		this.apagarCommand = apagarCommand;
	}

	public void setModoAhorroEnergiaCommand(ModoAhorroEnergiaCommand modoAhorroEnergiaCommand) {
		this.modoAhorroEnergiaCommand = modoAhorroEnergiaCommand;
	}

	public void ejecutarComandoEncenderse() {
		this.encenderCommand.execute();
	}

	public void ejecutarComandoApagarse() {
		this.apagarCommand.execute();
	}

	public void ejecutarComandoEntrarModoAhorroEnergia() {
		this.modoAhorroEnergiaCommand.execute();
	}

}
