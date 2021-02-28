package roadgraph;

import java.util.Comparator;

import geography.GeographicPoint;

public class ComparatorForAStar implements Comparator<DistanceToBegin> {

    private GeographicPoint begin;

    public ComparatorForAStar(GeographicPoint begin) {
        this.begin = begin;
    }

    public int compare(DistanceToBegin o1, DistanceToBegin o2) {
        double dist1 = o1.getDistance() + o1.getVertex().getLocation().distance(begin);
        double dist2 = o2.getDistance() + o2.getVertex().getLocation().distance(begin);
        return Double.compare(dist1, dist2);
    }
}
