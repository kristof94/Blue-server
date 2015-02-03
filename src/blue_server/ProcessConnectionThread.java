package blue_server;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
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
		int h;
		while(v)
		{
			try {

				while((h=s.read(tab.array()))>0)
				{		
					
					System.out.println(h);

					if(h==8)
					{
						x =( (tab.asIntBuffer().get() * ratiox) );

						if(x==-5)
						{
							robot.mousePress(InputEvent.BUTTON1_MASK);
							robot.mouseRelease(InputEvent.BUTTON1_MASK);
						}
						
						if(x==-2)
						{
							System.out.println("Decrease");
							
						}
						if(x==-3)
						{
							System.out.println("Rise");
						}
						if(x==-6)
							{
							System.out.println("dsgsggse");

							v=!v;
							break;
							}
					}
					else{
						x =( (tab.asIntBuffer().get() * ratiox) );
						y = ( (tab.asIntBuffer().get(4) * ratioy) );

						a = MouseInfo.getPointerInfo();
						bb = a.getLocation();

						robot.mouseMove((int)bb.getX() -x ,(int)bb.getY() - y);
					}
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		System.out.println("client deconnect�");
	}
}
