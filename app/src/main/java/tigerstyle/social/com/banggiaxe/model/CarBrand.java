package tigerstyle.social.com.banggiaxe.model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by billymobile on 12/28/16.
 */

/*
{
"carId":"2091",
"carName":"BT-50 3.2AT 4WD",
"carType":"Pick-up",
"carBrand":"Mazda",
"carOrigin":"Nh\u1eadp kh\u1ea9u",
"carPrice":"819",
"carPriceDeviation":"802",
"carEngine":"Diesel 3.2 I5",
"carGear":"AT 6 c\u1ea5p",
"carPower":"197",
"carMoment":"470",
"carSize":"5365x1850x1821",
"carFuelTankCapacity":"80",
"carGroundClearance":"237",
"carCompetitors":"592,593,555,308,569,610,1859",
"carTurningCircle":"12.4",
"carImage":"\/2017\/02\/27\/mazdabt50sidedrive-1488192653.jpg",
"shareUrl":"\/interactive\/2016\/bang-gia-xe\/pick-up-bt-50-3-2at-4wd-2091.html"
}
*/
public class CarBrand implements Parcelable {
    private String carID;
    private String carName;
    private String carType;
    private String carBrand;
    private String carOrigin;
    private String carPrice;
    private String carPriceDeviation;
    private int[] carTurnover1;
    private int[] carTurnover2;
    private String carEngine;
    private String carGear;
    private String carPower;
    private String carMoment;
    private String carSize;
    private String carFuelTankCapacity;
    private String carGroundClearance;
    private String[] carCompetitors;
    private String carTurningCirclel;
    private String carImage;
    private String shareUrl;

    public CarBrand(String carID, String carName, String carType, String carBrand, String carOrigin,
                    String carPrice, String carPriceDeviation, int[] carTurnover1, int[] carTurnover2,
                    String carEngine, String carGear, String carPower, String carMoment, String carSize,
                    String carFuelTankCapacity, String carGroundClearance, String[] carCompetitors,
                    String carTurningCirclel, String carImage, String shareUrl) {
        this.carID = carID;
        this.carName = carName;
        this.carType = carType;
        this.carBrand = carBrand;
        this.carOrigin = carOrigin;
        this.carPrice = carPrice;
        this.carPriceDeviation = carPriceDeviation;
        this.carTurnover1 = carTurnover1;
        this.carTurnover2 = carTurnover2;
        this.carEngine = carEngine;
        this.carGear = carGear;
        this.carPower = carPower;
        this.carMoment = carMoment;
        this.carSize = carSize;
        this.carFuelTankCapacity = carFuelTankCapacity;
        this.carGroundClearance = carGroundClearance;
        this.carCompetitors = carCompetitors;
        this.carTurningCirclel = carTurningCirclel;
        this.carImage = carImage;
        this.shareUrl = shareUrl;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
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

    public String getCarOrigin() {
        return carOrigin;
    }

    public void setCarOrigin(String carOrigin) {
        this.carOrigin = carOrigin;
    }

    public String getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(String carPrice) {
        this.carPrice = carPrice;
    }

    public String getCarPriceDeviation() {
        return carPriceDeviation;
    }

    public void setCarPriceDeviation(String carPriceDeviation) {
        this.carPriceDeviation = carPriceDeviation;
    }

    public int[] getCarTurnover1() {
        return carTurnover1;
    }

    public void setCarTurnover1(int[] carTurnover1) {
        this.carTurnover1 = carTurnover1;
    }

    public int[] getCarTurnover2() {
        return carTurnover2;
    }

    public void setCarTurnover2(int[] carTurnover2) {
        this.carTurnover2 = carTurnover2;
    }

    public String getCarEngine() {
        return carEngine;
    }

    public void setCarEngine(String carEngine) {
        this.carEngine = carEngine;
    }

    public String getCarGear() {
        return carGear;
    }

    public void setCarGear(String carGear) {
        this.carGear = carGear;
    }

    public String getCarPower() {
        return carPower;
    }

    public void setCarPower(String carPower) {
        this.carPower = carPower;
    }

    public String getCarMoment() {
        return carMoment;
    }

    public void setCarMoment(String carMoment) {
        this.carMoment = carMoment;
    }

    public String getCarSize() {
        return carSize;
    }

    public void setCarSize(String carSize) {
        this.carSize = carSize;
    }

    public String getCarFuelTankCapacity() {
        return carFuelTankCapacity;
    }

    public void setCarFuelTankCapacity(String carFuelTankCapacity) {
        this.carFuelTankCapacity = carFuelTankCapacity;
    }

    public String getCarGroundClearance() {
        return carGroundClearance;
    }

    public void setCarGroundClearance(String carGroundClearance) {
        this.carGroundClearance = carGroundClearance;
    }

    public String[] getCarCompetitors() {
        return carCompetitors;
    }

    public void setCarCompetitors(String[] carCompetitors) {
        this.carCompetitors = carCompetitors;
    }

    public String getCarTurningCirclel() {
        return carTurningCirclel;
    }

    public void setCarTurningCirclel(String carTurningCirclel) {
        this.carTurningCirclel = carTurningCirclel;
    }

    public String getCarImage() {
        return carImage;
    }

    public void setCarImage(String carImage) {
        this.carImage = carImage;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
