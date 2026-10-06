# Embedded Prototyping Simulator

The **Embedded Prototyping Simulator** is a console-based Java application designed to simulate the process of building and testing a small embedded system.

The user can create a virtual project, select a microcontroller such as an **ESP32** or **STM32**, and add different sensors and peripherals to it. Devices can be connected through interfaces such as **I2C, SPI, or UART**, depending on their capabilities.

The application can simulate basic operations of the connected components. For example, the user can request a measurement from a sensor and receive simulated sensor data.

The system also validates device compatibility and reports configuration errors when an invalid connection or configuration is attempted.

Projects and their configurations can be saved and loaded so that the user can continue working on them later.

The main goal of the project is to provide a simple environment for experimenting with **embedded-system concepts without requiring physical hardware**.

---

## Microcontrollers

| MCU       | Command            | Features                                             |
| --------- | ------------------ | ---------------------------------------------------- |
| **ESP32** | `create-mcu-esp32` | Wi-Fi, Bluetooth/BLE, I2C, SPI, UART, GPIO, ADC, PWM |
| **STM32** | `create-mcu-stm32` | High performance, I2C, SPI, UART, GPIO, ADC, PWM     |

Example output:

```text
esp32 (n) created
stm32 (n) created
```

Where `n` is the number of the given MCU type currently created.

---

# Sensors

| Sensor                     | Measurements / Output                                          |
| -------------------------- | -------------------------------------------------------------- |
| **Temperature + Humidity** | Temperature (°C), relative humidity (%)                        |
| **Barometer**              | Atmospheric pressure (hPa)                                     |
| **9DOF IMU**               | Accelerometer (X/Y/Z), gyroscope (X/Y/Z), magnetometer (X/Y/Z) |
| **Light Sensor**           | Visible light, infrared, UV                                    |
| **Distance Sensor**        | Distance (mm)                                                  |
| **Motion Sensor**          | `0` = no motion, `1` = motion detected                         |
| **Hall Effect Sensor**     | `0` = no magnetic field, `1` = magnetic field detected         |
| **Gas + CO₂ Sensor**       | Gas concentration (analog), CO₂ concentration (ppm)            |
| **Soil Moisture Sensor**   | Soil moisture (%) — analog                                     |
| **Rain Sensor**            | `0` = no water, `1` = water detected                           |
| **Water Level Sensor**     | Water level (0–100%)                                           |
| **GPS**                    | Latitude, longitude, speed, time                               |

---

# Peripherals

| Peripheral  | Function                            |
| ----------- | ----------------------------------- |
| **Display** | Text output                         |
| **RGB LED** | Visual indicator, RGB color control |
| **Button**  | Digital input (`0` / `1`)           |
| **Buzzer**  | Sound output                        |
| **SD Card** | Read and write data                 |
| **RTC**     | Read and store time information     |

---

# Communication Interfaces

| Interface | Purpose                             |
| --------- | ----------------------------------- |
| **GPIO**  | Digital input/output                |
| **ADC**   | Analog input                        |
| **PWM**   | Output/control signals              |
| **I2C**   | Sensor and peripheral communication |
| **SPI**   | High-speed peripheral communication |
| **UART**  | Serial communication                |

---

# Core Features

* Virtual MCU creation
* Virtual sensor creation
* Virtual peripheral creation
* Device-to-MCU connections
* Interface compatibility checking
* Simulated sensor measurements
* Digital input/output simulation
* Analog input simulation
* PWM simulation
* I2C / SPI / UART communication
* Project saving
* Project loading
* Configuration error reporting

---

# Example Project

```text
Project: Weather Station

ESP32
├── Temperature + Humidity Sensor
├── Barometer
├── Light Sensor
├── GPS
├── Display
├── RGB LED
├── Buzzer
└── SD Card
```

Example commands:

```text
read temperature
read humidity
read pressure
read light
read gps
display "System OK"
set rgb 0 255 0
write sd "measurement.txt"
```
