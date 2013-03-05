package ie.dit.c09368345;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

public class DBmanager {
	
	// QUESTION TABLE INITIALIZATION
	public static final String QUESTION_ID = "_id";
	public static final String QUESTION = "question";
	public static final String LAT = "lat";
	public static final String LONG = "lon";
	public static final String AREA = "area";
	
	// ANSWER TABLE INITIALIZATION
	public static final String ANSWER_ID = "_id";
	public static final String ANSWER = "answer";
	public static final String QUE_ID = "que_id";
	public static final String CORRECT = "correct";
	
	private static final String DATABASE_NAME = "ZooAdventurer";
	private static final String DATABASE_TABLE_QUESTIONS = "Questions";
	private static final String DATABASE_TABLE_ANSWERS = "Answers";
	
	private static final int DATABASE_VERSION = 1;
	
	private static final String CREATE_ANSWERS =
			"create table if not exists Questions (_id integer primary key autoincrement, "
			+ "question_id integer, question VARCHAR not null, lon DOUBLE, lat DOUBLE, area VARCHAR);";
	
	private static final String CREATE_QUESTIONS =
			"create table if not exists Answers (_id integer primary key autoincrement,"
			+ "answer VARCHAR not null, correct BOOLEAN default false, que_id integer, FOREIGN KEY(que_id) REFERENCES Questions(_id));";
	
	
	private final Context context;
	
	private DatabaseHelper DBHelper;
	private static SQLiteDatabase db;
	
	public DBmanager (Context ctx)
	{
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}
	
	
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		
		DatabaseHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			
			db.execSQL(CREATE_QUESTIONS);
			db.execSQL(CREATE_ANSWERS);
				
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			db.execSQL("DROP TABLE IF EXISTS");
			onCreate(db);
			
		}
	}
	
	public DBmanager open() throws SQLException
	{
		db = DBHelper.getWritableDatabase();
		return this;	
	}
	
	public void close()
	{
		DBHelper.close();
	}
	
	public long insertQuestion(Integer id,String question, Double lon, Double lat, String area)
	{
		ContentValues values = new ContentValues();
		values.put(QUESTION_ID, id);
		values.put(QUESTION, question);
		values.put(LAT, lat);
		values.put(LONG, lon);
		values.put(AREA, area);
		
		return db.insert(DATABASE_TABLE_QUESTIONS, null, values);
	}
	
	public long insertAnswer(String answer,Integer question_id, Boolean correct)
	{
		ContentValues values = new ContentValues();
		values.put(ANSWER, answer);
		values.put(QUE_ID, question_id);
		values.put(CORRECT, correct);
		
		return db.insert(DATABASE_TABLE_ANSWERS, null, values);
	}
	
	public Cursor returnAnswers(int id)
	{
		Cursor c = db.rawQuery("SELECT * FROM Answers WHERE que_id = "+id+"",null);
		return c;
	}
	
	public Cursor returnQuestion(int id)
	{
		Cursor c = db.rawQuery("SELECT * FROM Questions WHERE _id = "+id+"",null);
		return c;
	}
	
	
	public void insertRecords()
	{
		
		insertQuestion(1,"What is the name of", 32.567,-12.456,"Area1");
		insertAnswer("name",1,true);
		insertAnswer("name2", 1, false);
		insertAnswer("name3",1,false);
		
		insertQuestion(2,"What is the name of", 32.567,-12.456,"Area1");
		insertAnswer("sdfl",2,true);
		insertAnswer("F;LJEWFK", 2, false);
		insertAnswer("ALKDFHLIU",2,false);
	}
}






