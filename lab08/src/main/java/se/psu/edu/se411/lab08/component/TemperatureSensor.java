package se.psu.edu.se411.lab08.component;

public class TemperatureSensor extends Sensor {

    public TemperatureSensor(String name) {
        super(name, "C");   // plain "C" so the Windows console shows it correctly
    }

    @Override
    public String getType() {
        return "Temperature";
    }

    @Override
    public TemperatureSensor clone() {
        return (TemperatureSensor) super.clone();
    }
}
