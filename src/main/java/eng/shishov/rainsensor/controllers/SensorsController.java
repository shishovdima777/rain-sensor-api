package eng.shishov.rainsensor.controllers;

import eng.shishov.rainsensor.dto.SensorDTO;
import eng.shishov.rainsensor.models.Sensor;
import eng.shishov.rainsensor.services.SensorsService;
import eng.shishov.rainsensor.util.SensorErrorResponse;
import eng.shishov.rainsensor.util.SensorNotCreatedException;
import eng.shishov.rainsensor.util.SensorsValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;
    private final SensorsValidator sensorsValidator;

    public SensorsController(SensorsService sensorsService, ModelMapper modelMapper, SensorsValidator sensorsValidator) {
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
        this.sensorsValidator = sensorsValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registrationSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                         BindingResult bindingResult) {

        sensorsValidator.validate(convertToSensor(sensorDTO), bindingResult);

        if(bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error: errors)
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append("; ");

            throw new SensorNotCreatedException(errorMsg.toString());
        }

        sensorsService.save(convertToSensor(sensorDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }
    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
