package domain;

public class ApagarCommand implements Command {
	@Override
	public void execute(Inteligente dispositivo) {
		dispositivo.apagarse();
	}
}
