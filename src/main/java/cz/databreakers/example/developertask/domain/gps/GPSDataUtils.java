package cz.databreakers.example.developertask.domain.gps;

import io.jenetics.jpx.Length;
import io.jenetics.jpx.Track;
import io.jenetics.jpx.TrackSegment;
import io.jenetics.jpx.WayPoint;
import io.jenetics.jpx.geom.Geoid;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Utility functions for working with GPS data
 *
 * @author hvizdos
 */
public class GPSDataUtils {

    public static WayPoint getFirstWayPoint(GPSData gpsData) {
        return wayPoints(gpsData)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("no waypoints in GPS data"));
    }

    public static WayPoint getLastWayPoint(GPSData gpsData) {
        return wayPoints(gpsData)
                .reduce((first, second) -> second)
                .orElseThrow(() -> new IllegalArgumentException("no waypoints in GPS data"));
    }

    public static Stream<WayPoint> wayPoints(GPSData gpsData) {
        return gpsData.getParsed().tracks()
                .flatMap(Track::segments)
                .findFirst()
                .map(TrackSegment::points).orElse(Stream.empty());
    }

    public static BigDecimal calculateAverageSpeed(GPSData gpsData) {
        BigDecimal meters = calculateTotalDistance(gpsData);
        Optional<ZonedDateTime> startTime = getFirstWayPoint(gpsData).getTime();
        Optional<ZonedDateTime> endTime = getLastWayPoint(gpsData).getTime();

        if (startTime.isEmpty() || endTime.isEmpty()) {
            return null;
        }

        BigDecimal seconds = new BigDecimal(Duration.between(startTime.get(), endTime.get()).getSeconds());
        return meters.divide(seconds, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal calculateDistanceBetweenStartAndEnd(GPSData gpsData) {
        Length length = Geoid.WGS84.distance(getFirstWayPoint(gpsData), getLastWayPoint(gpsData));
        return new BigDecimal(length.doubleValue());
    }

    public static BigDecimal calculateTotalDistance(GPSData gpsData) {
        Length length = wayPoints(gpsData).collect(Geoid.WGS84.toPathLength());
        return new BigDecimal(length.doubleValue());
    }
}
