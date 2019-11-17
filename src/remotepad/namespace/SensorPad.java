package remotepad.namespace;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SensorPad {

	
	  	boolean clicked;
	  	int radius = 4;

	  	//x,y,z pos
		float oldx;
		float oldy;
	    float oldz; 
	    //new x,y,z pos
	    float xpos;
	    float ypos;
	    float zpos; 
	    //delta pos
	    float deltax;
	    float deltay;
	    float deltaz;
	    
	    String str;
	    
	    //for float to int conversion
	    int tempx;
		int tempy;
		int tempz;
		

		float NS2S = 1.0f / 1000000000.0f;
		float timestamp;

	
	
public SensorPad(){
	
	clicked = false;
	xpos=0;
	ypos=0;
	zpos=0;
	oldx=0;
	oldy=0;
	oldz=0;
	tempx=0;
	tempy=0;
	tempz=0;
	deltax=0;
	deltay=0;
	deltaz=0;
	timestamp = 0;
	
	}


void resetPos(){
	timestamp = 0;
	xpos=0;
	ypos=0;
	zpos=0;
	oldx=0;
	oldy=0;
	oldz=0;
	tempx=0;
	tempy=0;
	tempz=0;
	deltax=0;
	deltay=0;
	deltaz=0;
}

void sendAction(String act){
	/* actions
	
		server receives these action messages and acts accordingly
	
	
	*/
	
	if(act.equals("lc"))
	   str = "lc";
	else if(act.equals("rc"))
	   str = "rc";
	else if(act.equals("sc"))
	   str = "sc"+ ((int)deltay)/2;	
	else if(act.equals("bc"))
		str = "bc";		
	else if(act.equals("fc"))
		str = "fc";
	else if(act.equals("drC")){
		str = "drC";
	}
	else if(act.equals("slc"))
		str = "slc";

	
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
	}
				
	try {
				
			Connection.socket.send(Connection.pout);
	} catch (IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
			
}
	
	
}


boolean sendMoves(float x, float y, float z, float dT, boolean drag, boolean scroll){
	
	if(clicked){ // This timestep's delta rotation to be multiplied by the current rotation
        // after computing it from the gyro sample data.
       if (timestamp != 0) {
      	   
            
            //use trapezoidal rule to integrate over angular velocity to get position
            deltax = (((oldx+x)* dT) / 2.0f*1000);
            deltay = (((oldy+y)* dT) / 2.0f*1000);
            deltaz = (((oldz+z)* dT) / 2.0f*1000);
                   
         
            tempx = (int)(-deltaz*(Connection.sensorpadmultiplier/2));
		    tempy = (int)(-deltax*(Connection.sensorpadmultiplier/2));
		    tempz = (int)(deltay*(Connection.sensorpadmultiplier/2));
		    	
             
            str = "";
            
            
            
            if(Connection.wDY){
            	
            		if(drag){
            		
                    str = "dr:"+(tempx+tempz)+","+(tempy);
                    
                    }
            		else if(scroll)
                    str = "sc:"+tempy;
                    else
        	    	str = (tempx+tempz)+","+tempy;
            
            	
            }
            else{
            	
            if(drag){
            	
            str = "dr:"+(tempx)+","+(tempy);
            
            }
            else if(scroll)
            str = "sc:"+tempy;
            else
	    	str = (tempx)+","+tempy;
            
            }
            			 
	    	oldx = x;
	    	oldy = y;
	    	oldz = z;
	    	
	    	if(!Connection.isConnected()){
	    		
	    		try {
	    			Connection.socket = new DatagramSocket(Connection.port);
	    			Connection.connected = true;
	    		} catch (SocketException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    	}
	
	    	
	    	if(tempx>radius || tempy>radius || tempx<-radius || tempy<-radius ){
			
	    		Connection.buffer = new byte[16];
	    					 
	    		Connection.buffer = str.getBytes();
	    					
	    		try {
				Connection.pout = new DatagramPacket(Connection.buffer, Connection.buffer.length,InetAddress.getByName(Connection.ip),Connection.port);
				
	    		} catch (UnknownHostException e1) {
								// TODO Auto-generated catch block
				e1.printStackTrace();
				return false;
					}
	    		
	    		try {
	    						//datasocket.setBroadcast(true);
								Connection.socket.send(Connection.pout);
								return true;
				} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								return false;
				}  
            
        }
	    	else
	    		return false;

       }
 
	}
	return false;
}

	public int getRadius() {
		return radius;
	}


	public void setRadius(int radius) {
		this.radius = radius;
	}
public boolean isClicked() {
	return clicked;
}


public void setClicked(boolean clicked) {
	this.clicked = clicked;
}


public float getOldx() {
	return oldx;
}


public void setOldx(float oldx) {
	this.oldx = oldx;
}


public float getOldy() {
	return oldy;
}


public void setOldy(float oldy) {
	this.oldy = oldy;
}


public float getOldz() {
	return oldz;
}


public void setOldz(float oldz) {
	this.oldz = oldz;
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


public float getZpos() {
	return zpos;
}


public void setZpos(float zpos) {
	this.zpos = zpos;
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


public float getDeltaz() {
	return deltaz;
}


public void setDeltaz(float deltaz) {
	this.deltaz = deltaz;
}


public String getStr() {
	return str;
}


public void setStr(String str) {
	this.str = str;
}


public int getTempx() {
	return tempx;
}


public void setTempx(int tempx) {
	this.tempx = tempx;
}


public int getTempy() {
	return tempy;
}


public void setTempy(int tempy) {
	this.tempy = tempy;
}


public int getTempz() {
	return tempz;
}


public void setTempz(int tempz) {
	this.tempz = tempz;
}


public float getTimestamp() {
	return timestamp;
}


public void setTimestamp(float timestamp) {
	this.timestamp = timestamp;
}


public float getNs2s() {
	return NS2S;
}


}

