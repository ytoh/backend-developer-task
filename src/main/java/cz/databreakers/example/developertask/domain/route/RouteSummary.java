package cz.databreakers.example.developertask.domain.route;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author hvizdos
 */
@Entity @Table(name = "route_summary")
public class RouteSummary extends ResourceSupport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "data_id")
    private Long dataId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "start_latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "start_longitude")),
            @AttributeOverride(name = "elevation", column = @Column(name = "start_elevation")),
            @AttributeOverride(name = "time", column = @Column(name = "start_time"))
    })
    private RoutePoint start;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "end_latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "end_longitude")),
            @AttributeOverride(name = "elevation", column = @Column(name = "end_elevation")),
            @AttributeOverride(name = "time", column = @Column(name = "end_time"))
    })
    private RoutePoint end;

    @Column(nullable = false, name = "total_distance")
    private BigDecimal totalDistance;

    @Column(nullable = false, name = "start_end_distance")
    private BigDecimal startEndDistance;

    @Column(name = "average_speed")
    private BigDecimal averageSpeed;

    @Column(nullable = false, name = "upload_time")
    private ZonedDateTime uploadTime;

    RouteSummary() {
    }

    RouteSummary(Long dataId, RoutePoint start, RoutePoint end, BigDecimal totalDistance, BigDecimal startEndDistance, BigDecimal averageSpeed, ZonedDateTime uploadTime) {
        this.dataId = dataId;
        this.start = start;
        this.end = end;
        this.totalDistance = totalDistance;
        this.startEndDistance = startEndDistance;
        this.averageSpeed = averageSpeed;
        this.uploadTime = uploadTime;
    }

    public Long getRouteId() {
        return id;
    }

    public Long getDataId() {
        return dataId;
    }

    public RoutePoint getStart() {
        return start;
    }

    public RoutePoint getEnd() {
        return end;
    }

    public BigDecimal getTotalDistance() {
        return totalDistance;
    }

    public BigDecimal getStartEndDistance() {
        return startEndDistance;
    }

    public BigDecimal getAverageSpeed() {
        return averageSpeed;
    }

    public ZonedDateTime getUploadTime() {
        return uploadTime;
    }

    @Override
    public String toString() {
        return "RouteSummary{" +
                "id=" + id +
                ", dataId=" + dataId +
                ", start=" + start +
                ", end=" + end +
                ", totalDistance=" + totalDistance +
                ", startEndDistance=" + startEndDistance +
                ", averageSpeed=" + averageSpeed +
                ", uploadTime=" + uploadTime +
                '}';
    }
}
