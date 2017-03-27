package tigerstyle.social.com.banggiaxe.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tigerstyle.social.com.banggiaxe.BaseDialog;
import tigerstyle.social.com.banggiaxe.R;
import tigerstyle.social.com.banggiaxe.model.CarBrand;
import tigerstyle.social.com.banggiaxe.utils.NumberFormater;
import tigerstyle.social.com.banggiaxe.utils.PicassoLoader;

import static tigerstyle.social.com.banggiaxe.config.Contants.IMAGE_URL;

/**
 * Created by QuyDV on 3/27/17.
 */

public class ComparisonDialog extends BaseDialog {
    private Context mContext;
    private CarBrand car1;
    private CarBrand car2;

    private ImageView mImgVehical1;
    private ImageView mImgVehical2;

    // Information Detail
    private TextView mTxtCarName1;
    private TextView mTxtCarName2;
    private TextView mTxtVehicalPrice1;
    private TextView mTxtVehicalPrice2;
    private TextView mTxtVehicalSize1;
    private TextView mTxtVehicalSize2;
    private TextView mTxtFuelCapacity1;
    private TextView mTxtFuelCapacity2;
    private TextView mTxtDisplacement1;
    private TextView mTxtDisplacement2;
    private TextView mTxtOutputCapacity1;
    private TextView mTxtOutputCapacity2;
    private TextView mTxtTorquePower1;
    private TextView mTxtTorquePower2;
    private TextView mTxtGroundClearance1;
    private TextView mTxtGroundClearance2;
    private TextView mTxtTurningCircle1;
    private TextView mTxtTurningCircle2;
    private TextView mTxtTypeOfVehical1;
    private TextView mTxtTypeOfVehical2;
    private TextView mTxtNumberOfGears1;
    private TextView mTxtNumberOfGears2;

    private ImageView mImgClose;

    public ComparisonDialog(Context context, CarBrand car1, CarBrand car2) {
        super(context);
        mContext = context;
        this.car1 = car1;
        this.car2 = car2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_comparison);
        mTxtCarName1 = (TextView) findViewById(R.id.txt_name1);
        mTxtCarName2 = (TextView) findViewById(R.id.txt_name2);
        mImgVehical1 = (ImageView) findViewById(R.id.img_vehical1);
        mImgVehical2 = (ImageView) findViewById(R.id.img_vehical2);
        mTxtVehicalPrice1 = (TextView) findViewById(R.id.txt_vehical_price1);
        mTxtVehicalPrice2 = (TextView) findViewById(R.id.txt_vehical_price2);
        mTxtVehicalSize1 = (TextView) findViewById(R.id.txt_vehical_size1);
        mTxtVehicalSize2 = (TextView) findViewById(R.id.txt_vehical_size2);
        mTxtFuelCapacity1 = (TextView) findViewById(R.id.txt_fuel_capacity_value1);
        mTxtFuelCapacity2 = (TextView) findViewById(R.id.txt_fuel_capacity_value2);
        mTxtDisplacement1 = (TextView) findViewById(R.id.txt_displacement_value1);
        mTxtDisplacement2 = (TextView) findViewById(R.id.txt_displacement_value2);
        mTxtOutputCapacity1 = (TextView) findViewById(R.id.txt_output_capacity_value1);
        mTxtOutputCapacity2 = (TextView) findViewById(R.id.txt_output_capacity_value2);
        mTxtTorquePower1 = (TextView) findViewById(R.id.txt_torque_power_value1);
        mTxtTorquePower2 = (TextView) findViewById(R.id.txt_torque_power_value2);
        mTxtGroundClearance1 = (TextView) findViewById(R.id.txt_ground_clearance_value1);
        mTxtGroundClearance2 = (TextView) findViewById(R.id.txt_ground_clearance_value2);
        mTxtTurningCircle1 = (TextView) findViewById(R.id.txt_turning_circle_value1);
        mTxtTurningCircle2 = (TextView) findViewById(R.id.txt_turning_circle_value2);
        mTxtTypeOfVehical1 = (TextView) findViewById(R.id.txt_type_of_vehical1);
        mTxtTypeOfVehical2 = (TextView) findViewById(R.id.txt_type_of_vehical2);
        mTxtNumberOfGears1 = (TextView) findViewById(R.id.txt_number_of_gears1);
        mTxtNumberOfGears2 = (TextView) findViewById(R.id.txt_number_of_gears2);
        mImgClose = (ImageView) findViewById(R.id.imgClose);

        String urlImage1 = IMAGE_URL + car1.getCarImage();
        PicassoLoader.getInstance(mContext).load(urlImage1).placeholder(R.drawable.bg_captcha).
                error(R.drawable.bg_captcha).into(mImgVehical1);
        String urlImage2 = IMAGE_URL + car2.getCarImage();
        PicassoLoader.getInstance(mContext).load(urlImage2).placeholder(R.drawable.bg_captcha).
                error(R.drawable.bg_captcha).into(mImgVehical2);
        fillCarData();

        mImgClose.bringToFront();
        mImgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void fillCarData(){
        mTxtCarName1.setText(car1.getCarName());
        mTxtCarName2.setText(car2.getCarName());
        mTxtVehicalPrice1.setText(car1.getCarPrice());
        mTxtVehicalPrice2.setText(car2.getCarPrice());
        mTxtVehicalSize1.setText(car1.getCarSize());
        mTxtVehicalSize2.setText(car2.getCarSize());
        mTxtFuelCapacity1.setText(car1.getCarFuelTankCapacity());
        mTxtFuelCapacity2.setText(car2.getCarFuelTankCapacity());
        mTxtDisplacement1.setText(car1.getCarEngine());
        mTxtDisplacement2.setText(car2.getCarEngine());
        mTxtOutputCapacity1.setText(NumberFormater.twoDecimaFormat(Double.parseDouble(car1.getCarPower())));
        mTxtOutputCapacity2.setText(NumberFormater.twoDecimaFormat(Double.parseDouble(car2.getCarPower())));
        mTxtTorquePower1.setText(NumberFormater.twoDecimaFormat(Double.parseDouble(car1.getCarMoment())));
        mTxtTorquePower2.setText(NumberFormater.twoDecimaFormat(Double.parseDouble(car2.getCarMoment())));
        mTxtGroundClearance1.setText(car1.getCarGroundClearance());
        mTxtGroundClearance2.setText(car2.getCarGroundClearance());
        mTxtTurningCircle1.setText(car1.getCarTurningCirclel());
        mTxtTurningCircle2.setText(car2.getCarTurningCirclel());

        mTxtTypeOfVehical1.setText(car1.getCarType());
        mTxtTypeOfVehical2.setText(car2.getCarType());
        mTxtNumberOfGears1.setText(car1.getCarGear());
        mTxtNumberOfGears2.setText(car2.getCarGear());
    }
}
