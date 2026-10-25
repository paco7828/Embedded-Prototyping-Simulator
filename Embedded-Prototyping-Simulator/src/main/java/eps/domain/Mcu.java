package eps.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Mcu {
    private final String name;
    private final McuType type;
    private final ArrayList<Device> devices = new ArrayList<>();

    public Mcu(String name, McuType type) {
        this.name = name;
        this.type = type;
    }

    /** Checks the rules, then connects the device through the given interface. */
    public void connect(Device device, InterfaceType interfaceType) {
        if (!device.supports(interfaceType)) {
            throw new ConfigurationException(device.getName() + " cannot use " + interfaceType
                    + " (supported: " + Arrays.toString(device.getSupportedInterfaces()) + ")");
        }
        if (devices.size() >= type.getMaxDevices()) {
            throw new ConfigurationException(name + " is full (max " + type.getMaxDevices() + " devices)");
        }
        device.setConnectedVia(interfaceType);
        devices.add(device);
    }

    /** Devices sorted by name (see Device.compareTo). */
    public ArrayList<Device> getDevicesSorted() {
        ArrayList<Device> copy = new ArrayList<>(devices);
        Collections.sort(copy);
        return copy;
    }

    public ArrayList<Device> getDevices() {
        return new ArrayList<>(devices);
    }

    public String getName() {
        return name;
    }

    public McuType getType() {
        return type;
    }
}