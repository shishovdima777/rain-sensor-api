package eng.shishov.rainsensor.services;

import eng.shishov.rainsensor.dto.SensorDTO;
import eng.shishov.rainsensor.models.Sensor;
import eng.shishov.rainsensor.repositories.SensorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {
    private final SensorsRepository sensorsRepository;
    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }
    public Optional<Sensor> findSensorByName(Sensor sensor) {
         return sensorsRepository.findSensorByName(sensor.getName());
    }
    @Transactional
    public void save(Sensor sensor) {
        sensor.setCreatedAt(LocalDateTime.now());
        sensorsRepository.save(sensor);
    }

}
