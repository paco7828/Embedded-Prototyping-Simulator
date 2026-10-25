package eps.domain;

import eps.config.Config;

public enum McuType {
    ESP32("Wi-Fi, BLE, I2C, SPI, UART", "esp32.max_devices", 4),
    STM32("High performance, I2C, SPI, UART", "stm32.max_devices", 6);

    private final String features;
    private final String maxDevicesKey;
    private final int defaultMaxDevices;

    McuType(String features, String maxDevicesKey, int defaultMaxDevices) {
        this.features = features;
        this.maxDevicesKey = maxDevicesKey;
        this.defaultMaxDevices = defaultMaxDevices;
    }

    public String getFeatures() {
        return features;
    }

    /** Read from config.properties, the default is used if the key is missing. */
    public int getMaxDevices() {
        return Config.getInt(maxDevicesKey, defaultMaxDevices);
    }
}