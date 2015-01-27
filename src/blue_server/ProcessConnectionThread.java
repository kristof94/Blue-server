package blue_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.microedition.io.StreamConnection;

public class ProcessConnectionThread implements Runnable {
	InputStream s = null;
	StringBuffer b = new StringBuffer();
	StreamConnection connection;
	BufferedReader r;
	StringBuilder total;

	public ProcessConnectionThread(StreamConnection c) {
		try {
			connection = c;			
			s = connection.openInputStream();
			r = new BufferedReader(new InputStreamReader(s));
			total = new StringBuilder();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(true)
		{
			String line;
			try {
				while ((line = r.readLine()) != null) {
					total.append(line);
					System.out.println(total.toString());
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
