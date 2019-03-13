package cz.databreakers.example.developertask.domain.gps;

import io.jenetics.jpx.GPX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.time.ZonedDateTime;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.ZoneOffset.UTC;

/**
 * Service for parsing and storing GPS data in GPX format
 *
 * @author hvizdos
 */
@Service
public class GPSDataService {

    private static Logger logger = LoggerFactory.getLogger(GPSDataService.class);

    private GPSDataRepository gpsDataRepository;

    @Autowired
    public GPSDataService(GPSDataRepository gpsDataRepository) {
        this.gpsDataRepository = gpsDataRepository;
    }

    /**
     * Parses, validates and stores raw GPS data
     *
     * @return stored GPS data
     */
    public GPSData storeGPSData(String rawGPSData) {
        logger.debug("attempting to store raw GPS data");

        if (rawGPSData == null) {
            throw new IllegalArgumentException("'rawGPXData' missing");
        }

        GPSData gpsData = storeGPXData(parseGPX(rawGPSData), rawGPSData);
        logger.debug("stored raw GPS data {}", gpsData.getId());
        return gpsData;
    }

    /**
     * @return stored GPS data
     */
    public Optional<GPSData> loadGPSData(long id) {
        return gpsDataRepository.findById(id).map(data -> {
            data.setParsed(parseGPX(data.getData()));
            return data;
        });
    }

    private GPSData storeGPXData(GPX gpx, String rawGPXData) {
        GPSData data = new GPSData(ZonedDateTime.now(UTC), rawGPXData);
        data.setParsed(gpx);
        return gpsDataRepository.save(data);
    }

    private GPX parseGPX(String rawGPXData) {
        try {
           return GPX.read(new ByteArrayInputStream(rawGPXData.getBytes(UTF_8)));
        } catch (Exception e) {
            throw new IllegalArgumentException("'rawGPXData' invalid", e);
        }
    }
}
