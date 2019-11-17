package remotepad.namespace;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;



public class TouchPad {


	public float xpos = 0;
	public float ypos = 0;
	
	public float deltax = 0;
	public float deltay = 0;
	
	public String str;
	
	
	
	public TouchPad(){
		
		
		
	}
	
	
	
	void setPos(float x, float y){
		
		xpos = x;
		ypos = y;
		
	}
	
	boolean posChanged(float x, float y){
		
		if(xpos!=x || ypos !=y)
			return true;
		else
			return false;
		
		
	}
	
	
	public boolean sendAction(String cl){
		
		if(cl.equalsIgnoreCase("lc"))
			str = "lc";	
		else if(cl.equalsIgnoreCase("rc"))
			str = "rc";	
		else if(cl.equalsIgnoreCase("mv"))
			str = (int)deltax*(Connection.touchpadmultiplier/2)+","+(int)deltay*(Connection.touchpadmultiplier/2);
		else if(cl.equalsIgnoreCase("dr"))
			str= "dr:"+(int)deltax+","+(int)deltay;
		else if(cl.equalsIgnoreCase("sc"))
			str= "sc:" + (int)deltay;
		else if(cl.equalsIgnoreCase("bc"))
			str = "bc";	
		else if(cl.equalsIgnoreCase("fc"))
			str = "fc";	
		
		
		Connection.buffer = new byte[16];		 
		Connection.buffer = str.getBytes();
		
		if(!Connection.isConnected()){
			
			try {
				Connection.socket = new DatagramSocket(Connection.port);
				Connection.connected = true;
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			Connection.pout = new DatagramPacket(Connection.buffer, Connection.buffer.length,InetAddress.getByName(Connection.ip),Connection.port);
		} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
				e1.printStackTrace();
				return false;
		}
					
		try {
					
			
			    Connection.pout.setLength(Connection.buffer.length);
				Connection.socket.send(Connection.pout);
				
				
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
				return false;

		}
		return true;
		
	}
	
	
	public float getXpos() {
		return xpos;
	}


	public void setXpos(float xpos) {
		this.xpos = xpos;
	}


	public float getYpos() {
		return ypos;
	}


	public void setYpos(float ypos) {
		this.ypos = ypos;
	}


	public float getDeltax() {
		return deltax;
	}


	public void setDeltax(float deltax) {
		this.deltax = deltax;
	}


	public float getDeltay() {
		return deltay;
	}


	public void setDeltay(float deltay) {
		this.deltay = deltay;
	}

	public String getStr() {
		return str;
	}


	public void setStr(String str) {
		this.str = str;
	}


	public boolean status(){
		
		return Connection.connected;
		
	}
	
}
