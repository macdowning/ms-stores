package stores.core.beans;

import java.io.Serializable;
import java.util.List;

public class StoresGridResponseBean implements Serializable {

    private String itemsPerSlide;
    private String carouselTitle;
    private String delay;
    private String autoplay;
    private List<StoresBean> gridData;

    public String getItemsPerSlide() {
        return itemsPerSlide;
    }

    public void setItemsPerSlide(String itemsPerSlide) {
        this.itemsPerSlide = itemsPerSlide;
    }

    public String getCarouselTitle() {
        return carouselTitle;
    }

    public void setCarouselTitle(String carouselTitle) {
        this.carouselTitle = carouselTitle;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getAutoplay() {
        return autoplay;
    }

    public void setAutoplay(String autoplay) {
        this.autoplay = autoplay;
    }

    public List<StoresBean> getGridData() {
        return gridData;
    }

    public void setGridData(List<StoresBean> gridData) {
        this.gridData = gridData;
    }
}
