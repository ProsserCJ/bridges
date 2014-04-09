package com.gccpod1.bentleybridges;

import java.util.ArrayList;
import java.util.Arrays;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;

public class GraphFactory {
	public Context context;
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
	
	public GraphFactory(Context c){
		context = c;
        dbInit();
    }
    
    private void dbInit()
	{
		DBHelper = new DatabaseHelper(context);
	    
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
		
	public GraphicalView getView_nbi58DeckConditionRatings()
	{			
		//Configure dataset and renderer
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		XYMultipleSeriesRenderer rendererSet = new XYMultipleSeriesRenderer();		
		for (int i=1; i<nbi58DeckConditionRatings.size(); i++)
		{
			double x, y;
			try{	
				x = Double.parseDouble(nbi58DeckConditionRatings.get(i).get(0));
				y = Double.parseDouble(nbi58DeckConditionRatings.get(i).get(1));
			}
			catch(Exception e){continue;}		
			
			if (x < 0) continue;
			XYSeries series = new XYSeries(Double.toString(x));
			XYSeriesRenderer renderer = new XYSeriesRenderer();
			
			series.add(x,y);
			dataset.addSeries(series);	
			
			int col = Color.parseColor("#DF013A");
			if (i > 6) col = Color.parseColor("#F7FE2E");
			if (i > 9) col = Color.parseColor("#01DF01");
			
			renderer.setColor(col);
			rendererSet.addSeriesRenderer(renderer);
		}
		
		rendererSet.setXAxisMin(0);
		rendererSet.setXAxisMax(10);
		rendererSet.setYAxisMin(0);
		rendererSet.setYAxisMax(500);
		
		rendererSet.setBarSpacing(1);
		rendererSet.setBarWidth(30);
		rendererSet.setChartTitle("NBI 58 Deck Condition Ratings");	
		rendererSet.setPanEnabled(false);
		rendererSet.setShowLegend(false);
		rendererSet.setShowGridX(true);
		
		
		return ChartFactory.getBarChartView(context, dataset, rendererSet, Type.STACKED);
	}	

}
