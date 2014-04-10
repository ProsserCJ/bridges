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
    private Context context;
    private GraphFactory graphFactory;
    
    public GraphAdapter(Context c) {
        context = c;
        graphFactory = new GraphFactory(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
    	GraphicalView graphView = null;
        if (convertView == null) 
        {         	
        	switch(position)
        	{
        	case 0: graphView = graphFactory.getView_nbi58DeckConditionRatings(); break;
        	case 1: graphView = graphFactory.getView_nbi59SuperstructureConditionRatings(); break;
        	case 2: graphView = graphFactory.getView_nbi60SubstructureConditionRatings(); break;
        	case 3: graphView = graphFactory.getView_nbi62CulvertConditionRatings(); break;
        	case 4: graphView = graphFactory.getView_postedBridges(); break;
        	case 5: graphView = graphFactory.getView_StructurallyDeficientDeckArea(); break;
          	default: graphView = graphFactory.getView_nbi58DeckConditionRatings(); break;
        	}

        	graphView.setLayoutParams(new GridView.LayoutParams(400, 350));
        	graphView.setPadding(25,25,25,25);
        } 
        else 
        {
        	graphView = (GraphicalView) convertView;
        }        

        return graphView;
    }

	@Override
	public int getCount() {
		return 5;
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
