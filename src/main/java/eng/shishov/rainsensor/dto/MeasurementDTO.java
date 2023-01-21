package eng.shishov.rainsensor.dto;

import eng.shishov.rainsensor.models.Sensor;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class MeasurementDTO {
    @NotNull
    @Min(value = -100, message = "value should not be less than -100")
    @Max(value = 100, message = "value should not be greater than 100")
    private Double value;
    @NotNull
    private Boolean raining;
    @NotNull
    private SensorDTO sensor;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean isRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    public Boolean getRaining() {
        return raining;
    }

}
