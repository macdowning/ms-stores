package stores.core.beans;

import java.io.Serializable;

public class StoresBean implements Serializable {
    private String imagePath;
    private String cta;
    private String position;
    private String delay;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCta() {
        return cta;
    }

    public void setCta(String cta) {
        this.cta = cta;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    @Override
    public String toString() {
        return "StoresBean{" +
                "imagePath='" + imagePath + '\'' +
                ", cta='" + cta + '\'' +
                ", delay='" + delay + '\'' +
                '}';
    }
}
