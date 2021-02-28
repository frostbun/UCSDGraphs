package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;

public class MapVertex {
    private GeographicPoint location;
    private List<MapEdge> adjVertices;

    public MapVertex(GeographicPoint location) {
        this.location = location;
        adjVertices = new ArrayList<>();
    }

    public GeographicPoint getLocation() {
        return this.location;
    }

    public List<MapEdge> getAdjVertices() {
        return this.adjVertices;
    }

    public void addEdge(MapVertex to, String roadName, String roadType, double length) {
        adjVertices.add(new MapEdge(this, to, roadName, roadType, length));
    }
}
