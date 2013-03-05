package ie.dit.c09368345;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class QA extends ListActivity {
	
	private TextView question,timer, score;
	public int sc = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		question = (TextView) findViewById(R.id.Question);
		timer = (TextView) findViewById(R.id.counter);
		timer.setText("60");
		score = (TextView) findViewById(R.id.score);
		String[] listCol = new String[] {"answer"};
		int[] listRow = new int[]{R.id.Answer};
	
	
		
		final Counter timer = new Counter(60000,1000);
		timer.start();
		DBmanager db = new DBmanager(this);
		db.open();
		Cursor a = db.returnAnswers(2);
		//Cursor b = db.returnQuestion(2);
		
		SimpleCursorAdapter adapt = new SimpleCursorAdapter(QA.this,R.layout.row,a,listCol,listRow);
		this.setListAdapter(adapt);
		
		db.close();
		
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		Toast.makeText(QA.this, (CharSequence) getListView().getItemAtPosition(position), Toast.LENGTH_LONG).show();
		
		
	}

	public class Counter extends CountDownTimer{

		public Counter(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			
		}

		@Override
		public void onFinish() {
			
			timer.setText("Finished");
			sc++;
			score.setText("Score: "+sc);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			timer.setText((millisUntilFinished/1000)+"");
			if(millisUntilFinished <= 10000)
			{
				timer.setTextColor(android.graphics.Color.RED);
			}
			
		}
		
		
		
	}
	
	
	

}
