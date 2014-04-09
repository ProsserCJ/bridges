package com.gccpod1.bentleybridges;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;

public class BarGraph {
	public Context context;
	
	public BarGraph(Context c){context = c;}
	
	public Intent getIntent(ArrayList<ArrayList<String>> data, String name)
	{
		XYSeries series_nbi58DeckConditionRatings = initBarSeries(data, name);
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series_nbi58DeckConditionRatings);
		XYSeriesRenderer mCurrentRenderer = new XYSeriesRenderer();
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.addSeriesRenderer(mCurrentRenderer);		
		
		return ChartFactory.getBarChartIntent(context, dataset, renderer, null);
	}
	
	public GraphicalView getView(ArrayList<ArrayList<String>> data, String name)
	{
		XYSeries series_nbi58DeckConditionRatings = initBarSeries(data, name);
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series_nbi58DeckConditionRatings);
		XYSeriesRenderer mCurrentRenderer = new XYSeriesRenderer();
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.addSeriesRenderer(mCurrentRenderer);		
		
		return ChartFactory.getBarChartView(context, dataset, renderer, null);
	}

	

	private XYSeries initBarSeries(ArrayList<ArrayList<String>> list, String title)
	{
		XYSeries temp = new XYSeries(title);
		for (int i=1; i<list.size(); i++)
		{
			try{
				temp.add(Double.parseDouble((list.get(i).get(0))),
						Double.parseDouble((list.get(i).get(1))));
			}
			catch(Exception e){continue;}
		
		}
		return temp;	
	}
	

}
