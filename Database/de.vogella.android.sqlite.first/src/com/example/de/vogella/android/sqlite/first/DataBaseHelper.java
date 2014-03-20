/* Thanks to Juan-Manuel Fluxà at
 * http://www.reigndesign.com/blog/using-your-own-sqlite-database-in-android-applications/
 * for tutorial and heavy design influence on this class
 */

package com.example.de.vogella.android.sqlite.first;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper{
	 
    //The Android's default system path of your application database.
    private static String DB_PATH;
    private static String DB_NAME = "Bentley.db";
 
    private SQLiteDatabase myDataBase; 
    private final Context myContext;
 
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {
 
    	super(context, DB_NAME, null, 1);
        this.myContext = context;
        DB_PATH = "/data/data/"+ myContext.getPackageName() + "/databases/"; 
    }	
 
  /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
    	SQLiteDatabase dbRead = null;
    	if(!dbExists())
    	{
    		this.close();
        	dbRead = this.getReadableDatabase();
        	dbRead.close();
        	try { copyDataBase();} 
        	catch (IOException e){ throw new Error("Error copying database"); }
    	} 
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean dbExists(){
 
    	SQLiteDatabase checkDB = null; 
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    	}catch(SQLiteException e){}
 
    	if(checkDB != null) checkDB.close();  
    	return checkDB != null;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close(); 
    }
 
    public void openDataBase() throws SQLException{
    	
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY); 
    }
    
    @Override
	public synchronized void close() { 
    	if(myDataBase != null) myDataBase.close(); 
    	super.close(); 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {} 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
	
	//Query Helpers
	public Cursor query(String sql, String[] args) { return myDataBase.rawQuery(sql, args); }
	
	public Cursor getBridgeStatus() 					{return query(BRIDGE_STATUS, null);}
	public Cursor getNBI58DeckConditionRatings() 		{return query(NBI_58_DECK_CONDITION_RATINGS, null);}
	public Cursor getPostedBridges() 					{return query(POSTED_BRIDGES, null);}
	public Cursor getStructurallyDeficientDeckArea()	{return query(STRUCTURALLY_DEFICIENT_DECK_AREA, null);}
	public Cursor getStructurallyDeficientNHSDeckArea()	{return query(STRUCTURALLY_DEFICIENT_NHS_DECK_AREA, null);}
	public Cursor getBridgeSufficiencyRatingDeckArea() 	{return query(BRIDGE_SUFFICIENCY_RATING_DECK_AREA, null);}
	public Cursor getBridgeCondition() 					{return query(BRIDGE_CONDITION, null);}
	public Cursor getNHSBridgeCondition() 				{return query(NHS_BRIDGE_CONDITION, null);}
	public Cursor getDeckAreaBridgeCondition() 			{return query(DECK_AREA_BRIDGE_CONDITION, null);}
	public Cursor getDeckAreaNHSBridgeCondition() 		{return query(DECK_AREA_NHS_BRIDGE_CONDITION, null);}
	
	//Common SQL queries
	private final String BRIDGE_STATUS = 
			"select nbi_status, COUNT(distinct as_id) as cnt_val "
		+	"from tblCurrentNBIAggregated "
		+	"group by nbi_status "
		+	"order by nbi_status ";
	
	private final String NBI_58_DECK_CONDITION_RATINGS =
			"select nbi_058, COUNT(distinct as_id) cnt_val "
		+	"from tblCurrentNBIAggregated "
		+	"group by nbi_058 "
		+	"order by nbi_058 ";
	
	private final String POSTED_BRIDGES = 
			"select nbi_041, COUNT(distinct as_id) as cnt_val "
		+	"from tblCurrentNBIAggregated "
		+	"group by nbi_041 "
		+	"order by nbi_041 ";	
	
	private final String STRUCTURALLY_DEFICIENT_DECK_AREA =
			"select cast(100.0 * (select coalesce(sum(nbi_deck_area), 0) "
		+	"from tblCurrentNBIAggregated "
		+	"where nbi_status = 1 "
		+	")/nullif((select coalesce(SUM(nbi_deck_area), 0) "
		+	"from tblCurrentNBIAggregated "
		+	"),0) as decimal(18,2) "
		+	") as val";
	
	private final String STRUCTURALLY_DEFICIENT_NHS_DECK_AREA = 
			"select cast(100.0 * (select coalesce(sum(nbi_deck_area), 0) "
		+	"from tblCurrentNBIAggregated "
		+	"where nbi_status = 1 "
		+	"and nbi_104 = 1)/nullif((select coalesce(SUM(nbi_deck_area), 0) "
		+	"from tblCurrentNBIAggregated "
		+	"where nbi_104 = 1 ),0) as decimal(18,2) "
		+	") as val";
	
	private final String BRIDGE_SUFFICIENCY_RATING_DECK_AREA = 
			"select nbi_bsr, nbi_deck_area "
			+ "from tblCurrentNBIAggregated";
	
	private final String BRIDGE_CONDITION =
			"select nbi_059_060, count(distinct as_id) as cnt_val "
		+	"from tblCurrentNBIAggregated "
		+	"where tblCurrentNBIAggregated.nbi_062 not in (0,1,2,3,4,5,6,7,8,9) "
		+	"group by nbi_059_060 "
		+	"order by nbi_059_060";
			
	private final String NHS_BRIDGE_CONDITION =
			"select nbi_059_060, count(distinct as_id) as cnt_val "
		+	"from tblCurrentNBIAggregated "
		+	"where nbi_104 = 1 "
		+	"and tblCurrentNBIAggregated.nbi_062 not in (0,1,2,3,4,5,6,7,8,9) "
		+	"group by nbi_059_060 "
		+	"order by nbi_059_060";
	
	private final String DECK_AREA_BRIDGE_CONDITION =
			"select nbi_059_060, coalesce(sum(nbi_deck_area),0) val "
		+	"from tblCurrentNBIAggregated "
		+	"where tblCurrentNBIAggregated.nbi_062 not in (0,1,2,3,4,5,6,7,8,9) "
		+	"group by nbi_059_060 ";
	
	private final String DECK_AREA_NHS_BRIDGE_CONDITION = 
			"select nbi_059_060, coalesce(sum(nbi_deck_area),0) val "
		+	"from tblCurrentNBIAggregated "
		+	"where tblCurrentNBIAggregated.nbi_062 not in (0,1,2,3,4,5,6,7,8,9) "
		+	"and nbi_104 = 1 "
		+	"group by nbi_059_060";
       
 
}