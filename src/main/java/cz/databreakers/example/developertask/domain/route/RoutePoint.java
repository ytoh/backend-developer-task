package cz.databreakers.example.developertask.domain.route;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author hvizdos
 */
@Embeddable
public class RoutePoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, name = "latitude")
    private BigDecimal latitude;

    @Column(nullable = false, name = "longitude")
    private BigDecimal longitude;

    @Column(name = "elevation")
    private BigDecimal elevation;

    @Column(name = "time")
    private ZonedDateTime time;

    RoutePoint() {
    }

    RoutePoint(BigDecimal latitude, BigDecimal longitude, BigDecimal elevation, ZonedDateTime time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.time = time;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public BigDecimal getElevation() {
        return elevation;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "RoutePoint{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", elevation=" + elevation +
                ", time=" + time +
                '}';
    }
}
