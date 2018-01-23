package sample;

/**
 * Created by Anton on 17.01.2018.
 */
public class Material {
    public Material(double valueKs, String material) {
        this.valueKs = valueKs;
        this.material = material;
    }

    public double getValueKs() {
        return valueKs;
    }

    public void setValueKs(double valueKs) {
        this.valueKs = valueKs;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    private double valueKs;
    private String material;
}
