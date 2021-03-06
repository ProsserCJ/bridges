package com.gccpod1.bentleybridges;


import java.util.ArrayList;
import java.util.Arrays;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.DialRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;
import android.widget.PopupWindow;
@SuppressWarnings("deprecation")

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
    

    //static graphs
    private static GraphicalView		 	bridgeStatusView, 
    										nbi58DeckConditionRatingsView, 
    										nbi59SuperstructureConditionRatingsView, 
    										nbi60SubstructureConditionRatingsView,
    										nbi62CulvertConditionRatingsView, 
    										postedBridgesView, 
    										structurallyDeficientDeckAreaView, 
    										structurallyDeficientNHSDeckAreaView,	
    										bridgeSufficiencyRatingDeckAreaView, 	
    										bridgeConditionView,
    										nhsBridgeConditionView, 				
    										deckAreaBridgeConditionView, 			
    										deckAreaNHSBridgeConditionView;
	
	public GraphFactory(Context c){
		context = c;
        dbInit();        
        graphInit();
    }
	
	private void graphInit()
	{
		calc_bridgeStatus();
		calc_nbi58DeckConditionRatings();
		calc_nbi59SuperstructureConditionRatings(); 
		calc_nbi60SubstructureConditionRatings();
		calc_nbi62CulvertConditionRatings(); 
		calc_postedBridges(); 
		calc_structurallyDeficientDeckArea(); 
		calc_structurallyDeficientNHSDeckArea();	
		calc_bridgeSufficiencyRatingDeckArea(); 	
		calc_bridgeCondition();
		calc_nhsBridgeCondition(); 				
		calc_deckAreaBridgeCondition(); 			
		calc_deckAreaNHSBridgeCondition();
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

	public GraphicalView parse(String input){
	    
       	if (input.equalsIgnoreCase("Deck Condition Ratings")) 				return nbi58DeckConditionRatingsView;
       	if (input.equalsIgnoreCase("Superstructure Condition Ratings"))	 	return nbi59SuperstructureConditionRatingsView;    
       	if (input.equalsIgnoreCase("Substructure Condition Ratings")) 		return nbi60SubstructureConditionRatingsView;
       	if (input.equalsIgnoreCase("Culvert Condition Ratings"))			return nbi62CulvertConditionRatingsView;
    	if (input.equalsIgnoreCase("Posted Bridges")) 						return postedBridgesView;
    	if (input.equalsIgnoreCase("Bridge Status")) 						return bridgeStatusView; 
    	if (input.equalsIgnoreCase("Bridge Condition")) 					return bridgeConditionView; 
    	if (input.equalsIgnoreCase("NHS Bridge Condition"))					return nhsBridgeConditionView;
    	if (input.equalsIgnoreCase("Deck Area Bridge Condition"))			return deckAreaBridgeConditionView;
    	if (input.equalsIgnoreCase("Deck Area NHS Bridge Condition")) 		return deckAreaNHSBridgeConditionView;
    	if (input.equalsIgnoreCase("Structurally Deficient Deck Area"))		return structurallyDeficientDeckAreaView;
    	if (input.equalsIgnoreCase("Structurally Deficient NHS Deck Area"))	return structurallyDeficientNHSDeckAreaView;
    	if (input.equalsIgnoreCase("Bridge Sufficiency Rating Deck Area"))	return bridgeSufficiencyRatingDeckAreaView;
    	
       	if (input.equals("0"))	return nbi59SuperstructureConditionRatingsView;    
       	if (input.equals("1")) 	return nbi60SubstructureConditionRatingsView;
       	if (input.equals("2"))	return nbi62CulvertConditionRatingsView;
    	if (input.equals("3"))	return nhsBridgeConditionView;
    	if (input.equals("4"))	return deckAreaBridgeConditionView;
    	if (input.equals("5")) 	return deckAreaNHSBridgeConditionView;
    	if (input.equals("6"))	return structurallyDeficientDeckAreaView;
    	if (input.equals("7"))	return structurallyDeficientNHSDeckAreaView;
    	if (input.equals("8"))	return bridgeSufficiencyRatingDeckAreaView;
    	if (input.equals("9")) 	return nbi58DeckConditionRatingsView;
    	if (input.equals("10")) return postedBridgesView;
    	if (input.equals("11")) return bridgeStatusView; 
    	if (input.equals("12")) return bridgeConditionView; 
    	
    	
    	
    	return null;
    }
	
	public void calc_nbi58DeckConditionRatings()
	{			
		//Configure dataset and renderer
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		XYMultipleSeriesRenderer rendererSet = new XYMultipleSeriesRenderer();	
		double max = 0;
		for (int i=1; i<nbi58DeckConditionRatings.size(); i++)
		{
			double x, y;
			try{	
				x = Double.parseDouble(nbi58DeckConditionRatings.get(i).get(0));
				y = Double.parseDouble(nbi58DeckConditionRatings.get(i).get(1));
			}
			catch(Exception e){continue;}		
			
			if (x < 0) continue;
			if (y > max) max = y;
			XYSeries series = new XYSeries(Double.toString(x));
			XYSeriesRenderer renderer = new XYSeriesRenderer();
			
			series.add(x,y);
			dataset.addSeries(series);	
			
			int col = Color.parseColor("#DF013A");
			if (x > 4) col = Color.parseColor("#F7FE2E");
			if (x > 7) col = Color.parseColor("#01DF01");
			
			renderer.setColor(col);
			rendererSet.addSeriesRenderer(renderer);			
		}
		
		for (int i=0; i<10; i++) rendererSet.addXTextLabel(i, Integer.toString(i));
		
		rendererSet.setXAxisMin(-1);
		rendererSet.setXAxisMax(10);
		rendererSet.setYAxisMin(0);
		rendererSet.setYAxisMax(max + 25);
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
		rendererSet.setClickEnabled(true);
		rendererSet.setShowGridX(true);	
		rendererSet.setExternalZoomEnabled(false);
		rendererSet.setInScroll(true);
		
		nbi58DeckConditionRatingsView = ChartFactory.getBarChartView(context, dataset, rendererSet, Type.STACKED);
		
		nbi58DeckConditionRatingsView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SeriesSelection seriesSelection = nbi58DeckConditionRatingsView.getCurrentSeriesAndPoint();
				if (seriesSelection != null) {
					int x = (int)seriesSelection.getXValue();	
					GridView gv = new GridView(context);
					gv.setAdapter(new MyCursorAdapter(context, DBHelper.getNBI58DeckConditionRatingsDrilldown(x)));
					gv.setBackgroundColor(Color.WHITE);
					gv.setColumnWidth(100);
					gv.setNumColumns(GridView.AUTO_FIT);					
				
					final PopupWindow window = new PopupWindow(gv, 800, 400, true);
					window.setOutsideTouchable(true);
					window.setTouchable(true);	
					window.setBackgroundDrawable(new BitmapDrawable());
					window.showAtLocation(nbi58DeckConditionRatingsView,Gravity.CENTER, 0,0);
				
		          }
			}
		});
	}
	
	public void calc_nbi59SuperstructureConditionRatings()
	{			
		//Configure dataset and renderer
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		XYMultipleSeriesRenderer rendererSet = new XYMultipleSeriesRenderer();		
		double max = 0;
		for (int i=1; i<nbi59SuperstructureConditionRatings.size(); i++)
		{
			double x, y;
			try{	
				x = Double.parseDouble(nbi59SuperstructureConditionRatings.get(i).get(0));
				y = Double.parseDouble(nbi59SuperstructureConditionRatings.get(i).get(1));
			}
			catch(Exception e){continue;}		
			
			if (x < 0) continue;
			if (y > max) max = y;
			XYSeries series = new XYSeries(Double.toString(x));
			XYSeriesRenderer renderer = new XYSeriesRenderer();
			
			series.add(x,y);
			dataset.addSeries(series);	
			
			int col = Color.parseColor("#DF013A");
			if (x > 4) col = Color.parseColor("#F7FE2E");
			if (x > 7) col = Color.parseColor("#01DF01");
			
			renderer.setColor(col);
			rendererSet.addSeriesRenderer(renderer);
		}
		
		for (int i=0; i<10; i++) rendererSet.addXTextLabel(i, Integer.toString(i));
		
		rendererSet.setXAxisMin(-1);
		rendererSet.setXAxisMax(10);
		rendererSet.setYAxisMin(0);
		rendererSet.setYAxisMax(max + 25);
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
		rendererSet.setClickEnabled(true);
		rendererSet.setShowGridX(true);
		rendererSet.setExternalZoomEnabled(false);
		rendererSet.setInScroll(true);
		
		nbi59SuperstructureConditionRatingsView = ChartFactory.getBarChartView(context, dataset, rendererSet, Type.STACKED);
		
		nbi59SuperstructureConditionRatingsView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SeriesSelection seriesSelection = nbi59SuperstructureConditionRatingsView.getCurrentSeriesAndPoint();
				if (seriesSelection != null) {
					int x = (int)seriesSelection.getXValue();	
					GridView gv = new GridView(context);
					gv.setAdapter(new MyCursorAdapter(context, DBHelper.getNBI59SuperstructureConditionRatingsDrilldown(x)));
					gv.setBackgroundColor(Color.WHITE);
					gv.setColumnWidth(100);
					gv.setNumColumns(GridView.AUTO_FIT);					
				
					final PopupWindow window = new PopupWindow(gv, 800, 400, true);
					window.setOutsideTouchable(true);
					window.setTouchable(true);	
					window.setBackgroundDrawable(new BitmapDrawable());
					window.showAtLocation(nbi59SuperstructureConditionRatingsView,Gravity.CENTER, 0,0);
				
		          }
			}
		});
	}
	
	public void calc_nbi60SubstructureConditionRatings()
	{			
		//Configure dataset and renderer
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		XYMultipleSeriesRenderer rendererSet = new XYMultipleSeriesRenderer();
		double max = 0;
		for (int i=1; i<nbi60SubstructureConditionRatings.size(); i++)
		{
			double x, y;
			try{	
				x = Double.parseDouble(nbi60SubstructureConditionRatings.get(i).get(0));
				y = Double.parseDouble(nbi60SubstructureConditionRatings.get(i).get(1));
			}
			catch(Exception e){continue;}		
			
			if (x < 0) continue;
			if (y > max) max = y;
			XYSeries series = new XYSeries(Double.toString(x));
			XYSeriesRenderer renderer = new XYSeriesRenderer();
			
			series.add(x,y);
			dataset.addSeries(series);	
			
			int col = Color.parseColor("#DF013A");
			if (x > 4) col = Color.parseColor("#F7FE2E");
			if (x > 7) col = Color.parseColor("#01DF01");
			
			renderer.setColor(col);
			rendererSet.addSeriesRenderer(renderer);
		}
		
		for (int i=0; i<10; i++) rendererSet.addXTextLabel(i, Integer.toString(i));
		
		rendererSet.setXAxisMin(-1);
		rendererSet.setXAxisMax(10);
		rendererSet.setYAxisMin(0);
		rendererSet.setYAxisMax(max + 25);
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
		rendererSet.setClickEnabled(true);
		rendererSet.setShowGridX(true);	
		rendererSet.setExternalZoomEnabled(false);
		rendererSet.setInScroll(true);
		
		nbi60SubstructureConditionRatingsView = ChartFactory.getBarChartView(context, dataset, rendererSet, Type.STACKED);
		
		nbi60SubstructureConditionRatingsView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SeriesSelection seriesSelection = nbi60SubstructureConditionRatingsView.getCurrentSeriesAndPoint();
				if (seriesSelection != null) {
					int x = (int)seriesSelection.getXValue();	
					GridView gv = new GridView(context);
					gv.setAdapter(new MyCursorAdapter(context, DBHelper.getNBI60SubstructureConditionRatingsDrilldown(x)));
					gv.setBackgroundColor(Color.WHITE);
					gv.setColumnWidth(100);
					gv.setNumColumns(GridView.AUTO_FIT);					
				
					final PopupWindow window = new PopupWindow(gv, 800, 400, true);
					window.setOutsideTouchable(true);
					window.setTouchable(true);	
					window.setBackgroundDrawable(new BitmapDrawable());
					window.showAtLocation(nbi60SubstructureConditionRatingsView,Gravity.CENTER, 0,0);
				
		          }
			}
		});
		
	}
	
	public void calc_nbi62CulvertConditionRatings()
	{			
		//Configure dataset and renderer
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		XYMultipleSeriesRenderer rendererSet = new XYMultipleSeriesRenderer();
		double max = 0;
		for (int i=1; i<nbi62CulvertConditionRatings.size(); i++)
		{
			double x, y;
			try{	
				x = Double.parseDouble(nbi62CulvertConditionRatings.get(i).get(0));
				y = Double.parseDouble(nbi62CulvertConditionRatings.get(i).get(1));
			}
			catch(Exception e){continue;}		
			
			if (x < 0) continue;
			if (y > max) max = y;
			XYSeries series = new XYSeries(Double.toString(x));
			XYSeriesRenderer renderer = new XYSeriesRenderer();
			
			series.add(x,y);
			dataset.addSeries(series);	
			
			int col = Color.parseColor("#DF013A");
			if (x > 4) col = Color.parseColor("#F7FE2E");
			if (x > 7) col = Color.parseColor("#01DF01");
			
			renderer.setColor(col);
			rendererSet.addSeriesRenderer(renderer);
		}
		
		for (int i=0; i<10; i++) rendererSet.addXTextLabel(i, Integer.toString(i));
		
		rendererSet.setXAxisMin(-1);
		rendererSet.setXAxisMax(10);
		rendererSet.setYAxisMin(0);
		rendererSet.setYAxisMax(max + 25);
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
		rendererSet.setClickEnabled(true);
		rendererSet.setShowGridX(true);	
		rendererSet.setExternalZoomEnabled(false);
		rendererSet.setInScroll(true);
		
		nbi62CulvertConditionRatingsView = ChartFactory.getBarChartView(context, dataset, rendererSet, Type.STACKED);
		
		nbi62CulvertConditionRatingsView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SeriesSelection seriesSelection = nbi62CulvertConditionRatingsView.getCurrentSeriesAndPoint();
				if (seriesSelection != null) {
					int x = (int)seriesSelection.getXValue();	
					GridView gv = new GridView(context);
					gv.setAdapter(new MyCursorAdapter(context, DBHelper.getNBI62CulvertConditionRatingsDrilldown(x)));
					gv.setBackgroundColor(Color.WHITE);
					gv.setColumnWidth(100);
					gv.setNumColumns(GridView.AUTO_FIT);					
				
					final PopupWindow window = new PopupWindow(gv, 800, 400, true);
					window.setOutsideTouchable(true);
					window.setTouchable(true);	
					window.setBackgroundDrawable(new BitmapDrawable());
					window.showAtLocation(nbi62CulvertConditionRatingsView,Gravity.CENTER, 0,0);
				
		          }
			}
		});
	}

	public void calc_postedBridges()
	{		
		int A = 0;
		double max = 0;
		
		//Configure dataset and renderer
		final XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		XYMultipleSeriesRenderer rendererSet = new XYMultipleSeriesRenderer();		
		for (int i=1; i<postedBridges.size(); i++)
		{			
			int y;
			try{
				y = Integer.parseInt(postedBridges.get(i).get(1));
			}
			catch(Exception e){continue;}
			
			String title = postedBridges.get(i).get(0);
			if (title == null) continue;
			if (title.equals("A")){ A = y; continue; } 
			
			if (y > max) max = y;			
			
			XYSeries series = new XYSeries(title);
			XYSeriesRenderer renderer = new XYSeriesRenderer();
			
			series.add(i-1,y);
			dataset.addSeries(series);	
						
			renderer.setColor(Color.parseColor("#6495ED"));			
			rendererSet.addSeriesRenderer(renderer);
			rendererSet.addXTextLabel(i-1,title);
		}
		
		rendererSet.setXAxisMin(1);
		rendererSet.setXAxisMax(2 + dataset.getSeriesCount());
		rendererSet.setYAxisMin(0);
		rendererSet.setYAxisMax(max + 25);
		rendererSet.setYLabels(10);				
		rendererSet.addXTextLabel(1, "A: " + A);
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
		rendererSet.setClickEnabled(true);
		rendererSet.setShowGridX(true);	
		rendererSet.setExternalZoomEnabled(false);
		rendererSet.setInScroll(true);
		
		postedBridgesView = ChartFactory.getBarChartView(context, dataset, rendererSet, Type.STACKED);
		
		postedBridgesView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SeriesSelection seriesSelection = postedBridgesView.getCurrentSeriesAndPoint();
				if (seriesSelection != null) {
					int index = seriesSelection.getSeriesIndex();				
					
					GridView gv = new GridView(context);
					gv.setAdapter(new MyCursorAdapter(context, DBHelper.getPostedBridgesDrilldown(dataset.getSeriesAt(index).getTitle())));
					gv.setBackgroundColor(Color.WHITE);
					gv.setColumnWidth(100);
					gv.setNumColumns(GridView.AUTO_FIT);					
				
					final PopupWindow window = new PopupWindow(gv, 800, 400, true);
					window.setOutsideTouchable(true);
					window.setTouchable(true);	
					window.setBackgroundDrawable(new BitmapDrawable());
					window.showAtLocation(nbi62CulvertConditionRatingsView,Gravity.CENTER, 0,0);
				
		          }
			}
		});
	}	

	public void calc_bridgeStatus()
	{		
		//Configure dataset and renderer
		final CategorySeries dataset = new CategorySeries("Bridge Status");
		final DefaultRenderer rendererSet = new DefaultRenderer();	
				
		for (int i=1; i<postedBridges.size(); i++)
		{
			double y; int x;
			try{
				x = Integer.parseInt(bridgeStatus.get(i).get(0));
				y = Double.parseDouble(bridgeStatus.get(i).get(1));
			}
			catch(Exception e){continue;}
		
			
			String title = "";
			XYSeriesRenderer renderer = new XYSeriesRenderer();
			
			if (x == 0)
			{
				title = "OK";
				renderer.setColor(Color.parseColor("#01DF01"));
			}
			else if (x == 1)
			{
				title = "Structurally Deficient";
				renderer.setColor(Color.parseColor("#DF013A"));
			}
			else if (x == 2)
			{
				title = "Functionally Obsolete";
				renderer.setColor(Color.parseColor("#F7FE2E"));
			}	
			else continue;

			dataset.add(title, y);								
			rendererSet.addSeriesRenderer(renderer);
		}		

		
		rendererSet.setChartTitle("Bridge Status");
		rendererSet.setChartTitleTextSize(TITLE_SIZE);
		rendererSet.setLabelsColor(Color.BLACK);
		
		rendererSet.setShowLabels(true);
		rendererSet.setShowLegend(true);
		rendererSet.setLegendTextSize(15);

		rendererSet.setPanEnabled(false);
		rendererSet.setZoomEnabled(false);
		rendererSet.setClickEnabled(true);
		rendererSet.setDisplayValues(true);
		rendererSet.setExternalZoomEnabled(false);
		rendererSet.setInScroll(true);
	
		
		bridgeStatusView = ChartFactory.getPieChartView(context, dataset, rendererSet);
		
		
		bridgeStatusView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SeriesSelection seriesSelection = bridgeStatusView.getCurrentSeriesAndPoint();		
				if (seriesSelection != null) {
					int index = seriesSelection.getPointIndex();

					//Use to highlight certain slice					
					//rendererSet.getSeriesRendererAt(index).setHighlighted(true);	
				
					GridView gv = new GridView(context);
					gv.setAdapter(new MyCursorAdapter(context, DBHelper.getBridgeStatusDrilldown(index)));
					gv.setBackgroundColor(Color.WHITE);
					gv.setColumnWidth(100);
					gv.setNumColumns(GridView.AUTO_FIT);		              
				
					final PopupWindow window = new PopupWindow(gv, 800, 400, true);
					window.setOutsideTouchable(true);
					window.setTouchable(true);	
					window.setBackgroundDrawable(new BitmapDrawable());
					window.showAtLocation(bridgeStatusView,Gravity.CENTER, 0,0);
								
		          }
			}
		});
	}
	
	public void calc_bridgeCondition()
	{		
		//Configure dataset and renderer
		final CategorySeries dataset = new CategorySeries("Bridge Condition");
		DefaultRenderer rendererSet = new DefaultRenderer();	
				
		for (int i=1; i<bridgeCondition.size(); i++)
		{
			int y;
			try{				
				y = Integer.parseInt(bridgeCondition.get(i).get(1));
			}
			catch(Exception e){continue;}		
			
			String title = "";
			XYSeriesRenderer renderer = new XYSeriesRenderer();
			
			String x = bridgeCondition.get(i).get(0);
			if (x.equals("F"))
			{
				title = "Fair";
				renderer.setColor(Color.parseColor("#F7FE2E"));
			}
			else if (x.equals("G"))
			{
				title = "Good";
				renderer.setColor(Color.parseColor("#01DF01"));				
			}
			else if (x.equals("P"))
			{
				title = "Poor";
				renderer.setColor(Color.parseColor("#DF013A"));
			}	
			else
			{
				title = "No Value";
				renderer.setColor(Color.parseColor("#FFFFFF"));
			}

			dataset.add(title, y);								
			rendererSet.addSeriesRenderer(renderer);
		}		
		
		rendererSet.setChartTitle("Bridge Condition");
		rendererSet.setChartTitleTextSize(TITLE_SIZE);
		rendererSet.setLabelsColor(Color.BLACK);
		
		rendererSet.setShowLabels(true);
		rendererSet.setShowLegend(false);

		rendererSet.setPanEnabled(false);
		rendererSet.setZoomEnabled(false);		
		rendererSet.setClickEnabled(true);
		rendererSet.setDisplayValues(true);
		rendererSet.setExternalZoomEnabled(false);
		rendererSet.setInScroll(true);
		
		bridgeConditionView = ChartFactory.getPieChartView(context, dataset, rendererSet);
		
		bridgeConditionView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SeriesSelection seriesSelection = bridgeConditionView.getCurrentSeriesAndPoint();		
				if (seriesSelection != null) {
					int index = seriesSelection.getPointIndex();

					//Use to highlight certain slice					
					//rendererSet.getSeriesRendererAt(index).setHighlighted(true);	
					
					GridView gv = new GridView(context);
					gv.setAdapter(new MyCursorAdapter(context, DBHelper.getBridgeConditionDrilldown(dataset.getCategory(index))));
					gv.setBackgroundColor(Color.WHITE);
					gv.setColumnWidth(100);
					gv.setNumColumns(GridView.AUTO_FIT);		              
				
					final PopupWindow window = new PopupWindow(gv, 800, 400, true);
					window.setOutsideTouchable(true);
					window.setTouchable(true);	
					window.setBackgroundDrawable(new BitmapDrawable());
					window.showAtLocation(bridgeStatusView,Gravity.CENTER, 0,0);
								
		          }
			}
		});
	}
	
	public void calc_nhsBridgeCondition()
	{		
		//Configure dataset and renderer
		final CategorySeries dataset = new CategorySeries("NHS Bridge Condition");
		DefaultRenderer rendererSet = new DefaultRenderer();	
				
		for (int i=1; i<nhsBridgeCondition.size(); i++)
		{
			int y;
			try{				
				y = Integer.parseInt(nhsBridgeCondition.get(i).get(1));
			}
			catch(Exception e){continue;}		
			
			String title = "";
			XYSeriesRenderer renderer = new XYSeriesRenderer();
			
			String x = nhsBridgeCondition.get(i).get(0);
			if (x.equals("F"))
			{
				title = "Fair";
				renderer.setColor(Color.parseColor("#F7FE2E"));
			}
			else if (x.equals("G"))
			{
				title = "Good";
				renderer.setColor(Color.parseColor("#01DF01"));				
			}
			else if (x.equals("P"))
			{
				title = "Poor";
				renderer.setColor(Color.parseColor("#DF013A"));
			}	
			else
			{
				title = "No Value";
				renderer.setColor(Color.parseColor("#FFFFFF"));
			}

			dataset.add(title, y);								
			rendererSet.addSeriesRenderer(renderer);
		}		
		
		rendererSet.setChartTitle("NHS Bridge Condition");
		rendererSet.setChartTitleTextSize(TITLE_SIZE);
		rendererSet.setLabelsColor(Color.BLACK);
		
		rendererSet.setShowLabels(true);
		rendererSet.setShowLegend(false);

		rendererSet.setPanEnabled(false);
		rendererSet.setZoomEnabled(false);
		rendererSet.setClickEnabled(true);
		rendererSet.setDisplayValues(true);
		rendererSet.setExternalZoomEnabled(false);
		rendererSet.setInScroll(true);
		
		nhsBridgeConditionView = ChartFactory.getPieChartView(context, dataset, rendererSet);
		
		nhsBridgeConditionView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SeriesSelection seriesSelection = nhsBridgeConditionView.getCurrentSeriesAndPoint();		
				if (seriesSelection != null) {
					int index = seriesSelection.getPointIndex();

					//Use to highlight certain slice					
					//rendererSet.getSeriesRendererAt(index).setHighlighted(true);	
					
					GridView gv = new GridView(context);
					gv.setAdapter(new MyCursorAdapter(context, DBHelper.getNHSBridgeConditionDrilldown(dataset.getCategory(index))));
					gv.setBackgroundColor(Color.WHITE);
					gv.setColumnWidth(100);
					gv.setNumColumns(GridView.AUTO_FIT);		              
				
					final PopupWindow window = new PopupWindow(gv, 800, 400, true);
					window.setOutsideTouchable(true);
					window.setTouchable(true);	
					window.setBackgroundDrawable(new BitmapDrawable());
					window.showAtLocation(bridgeStatusView,Gravity.CENTER, 0,0);
								
		          }
			}
		});
	}
	
	public void calc_deckAreaBridgeCondition()
	{		
		//Configure dataset and renderer
		final CategorySeries dataset = new CategorySeries("Deck Area Bridge Condition");
		DefaultRenderer rendererSet = new DefaultRenderer();	
				
		for (int i=1; i<deckAreaBridgeCondition.size(); i++)
		{
			double y;
			try{				
				y = Double.parseDouble(deckAreaBridgeCondition.get(i).get(1));
			}
			catch(Exception e){continue;}		
			
			String title = "";
			XYSeriesRenderer renderer = new XYSeriesRenderer();
			
			String x = deckAreaBridgeCondition.get(i).get(0);
			if (x.equals("F"))
			{
				title = "Fair";
				renderer.setColor(Color.parseColor("#F7FE2E"));
			}
			else if (x.equals("G"))
			{
				title = "Good";
				renderer.setColor(Color.parseColor("#01DF01"));				
			}
			else if (x.equals("P"))
			{
				title = "Poor";
				renderer.setColor(Color.parseColor("#DF013A"));
			}	
			else
			{
				title = "No Value";
				renderer.setColor(Color.parseColor("#FFFFFF"));
			}

			dataset.add(title, y);								
			rendererSet.addSeriesRenderer(renderer);
		}		
		
		rendererSet.setChartTitle("Deck Area Bridge Condition");
		rendererSet.setChartTitleTextSize(TITLE_SIZE);
		rendererSet.setLabelsColor(Color.BLACK);
		
		rendererSet.setShowLabels(true);
		rendererSet.setShowLegend(false);

		rendererSet.setPanEnabled(false);
		rendererSet.setZoomEnabled(false);
		rendererSet.setClickEnabled(true);
		rendererSet.setExternalZoomEnabled(false);
		rendererSet.setInScroll(true);

		
		deckAreaBridgeConditionView = ChartFactory.getPieChartView(context, dataset, rendererSet);
		
		deckAreaBridgeConditionView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SeriesSelection seriesSelection = deckAreaBridgeConditionView.getCurrentSeriesAndPoint();		
				if (seriesSelection != null) {
					int index = seriesSelection.getPointIndex();

					//Use to highlight certain slice					
					//rendererSet.getSeriesRendererAt(index).setHighlighted(true);	
				
					GridView gv = new GridView(context);
					gv.setAdapter(new MyCursorAdapter(context, DBHelper.getDeckAreaBridgeConditionDrilldown(dataset.getCategory(index))));
					gv.setBackgroundColor(Color.WHITE);
					gv.setColumnWidth(100);
					gv.setNumColumns(GridView.AUTO_FIT);		              
				
					final PopupWindow window = new PopupWindow(gv, 800, 400, true);
					window.setOutsideTouchable(true);
					window.setTouchable(true);	
					window.setBackgroundDrawable(new BitmapDrawable());
					window.showAtLocation(bridgeStatusView,Gravity.CENTER, 0,0);
								
		          }
			}
		});
	}
	
	public void calc_deckAreaNHSBridgeCondition()
	{		
		//Configure dataset and renderer
		final CategorySeries dataset = new CategorySeries("Deck Area NHS Bridge Condition");
		DefaultRenderer rendererSet = new DefaultRenderer();	
				
		for (int i=1; i<deckAreaNHSBridgeCondition.size(); i++)
		{
			double y;
			try{				
				y = Double.parseDouble(deckAreaNHSBridgeCondition.get(i).get(1));
			}
			catch(Exception e){continue;}		
			
			String title = "";
			XYSeriesRenderer renderer = new XYSeriesRenderer();
			
			String x = deckAreaNHSBridgeCondition.get(i).get(0);
			if (x.equals("F"))
			{
				title = "Fair";
				renderer.setColor(Color.parseColor("#F7FE2E"));
			}
			else if (x.equals("G"))
			{
				title = "Good";
				renderer.setColor(Color.parseColor("#01DF01"));				
			}
			else if (x.equals("P"))
			{
				title = "Poor";
				renderer.setColor(Color.parseColor("#DF013A"));
			}	
			else
			{
				title = "No Value";
				renderer.setColor(Color.parseColor("#FFFFFF"));
			}

			dataset.add(title, y);								
			rendererSet.addSeriesRenderer(renderer);
		}		
		
		rendererSet.setChartTitle("Deck Area NHS Bridge Condition");
		rendererSet.setChartTitleTextSize(TITLE_SIZE);
		rendererSet.setLabelsColor(Color.BLACK);
		
		rendererSet.setShowLabels(true);
		rendererSet.setShowLegend(false);

		rendererSet.setPanEnabled(false);
		rendererSet.setZoomEnabled(false);
		rendererSet.setClickEnabled(true);
		rendererSet.setExternalZoomEnabled(false);
		rendererSet.setInScroll(true);
		
		deckAreaNHSBridgeConditionView = ChartFactory.getPieChartView(context, dataset, rendererSet);
		
		deckAreaNHSBridgeConditionView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SeriesSelection seriesSelection = deckAreaNHSBridgeConditionView.getCurrentSeriesAndPoint();		
				if (seriesSelection != null) {
					int index = seriesSelection.getPointIndex();

					//Use to highlight certain slice					
					//rendererSet.getSeriesRendererAt(index).setHighlighted(true);						
					GridView gv = new GridView(context);
					gv.setAdapter(new MyCursorAdapter(context, DBHelper.getDeckAreaNHSBridgeConditionDrilldown(dataset.getCategory(index))));
					gv.setBackgroundColor(Color.WHITE);
					gv.setColumnWidth(100);
					gv.setNumColumns(GridView.AUTO_FIT);		              
				
					final PopupWindow window = new PopupWindow(gv, 800, 400, true);
					window.setOutsideTouchable(true);
					window.setTouchable(true);	
					window.setBackgroundDrawable(new BitmapDrawable());
					window.showAtLocation(bridgeStatusView,Gravity.CENTER, 0,0);
								
		          }
			}
		});
	}
	
	public void calc_bridgeSufficiencyRatingDeckArea()
	{		
		//Configure dataset and renderer
		final CategorySeries dataset = new CategorySeries("Bridge Sufficiency Rating Deck Area");
		DefaultRenderer rendererSet = new DefaultRenderer();
		
		double[] categories = new double[10];		
				
		for (int i=1; i<bridgeSufficiencyRatingDeckArea.size(); i++)
		{
			double x, y;
			try{				
				x = Double.parseDouble(bridgeSufficiencyRatingDeckArea.get(i).get(0));
				y = Double.parseDouble(bridgeSufficiencyRatingDeckArea.get(i).get(1));
			}
			catch(Exception e){continue;}		
			
			if (x < 0) continue;	

			if (x <= 10) categories[0] += y;
			else if (x <= 20) categories[1] += y;
			else if (x <= 30) categories[2] += y;
			else if (x <= 40) categories[3] += y;
			else if (x <= 50) categories[4] += y;
			else if (x <= 60) categories[5] += y;
			else if (x <= 70) categories[6] += y;
			else if (x <= 80) categories[7] += y;
			else if (x <= 90) categories[8] += y;
			else if (x <= 100) categories[9] += y;
		}
		
		//Load aggregate data
		for (int i=0; i<10; i++)
		{
			String title = "";
			XYSeriesRenderer renderer = new XYSeriesRenderer();	
		
			switch(i)
			{
			case 0:
				title = "0-10";
				renderer.setColor(Color.rgb(237,194,64));
				break;
			case 1:
				title = "11-20";
				renderer.setColor(Color.rgb(175,216,248));	
				break;
			case 2:
				title = "21-30";
				renderer.setColor(Color.rgb(203,75,75));
				break;
			case 3:
				title = "31-40";
				renderer.setColor(Color.rgb(77,167,77));
				break;
			case 4:
				title = "41-50";
				renderer.setColor(Color.rgb(148,64,237));
				break;
			case 5:
				title = "51-60";
				renderer.setColor(Color.rgb(189,155,51));	
				break;
			case 6:
				title = "61-70";
				renderer.setColor(Color.rgb(140,172,198));	
				break;
			case 7:
				title = "71-80";
				renderer.setColor(Color.rgb(162,60,60));	
				break;
			case 8:
				title = "81-90";
				renderer.setColor(Color.rgb(61,133,61));
				break;
			case 9:
				title = "91-100";
				renderer.setColor(Color.rgb(118,51,189));
				break;
			}				
			
			dataset.add(title, categories[i]);		
			rendererSet.addSeriesRenderer(renderer);				
		}		
		
		rendererSet.setChartTitle("Bridge Sufficiency Rating Deck Area");
		rendererSet.setChartTitleTextSize(TITLE_SIZE);
		rendererSet.setLabelsColor(Color.BLACK);
		
		rendererSet.setShowLabels(true);
		rendererSet.setShowLegend(false);

		rendererSet.setPanEnabled(false);
		rendererSet.setZoomEnabled(false);
		rendererSet.setClickEnabled(true);
		rendererSet.setExternalZoomEnabled(false);
		rendererSet.setInScroll(true);
		
		bridgeSufficiencyRatingDeckAreaView = ChartFactory.getPieChartView(context, dataset, rendererSet);
		
		bridgeSufficiencyRatingDeckAreaView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SeriesSelection seriesSelection = bridgeSufficiencyRatingDeckAreaView.getCurrentSeriesAndPoint();
				if (seriesSelection != null) {
					int index = seriesSelection.getPointIndex();				
					
					GridView gv = new GridView(context);
					gv.setAdapter(new MyCursorAdapter(context, DBHelper.getBridgeSufficiencyRatingDeckAreaDrilldown(index*10)));
					gv.setBackgroundColor(Color.WHITE);
					gv.setColumnWidth(100);
					gv.setNumColumns(GridView.AUTO_FIT);					
				
					final PopupWindow window = new PopupWindow(gv, 800, 400, true);
					window.setOutsideTouchable(true);
					window.setTouchable(true);	
					window.setBackgroundDrawable(new BitmapDrawable());
					window.showAtLocation(bridgeSufficiencyRatingDeckAreaView,Gravity.CENTER, 0,0);
				
		          }
			}
		});
	}

	public void calc_structurallyDeficientDeckArea()
	{
		
		DialRenderer rendererSet = new DialRenderer();	
		CategorySeries dataset = new CategorySeries("DeficientDeckArea");
	
		double percent;
		percent = Double.parseDouble(structurallyDeficientDeckArea.get(1).get(0));
		dataset.add(percent);
		
		double display_percent;
		display_percent = Math.round(percent * 100);
		display_percent = display_percent/100;
		
		
		//Set Title
		rendererSet.setChartTitle("Structurally Deficient Deck Area "+ display_percent +"%");
		rendererSet.setChartTitleTextSize(TITLE_SIZE);
		rendererSet.setLabelsColor(Color.BLACK);
						
		//Set Needle
		SimpleSeriesRenderer r = new SimpleSeriesRenderer();
		r.setColor(Color.RED);
		rendererSet.addSeriesRenderer(r);
		//Dial Chart Format
		rendererSet.setAngleMax(90);
		rendererSet.setAngleMin(270);
		rendererSet.setMinValue(0);
		rendererSet.setMaxValue(30);
		rendererSet.setMajorTicksSpacing(5);
				
		rendererSet.setPanEnabled(false);
		rendererSet.setShowLegend(false);
		rendererSet.setZoomEnabled(false);
		rendererSet.setClickEnabled(true);
		rendererSet.setExternalZoomEnabled(false);		
		rendererSet.setInScroll(true);
				
		structurallyDeficientDeckAreaView = ChartFactory.getDialChartView(context, dataset, rendererSet);
		
		structurallyDeficientDeckAreaView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
						
					
				GridView gv = new GridView(context);
				gv.setAdapter(new MyCursorAdapter(context, DBHelper.getStructurallyDeficientDeckAreaDrilldown()));
				gv.setBackgroundColor(Color.WHITE);
				gv.setColumnWidth(100);
				gv.setNumColumns(GridView.AUTO_FIT);					
			
				final PopupWindow window = new PopupWindow(gv, 800, 400, true);
				window.setOutsideTouchable(true);
				window.setTouchable(true);	
				window.setBackgroundDrawable(new BitmapDrawable());
				window.showAtLocation(structurallyDeficientDeckAreaView,Gravity.CENTER, 0,0);
			}
		});		
		
	}
	
	public void calc_structurallyDeficientNHSDeckArea()
	{
		
		DialRenderer rendererSet = new DialRenderer();	
		CategorySeries dataset = new CategorySeries("DeficientNHSDeckArea");
	
		double percent;
		percent = Double.parseDouble(structurallyDeficientNHSDeckArea.get(1).get(0));
		dataset.add(percent);
		double display_percent;
		display_percent = Math.round(percent * 100);
		display_percent = display_percent/100;
		
		//Set Title
		rendererSet.setChartTitle("Structurally Deficient NHS Deck Area "+ display_percent +"%");
		rendererSet.setChartTitleTextSize(TITLE_SIZE);
		rendererSet.setLabelsColor(Color.BLACK);
				
		
	    //Set Needle
	    SimpleSeriesRenderer r = new SimpleSeriesRenderer();
	    r.setColor(Color.RED);
	    rendererSet.addSeriesRenderer(r);
		
	    //Dial Chart Format
	    rendererSet.setAngleMax(90);
	    rendererSet.setAngleMin(270);
	    rendererSet.setMinValue(0);
		rendererSet.setMaxValue(30);
		rendererSet.setMajorTicksSpacing(5);		
					
		rendererSet.setPanEnabled(false);
		rendererSet.setShowLegend(false);
		rendererSet.setZoomEnabled(false);
		rendererSet.setClickEnabled(true);
		rendererSet.setExternalZoomEnabled(false);
		rendererSet.setInScroll(true);
		
		structurallyDeficientNHSDeckAreaView = ChartFactory.getDialChartView(context, dataset, rendererSet);
		
		structurallyDeficientNHSDeckAreaView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {					
					
				GridView gv = new GridView(context);
				gv.setAdapter(new MyCursorAdapter(context, DBHelper.getStructurallyDeficientNHSDeckAreaDrilldown()));
				gv.setBackgroundColor(Color.WHITE);
				gv.setColumnWidth(100);
				gv.setNumColumns(GridView.AUTO_FIT);					
			
				final PopupWindow window = new PopupWindow(gv, 800, 400, true);
				window.setOutsideTouchable(true);
				window.setTouchable(true);	
				window.setBackgroundDrawable(new BitmapDrawable());
				window.showAtLocation(structurallyDeficientNHSDeckAreaView,Gravity.CENTER, 0,0);
			}
		});
	}

}


