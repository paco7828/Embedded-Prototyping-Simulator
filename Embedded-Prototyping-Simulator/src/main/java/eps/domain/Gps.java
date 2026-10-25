package eps.domain;

import eps.config.Config;

public class Gps extends Device {
    public Gps(String name) {
        super(name);
    }

    @Override
    public String getTypeKey() {
        return "gps";
    }

    @Override
    public InterfaceType[] getSupportedInterfaces() {
        return new InterfaceType[] {InterfaceType.UART};
    }

    @Override
    public String simulate() {
        double minLat = Config.getDouble("gps.latitude.min", 47.49);
        double maxLat = Config.getDouble("gps.latitude.max", 47.50);
        double minLon = Config.getDouble("gps.longitude.min", 19.04);
        double maxLon = Config.getDouble("gps.longitude.max", 19.05);

        double latitude = round(randomBetween(minLat, maxLat), 5);
        double longitude = round(randomBetween(minLon, maxLon), 5);
        return "lat = " + latitude + ", lon = " + longitude;
    }
}