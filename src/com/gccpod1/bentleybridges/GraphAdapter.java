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
    private final int NUM_GRAPHS = 10;
    
    public GraphAdapter(Context c) {
        context = c;
        graphFactory = new GraphFactory(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
    	GraphicalView graphView = null;
    	
    	switch(position)
    	{
    	case 0: graphView = graphFactory.getView_nbi58DeckConditionRatings(); break;
    	case 1: graphView = graphFactory.getView_nbi59SuperstructureConditionRatings(); break;
    	case 2: graphView = graphFactory.getView_nbi60SubstructureConditionRatings(); break;
    	case 3: graphView = graphFactory.getView_nbi62CulvertConditionRatings(); break;
    	case 4: graphView = graphFactory.getView_postedBridges(); break;
    	case 5: graphView = graphFactory.getView_bridgeStatus(); break;
    	case 6: graphView = graphFactory.getView_bridgeCondition(); break;
    	case 7: graphView = graphFactory.getView_nhsBridgeCondition(); break;
    	case 8: graphView = graphFactory.getView_deckAreaBridgeCondition(); break;
    	case 9: graphView = graphFactory.getView_deckAreaNHSBridgeCondition(); break;
    	default: graphView = graphFactory.getView_bridgeCondition(); break;
    	}

    	graphView.setLayoutParams(new GridView.LayoutParams(400, 350));
    	graphView.setPadding(25,25,25,25);          

        return graphView;
    }

	@Override
	public int getCount() {
		return NUM_GRAPHS;
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
