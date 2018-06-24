package tigerstyle.social.com.banggiaxe.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by quydv on 2/1/18.
 */

public class ListOldCar implements Serializable{

    private String carId;

    private String carName;

    private String carType;

    private String carBrand;

    private String shareUrl;

    private List<OldCarObject> listVersions;

    public ListOldCar() {
    }

    public ListOldCar(String carId, String carName, String carType,
                      String carBrand, String shareUrl, List<OldCarObject> listVersions) {
        this.carId = carId;
        this.carName = carName;
        this.carType = carType;
        this.carBrand = carBrand;
        this.shareUrl = shareUrl;
        this.listVersions = listVersions;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public List<OldCarObject> getListVersions() {
        return listVersions;
    }

    public void setListVersions(List<OldCarObject> listVersions) {
        this.listVersions = listVersions;
    }
}
