package tigerstyle.social.com.banggiaxe.model;

import java.io.Serializable;

/**
 * Created by billymobile on 12/9/16.
 */

/*      "carId":"1686",
        "carName":"Wave Apha",
        "carType":"Xe s\u1ed1",
        "carBrand":"Honda",
        "carOrigin":"L\u1eafp r\u00e1p",
        "carPrice":"17.0",
        "carPriceDeviation":"16.5",
        "carEngine":"100",
        "carGear":"4 s\u1ed1 ",
        "carPower":"6.8",
        "carMoment":"7",
        "carSize":"1908x699x1070",
        "carFuelTankCapacity":"3.6",
        "carGroundClearance":"",
        "carCompetitors":"[]",
        "carTurningCircle":"98",
        "carImage":"importxls\/importxls-event-19-coordinates-R2-time-1480913243.jpg" */

public class MotobikeBrand implements Serializable{
    private String carID;
    private String carName;
    private String carType;
    private String carBrand;
    private String carOrigin;
    private String carPrice;
    private String carPriceDeviation;
    private String carEngine;
    private String carGear;
    private String carPower;
    private String carMoment;
    private String carSize;
    private String carFuelTankCapacity;
    private String carTurningCirclel;
    private String carImage;

    public MotobikeBrand() {
    }

    public MotobikeBrand(String carID, String carName, String carType, String carBrand, String carOrigin, String carPrice, String carPriceDeviation, String carEngine, String carGear, String carPower, String carMoment, String carSize, String carFuelTankCapacity, String carTurningCirclel, String carImage) {
        this.carID = carID;
        this.carName = carName;
        this.carType = carType;
        this.carBrand = carBrand;
        this.carOrigin = carOrigin;
        this.carPrice = carPrice;
        this.carPriceDeviation = carPriceDeviation;
        this.carEngine = carEngine;
        this.carGear = carGear;
        this.carPower = carPower;
        this.carMoment = carMoment;
        this.carSize = carSize;
        this.carFuelTankCapacity = carFuelTankCapacity;
        this.carTurningCirclel = carTurningCirclel;
        this.carImage = carImage;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public void setCarOrigin(String carOrigin) {
        this.carOrigin = carOrigin;
    }

    public void setCarPrice(String carPrice) {
        this.carPrice = carPrice;
    }

    public void setCarPriceDeviation(String carPriceDeviation) {
        this.carPriceDeviation = carPriceDeviation;
    }

    public void setCarEngine(String carEngine) {
        this.carEngine = carEngine;
    }

    public void setCarGear(String carGear) {
        this.carGear = carGear;
    }

    public void setCarPower(String carPower) {
        this.carPower = carPower;
    }

    public void setCarMoment(String carMoment) {
        this.carMoment = carMoment;
    }

    public void setCarSize(String carSize) {
        this.carSize = carSize;
    }

    public void setCarFuelTankCapacity(String carFuelTankCapacity){
        this.carFuelTankCapacity = carFuelTankCapacity;
    }

    public void setCarTurningCirclel(String carTurningCirclel){
        this.carTurningCirclel = carTurningCirclel;
    }

    public void setCarImage(String carImage) {
        this.carImage = carImage;
    }

    public String getCarID() {
        return carID;
    }

    public String getCarName() {
        return carName;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public String getCarOrigin() {
        return carOrigin;
    }

    public String getCarPrice() {
        return carPrice;
    }

    public String getCarPriceDeviation() {
        return carPriceDeviation;
    }

    public String getCarEngine() {
        return carEngine;
    }

    public String getCarGear() {
        return carGear;
    }

    public String getCarPower() {
        return carPower;
    }

    public String getCarMoment() {
        return carMoment;
    }

    public String getCarSize() {
        return carSize;
    }

    public String getCarFuelTankCapacity(){
        return this.carFuelTankCapacity;
    }

    public String getCarTurningCirclel(){
        return this.carTurningCirclel;
    }

    public String getCarImage() {
        return carImage;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }
}
