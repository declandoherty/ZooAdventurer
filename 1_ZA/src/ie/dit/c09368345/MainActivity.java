package ie.dit.c09368345;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.LightingColorFilter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button play = (Button) findViewById(R.id.play);
        play.getBackground().setColorFilter(new LightingColorFilter(0xFF00FF00, 0xFFAA0000));
        play.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent playIntent = new Intent(MainActivity.this,QA.class);
				startActivity(playIntent);
			}
		});
        
        
        // INSERTING DATA INTO DATABASE ---- EXECUTES ONLY ONCE ///////////
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("firstTime", false)) {
        	
        	DBmanager db = new DBmanager(this);
            db.open();
            db.insertRecords();
            db.close();
    
        	SharedPreferences.Editor editor = prefs.edit();
        	editor.putBoolean("firstTime", true);
        	editor.commit();
        }


        
        
        
      
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
