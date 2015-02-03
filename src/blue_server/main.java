package blue_server;

public class main {
	
	public static void main(String[] args) {

		WaitThread server = new WaitThread();
		server.run();
		Thread processThread = new Thread(new ProcessConnectionThread(server.getConnection()));
		
		if(server.isReady())
			processThread.start();
		
		
	}

}
