package eng.shishov.rainsensor.repositories;

import eng.shishov.rainsensor.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SensorsRepository extends JpaRepository<Sensor, Integer> {

    Optional<Sensor> findSensorByName(String sensorName);
}
