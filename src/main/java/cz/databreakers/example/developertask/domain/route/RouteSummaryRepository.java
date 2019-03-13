package cz.databreakers.example.developertask.domain.route;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * @author hvizdos
 */
interface RouteSummaryRepository extends CrudRepository<RouteSummary, Long> {

    Page<RouteSummary> findAll(Pageable pageable);
}
