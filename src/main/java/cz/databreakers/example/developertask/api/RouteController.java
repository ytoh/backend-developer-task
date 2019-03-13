package cz.databreakers.example.developertask.api;

import cz.databreakers.example.developertask.domain.route.RouteDetail;
import cz.databreakers.example.developertask.domain.route.RouteSummary;
import cz.databreakers.example.developertask.domain.route.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

/**
 * @author hvizdos
 */
@RestController
@RequestMapping("/routes")
public class RouteController {

    private RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public Page<RouteSummary> getRoutes(Pageable page) {
        Page<RouteSummary> routeSummaries = routeService.getRouteSummaries(page);
        routeSummaries.forEach(r -> r.add(linkTo(RouteController.class).slash(r.getRouteId()).withSelfRel()));
        return routeSummaries;
    }

    @GetMapping(path = "/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public ResponseEntity<RouteDetail> getRoute(@PathVariable Long id) {
        return routeService.getRouteDetail(id).map(r -> {
            r.add(linkTo(RouteController.class).slash(r.getSummary().getRouteId()).withSelfRel());
            return ResponseEntity.ok(r);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = APPLICATION_XML_VALUE)
    public ResponseEntity<?> addRoute(@RequestBody String body) {
        long id = routeService.storeRoute(body);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();

        return ResponseEntity.status(CREATED).location(location).build();
    }
}
