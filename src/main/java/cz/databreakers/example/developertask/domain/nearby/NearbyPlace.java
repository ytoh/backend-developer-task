package cz.databreakers.example.developertask.domain.nearby;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author hvizdos
 */
@Entity @Table(name = "nearby_place")
public class NearbyPlace implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "data_id")
    private Long dataId;

    @Column(nullable = false, name = "distance")
    private BigDecimal distance;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "category")
    private String category;

    NearbyPlace() {
    }

    NearbyPlace(Long dataId, BigDecimal distance, String title, String category) {
        this.dataId = dataId;
        this.distance = distance;
        this.title = title;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public Long getDataId() {
        return dataId;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "NearbyPlace{" +
                "id=" + id +
                ", dataId=" + dataId +
                ", distance=" + distance +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
