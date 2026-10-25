package eps.domain;

/** Thrown when the user tries something that breaks a rule of the simulator. */
public class ConfigurationException extends RuntimeException {
    public ConfigurationException(String message) {
        super(message);
    }
}