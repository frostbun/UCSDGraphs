# Class: MapGraph
* HashMap<GeographicPoint, MapVertex> __trace__: map vertex with its location (also avoid duplication)
* MapGraph: constructor
* getNumVertices: return __trace__.size()
* getVertices: return __trace__.keySet()
* getNumEdges: iterate over all __MapVertex__ in __trace__ and return the total adj vertices
* addVertex: put a new key __location__ in trace with __new MapVertex(location)__
* addEdge: use trace to find __MapVertex__ objects of __from__, __to__ and add new adj vertex to from
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