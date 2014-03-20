package com.example.de.vogella.android.sqlite.first;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class TestDatabaseActivity extends Activity {
  private DataBaseHelper DBHelper;
  private TextView t;
  
  //Data for each chart
  private ArrayList<ArrayList<String>> 	bridgeStatus, 
  										nbi58DeckConditionRatings, 
  										postedBridges, 
  										structurallyDeficientDeckArea, 
  										structurallyDeficientNHSDeckArea,	
  										bridgeSufficiencyRatingDeckArea, 	
  										bridgeCondition,
  										nhsBridgeCondition, 				
  										deckAreaBridgeCondition, 			
  										deckAreaNHSBridgeCondition;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);  
    t = (TextView)findViewById(R.id.textview);
    
    DBHelper = new DataBaseHelper(this);    
    try {DBHelper.createDataBase(); }
    catch(IOException e) {throw new Error("Unable to populate database.");}
    DBHelper.openDataBase();
    
    //Load data from database
    bridgeStatus = getDataFromCursor(DBHelper.getBridgeStatus());     
	nbi58DeckConditionRatings = getDataFromCursor(DBHelper.getNBI58DeckConditionRatings());		
	postedBridges = getDataFromCursor(DBHelper.getPostedBridges()); 					
	structurallyDeficientDeckArea = getDataFromCursor(DBHelper.getStructurallyDeficientDeckArea());	
	structurallyDeficientNHSDeckArea = getDataFromCursor(DBHelper.getStructurallyDeficientNHSDeckArea());	
	bridgeSufficiencyRatingDeckArea = getDataFromCursor(DBHelper.getBridgeSufficiencyRatingDeckArea()); 	
	bridgeCondition = getDataFromCursor(DBHelper.getBridgeCondition());
	nhsBridgeCondition = getDataFromCursor(DBHelper.getNHSBridgeCondition()); 				
	deckAreaBridgeCondition = getDataFromCursor(DBHelper.getDeckAreaBridgeCondition()); 			
	deckAreaNHSBridgeCondition = getDataFromCursor(DBHelper.getDeckAreaNHSBridgeCondition()); 		
    
  }  
  @Override
  public void onResume(){
	  super.onResume();
	  t.setText("Done.");
  }  
  
  public ArrayList<ArrayList<String>> getDataFromCursor(Cursor c){
	  ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
	  
	  //add column headings
	  data.add(new ArrayList<String>(Arrays.asList(c.getColumnNames())));
	  
	  c.moveToFirst();
	  for (int i=0; i<c.getCount(); i++){
		  ArrayList<String> current = new ArrayList<String>();	
		  for (int j=0; j<c.getColumnCount(); j++){
			  try{current.add(c.getString(j));}
			  catch(Exception e){current.add("");}			
		  }
		 data.add(current);
		 c.moveToNext();
	  }
	  return data;
  }
} 
