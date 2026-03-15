package PamakBook;

import java.awt.*;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import javax.swing.*;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.shortestpath.UnweightedShortestPath;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.visualization.*;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

@SuppressWarnings("serial")
public class FriendGraph extends JFrame{
	private Graph <String, String> friendGraph; 
	private JLabel diameterLabel;
	private JPanel LabelPanel;
	private Container contentPane;
	private BorderLayout layout;
	
	 @SuppressWarnings("unchecked")
	 FriendGraph(ArrayList<User> users)
	{
		 
		 contentPane = new Container();
		layout = new BorderLayout();
		 
		LabelPanel = new JPanel();
		contentPane.setLayout(layout);
		
		friendGraph = new SparseMultigraph<>(); 
		for(User u : users)
		{
			friendGraph.addVertex(u.getName());
		}
		
		for(User u : users)
		{	
			for(User f: u.getFriends())
			{
				if(u.isFriend(f) && !friendGraph.containsEdge(friendGraph.findEdge(f.getName(), u.getName())))
				{
					friendGraph.addEdge(u.getName() +" to " + f.getName(), u.getName(), f.getName());
					
				}
			}
		}
		
		
			
		VisualizationViewer<Integer, Paint> vv = new VisualizationViewer<Integer, Paint>(new CircleLayout(friendGraph));
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<>());
		
		 diameterLabel  = new JLabel();
			diameterLabel.setText("Diameter = "+ findDiameter(friendGraph));
			diameterLabel.setSize(11, 11);
		
		LabelPanel.add(diameterLabel);
		
		contentPane.add(vv, BorderLayout.CENTER);
		contentPane.add(LabelPanel, BorderLayout.SOUTH);
		
		
		this.setVisible(true);
		this.setContentPane(contentPane);
		this.setSize(800, 1000);
		this.setLocation(750, 500);
		this.setTitle("Friend Graph");	
		
	}
	 
	 
	 
	 public <V, E> double findDiameter(Graph<V, E> friendGraph)
	 {
		
		double diameter = 0;
		
		 if(friendGraph == null || friendGraph.getVertexCount() == 0) //if there is no diagram of diagram has no vertices
		 {
			 diameter = 0;
		 }
		 
		 Collection<V> vertices= friendGraph.getVertices();
		 
		 //value to find shortest paths between vertices
		 UnweightedShortestPath<V, E> shortestPath = new UnweightedShortestPath<V, E>(friendGraph);
		 
		 for(V source : vertices)
		 {
				 Map<V, Number> distances = shortestPath.getDistanceMap(source); //to store all shortest paths together
				 
				for(Number dist: distances.values())
				{
					 if (dist!= null)
					 {
						 diameter = Math.max(diameter, dist.floatValue()); //finds the maximum shortest path between the shotests paths
					 }
				}
		 }
		 
		 return diameter;
		 
		
		 
	 }
	 
	

}
