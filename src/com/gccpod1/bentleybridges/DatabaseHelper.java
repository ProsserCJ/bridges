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
	
	public Cursor getBridgeStatus() 						{return query(BRIDGE_STATUS, null);}
	public Cursor getNBI58DeckConditionRatings() 			{return query(NBI_58_DECK_CONDITION_RATINGS, null);}
	public Cursor getNBI59SuperstructureConditionRatings() 	{return query(NBI_59_SUPERSTRUCTURE_CONDITION_RATINGS, null);}
	public Cursor getNBI60SubstructureConditionRatings() 	{return query(NBI_60_SUBSTRUCTURE_CONDITION_RATINGS, null);}
	public Cursor getNBI62CulvertConditionRatings() 		{return query(NBI_62_CULVERT_CONDITION_RATINGS, null);}
	public Cursor getPostedBridges() 						{return query(POSTED_BRIDGES, null);}
	public Cursor getStructurallyDeficientDeckArea()		{return query(STRUCTURALLY_DEFICIENT_DECK_AREA, null);}
	public Cursor getStructurallyDeficientNHSDeckArea()		{return query(STRUCTURALLY_DEFICIENT_NHS_DECK_AREA, null);}
	public Cursor getBridgeSufficiencyRatingDeckArea() 		{return query(BRIDGE_SUFFICIENCY_RATING_DECK_AREA, null);}
	public Cursor getBridgeCondition() 						{return query(BRIDGE_CONDITION, null);}
	public Cursor getNHSBridgeCondition() 					{return query(NHS_BRIDGE_CONDITION, null);}
	public Cursor getDeckAreaBridgeCondition() 				{return query(DECK_AREA_BRIDGE_CONDITION, null);}
	public Cursor getDeckAreaNHSBridgeCondition() 			{return query(DECK_AREA_NHS_BRIDGE_CONDITION, null);}
	
	public Cursor getBridgeStatusDrilldown(int category) 						{return query(BRIDGE_STATUS_DRILLDOWN, new String[] {Integer.toString(category)});}
	public Cursor getNBI58DeckConditionRatingsDrilldown(int category) 			{return query(NBI_58_DECK_CONDITION_RATINGS_DRILLDOWN, new String[] {Integer.toString(category)});}
	public Cursor getNBI59SuperstructureConditionRatingsDrilldown(int category) {return query(NBI_59_SUPERSTRUCTURE_CONDITION_RATINGS_DRILLDOWN, new String[] {Integer.toString(category)});}
	public Cursor getNBI60SubstructureConditionRatingsDrilldown(int category) 	{return query(NBI_60_SUBSTRUCTURE_CONDITION_RATINGS_DRILLDOWN, new String[] {Integer.toString(category)});}
	public Cursor getNBI62CulvertConditionRatingsDrilldown(int category)		{return query(NBI_62_CULVERT_CONDITION_RATINGS_DRILLDOWN, new String[] {Integer.toString(category)});}
	public Cursor getPostedBridgesDrilldown(String category) 					{return query(POSTED_BRIDGES_DRILLDOWN, new String[] {category});}
	public Cursor getStructurallyDeficientDeckAreaDrilldown()					{return query(STRUCTURALLY_DEFICIENT_DECK_AREA_DRILLDOWN, null);}
	public Cursor getStructurallyDeficientNHSDeckAreaDrilldown()				{return query(STRUCTURALLY_DEFICIENT_NHS_DECK_AREA_DRILLDOWN, null);}
	
	public Cursor getBridgeSufficiencyRatingDeckAreaDrilldown(int low) 	
	{
		int high = low + 10;
		if (low != 0) low++;
		return query(BRIDGE_SUFFICIENCY_RATING_DECK_AREA_DRILLDOWN, new String[] {Integer.toString(low), Integer.toString(high)});
	}
	
	public Cursor getBridgeConditionDrilldown(String category) 					{return query(BRIDGE_CONDITION_DRILLDOWN, new String[] {category});}
	public Cursor getNHSBridgeConditionDrilldown(String category) 				{return query(NHS_BRIDGE_CONDITION_DRILLDOWN, new String[] {category});}
	public Cursor getDeckAreaBridgeConditionDrilldown(String category) 			{return query(DECK_AREA_BRIDGE_CONDITION_DRILLDOWN, new String[] {category});}
	public Cursor getDeckAreaNHSBridgeConditionDrilldown(String category) 		{return query(DECK_AREA_NHS_BRIDGE_CONDITION_DRILLDOWN, new String[] {category});}
	
	/*********************
	  Chart SQL queries
	*********************/
	
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
	
	private final String NBI_59_SUPERSTRUCTURE_CONDITION_RATINGS =
			"select nbi_059, COUNT(distinct as_id) cnt_val "
		+	"from tblCurrentNBIAggregated "
		+	"group by nbi_059 "
		+	"order by nbi_059 ";
	
	private final String NBI_60_SUBSTRUCTURE_CONDITION_RATINGS =
			"select nbi_060, COUNT(distinct as_id) cnt_val "
		+	"from tblCurrentNBIAggregated "
		+	"group by nbi_060 "
		+	"order by nbi_060 ";
	
	private final String NBI_62_CULVERT_CONDITION_RATINGS =
			"select nbi_062, COUNT(distinct as_id) cnt_val "
		+	"from tblCurrentNBIAggregated "
		+	"group by nbi_062 "
		+	"order by nbi_062 ";
	
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
	
	
	/*********************
	  Drilldown SQL queries
	*********************/
	
	//All drilldown queries begin with this,
	//varying only in the final where clause
	
	private final String DRILLDOWN_BASE =
			"select aggregated.as_id, "
		+	"aggregated.nbi_058 deck, "
		+	"aggregated.nbi_059 super, "
		+	"aggregated.nbi_060 sub, "
		+	"aggregated.nbi_062 culvert, "
		+	"aggregated.nbi_041 posting_status, "
		+	"aggregated.nbi_bsr sufficiency_rating, "
		+	"aggregated.nbi_status "
		+	"from ( "
		+	"select * "
		+	"from tblCurrentNBIAggregated ";
	
	
	private final String BRIDGE_STATUS_DRILLDOWN = 
		DRILLDOWN_BASE + "where nbi_status = ? ) as aggregated";	
	
	private final String NBI_58_DECK_CONDITION_RATINGS_DRILLDOWN =
		DRILLDOWN_BASE + "where nbi_058 = ?) as aggregated ";
	
	private final String NBI_59_SUPERSTRUCTURE_CONDITION_RATINGS_DRILLDOWN =
		DRILLDOWN_BASE + "where nbi_059 = ?) as aggregated ";
	
	private final String NBI_60_SUBSTRUCTURE_CONDITION_RATINGS_DRILLDOWN =
		DRILLDOWN_BASE + "where nbi_060 = ?) as aggregated ";
	
	private final String NBI_62_CULVERT_CONDITION_RATINGS_DRILLDOWN =
		DRILLDOWN_BASE + "where nbi_062 = ?) as aggregated ";
	
	private final String POSTED_BRIDGES_DRILLDOWN = 
		DRILLDOWN_BASE + "where upper( nbi_041) = upper(substr(?,1,1))) as aggregated";	
	
	private final String STRUCTURALLY_DEFICIENT_DECK_AREA_DRILLDOWN =
		DRILLDOWN_BASE + "where nbi_status = 1 ) as aggregated";		
	
	private final String STRUCTURALLY_DEFICIENT_NHS_DECK_AREA_DRILLDOWN = 
		DRILLDOWN_BASE + "where nbi_status = 1 and nbi_104 = 1 ) as aggregated";
	
	private final String BRIDGE_SUFFICIENCY_RATING_DECK_AREA_DRILLDOWN = 
		DRILLDOWN_BASE + "where nbi_bsr >= ? and nbi_bsr <=  ?  ) as aggregated";	
	
	private final String BRIDGE_CONDITION_DRILLDOWN =
		DRILLDOWN_BASE + "where upper(nbi_059_060) = upper(substr( ?, 1, 1))) as aggregated";	
			
	private final String NHS_BRIDGE_CONDITION_DRILLDOWN =
		DRILLDOWN_BASE + "where nbi_104 = 1 and upper(nbi_059_060) = upper(substr( ?, 1, 1))) as aggregated";
	
	private final String DECK_AREA_BRIDGE_CONDITION_DRILLDOWN =
		DRILLDOWN_BASE + "where upper(nbi_059_060) = upper(substr( ?, 1, 1))) as aggregated";
	
	private final String DECK_AREA_NHS_BRIDGE_CONDITION_DRILLDOWN = 
		DRILLDOWN_BASE + "where nbi_104 = 1 and upper(nbi_059_060) = upper(substr( ?, 1, 1))) as aggregated";       
 
}