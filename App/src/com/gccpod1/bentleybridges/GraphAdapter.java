package com.gccpod1.bentleybridges;

import java.util.ArrayList;
import java.util.Arrays;

import org.achartengine.GraphicalView;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class GraphAdapter extends BaseAdapter {
    private Context mContext;
    private BarGraph BGFactory;
	private DatabaseHelper DBHelper;
    
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

    public GraphAdapter(Context c) {
        mContext = c;
        BGFactory = new BarGraph(c);
        dbInit();
    }
    
    private void dbInit()
	{
		DBHelper = new DatabaseHelper(mContext);
	    
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

    public View getView(int position, View convertView, ViewGroup parent) {
    	GraphicalView graphView = null;
        if (convertView == null) 
        {         	
        	graphView = BGFactory.getView(nbi58DeckConditionRatings, "NBI 58 Deck Condition Ratings");	

        	graphView.setLayoutParams(new GridView.LayoutParams(400, 400));
        	graphView.setPadding(0,0,0,0);
        } 
        else 
        {
        	graphView = (GraphicalView) convertView;
        }        

        return graphView;
    }

	@Override
	public int getCount() {
		return 10;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}	
 

}
