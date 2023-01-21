package eng.shishov.rainsensor.repositories;

import eng.shishov.rainsensor.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {
    Long countByRainingIsTrue();
}
