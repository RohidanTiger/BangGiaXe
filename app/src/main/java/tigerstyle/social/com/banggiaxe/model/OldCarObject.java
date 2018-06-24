package tigerstyle.social.com.banggiaxe.model;

import java.io.Serializable;

/**
 * Created by quydv on 2/1/18.
 */

public class OldCarObject implements Serializable{

    private String version;

    private String image;

    private String curent_price;

    private String car_origin;

    private String car_size;

    private String tank_capacity;

    private String car_engine;

    private String car_power;

    private String car_gear;

    public OldCarObject() {
    }

    public OldCarObject(String version, String image, String curent_price,
                        String car_origin, String car_size, String tank_capacity,
                        String car_engine, String car_power, String car_gear) {
        this.version = version;
        this.image = image;
        this.curent_price = curent_price;
        this.car_origin = car_origin;
        this.car_size = car_size;
        this.tank_capacity = tank_capacity;
        this.car_engine = car_engine;
        this.car_power = car_power;
        this.car_gear = car_gear;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCurent_price() {
        return curent_price;
    }

    public void setCurent_price(String curent_price) {
        this.curent_price = curent_price;
    }

    public String getCar_origin() {
        return car_origin;
    }

    public void setCar_origin(String car_origin) {
        this.car_origin = car_origin;
    }

    public String getCar_size() {
        return car_size;
    }

    public void setCar_size(String car_size) {
        this.car_size = car_size;
    }

    public String getTank_capacity() {
        return tank_capacity;
    }

    public void setTank_capacity(String tank_capacity) {
        this.tank_capacity = tank_capacity;
    }

    public String getCar_engine() {
        return car_engine;
    }

    public void setCar_engine(String car_engine) {
        this.car_engine = car_engine;
    }

    public String getCar_power() {
        return car_power;
    }

    public void setCar_power(String car_power) {
        this.car_power = car_power;
    }

    public String getCar_gear() {
        return car_gear;
    }

    public void setCar_gear(String car_gear) {
        this.car_gear = car_gear;
    }
}
