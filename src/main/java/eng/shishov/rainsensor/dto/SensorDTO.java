package eng.shishov.rainsensor.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SensorDTO {
    @NotEmpty(message = "This field should not be empty")
    @Size(min = 3, max = 30, message = "Sensor name must be between 3 and 30 symbols length")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
