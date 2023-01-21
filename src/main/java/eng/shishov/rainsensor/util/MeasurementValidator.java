package eng.shishov.rainsensor.util;

import eng.shishov.rainsensor.models.Measurement;
import eng.shishov.rainsensor.models.Sensor;
import eng.shishov.rainsensor.services.MeasurementsService;
import eng.shishov.rainsensor.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
public class MeasurementValidator implements Validator {
    private final MeasurementsService measurementsService;
    private final SensorsService sensorsService;
    @Autowired
    public MeasurementValidator(MeasurementsService measurementsService, SensorsService sensorsService) {
        this.measurementsService = measurementsService;
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;

        Sensor sensor = measurement.getSensor();

        System.out.println(sensor.getName());

        if(sensorsService.findSensorByName(sensor).isEmpty()) {
            errors.rejectValue("sensor", "", "Sensor with this name does not exists in the Table: `Sensor`");

            throw new MeasurementNotPerformedException(errors.getFieldError().getDefaultMessage());
        }

        return;

    }

    @ExceptionHandler
    private ResponseEntity<MeasurementInsertionErrorResponse> handleException(MeasurementNotPerformedException e) {
        MeasurementInsertionErrorResponse response = new MeasurementInsertionErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
