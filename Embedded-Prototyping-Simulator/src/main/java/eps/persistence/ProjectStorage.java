package eps.persistence;

import eps.domain.Device;
import eps.domain.InterfaceType;
import eps.domain.Mcu;
import eps.domain.McuType;
import eps.domain.Project;
import eps.service.DeviceFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Saves a project as a simple text file, one line per item:
 * PROJECT;name / MCU;type;name / DEVICE;mcuName;typeKey;deviceName;interface
 */
public class ProjectStorage {
    private final Path directory;
    private final DeviceFactory factory = new DeviceFactory();

    public ProjectStorage(String directory) {
        this.directory = Path.of(directory);
    }

    public void save(Project project) throws IOException {
        Files.createDirectories(directory);
        try (BufferedWriter writer = Files.newBufferedWriter(fileFor(project.getName()))) {
            writer.write("PROJECT;" + project.getName());
            writer.newLine();
            for (Mcu mcu : project.getMcus()) {
                writer.write("MCU;" + mcu.getType() + ";" + mcu.getName());
                writer.newLine();
                for (Device device : mcu.getDevices()) {
                    writer.write("DEVICE;" + mcu.getName() + ";" + device.getTypeKey() + ";"
                            + device.getName() + ";" + device.getConnectedVia());
                    writer.newLine();
                }
            }
        }
    }

    public Project load(String name) throws IOException {
        Path file = fileFor(name);
        if (!Files.exists(file)) {
            throw new IOException("No saved project named '" + name + "'");
        }
        Project project = null;
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] f = line.split(";");
                if (f[0].equals("PROJECT")) {
                    project = new Project(f[1]);
                } else if (project == null) {
                    throw new IOException("Invalid file: PROJECT line is missing");
                } else if (f[0].equals("MCU")) {
                    project.addMcu(new Mcu(f[2], McuType.valueOf(f[1])));
                } else if (f[0].equals("DEVICE")) {
                    Mcu mcu = project.findMcu(f[1]);
                    mcu.connect(factory.create(f[2], f[3]), InterfaceType.parse(f[4]));
                }
            }
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            throw new IOException("Corrupt project file: " + file.getFileName());
        }
        if (project == null) {
            throw new IOException("Project file is empty");
        }
        return project;
    }

    private Path fileFor(String name) {
        return directory.resolve(name.trim().replaceAll("[^A-Za-z0-9_-]", "_") + ".txt");
    }
}