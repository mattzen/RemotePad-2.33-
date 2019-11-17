package remotepad.namespace;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TouchPadActivity extends Activity{

	TextView tv;
	TouchPad tp;
	Button lc;
	Button rc;
	boolean drag = false;
	LinearLayout lclickArea;
	LinearLayout rclickArea;
	LinearLayout scrollArea;
	LinearLayout forwardArea;
	LinearLayout backArea;
	LinearLayout moveArea;
	static int singleclick = 0;
	
	static int tempid =0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_layout);
        
 
        tp = new TouchPad();
         
        lclickArea = (LinearLayout) findViewById(R.id.lclickArea);
        forwardArea = (LinearLayout) findViewById(R.id.forwardArea);
        scrollArea = (LinearLayout) findViewById(R.id.scrollArea);
        backArea = (LinearLayout) findViewById(R.id.backArea);
        rclickArea = (LinearLayout) findViewById(R.id.rclickArea);
        moveArea = (LinearLayout) findViewById(R.id.moveArea);
        
        lclickArea.setBackgroundColor(Color.BLACK);
        forwardArea.setBackgroundColor(Color.RED);
        scrollArea.setBackgroundColor(Color.GRAY);
        backArea.setBackgroundColor(Color.RED);
        rclickArea.setBackgroundColor(Color.BLACK);
        moveArea.setBackgroundColor(Color.WHITE);
        
        //lay.setLayoutParams(new LinearLayout.LayoutParams(Connection.screenwidth,(int) (Connection.screenheight/1.2)));
        
        backArea.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if(singleclick++<1){
					backArea.setBackgroundColor(Color.BLUE);
					tp.sendAction("bc");
					}
					
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					backArea.setBackgroundColor(Color.RED);
					singleclick=0;
				
				}
				
				
				return true;
			}
		});
        
        forwardArea.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if(singleclick++<1){
					forwardArea.setBackgroundColor(Color.BLUE);
					tp.sendAction("fc");
					}
				}
				
				if (event.getAction() == MotionEvent.ACTION_UP) {
					
					forwardArea.setBackgroundColor(Color.RED);
					singleclick=0;
				
				}
				
				return true;
			}
		});
      
        
        
        
        scrollArea.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					
					
					tp.setPos(event.getX(), event.getY());
				

				}		
			  if (event.getAction() == MotionEvent.ACTION_MOVE) {
				
				  if(tp.posChanged(event.getX(),event.getY())){		
						tp.setDeltax(event.getX()-tp.getXpos());
						tp.setDeltay(event.getY()-tp.getYpos());								
						tp.setPos(event.getX(), event.getY());	
						tp.sendAction("sc");		
							
					}
			  }
			
	
				  	
				return true;
			}
		});
          
        
        rclickArea.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					
					if(singleclick++<1){
						rclickArea.setBackgroundColor(Color.GRAY);
						tp.sendAction("rc");
				
					}
				}
							
				else if(event.getAction() == MotionEvent.ACTION_UP)
				{
					rclickArea.setBackgroundColor(Color.BLACK);
					singleclick = 0;
					
				}	
				
				// TODO Auto-generated method stub
				return true;
			}
		});
        
       
        lclickArea.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				
				if(event.getAction() == MotionEvent.ACTION_POINTER_DOWN){
						
						
					if(singleclick++<1){

						lclickArea.setBackgroundColor(Color.GRAY);
						tp.sendAction("slc");

					}
						
						
					
				}		
				if(event.getAction() == MotionEvent.ACTION_POINTER_UP){
							
					lclickArea.setBackgroundColor(Color.BLACK);
					singleclick = 0;
					tp.sendAction("drC");
					
				}
				
				
				if(event.getAction() == MotionEvent.ACTION_DOWN){
				
				if(singleclick++<1){

					lclickArea.setBackgroundColor(Color.GRAY);
					tp.sendAction("lc");

				}
				}
				else if(event.getAction() == MotionEvent.ACTION_UP)
				{
					lclickArea.setBackgroundColor(Color.BLACK);
					singleclick = 0;
					
					
				}
		
				return true;
			}
		});
        
        
        moveArea.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub 
				
				if (event.getAction() == MotionEvent.ACTION_DOWN) {

						tp.setPos(event.getX(), event.getY());
						
					}
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
										
					if(singleclick==1){
						
						Log.d("actmv", "in here");
						tp.setDeltax(event.getX(tempid)-tp.getXpos());
						tp.setDeltay(event.getY(tempid)-tp.getYpos());		
						tp.setPos(event.getX(tempid), event.getY(tempid));							
						tp.sendAction("dr");
					}
					
					
			
						if(tp.posChanged(event.getX(),event.getY())){		
							tp.setDeltax(event.getX()-tp.getXpos());
							tp.setDeltay(event.getY()-tp.getYpos());								
							tp.setPos(event.getX(), event.getY());							
							tp.sendAction("mv");
						
				}
				}
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					
					if(singleclick==1){						
						tp.sendAction("drC");		
					}
					
				}
							
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
        // Another activity is taking focus (this activity is about to be "paused").
    }
    @Override
    protected void onStop() {
        super.onStop();

        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Connection.disconnect();
        // The activity is about to be destroyed.
    }
   
}