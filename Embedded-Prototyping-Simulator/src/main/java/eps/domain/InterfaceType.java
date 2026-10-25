package eps.domain;

public enum InterfaceType {
    I2C, SPI, UART;

    public static InterfaceType parse(String text) {
        try {
            return valueOf(text.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ConfigurationException("Unknown interface: " + text + " (I2C, SPI, UART)");
        }
    }
}