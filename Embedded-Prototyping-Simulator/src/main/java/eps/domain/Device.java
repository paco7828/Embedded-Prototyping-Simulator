package eps.domain;

import java.util.Random;

/** Common parent of every sensor and peripheral. */
public abstract class Device implements Comparable<Device> {
    private static final Random random = new Random();

    private final String name;
    private InterfaceType connectedVia;

    protected Device(String name) {
        if (TextUtil.isBlank(name)) {
            throw new ConfigurationException("Device name must not be empty");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public InterfaceType getConnectedVia() {
        return connectedVia;
    }

    public void setConnectedVia(InterfaceType connectedVia) {
        this.connectedVia = connectedVia;
    }

    /** Short key used in commands and in the save file, e.g. "temp". */
    public abstract String getTypeKey();

    public abstract InterfaceType[] getSupportedInterfaces();

    /** Simulated behaviour: a sensor returns a measurement, a display shows its text... */
    public abstract String simulate();

    public boolean supports(InterfaceType interfaceType) {
        for (InterfaceType supported : getSupportedInterfaces()) {
            if (supported == interfaceType) {
                return true;
            }
        }
        return false;
    }

    /** Devices are ordered by name (used when sorting). */
    @Override
    public int compareTo(Device other) {
        return name.compareToIgnoreCase(other.name);
    }

    /** Random number from min (included) up to max (not included). */
    protected static double randomBetween(double min, double max) {
        return min + random.nextDouble() * (max - min);
    }

    /** Rounds to the given number of decimals, e.g. round(23.8472, 1) = 23.8 */
    protected static double round(double value, int decimals) {
        double factor = Math.pow(10, decimals);
        return Math.round(value * factor) / factor;
    }
}