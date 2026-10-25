package eps.ui;

import eps.domain.ConfigurationException;
import eps.service.Simulator;

import java.io.IOException;
import java.util.Scanner;
import eps.config.Config;

public class Main {
    public static void main(String[] args) {
        Config.load();
        Simulator simulator = new Simulator();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Embedded Prototyping Simulator - type 'help' or 'exit'");
        while (true) {
            System.out.print("> ");
            if (!scanner.hasNextLine()) {
                break;
            }
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            if (line.equalsIgnoreCase("exit")) {
                break;
            }
            try {
                System.out.println(simulator.execute(line));
            } catch (ConfigurationException | IOException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }
}