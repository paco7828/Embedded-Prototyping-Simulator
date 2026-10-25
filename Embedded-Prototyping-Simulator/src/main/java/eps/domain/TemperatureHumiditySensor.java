package eps.domain;

import eps.config.Config;

public class TemperatureHumiditySensor extends Device {
    public TemperatureHumiditySensor(String name) {
        super(name);
    }

    @Override
    public String getTypeKey() {
        return "temp";
    }

    @Override
    public InterfaceType[] getSupportedInterfaces() {
        return new InterfaceType[] {InterfaceType.I2C};
    }

    @Override
    public String simulate() {
        double minTemp = Config.getDouble("temperature.min", 18.0);
        double maxTemp = Config.getDouble("temperature.max", 28.0);
        double minHum = Config.getDouble("humidity.min", 35.0);
        double maxHum = Config.getDouble("humidity.max", 65.0);

        double temperature = round(randomBetween(minTemp, maxTemp), 1);
        double humidity = round(randomBetween(minHum, maxHum), 1);
        return "temperature = " + temperature + " C, humidity = " + humidity + " %";
    }
}