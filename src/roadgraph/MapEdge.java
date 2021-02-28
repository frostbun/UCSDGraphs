package roadgraph;

public class MapEdge {
    private MapVertex begin;
    private MapVertex end;

    private String roadName;
    private String roadType;
    private double length;

    public MapEdge(MapVertex begin, MapVertex end, String roadName, String roadType, double length) {
        this.begin = begin;
        this.end = end;
        this.roadName = roadName;
        this.roadType = roadType;
        this.length = length;
    }

    public MapVertex getBegin() {
        return this.begin;
    }

    public MapVertex getEnd() {
        return this.end;
    }

    public String getRoadName() {
        return this.roadName;
    }

    public String getRoadType() {
        return this.roadType;
    }

    public double getLength() {
        return this.length;
    }
}
