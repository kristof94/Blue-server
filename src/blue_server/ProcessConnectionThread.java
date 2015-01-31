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
		int X = 0,Y = 0;
		PointerInfo a = MouseInfo.getPointerInfo();
		Point bb = a.getLocation();
		int dx,dy;
		System.out.println("width = "+width);
		System.out.println("height = "+height);
		int vit=4;
		int h;
		while(v)
		{
			System.out.println(" =<0");
			try {
				
				while((h=s.read(tab.array()))>0)
				{		
					System.out.println(h);
					x =( (tab.asIntBuffer().get() * ratiox) );
					y = ( (tab.asIntBuffer().get(4) * ratioy) );
					
					a = MouseInfo.getPointerInfo();
					bb = a.getLocation();
					
					if(x-bb.getX()==0)
						dx=0;
					/*if(y-bb.getY()==0)
						dy=0;*/
					dx = x-X;
					dy = y-Y;
					
					if(dx>1)
						dx+=vit;
					else if (dx<-1)
						dx+=-vit;
					else dx =0;
					
					robot.mouseMove((int)bb.getX() +dx ,(int)bb.getY() + 0);
					X = x;
					Y = y;
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
