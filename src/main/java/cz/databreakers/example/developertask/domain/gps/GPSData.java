package cz.databreakers.example.developertask.domain.gps;

import io.jenetics.jpx.GPX;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * RAW GPS data in GPX format
 *
 * @author hvizdos
 */
@Entity @Table(name = "gps_data")
public class GPSData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "upload_time")
    private ZonedDateTime uploadTime;

    @Column(nullable = false, name = "data")
    private String data;

    @Transient
    private GPX parsed;

    GPSData() {
    }

    GPSData(ZonedDateTime uploadTime, String data) {
        this.uploadTime = uploadTime;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public GPX getParsed() {
        return parsed;
    }

    void setParsed(GPX parsed) {
        this.parsed = parsed;
    }

    public ZonedDateTime getUploadTime() {
        return uploadTime;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "GPSData{" +
                "id=" + id +
                ", uploadTime=" + uploadTime +
                '}';
    }
}
