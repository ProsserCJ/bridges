package com.gccpod1.bentleybridges;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.achartengine.GraphicalView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class GraphAdapter extends BaseAdapter {
    private Context context;
    private GraphFactory graphFactory;
    private int numGraphs = 0;
    private int width, height;
    Scanner input;
    ArrayList<GraphicalView> graphs;
    
    public GraphAdapter(Context c) {
        context = c;
        graphFactory = new GraphFactory(context); 
        graphs = new ArrayList<GraphicalView>();
        refresh();
    }

    public View getView(int position, View convertView, ViewGroup parent) {    	
    	GraphicalView graphView = graphs.get(position);      	 
    	
    	graphView.setLayoutParams(new GridView.LayoutParams(width, height));
    	graphView.setPadding(25,25,25,25);

        return graphView;
    }
    
    private GraphicalView parse(String input){
    
       	if (input.equalsIgnoreCase("Deck Condition Ratings")) 				return graphFactory.getView_nbi58DeckConditionRatings();
       	if (input.equalsIgnoreCase("Superstructure Condition Ratings"))	 	return graphFactory.getView_nbi59SuperstructureConditionRatings();    
       	if (input.equalsIgnoreCase("Substructure Condition Ratings")) 		return graphFactory.getView_nbi60SubstructureConditionRatings();
       	if (input.equalsIgnoreCase("Culvert Condition Ratings"))			return graphFactory.getView_nbi62CulvertConditionRatings();
    	if (input.equalsIgnoreCase("Posted Bridges")) 						return graphFactory.getView_postedBridges();
    	if (input.equalsIgnoreCase("Bridge Status")) 						return graphFactory.getView_bridgeStatus(); 
    	if (input.equalsIgnoreCase("Bridge Condition")) 					return graphFactory.getView_bridgeCondition(); 
    	if (input.equalsIgnoreCase("NHS Bridge Condition"))					return graphFactory.getView_nhsBridgeCondition();
    	if (input.equalsIgnoreCase("Deck Area Bridge Condition"))			return graphFactory.getView_deckAreaBridgeCondition();
    	if (input.equalsIgnoreCase("Deck Area NHS Bridge Condition")) 		return graphFactory.getView_deckAreaNHSBridgeCondition();
    	if (input.equalsIgnoreCase("Structurally Deficient Deck Area"))		return graphFactory.getView_StructurallyDeficientDeckArea();
    	if (input.equalsIgnoreCase("Structurally Deficient NHS Deck Area"))	return graphFactory.getView_StructurallyDeficientNHSDeckArea();
    	if (input.equalsIgnoreCase("Bridge Sufficiency Rating Deck Area"))	return graphFactory.getView_bridgeSufficiencyRatingDeckArea();
    	
    	return null;
    }
    
    public void refresh()
    {
    	graphs.clear();
    	
        try {
        	input = new Scanner(context.getAssets().open("graph_order.txt"));
		} catch (IOException e) {			
			e.printStackTrace();
			input = null;
		}         

        width = input.nextInt();
        height = input.nextInt();
        input.nextLine(); input.nextLine();        

    	for (numGraphs = 0; input.hasNextLine(); numGraphs++){
    		graphs.add(parse(input.nextLine()));    	
    	}
    }

	@Override
	public int getCount() {
		return numGraphs;
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

