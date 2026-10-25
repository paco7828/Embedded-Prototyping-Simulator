package eps.service;

import eps.domain.Barometer;
import eps.domain.ConfigurationException;
import eps.domain.Device;
import eps.domain.Display;
import eps.domain.Gps;
import eps.domain.TemperatureHumiditySensor;

/** Factory pattern: creates the right Device subclass from a short key. */
public class DeviceFactory {
    public Device create(String key, String name) {
        return switch (key.toLowerCase()) {
            case "temp" -> new TemperatureHumiditySensor(name);
            case "baro" -> new Barometer(name);
            case "gps" -> new Gps(name);
            case "display" -> new Display(name);
            default -> throw new ConfigurationException("Unknown device type: " + key + " (temp, baro, gps, display)");
        };
    }
}