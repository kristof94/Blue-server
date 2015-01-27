package blue_server;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class WaitThread implements Runnable{

	LocalDevice local = null;
	StreamConnectionNotifier notifier;
	StreamConnection connection = null;	
	String url;
	boolean ready = false;

	public boolean isReady() {
		return ready;
	}

	public WaitThread() {
		// retrieve the local Bluetooth device object				
		// setup the server to listen for connection
		try {
			local = LocalDevice.getLocalDevice();
			local.setDiscoverable(DiscoveryAgent.GIAC);

			UUID uuid = new UUID("d0c722b07e1511e1b0c40800200c9a66", false);
			System.out.println(uuid.toString());
			System.out.println(local.getBluetoothAddress());
			System.out.println(local.getFriendlyName());
			url = "btspp://localhost:" + uuid.toString() + ";name=RemoteBluetooth";
			notifier = (StreamConnectionNotifier)Connector.open(url);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	@Override
	public void run() {

		try {
			System.out.println("waiting for connection...");
			connection = notifier.acceptAndOpen();
			System.out.println("After AcceptAndOpen...");
			ready = true;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public StreamConnection getConnection() {
		return connection;
	}

}