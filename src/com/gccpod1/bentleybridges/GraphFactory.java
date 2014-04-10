package com.gccpod1.bentleybridges;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DialRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;

public class GraphFactory {
	public final int TITLE_SIZE = 18;
	public Context context;
	private DatabaseHelper DBHelper;
    
    //Data for each chart
    private ArrayList<ArrayList<String>> 	bridgeStatus, 
    										nbi58DeckConditionRatings, 
    										nbi59SuperstructureConditionRatings, 
    										nbi60SubstructureConditionRatings,
    										nbi62CulvertConditionRatings, 
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
		nbi59SuperstructureConditionRatings = getDataFromCursor(DBHelper.getNBI59SuperstructureConditionRatings());
		nbi60SubstructureConditionRatings = getDataFromCursor(DBHelper.getNBI60SubstructureConditionRatings());
		nbi62CulvertConditionRatings = getDataFromCursor(DBHelper.getNBI62CulvertConditionRatings());
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
			if (x > 4) col = Color.parseColor("#F7FE2E");
			if (x > 7) col = Color.parseColor("#01DF01");
			
			renderer.setColor(col);
			rendererSet.addSeriesRenderer(renderer);
			rendererSet.addXTextLabel(x, Integer.toString((int)x));
		}
		
		rendererSet.setXAxisMin(-1);
		rendererSet.setXAxisMax(10);
		rendererSet.setYAxisMin(0);
		rendererSet.setYAxisMax(510);
		rendererSet.setYLabels(10);
		rendererSet.setXLabels(0);
		
		rendererSet.setBarSpacing(1);
		rendererSet.setBarWidth(30);
		
		rendererSet.setChartTitle("NBI 58 Deck Condition Ratings");
		rendererSet.setChartTitleTextSize(TITLE_SIZE);
		rendererSet.setLabelsColor(Color.BLACK);
				
		rendererSet.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));
		rendererSet.setXLabelsColor(Color.BLACK);
		rendererSet.setYLabelsColor(0, Color.BLACK);
					
		rendererSet.setPanEnabled(false);
		rendererSet.setShowLegend(false);
		rendererSet.setZoomEnabled(false);
		rendererSet.setShowGridX(true);		
		
		return ChartFactory.getBarChartView(context, dataset, rendererSet, Type.STACKED);
	}
	
	public GraphicalView getView_nbi59SuperstructureConditionRatings()
	{			
		//Configure dataset and renderer
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		XYMultipleSeriesRenderer rendererSet = new XYMultipleSeriesRenderer();		
		for (int i=1; i<nbi59SuperstructureConditionRatings.size(); i++)
		{
			double x, y;
			try{	
				x = Double.parseDouble(nbi59SuperstructureConditionRatings.get(i).get(0));
				y = Double.parseDouble(nbi59SuperstructureConditionRatings.get(i).get(1));
			}
			catch(Exception e){continue;}		
			
			if (x < 0) continue;
			XYSeries series = new XYSeries(Double.toString(x));
			XYSeriesRenderer renderer = new XYSeriesRenderer();
			
			series.add(x,y);
			dataset.addSeries(series);	
			
			int col = Color.parseColor("#DF013A");
			if (x > 4) col = Color.parseColor("#F7FE2E");
			if (x > 7) col = Color.parseColor("#01DF01");
			
			renderer.setColor(col);
			rendererSet.addSeriesRenderer(renderer);
			rendererSet.addXTextLabel(x, Integer.toString((int)x));
		}
		
		rendererSet.setXAxisMin(-1);
		rendererSet.setXAxisMax(10);
		rendererSet.setYAxisMin(0);
		rendererSet.setYAxisMax(510);
		rendererSet.setYLabels(10);
		rendererSet.setXLabels(0);
		
		rendererSet.setBarSpacing(1);
		rendererSet.setBarWidth(30);
		
		rendererSet.setChartTitle("NBI 59 Superstructure Condition Ratings");
		rendererSet.setChartTitleTextSize(TITLE_SIZE);
		rendererSet.setLabelsColor(Color.BLACK);
				
		rendererSet.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));
		rendererSet.setXLabelsColor(Color.BLACK);
		rendererSet.setYLabelsColor(0, Color.BLACK);
					
		rendererSet.setPanEnabled(false);
		rendererSet.setShowLegend(false);
		rendererSet.setZoomEnabled(false);
		rendererSet.setShowGridX(true);		
		
		return ChartFactory.getBarChartView(context, dataset, rendererSet, Type.STACKED);
	}
	
	public GraphicalView getView_nbi60SubstructureConditionRatings()
	{			
		//Configure dataset and renderer
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		XYMultipleSeriesRenderer rendererSet = new XYMultipleSeriesRenderer();		
		for (int i=1; i<nbi60SubstructureConditionRatings.size(); i++)
		{
			double x, y;
			try{	
				x = Double.parseDouble(nbi60SubstructureConditionRatings.get(i).get(0));
				y = Double.parseDouble(nbi60SubstructureConditionRatings.get(i).get(1));
			}
			catch(Exception e){continue;}		
			
			if (x < 0) continue;
			XYSeries series = new XYSeries(Double.toString(x));
			XYSeriesRenderer renderer = new XYSeriesRenderer();
			
			series.add(x,y);
			dataset.addSeries(series);	
			
			int col = Color.parseColor("#DF013A");
			if (x > 4) col = Color.parseColor("#F7FE2E");
			if (x > 7) col = Color.parseColor("#01DF01");
			
			renderer.setColor(col);
			rendererSet.addSeriesRenderer(renderer);
			rendererSet.addXTextLabel(x, Integer.toString((int)x));
		}
		
		rendererSet.setXAxisMin(-1);
		rendererSet.setXAxisMax(10);
		rendererSet.setYAxisMin(0);
		rendererSet.setYAxisMax(510);
		rendererSet.setYLabels(10);
		rendererSet.setXLabels(0);
		
		rendererSet.setBarSpacing(1);
		rendererSet.setBarWidth(30);
		
		rendererSet.setChartTitle("NBI 60 Substructure Condition Ratings");
		rendererSet.setChartTitleTextSize(TITLE_SIZE);
		rendererSet.setLabelsColor(Color.BLACK);
				
		rendererSet.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));
		rendererSet.setXLabelsColor(Color.BLACK);
		rendererSet.setYLabelsColor(0, Color.BLACK);
					
		rendererSet.setPanEnabled(false);
		rendererSet.setShowLegend(false);
		rendererSet.setZoomEnabled(false);
		rendererSet.setShowGridX(true);		
		
		return ChartFactory.getBarChartView(context, dataset, rendererSet, Type.STACKED);
	}
	
	public GraphicalView getView_nbi62CulvertConditionRatings()
	{			
		//Configure dataset and renderer
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		XYMultipleSeriesRenderer rendererSet = new XYMultipleSeriesRenderer();		
		for (int i=1; i<nbi62CulvertConditionRatings.size(); i++)
		{
			double x, y;
			try{	
				x = Double.parseDouble(nbi62CulvertConditionRatings.get(i).get(0));
				y = Double.parseDouble(nbi62CulvertConditionRatings.get(i).get(1));
			}
			catch(Exception e){continue;}		
			
			if (x < 0) continue;
			XYSeries series = new XYSeries(Double.toString(x));
			XYSeriesRenderer renderer = new XYSeriesRenderer();
			
			series.add(x,y);
			dataset.addSeries(series);	
			
			int col = Color.parseColor("#DF013A");
			if (x > 4) col = Color.parseColor("#F7FE2E");
			if (x > 7) col = Color.parseColor("#01DF01");
			
			renderer.setColor(col);
			rendererSet.addSeriesRenderer(renderer);
			rendererSet.addXTextLabel(x, Integer.toString((int)x));
		}
		
		rendererSet.setXAxisMin(-1);
		rendererSet.setXAxisMax(10);
		rendererSet.setYAxisMin(0);
		rendererSet.setYAxisMax(510);
		rendererSet.setYLabels(10);
		rendererSet.setXLabels(0);
		
		rendererSet.setBarSpacing(1);
		rendererSet.setBarWidth(30);
		
		rendererSet.setChartTitle("NBI 62 Culvert Condition Ratings");
		rendererSet.setChartTitleTextSize(TITLE_SIZE);
		rendererSet.setLabelsColor(Color.BLACK);
				
		rendererSet.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));
		rendererSet.setXLabelsColor(Color.BLACK);
		rendererSet.setYLabelsColor(0, Color.BLACK);
					
		rendererSet.setPanEnabled(false);
		rendererSet.setShowLegend(false);
		rendererSet.setZoomEnabled(false);
		rendererSet.setShowGridX(true);		
		
		return ChartFactory.getBarChartView(context, dataset, rendererSet, Type.STACKED);
	}

	public GraphicalView getView_postedBridges()
	{		
		double max = 0;
		//Configure dataset and renderer
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		XYMultipleSeriesRenderer rendererSet = new XYMultipleSeriesRenderer();		
		for (int i=1; i<postedBridges.size(); i++)
		{
			double y;
			try{
				y = Double.parseDouble(postedBridges.get(i).get(1));
			}
			catch(Exception e){continue;}
			if (y > max) max = y;
			
			String title = postedBridges.get(i).get(0);
			if (title == null) continue;
			
			XYSeries series = new XYSeries(title);
			XYSeriesRenderer renderer = new XYSeriesRenderer();
			
			series.add(i,y);
			dataset.addSeries(series);	
						
			renderer.setColor(Color.parseColor("#6495ED"));			
			rendererSet.addSeriesRenderer(renderer);
			rendererSet.addXTextLabel(i,title);
		}
		
		rendererSet.setXAxisMin(1);
		rendererSet.setXAxisMax(2 + dataset.getSeriesCount());
		rendererSet.setYAxisMin(0);
		rendererSet.setYAxisMax(max + 100);
		rendererSet.setYLabels(10);
		rendererSet.setXLabels(0);
		
		
		rendererSet.setBarSpacing(1);
		rendererSet.setBarWidth(60);
		
		rendererSet.setChartTitle("Posted Bridges");
		rendererSet.setChartTitleTextSize(TITLE_SIZE);
		rendererSet.setLabelsColor(Color.BLACK);
				
		rendererSet.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));
		rendererSet.setXLabelsColor(Color.BLACK);
		rendererSet.setYLabelsColor(0, Color.BLACK);
					
		rendererSet.setPanEnabled(false);
		rendererSet.setShowLegend(false);
		rendererSet.setZoomEnabled(false);
		rendererSet.setShowGridX(true);		
		
		return ChartFactory.getBarChartView(context, dataset, rendererSet, Type.STACKED);
	}
	
	public GraphicalView getView_StructurallyDeficientDeckArea()
	{
		
		DialRenderer rendererSet = new DialRenderer();	
		CategorySeries dataset = new CategorySeries("hello");
	
		double percent;
		percent = Double.parseDouble(structurallyDeficientDeckArea.get(1).get(0));
		dataset.add(percent);
		percent = Math.round(percent * 100);
		percent = percent/100;
		rendererSet.setChartTitle("Structurally Deficient Deck Area "+ percent);
		rendererSet.setChartTitleTextSize(TITLE_SIZE);
		
		
		
	    
	    SimpleSeriesRenderer r = new SimpleSeriesRenderer();
	    r.setColor(Color.RED);
	    rendererSet.addSeriesRenderer(r);
		
	    rendererSet.setAngleMax(90);
	    rendererSet.setAngleMin(270);
	    rendererSet.setShowLegend(false);
		
	    rendererSet.setMinValue(0);
		rendererSet.setMaxValue(30);
		rendererSet.setMajorTicksSpacing(5);
		
		
		return ChartFactory.getDialChartView(context, dataset, rendererSet);
	}
	
}


