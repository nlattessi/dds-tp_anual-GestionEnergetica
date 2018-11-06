package server;

import spark.Spark;
import spark.debug.DebugScreen;

public class Server {
	public static void main(String[] args) {
		Spark.port(9000);
		Spark.threadPool(4);
		DebugScreen.enableDebugScreen();
		new Bootstrap().init();
		Router.configure();
	}
}