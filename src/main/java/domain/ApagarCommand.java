package domain;

public class ApagarCommand implements Command {
	@Override
	public void execute(DispositivoInteligente dispositivo) {
		dispositivo.apagarse();
	}
}
