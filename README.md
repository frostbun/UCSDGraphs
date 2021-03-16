# Class: MapGraph
* HashMap<GeographicPoint, MapVertex> trace: map vertex with its location (also avoid duplication)
* MapGraph: constructor
* getNumVertices: return trace.size()
* getVertices: return trace.keySet()
* getNumEdges: iterate over all MapVertex in trace and return the total adj vertices
* addVertex: put a new key "location" in trace with "new MapVertex(location)"
* addEdge: use trace to find MapVertex objects of from, to and add new adj vertex to from
* bfs
    * queue: a LinkedList queue
    * visited: a HashSet to avoid revisit vertices
    * prev: a HashMap to trace back the road you went through
    * normal implement of bfs

# Class name: MapVertex
* Contain informations about graph vertices
    * It's location
    * ArrayList of type __MapEdge__ contains informations about all adj vertices

# Class name: MapEdge
* Contain informations about graph edges
    * Begin vertex
    * End vertex
    * Road name
    * Road type
    * Road length