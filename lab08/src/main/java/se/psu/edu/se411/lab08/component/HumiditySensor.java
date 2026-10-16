package se.psu.edu.se411.lab08.component;

public class HumiditySensor extends Sensor {

    public HumiditySensor(String name) {
        super(name, "%");
    }

    @Override
    public String getType() {
        return "Humidity";
    }

    @Override
    public HumiditySensor clone() {
        return (HumiditySensor) super.clone();
    }
}
