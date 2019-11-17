package pl.xcrafters.redisconfig.api.values;

public class ConfigColor {

    private String color;

    public ConfigColor(String color) {
        this.color = color;
    }

    public String getColor() { return this.color; }

    @Override
    public String toString() { return this.color; }

    @Override
    public boolean equals(Object o) {
        if(o == null || o.getClass() != getClass()) {
            return false;
        }

        ConfigColor color = (ConfigColor) o;
        return this.toString().equals(color.toString());
    }

}
