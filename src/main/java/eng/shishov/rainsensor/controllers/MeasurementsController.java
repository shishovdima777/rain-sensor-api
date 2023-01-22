package eng.shishov.rainsensor.controllers;

import eng.shishov.rainsensor.dto.MeasurementDTO;
import eng.shishov.rainsensor.dto.MeasurementsList;
import eng.shishov.rainsensor.models.Measurement;
import eng.shishov.rainsensor.services.MeasurementsService;
import eng.shishov.rainsensor.util.MeasurementInsertionErrorResponse;
import eng.shishov.rainsensor.util.MeasurementNotPerformedException;
import eng.shishov.rainsensor.util.MeasurementValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final ModelMapper modelMapper;
    private final MeasurementsService measurementsService;
    private final MeasurementValidator measurementValidator;
    @Autowired
    public MeasurementsController(ModelMapper modelMapper, MeasurementsService measurementsService, MeasurementValidator measurementValidator) {
        this.modelMapper = modelMapper;
        this.measurementsService = measurementsService;
        this.measurementValidator = measurementValidator;
    }
    @GetMapping()
    public MeasurementsList showAll() {
        return new MeasurementsList(measurementsService.findAll().stream().map(this::convertToMeasurementDTO).toList());
    }
    @GetMapping("/rainyDaysCount")
    public Long rainyDaysCount() {
        return measurementsService.findRainyDaysQty();
    }


    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult) {

        measurementValidator.validate(convertToMeasurement(measurementDTO), bindingResult);


        if(bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            StringBuilder msg = new StringBuilder();

            for (FieldError error: errors)
                msg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append("; ");


            throw new MeasurementNotPerformedException(msg.toString());

        }

        measurementsService.save(convertToMeasurement(measurementDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }
    @ExceptionHandler
    private ResponseEntity<MeasurementInsertionErrorResponse> handleException(MeasurementNotPerformedException e) {
        MeasurementInsertionErrorResponse response = new MeasurementInsertionErrorResponse(
              e.getMessage(),
              System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

}
