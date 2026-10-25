package eps.domain;

import eps.config.Config;

public class Barometer extends Device {
    public Barometer(String name) {
        super(name);
    }

    @Override
    public String getTypeKey() {
        return "baro";
    }

    @Override
    public InterfaceType[] getSupportedInterfaces() {
        return new InterfaceType[] {InterfaceType.I2C, InterfaceType.SPI};
    }

    @Override
    public String simulate() {
        double minPressure = Config.getDouble("pressure.min", 1000.0);
        double maxPressure = Config.getDouble("pressure.max", 1025.0);

        double pressure = round(randomBetween(minPressure, maxPressure), 1);
        return "pressure = " + pressure + " hPa";
    }
}