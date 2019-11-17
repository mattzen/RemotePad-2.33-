package remotepad.namespace;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import android.view.Display;


public class Connection {

	static int screenwidth = 0;
	static int screenheight = 0;
	static String ip;
	static int port;
	static DatagramPacket pout;
	static DatagramSocket socket;
	static byte[] buffer;
	static boolean connected = false;
	static int BUFFER_SIZE = 16;
	static int touchpadmultiplier = 1;
	static int sensorpadmultiplier= 1;
	static boolean wDY = false;
	public Connection(){
		
	
	}
	public static boolean setconnect(String i,int p){

			ip = i;
			port = p;
			
			
			
		
		return true;
	}
	
	public static boolean disconnect(){
		
		if(connected){
			socket.close();
			connected = false;
		}
		return true;
		
	}
	
	public static boolean isConnected(){
		return connected;
	}

	public static boolean sendMove(int deltax, int deltay){
		
		buffer=  new byte[BUFFER_SIZE];
		String s = new String("e1:"+deltax+","+deltay);
		buffer = s.getBytes();

		try {
			
			pout = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip),port);
			
			try {
				
				socket.send(pout);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				connected = false;
				return false;
			}
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			connected = false;
			return false;
		}
		
	}
	
	public static boolean sendrClick(){
		return false;
	}
	
	public static boolean sendlClick(){
		return false;
	}

	public static boolean sendScroll(){
		return false;
	}
	
	
}
