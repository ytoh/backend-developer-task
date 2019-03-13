package cz.databreakers.example.developertask.domain.route;

import cz.databreakers.example.developertask.domain.nearby.NearbyPlace;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

/**
 * @author hvizdos
 */
public class RouteDetail extends ResourceSupport {

    private RouteSummary summary;
    private List<RoutePoint> points;
    private List<NearbyPlace> nearbyPlaces;

    RouteDetail(RouteSummary summary, List<RoutePoint> points, List<NearbyPlace> nearbyPlaces) {
        this.summary = summary;
        this.points = points;
        this.nearbyPlaces = nearbyPlaces;
    }

    public RouteSummary getSummary() {
        return summary;
    }

    public List<RoutePoint> getPoints() {
        return points;
    }

    public List<NearbyPlace> getNearbyPlaces() {
        return nearbyPlaces;
    }
}
