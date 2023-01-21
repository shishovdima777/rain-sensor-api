package eng.shishov.rainsensor.util;

public class MeasurementNotPerformedException extends RuntimeException{
    public MeasurementNotPerformedException(String message) {
        super(message);
    }
}
