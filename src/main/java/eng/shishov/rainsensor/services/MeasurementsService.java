package eng.shishov.rainsensor.services;

import eng.shishov.rainsensor.dto.MeasurementDTO;
import eng.shishov.rainsensor.models.Measurement;
import eng.shishov.rainsensor.repositories.MeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorsService sensorsService;
    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsService sensorsService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsService = sensorsService;
    }
    public List<Measurement> findAll() {
        return measurementsRepository.findAll();
    }

    public Long findRainyDaysQty() {
        return measurementsRepository.countByRainingIsTrue();
    }

    @Transactional
    public void save(Measurement measurement) {
        enrichMeasurement(measurement);
        measurementsRepository.save(measurement);
    }

    private void enrichMeasurement(Measurement measurement) {
        measurement.setSensor(sensorsService.findSensorByName(measurement.getSensor()).get());
        measurement.setMeasuredAt(LocalDateTime.now());
    }
}
