package roadgraph;

public class DistanceToBegin implements Comparable<DistanceToBegin> {
    private MapVertex vertex;
    private double distance;

    public DistanceToBegin(MapVertex vertex, double distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    public MapVertex getVertex() {
        return this.vertex;
    }

    public double getDistance() {
        return this.distance;
    }

    public int compareTo(DistanceToBegin other) {
        return Double.compare(this.distance, other.distance);
    }
}
