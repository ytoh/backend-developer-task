package cz.databreakers.example.developertask.domain.nearby;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author hvizdos
 */
interface NearbyPlaceRepository extends CrudRepository<NearbyPlace, Long> {

    List<NearbyPlace> findAllByDataId(long dataId);
}
