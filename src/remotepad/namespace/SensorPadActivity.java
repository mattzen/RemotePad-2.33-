package remotepad.namespace;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SensorPadActivity extends Activity{
    /** Called when the activity is first created. */
	
	Sensor sensor;
    SensorManager mngr;
    SensorEventListener mySensorEventListener;

    Button button5;
    TextView z;
    
	LinearLayout lclickArea2;
	LinearLayout rclickArea2;
	LinearLayout scrollArea2;
	LinearLayout forwardArea2;
	LinearLayout backArea2;
	LinearLayout sensortouchArea;
	static int sensctr = 0;
	SensorPad senpad;
	public static int singleclick=0;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
		        super.onCreate(savedInstanceState);
		        setContentView(R.layout.sensorpad);
		      
		        
		        mngr = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		      		
		        senpad = new SensorPad();
		      
		        
		        Display display = getWindowManager().getDefaultDisplay(); 
				int screenwidth = display.getWidth();
				int screenheight = display.getHeight();
		        
		        
				 lclickArea2 = (LinearLayout) findViewById(R.id.lclickArea2);
			     forwardArea2 = (LinearLayout) findViewById(R.id.forwardArea2);
			     scrollArea2 = (LinearLayout) findViewById(R.id.scrollArea2);
			     backArea2 = (LinearLayout) findViewById(R.id.backArea2);
			     rclickArea2 = (LinearLayout) findViewById(R.id.rclickArea2);
			     sensortouchArea = (LinearLayout) findViewById(R.id.sensortouchArea);
			        
			     lclickArea2.setBackgroundColor(Color.BLACK);
			     forwardArea2.setBackgroundColor(Color.RED);
			     scrollArea2.setBackgroundColor(Color.GRAY);
			     backArea2.setBackgroundColor(Color.RED);
			     rclickArea2.setBackgroundColor(Color.BLACK);
			     sensortouchArea.setBackgroundColor(Color.WHITE);
			     
			    
			     
    			
    	        backArea2.setOnTouchListener(new OnTouchListener() {
    				
    				@Override
    				public boolean onTouch(View v, MotionEvent event) {
    					// TODO Auto-generated method stub
    					if (event.getAction() == MotionEvent.ACTION_DOWN) {
    						if(singleclick++<1){
    						backArea2.setBackgroundColor(Color.BLUE);
    						senpad.sendAction("bc");
    						}
    						
    					}
    					if (event.getAction() == MotionEvent.ACTION_UP) {
    						
    						backArea2.setBackgroundColor(Color.RED);
    						singleclick=0;
    					
    					}
    					
    					
    					return true;
    				}
    			});
    	        
    	        forwardArea2.setOnTouchListener(new OnTouchListener() {
    				
    				@Override
    				public boolean onTouch(View v, MotionEvent event) {
    					// TODO Auto-generated method stub
    					forwardArea2.setBackgroundColor(Color.BLUE);
    					if (event.getAction() == MotionEvent.ACTION_DOWN) {
    						if(singleclick++<1){
    						senpad.sendAction("fc");
    						}
    					}
    					
    					if (event.getAction() == MotionEvent.ACTION_UP) {
    						
    						singleclick=0;
    						forwardArea2.setBackgroundColor(Color.RED);
    					}
    					
    					return true;
    				}
    			});
    			
 
    			
    			  scrollArea2.setOnTouchListener(new OnTouchListener() {
    					
    					@Override
    					public boolean onTouch(View v, MotionEvent event) {
    						// TODO Auto-generated method stub
    						
    						
    						if(event.getAction()== MotionEvent.ACTION_DOWN){
    							
    							senpad.resetPos();
    							senpad.setClicked(true);
    							scrollArea2.setBackgroundColor(Color.WHITE);
    							
    							mySensorEventListener = new SensorEventListener() {
    					        
    								
    					        	public void onSensorChanged(SensorEvent sensorEvent) {
    					        			        				

    					        		
    					        		senpad.sendMoves(sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2],(sensorEvent.timestamp - senpad.getTimestamp()) * senpad.getNs2s(),false,true);
    					        		senpad.setTimestamp(sensorEvent.timestamp);
    					        		
    					        		
    					        	}
    					        				
    								@Override
    								public void onAccuracyChanged(Sensor arg0, int arg1) {
    									// TODO Auto-generated method stub
    									
    								}
    					      
    					        };
    						
    					        sensor = mngr.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    					        mngr.registerListener(mySensorEventListener,
    					        sensor,
    					        SensorManager.SENSOR_DELAY_GAME);
    					  
    					        
    						}
    						
    						else if(event.getAction()== MotionEvent.ACTION_UP){
    							
    							if(senpad.isClicked()){
    								scrollArea2.setBackgroundColor(Color.GRAY);
    								senpad.resetPos();
    								senpad.setClicked(false);
    								mngr.unregisterListener(mySensorEventListener);
    							}
    							
    							return true;
    							
    							
    						}		
    						
    						return true;				
    					}
    				});

    			
    			
    			
    			sensortouchArea.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						
						
						if(event.getAction()== MotionEvent.ACTION_DOWN){
							
							sensortouchArea.setBackgroundColor(Color.GRAY);
							senpad.resetPos();
							senpad.setClicked(true);
							senpad.setRadius(4);
							
							mySensorEventListener = new SensorEventListener() {
					        
								
					        	public void onSensorChanged(SensorEvent sensorEvent) {
					        			        				

					        		if(sensctr++>3){
					        		senpad.sendMoves(sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2],(sensorEvent.timestamp - senpad.getTimestamp()) * senpad.getNs2s(),false,false);
					        		senpad.setTimestamp(sensorEvent.timestamp);
					        		}
					        		
					        	}
					        				
								@Override
								public void onAccuracyChanged(Sensor arg0, int arg1) {
									// TODO Auto-generated method stub
									
								}
					      
					        };
						
					        sensor = mngr.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
					        mngr.registerListener(mySensorEventListener,
					        sensor,
					        SensorManager.SENSOR_DELAY_GAME);
					  
					        
						}
						
						else if(event.getAction()== MotionEvent.ACTION_UP){
							
							if(senpad.isClicked()){
								sensctr=0;
								senpad.resetPos();
								senpad.setClicked(false);
								mngr.unregisterListener(mySensorEventListener);
							}
							sensortouchArea.setBackgroundColor(Color.WHITE);
							return true;
							
							
						}		
						
						return true;
					}
				});
    			
    		/*	 lclickArea2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						senpad.sendAction("lc");
						
					}
				});
    			*/        
    			
    			lclickArea2.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						
						
						if(event.getAction()== MotionEvent.ACTION_DOWN){
							
							senpad.resetPos();
							senpad.setClicked(true);
							//senpad.sendAction("lc");
							lclickArea2.setBackgroundColor(Color.GRAY);
							senpad.sendAction("slc");
							senpad.setRadius(5);
							mySensorEventListener = new SensorEventListener() {
					        
								
					        	public void onSensorChanged(SensorEvent sensorEvent) {
					        		
					        		if(sensctr++>3){
					        		senpad.sendMoves(sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2],(sensorEvent.timestamp - senpad.getTimestamp()) * senpad.getNs2s(),true,false);
					        		senpad.setTimestamp(sensorEvent.timestamp);
					        		}
		
	
					        	}
					        				
								@Override
								public void onAccuracyChanged(Sensor arg0, int arg1) {
									// TODO Auto-generated method stub
									
								}
					      
					        };
						
					        sensor = mngr.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
					        mngr.registerListener(mySensorEventListener,
					        sensor,
					        SensorManager.SENSOR_DELAY_GAME);
					  
					        
						}
						
						else if(event.getAction()== MotionEvent.ACTION_UP){
							
		

							if(senpad.isClicked()){
								sensctr =0;
								senpad.resetPos();
								senpad.sendAction("drC");
								senpad.setClicked(false);
								mngr.unregisterListener(mySensorEventListener);
							}
							lclickArea2.setBackgroundColor(Color.BLACK);
							return true;
							
							
						}
							
						
						// TODO Auto-generated method stub
						return true;
					}
				});
					
								
    			
    			 
    	        rclickArea2.setOnTouchListener(new OnTouchListener() {
    				
    				@Override
    				public boolean onTouch(View v, MotionEvent event) {
    					
    					if(event.getAction() == MotionEvent.ACTION_DOWN){
    						
    						if(singleclick++<1){
    							rclickArea2.setBackgroundColor(Color.GRAY);
    							senpad.sendAction("rc");
    					
    						}
    					}
    								
    					else if(event.getAction() == MotionEvent.ACTION_UP)
    					{
    						rclickArea2.setBackgroundColor(Color.BLACK);
    						singleclick = 0;
    						
    					}	
    					
    					// TODO Auto-generated method stub
    					return true;
    				}
    			});
 			
    }
    
    @Override
    protected void onStart() {
        super.onStart();
 
        // The activity is about to become visible.
    }
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(senpad.isClicked()){
        mngr.unregisterListener(mySensorEventListener);
        senpad.setClicked(false);
        }
        
        // Another activity is taking focus (this activity is about to be "paused").
    }
    @Override
    protected void onStop() {
        super.onStop();
        //datasocket.close();
        if(senpad.isClicked()){
            mngr.unregisterListener(mySensorEventListener);
            senpad.setClicked(false);
            }
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Connection.disconnect();
        
        if(senpad.isClicked()){
            mngr.unregisterListener(mySensorEventListener);
            senpad.setClicked(false);
         }
        //mngr.unregisterListener(mySensorEventListener);
        // The activity is about to be destroyed.
    }


}