/* Thanks to Juan-Manuel Fluxà at
 * http://www.reigndesign.com/blog/using-your-own-sqlite-database-in-android-applications/
 * for tutorial and heavy design influence on this class
 */

package com.gccpod1.bentleybridges;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteAssetHelper {
	
	SQLiteDatabase db;

    private static final String DATABASE_NAME = "Bentley.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getReadableDatabase();
    }
	
	//Query Helpers
	public Cursor query(String sql, String[] args) { return db.rawQuery(sql, args); }
	
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