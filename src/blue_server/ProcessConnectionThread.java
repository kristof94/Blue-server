package blue_server;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

import javax.microedition.io.StreamConnection;

public class ProcessConnectionThread implements Runnable {
	DataInputStream s = null;
	StringBuffer b = new StringBuffer();
	StreamConnection connection;
	BufferedReader r;
	StringBuilder total;
	boolean v=true;
	Robot robot;
	GraphicsDevice gd;
	public ProcessConnectionThread(StreamConnection c) {
		try {
			connection = c;			
			s = connection.openDataInputStream();
			r = new BufferedReader(new InputStreamReader(s));
			total = new StringBuilder();
			try {
				robot = new Robot();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

	}

	@Override
	public void run() {

		ByteBuffer tab = ByteBuffer.allocate(Integer.SIZE);

		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		int x=width/2,y=height/2;
		int ratiox=1920/width;
		int ratioy=1080/height;
		PointerInfo a = MouseInfo.getPointerInfo();
		Point bb = a.getLocation();

		System.out.println("width = "+width);
		System.out.println("height = "+height);

		
		while(v)
		{
			try {

				while(s.read(tab.array())>0)
				{
					
					
					x =( (tab.asIntBuffer().get() * ratiox) );
					y = ( (tab.asIntBuffer().get(4) * ratioy) );
					System.out.print("x = "+x);
					System.out.println(" ; y = "+y);
					//robot.mouseMove((int)bb.getX() + x  ,(int)bb.getY()  + y );

				}
				v=!v;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("client deconnecté");
	}
}
