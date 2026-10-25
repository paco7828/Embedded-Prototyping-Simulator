package eps.domain;

public class Display extends Device {
    private String text = "";

    public Display(String name) {
        super(name);
    }

    public void setText(String text) {
        if (TextUtil.isBlank(text)) {
            throw new ConfigurationException("Display text must not be empty");
        }
        this.text = text;
    }

    @Override
    public String getTypeKey() {
        return "display";
    }

    @Override
    public InterfaceType[] getSupportedInterfaces() {
        return new InterfaceType[] {InterfaceType.I2C, InterfaceType.SPI};
    }

    @Override
    public String simulate() {
        return text.isEmpty() ? "display is empty" : "display shows: " + text;
    }
}