/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	private HashMap<GeographicPoint, MapVertex> trace;
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		this.trace = new HashMap<>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		return trace.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		return trace.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		int count = 0;
		for(MapVertex vertex: trace.values()) {
			count += vertex.getAdjVertices().size();
		}
		return count;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		if(location == null || trace.containsKey(location)) {
			return false;
		}

		trace.put(location, new MapVertex(location));
		return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		if(from == null || to == null || roadName == null ||
				roadType == null || length < 0) {

			throw new IllegalArgumentException();
		}

		trace.get(from).addEdge(trace.get(to), roadName, roadType, length);
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal,
									 Consumer<GeographicPoint> nodeSearched)
	{
		// init
		List<MapVertex> queue = new LinkedList<>();
		Set<MapVertex> visited = new HashSet<>();
		Map<MapVertex, MapVertex> prev = new HashMap<>();
		MapVertex begin = trace.get(start);
		MapVertex end = trace.get(goal);
		queue.add(begin);
		visited.add(begin);
		prev.put(begin, null);
		nodeSearched.accept(begin.getLocation());

		// bfs
		while(!queue.isEmpty() && !visited.contains(end)) {
			MapVertex curr = queue.remove(0);
			for(MapEdge edge: curr.getAdjVertices()) {
				MapVertex next = edge.getEnd();
				if(!visited.contains(next)) {
					queue.add(next);
					visited.add(next);
					prev.put(next, curr);
					nodeSearched.accept(next.getLocation());
				}
			}
		}

		// cant find a way
		if(!visited.contains(end)) {
			return new LinkedList<>();
		}

		// backtrack
		List<GeographicPoint> road = new LinkedList<>();
		MapVertex curr = end;
		road.add(0, curr.getLocation());
		while(prev.get(curr) != null) {
			curr = prev.get(curr);
			road.add(0, curr.getLocation());
		}
		return road;
	}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal,
										  Consumer<GeographicPoint> nodeSearched) {
		
		// init
		Map<MapVertex, Double> distance = new HashMap<>();
		Map<MapVertex, MapVertex> prev = new HashMap<>();
		MapVertex begin = trace.get(start);
		MapVertex end = trace.get(goal);
		distance.put(begin, 0.0);
		prev.put(begin, null);
		
		PriorityQueue<DistanceToBegin> queue = new PriorityQueue<>();
		Set<MapVertex> visited = new HashSet<>();
		queue.add(new DistanceToBegin(begin, 0));

		// dijkstra
		if(!dijkstraImplement(prev, distance, begin, end, nodeSearched, queue, visited)) {
			// cant find a way
			return new LinkedList<>();
		}

		// backtrack
		List<GeographicPoint> road = new LinkedList<>();
		MapVertex curr = end;
		road.add(0, curr.getLocation());
		while(prev.get(curr) != null) {
			curr = prev.get(curr);
			road.add(0, curr.getLocation());
		}
		return road;
	}

	private boolean dijkstraImplement(Map<MapVertex, MapVertex> prev,
										Map<MapVertex, Double> distance,
										MapVertex begin, MapVertex end,
										Consumer<GeographicPoint> nodeSearched,
										PriorityQueue<DistanceToBegin> queue,
										Set<MapVertex> visited) {

		// init

		while(!queue.isEmpty()) {

			// dequeue
			DistanceToBegin curr = queue.remove();
			while(visited.contains(curr.getVertex()) && !queue.isEmpty()) {
				curr = queue.remove();
			}

			MapVertex vertex = curr.getVertex();
			double dist = curr.getDistance();
			// System.out.println(dist);

			// visited all vertex in queue but cant find a way
			if(visited.contains(vertex)) {
				return false;
			}
			
			// set curr vertex as visited
			visited.add(vertex);
			nodeSearched.accept(vertex.getLocation());

			// found shortest way
			if(vertex == end) {
				System.out.println(visited.size());
				return true;
			}

			// add adj vertices to queue
			for(MapEdge edge: vertex.getAdjVertices()) {

				MapVertex next = edge.getEnd();
				double distToNext = dist + edge.getLength();

				if(!visited.contains(next) && (distance.get(next) == null || distToNext < distance.get(next))) {
					queue.add(new DistanceToBegin(next, distToNext));
					distance.put(next, distToNext);
					prev.put(next, vertex);
				}
			}
		}

		return false;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal,
											 Consumer<GeographicPoint> nodeSearched) {
		
		// init
		Map<MapVertex, Double> distance = new HashMap<>();
		Map<MapVertex, MapVertex> prev = new HashMap<>();
		MapVertex begin = trace.get(start);
		MapVertex end = trace.get(goal);
		distance.put(begin, 0.0);
		prev.put(begin, null);
		
		// the only difference to dijkstra
		PriorityQueue<DistanceToBegin> queue = new PriorityQueue<>(new ComparatorForAStar(goal));
		
		Set<MapVertex> visited = new HashSet<>();
		queue.add(new DistanceToBegin(begin, 0));

		// dijkstra
		if(!dijkstraImplement(prev, distance, begin, end, nodeSearched, queue, visited)) {
			// cant find a way
			return new LinkedList<>();
		}

		// backtrack
		List<GeographicPoint> road = new LinkedList<>();
		MapVertex curr = end;
		road.add(0, curr.getLocation());
		while(prev.get(curr) != null) {
			curr = prev.get(curr);
			road.add(0, curr.getLocation());
		}
		return road;
	}

	
	
	public static void main(String[] args) {
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		
		// You can use this method for testing.  
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */

		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		simpleTestMap.dijkstra(testStart,testEnd);
		simpleTestMap.aStarSearch(testStart,testEnd);
		
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testMap.dijkstra(testStart,testEnd);
		testMap.aStarSearch(testStart,testEnd);
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testMap.dijkstra(testStart,testEnd);
		testMap.aStarSearch(testStart,testEnd);
		
		/* Use this code in Week 3 End of Week Quiz */
		
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		theMap.dijkstra(start,end);
		theMap.aStarSearch(start,end);
	}
}
