package remotepad.namespace;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.UrlQuerySanitizer.ValueSanitizer;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.SyncStateContract.Constants;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ConnectActivity extends Activity{
	//Globals
	
	
	Button touchpadbtn;
	Button sensorpadbtn;
	EditText iptxt;
	EditText porttxt;
	TextView msglbl;
	Intent intent;
	//Connection con;
	SeekBar touchbar;
	SeekBar sensorbar;
	SharedPreferences sharedPref;
	CheckBox wDYbox;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy); 
        touchpadbtn = (Button) findViewById(R.id.button1);
        sensorpadbtn = (Button) findViewById(R.id.button2);
        
        iptxt = (EditText) findViewById(R.id.editText1);
        porttxt = (EditText) findViewById(R.id.editText2);
        msglbl = (TextView) findViewById(R.id.textView2);
       

        touchbar = (SeekBar) findViewById(R.id.seekBar1);
        sensorbar = (SeekBar) findViewById(R.id.seekBar2);
        
        wDYbox = (CheckBox) findViewById(R.id.checkBox1);
        
        Display display = getWindowManager().getDefaultDisplay(); 
		Connection.screenwidth = display.getWidth();
		Connection.screenheight = display.getHeight();
        
		iptxt.setWidth((int) (Connection.screenwidth/1.5));
		porttxt.setWidth((int) (Connection.screenwidth-Connection.screenwidth/1.5));
        
		//option with motion in additional, third dimension(experimental)
		wDYbox.setText("sensor: with DY");
		
		sharedPref = getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
		
		if(sharedPref!=null){
        Connection.port = sharedPref.getInt("port", 0);
        Connection.ip = sharedPref.getString("ip", null);
        Connection.touchpadmultiplier = sharedPref.getInt("sens1", 0);
        Connection.sensorpadmultiplier = sharedPref.getInt("sens2", 0);
        Connection.wDY = sharedPref.getBoolean("wDY", (Boolean) false);
        
        touchbar.setProgress( Connection.touchpadmultiplier);
        sensorbar.setProgress( Connection.sensorpadmultiplier);
        porttxt.setText(String.valueOf(Connection.port));
        iptxt.setText(Connection.ip);
        wDYbox.setChecked(Connection.wDY);
        
        
		}
		
		
		
		wDYbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				
				Connection.wDY = isChecked;
				
				
			}
		});
        
		touchbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				
				Connection.touchpadmultiplier = progress;
				//msglbl.setText(progress);
				
				
			}
		});
		
		sensorbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				Connection.sensorpadmultiplier = progress;
				
			}
		});
		
        touchpadbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				sharedPref = getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
		        SharedPreferences.Editor prefsEditor = sharedPref.edit();
		        prefsEditor.putString("ip", iptxt.getText().toString());
		        prefsEditor.putInt("port", Integer.parseInt(porttxt.getText().toString()));
		        prefsEditor.putInt("sens1", Connection.touchpadmultiplier);
		        prefsEditor.putInt("sens2", Connection.sensorpadmultiplier);
		        prefsEditor.putBoolean("wDY", Connection.wDY);
		        prefsEditor.commit();
		
			
				Connection.setconnect(iptxt.getText().toString(), Integer.parseInt(porttxt.getText().toString()));
				intent = new Intent("remotepad.namespace.TouchPadActivity");

				startActivity(intent);
				
				
		
			}
		});   
        
        
        sensorpadbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				// TODO Auto-generated method stub
				sharedPref = getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
		        SharedPreferences.Editor prefsEditor = sharedPref.edit();
		        prefsEditor.putString("ip", iptxt.getText().toString());
		        prefsEditor.putInt("port", Integer.parseInt(porttxt.getText().toString()));
		        prefsEditor.putInt("sen1", Connection.touchpadmultiplier);
		        prefsEditor.putInt("sens2", Connection.sensorpadmultiplier);
		        prefsEditor.putBoolean("wDY", Connection.wDY);
		        prefsEditor.commit();
		        
					Connection.setconnect(iptxt.getText().toString(), Integer.parseInt(porttxt.getText().toString()));
						
					//Intent i = new Intent(this, Y.class);
					//i.putExtra("sampleObject", dene);
					//startActivity(i);
					
					
						intent = new Intent("remotepad.namespace.SensorPadActivity");
						//Bundle b = new Bundle();
					
						
						///.putParcelable("con",(Parcelable) con);
						
						//intent.putExtra("con",(Serializable)con);
						//startActivityForResult(intent,1);						
						startActivity(intent);
	
				
		
											
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
       // if(Connection.connected)
       // startActivity(intent);
            
        // The activity has become visible (it is now "resumed").
    }
    @Override
    protected void onPause() {
        super.onPause();
        Connection.disconnect();
        // Another activity is taking focus (this activity is about to be "paused").
    }
    @Override
    protected void onStop() {
        super.onStop();
        Connection.disconnect();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Connection.disconnect();
        // The activity is about to be destroyed.
    }

}