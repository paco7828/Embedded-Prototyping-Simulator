package eps.domain;

import java.util.ArrayList;

public class Project {
    private final String name;
    private final ArrayList<Mcu> mcus = new ArrayList<>();

    public Project(String name) {
        if (TextUtil.isBlank(name)) {
            throw new ConfigurationException("Project name must not be empty");
        }
        this.name = name.trim();
    }

    public String getName() {
        return name;
    }

    public void addMcu(Mcu mcu) {
        mcus.add(mcu);
    }

    public ArrayList<Mcu> getMcus() {
        return new ArrayList<>(mcus);
    }

    public int countMcus(McuType type) {
        int count = 0;
        for (Mcu mcu : mcus) {
            if (mcu.getType() == type) {
                count++;
            }
        }
        return count;
    }

    public int countDevices(String typeKey) {
        int count = 0;
        for (Mcu mcu : mcus) {
            for (Device device : mcu.getDevices()) {
                if (device.getTypeKey().equals(typeKey)) {
                    count++;
                }
            }
        }
        return count;
    }

    public Mcu findMcu(String name) {
        for (Mcu mcu : mcus) {
            if (mcu.getName().equalsIgnoreCase(name)) {
                return mcu;
            }
        }
        throw new ConfigurationException("No such MCU: " + name);
    }

    public Device findDevice(String name) {
        for (Mcu mcu : mcus) {
            for (Device device : mcu.getDevices()) {
                if (device.getName().equalsIgnoreCase(name)) {
                    return device;
                }
            }
        }
        throw new ConfigurationException("No such device: " + name);
    }
}