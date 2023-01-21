package eng.shishov.rainsensor.util;


import eng.shishov.rainsensor.models.Sensor;
import eng.shishov.rainsensor.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Component
public class SensorsValidator implements Validator {
    private final SensorsService sensorsService;
    @Autowired
    public SensorsValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;
        if(sensorsService.findSensorByName(sensor).isPresent()) {
            errors.rejectValue("name", "", "Sensor with this name already exists");
            throw new SensorNotCreatedException(errors.getFieldError().getDefaultMessage());
        }
    }
    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
