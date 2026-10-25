package eps.service;

import eps.domain.ConfigurationException;
import eps.domain.Device;
import eps.domain.Display;
import eps.domain.InterfaceType;
import eps.domain.Mcu;
import eps.domain.McuType;
import eps.domain.Project;
import eps.persistence.ProjectStorage;

import java.io.IOException;
import java.util.ArrayList;

/** Executes the text commands typed by the user. */
public class Simulator {
    private static final String HELP = "create-mcu-esp32 | create-mcu-stm32\n"
            + "add <temp|baro|gps|display> <i2c|spi|uart>   (adds to the last created MCU)\n"
            + "read <device name>      e.g. read temp-1\n"
            + "display <text>\n"
            + "list | new <name> | save | load <name> | help | exit";

    private final DeviceFactory factory = new DeviceFactory();
    private final ProjectStorage storage = new ProjectStorage("projects");
    private Project project = new Project("untitled");
    private Mcu currentMcu;

    public String execute(String line) throws IOException {
        String[] parts = line.trim().split("\\s+");
        switch (parts[0].toLowerCase()) {
            case "create-mcu-esp32":
                return createMcu(McuType.ESP32);
            case "create-mcu-stm32":
                return createMcu(McuType.STM32);
            case "add":
                return addDevice(parts);
            case "read":
                if (parts.length != 2) {
                    throw new ConfigurationException("Usage: read <device name>");
                }
                Device device = project.findDevice(parts[1]);
                return device.getName() + ": " + device.simulate();
            case "display":
                return showOnDisplays(rest(line, "display <text>"));
            case "list":
                return list();
            case "new":
                project = new Project(rest(line, "new <name>"));
                currentMcu = null;
                return "Project '" + project.getName() + "' created";
            case "save":
                storage.save(project);
                return "Project '" + project.getName() + "' saved";
            case "load":
                project = storage.load(rest(line, "load <name>"));
                ArrayList<Mcu> mcus = project.getMcus();
                currentMcu = mcus.isEmpty() ? null : mcus.get(mcus.size() - 1);
                return "Project '" + project.getName() + "' loaded";
            case "help":
                return HELP;
            default:
                throw new ConfigurationException("Unknown command: " + parts[0] + " (type 'help')");
        }
    }

    private String createMcu(McuType type) {
        int number = project.countMcus(type) + 1;
        String key = type.name().toLowerCase();
        currentMcu = new Mcu(key + "-" + number, type);
        project.addMcu(currentMcu);
        return key + " (" + number + ") created";
    }

    private String addDevice(String[] parts) {
        if (parts.length != 3) {
            throw new ConfigurationException("Usage: add <temp|baro|gps|display> <i2c|spi|uart>");
        }
        if (currentMcu == null) {
            throw new ConfigurationException("Create an MCU first (create-mcu-esp32 / create-mcu-stm32)");
        }
        String key = parts[1].toLowerCase();
        int number = project.countDevices(key) + 1;
        Device device = factory.create(key, key + "-" + number);
        currentMcu.connect(device, InterfaceType.parse(parts[2]));
        return device.getName() + " added to " + currentMcu.getName() + " via " + device.getConnectedVia();
    }

    private String showOnDisplays(String text) {
        int count = 0;
        for (Mcu mcu : project.getMcus()) {
            for (Device device : mcu.getDevices()) {
                if (device instanceof Display) {
                    ((Display) device).setText(text);
                    count++;
                }
            }
        }
        if (count == 0) {
            throw new ConfigurationException("There is no display in the project");
        }
        return "Display: " + text;
    }

    private String list() {
        StringBuilder sb = new StringBuilder("Project: " + project.getName());
        for (Mcu mcu : project.getMcus()) {
            sb.append("\n").append(mcu.getName()).append(" (").append(mcu.getType().getFeatures()).append(")");
            for (Device device : mcu.getDevicesSorted()) {
                sb.append("\n  - ").append(device.getName()).append(" [").append(device.getConnectedVia()).append("]");
            }
        }
        return sb.toString();
    }

    private static String rest(String line, String usage) {
        String[] split = line.trim().split("\\s+", 2);
        if (split.length < 2) {
            throw new ConfigurationException("Usage: " + usage);
        }
        return split[1].replace("\"", "").trim();
    }
}