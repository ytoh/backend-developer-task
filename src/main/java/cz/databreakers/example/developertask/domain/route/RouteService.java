package cz.databreakers.example.developertask.domain.route;

import cz.databreakers.example.developertask.domain.gps.GPSData;
import cz.databreakers.example.developertask.domain.gps.GPSDataService;
import cz.databreakers.example.developertask.domain.nearby.NearbyPlace;
import cz.databreakers.example.developertask.domain.nearby.NearbyService;
import io.jenetics.jpx.WayPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static cz.databreakers.example.developertask.domain.gps.GPSDataUtils.*;
import static java.util.stream.Collectors.toList;

/**
 * A facade for storing and retrieving information about routes.
 *
 * @author hvizdos
 */
@Service
@Transactional
public class RouteService {

    private static Logger logger = LoggerFactory.getLogger(RouteService.class);

    private GPSDataService gpsDataService;
    private NearbyService nearbyService;
    private RouteSummaryRepository routeSummaryRepository;

    @Autowired
    public RouteService(GPSDataService gpsDataService, NearbyService nearbyService, RouteSummaryRepository routeSummaryRepository) {
        this.gpsDataService = gpsDataService;
        this.nearbyService = nearbyService;
        this.routeSummaryRepository = routeSummaryRepository;
    }

    /**
     * @return route ID
     */
    public long storeRoute(String rawGPXData) {
        logger.debug("attempting to store new route");
        GPSData gpsData = gpsDataService.storeGPSData(rawGPXData);
        RouteSummary summary = calculateSummary(gpsData);
        routeSummaryRepository.save(summary);
        nearbyService.save(nearbyService.fetchNearbyPlaces(getLastWayPoint(gpsData), gpsData.getId()));
        logger.info("new route [{}] stored", summary);
        return gpsData.getId();
    }

    /**
     * @return page of route summaries
     */
    public Page<RouteSummary> getRouteSummaries(Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException("'pageable' missing");
        }

        return routeSummaryRepository.findAll(pageable);
    }

    /**
     * @return detail for a route
     */
    public Optional<RouteDetail> getRouteDetail(Long routeId) {
        if (routeId == null) {
            throw new IllegalArgumentException("'routeId' missing");
        }

        return gpsDataService.loadGPSData(routeId).map(data -> {
            RouteSummary summary = routeSummaryRepository.findById(routeId).orElseThrow();
            List<RoutePoint> points = getRoutePoints(data);
            List<NearbyPlace> nearbyPlaces = nearbyService.getNearbyPlaces(data.getId());
            return new RouteDetail(summary, points, nearbyPlaces);
        });
    }

    private List<RoutePoint> getRoutePoints(GPSData gpsData) {
        return wayPoints(gpsData).map(this::toRoutePoint).collect(toList());
    }

    private RoutePoint toRoutePoint(WayPoint wayPoint) {
        BigDecimal latitude  = new BigDecimal(wayPoint.getLatitude().doubleValue());
        BigDecimal longitude = new BigDecimal(wayPoint.getLongitude().doubleValue());
        BigDecimal elevation = wayPoint.getElevation().map(e -> new BigDecimal(e.doubleValue())).orElse(null);
        ZonedDateTime time   = wayPoint.getTime().orElse(null);

        return new RoutePoint(latitude, longitude, elevation, time);
    }

    private RouteSummary calculateSummary(GPSData gpsData) {
        RoutePoint firstPoint = toRoutePoint(getFirstWayPoint(gpsData));
        RoutePoint lastPoint = toRoutePoint(getLastWayPoint(gpsData));
        BigDecimal totalDistance = calculateTotalDistance(gpsData);
        BigDecimal startEndDistance = calculateDistanceBetweenStartAndEnd(gpsData);
        BigDecimal averageSpeed = calculateAverageSpeed(gpsData);

        return new RouteSummary(gpsData.getId(), firstPoint, lastPoint, totalDistance, startEndDistance, averageSpeed, gpsData.getUploadTime());
    }
}
