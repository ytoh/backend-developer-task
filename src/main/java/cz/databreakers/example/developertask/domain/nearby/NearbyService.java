package cz.databreakers.example.developertask.domain.nearby;

import io.jenetics.jpx.WayPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Service for fetching, storing and retrieving nearby places
 *
 * @author hvizdos
 */
@Service
public class NearbyService {

    private static Logger logger = LoggerFactory.getLogger(NearbyService.class);

    private RestTemplate restTemplate;
    private String appId;
    private String appCode;
    private String hereApiBaseUrl;
    private NearbyPlaceRepository repository;

    @Autowired
    public NearbyService(RestTemplate restTemplate, @Value("${here-api.app-id}") String appId, @Value("${here-api.app-code}") String appCode, @Value("${here-api.base-url}") String hereApiBaseUrl, NearbyPlaceRepository repository) {
        this.restTemplate = restTemplate;
        this.appId = appId;
        this.appCode = appCode;
        this.hereApiBaseUrl = hereApiBaseUrl;
        this.repository = repository;
    }

    /**
     * @return all nearby places fetched from here.com
     */
    public List<NearbyPlace> fetchNearbyPlaces(WayPoint wayPoint, long dataId) {
        logger.debug("attempting to fetch nearby places");

        Map<String, String> parameters = Map.of("app_id", appId, "app_code", appCode, "location", String.format("%s,%s", wayPoint.getLatitude(), wayPoint.getLongitude()));

        ResponseEntity<HereNearbyPlaceResponse> response = restTemplate.getForEntity(hereApiBaseUrl + "/places/v1/discover/here?app_id={app_id}&app_code={app_code}&at={location}", HereNearbyPlaceResponse.class, parameters);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new HereException("failed to retrieve nearby places from here.com (status code: " + response.getStatusCode() + ")");
        }

        logger.debug("fetch {} nearby places", response.getBody().getResults().getItems().size());
        return response.getBody().getResults().getItems().stream().map(i -> new NearbyPlace(dataId, i.getDistance(), i.getTitle(), i.getCategory().getTitle())).collect(toList());
    }

    public void save(List<NearbyPlace> nearbyPlaces) {
        repository.saveAll(nearbyPlaces);
    }

    public List<NearbyPlace> getNearbyPlaces(long dataId) {
        return repository.findAllByDataId(dataId);
    }
}
