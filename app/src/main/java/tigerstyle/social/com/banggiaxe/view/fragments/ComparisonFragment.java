package tigerstyle.social.com.banggiaxe.view.fragments;

import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tigerstyle.social.com.banggiaxe.BaseFragment;
import tigerstyle.social.com.banggiaxe.R;
import tigerstyle.social.com.banggiaxe.customize.CustomSpinner;
import tigerstyle.social.com.banggiaxe.model.CarBrand;
import tigerstyle.social.com.banggiaxe.model.MotobikeBrand;
import tigerstyle.social.com.banggiaxe.utils.NumberFormater;
import tigerstyle.social.com.banggiaxe.utils.PicassoLoader;
import tigerstyle.social.com.banggiaxe.view.adapters.BaseSpinerAdapter;

import static tigerstyle.social.com.banggiaxe.config.Contants.IMAGE_URL;

/**
 * Created by billymobile on 2/3/17.
 */

public class ComparisonFragment extends BaseFragment {
    private Button mBtnMotoComparison;
    private View mViewMoto;
    private Button mBtnOtoComparison;
    private View mViewOto;
    private CustomSpinner mSpinerBrand1;
    private CustomSpinner mSpinerType1;
    private CustomSpinner mSpinerBrand2;
    private CustomSpinner mSpinerType2;
    private Button mButtonComparison;
    private ImageView mImgVehical1;
    private ImageView mImgVehical2;

    // Information Detail
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
    private TextView mTxtGrossWeight1;
    private TextView mTxtGrossWeight2;
    private TextView mTxtTurningCircle1;
    private TextView mTxtTurningCircle2;
    private TextView mTxtTypeOfVehical1;
    private TextView mTxtTypeOfVehical2;
    private TextView mTxtNumberOfGears1;
    private TextView mTxtNumberOfGears2;
    private PercentRelativeLayout mLayoutTurningCircle;
    private PercentRelativeLayout mLayoutGrossWeight;
    private PercentRelativeLayout mLayoutGroundClearance;

    private ArrayList<MotobikeBrand> listMoto1;
    private ArrayList<MotobikeBrand> listMoto2;
    private ArrayList<String> listMotoName1 = new ArrayList<>();
    private ArrayList<String> listMotoName2 = new ArrayList<>();
    private MotobikeBrand moto1,moto2;

    private ArrayList<CarBrand> listCar1;
    private ArrayList<CarBrand> listCar2;
    private ArrayList<String> listCarName1 = new ArrayList<>();
    private ArrayList<String> listCarName2 = new ArrayList<>();
    private CarBrand car1,car2;

    private List<String> listMotoBrand;
    private List<String> listCarBrand;

    private int mComparisonType = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_comparison, null);
        mBtnMotoComparison = (Button) rootView.findViewById(R.id.btn_moto_comparison);
        mViewMoto = rootView.findViewById(R.id.view_moto);
        mBtnOtoComparison = (Button) rootView.findViewById(R.id.btn_oto_comparison);
        mViewOto = rootView.findViewById(R.id.view_oto);

        mButtonComparison = (Button) rootView.findViewById(R.id.btn_comparison);
        mTxtVehicalSize1 = (TextView) rootView.findViewById(R.id.txt_vehical_size1);
        mTxtVehicalSize2 = (TextView) rootView.findViewById(R.id.txt_vehical_size2);
        mTxtFuelCapacity1 = (TextView) rootView.findViewById(R.id.txt_fuel_capacity_value1);
        mTxtFuelCapacity2 = (TextView) rootView.findViewById(R.id.txt_fuel_capacity_value2);
        mTxtDisplacement1 = (TextView) rootView.findViewById(R.id.txt_displacement_value1);
        mTxtDisplacement2 = (TextView) rootView.findViewById(R.id.txt_displacement_value2);
        mTxtOutputCapacity1 = (TextView) rootView.findViewById(R.id.txt_output_capacity_value1);
        mTxtOutputCapacity2 = (TextView) rootView.findViewById(R.id.txt_output_capacity_value2);
        mTxtTorquePower1 = (TextView) rootView.findViewById(R.id.txt_torque_power_value1);
        mTxtTorquePower2 = (TextView) rootView.findViewById(R.id.txt_torque_power_value2);
        mTxtGroundClearance1 = (TextView) rootView.findViewById(R.id.txt_ground_clearance_value1);
        mTxtGroundClearance2 = (TextView) rootView.findViewById(R.id.txt_ground_clearance_value2);
        mTxtGrossWeight1 = (TextView) rootView.findViewById(R.id.txt_gross_weight_value1);
        mTxtGrossWeight2 = (TextView) rootView.findViewById(R.id.txt_gross_weight_value2);
        mTxtTurningCircle1 = (TextView) rootView.findViewById(R.id.txt_turning_circle_value1);
        mTxtTurningCircle2 = (TextView) rootView.findViewById(R.id.txt_turning_circle_value2);
        mTxtTypeOfVehical1 = (TextView) rootView.findViewById(R.id.txt_type_of_vehical1);
        mTxtTypeOfVehical2 = (TextView) rootView.findViewById(R.id.txt_type_of_vehical2);
        mTxtNumberOfGears1 = (TextView) rootView.findViewById(R.id.txt_number_of_gears1);
        mTxtNumberOfGears2 = (TextView) rootView.findViewById(R.id.txt_number_of_gears2);
        mLayoutTurningCircle = (PercentRelativeLayout) rootView.findViewById(R.id.layout_turning_circle);
        mLayoutGrossWeight = (PercentRelativeLayout) rootView.findViewById(R.id.layout_gross_weight);
        mLayoutGroundClearance = (PercentRelativeLayout) rootView.findViewById(R.id.layout_ground_clearance);

        // Spiner
        mSpinerBrand1 = (CustomSpinner) rootView.findViewById(R.id.spiner_brand);
        mSpinerType1 = (CustomSpinner) rootView.findViewById(R.id.spiner_type);
        mSpinerBrand2 = (CustomSpinner) rootView.findViewById(R.id.spiner_brand2);
        mSpinerType2 = (CustomSpinner) rootView.findViewById(R.id.spiner_type2);

        mImgVehical1 = (ImageView) rootView.findViewById(R.id.img_vehical1);
        mImgVehical2 = (ImageView) rootView.findViewById(R.id.img_vehical2);

        listMotoBrand = Arrays.asList(getResources().getStringArray(R.array.brand_array2));
        listCarBrand = Arrays.asList(getResources().getStringArray(R.array.car_brand_array2));
        listMoto1 = new ArrayList<>();
        listMoto2 = new ArrayList<>();
        listCar1 = new ArrayList<>();
        listCar2 = new ArrayList<>();

        mBtnMotoComparison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayoutGrossWeight.setVisibility(View.VISIBLE);
                mLayoutTurningCircle.setVisibility(View.GONE);
                mLayoutGroundClearance.setVisibility(View.GONE);
                resetStatus();
                mBtnMotoComparison.setBackgroundColor(ContextCompat.getColor(context, R.color.color_menu_click));
                mViewMoto.setVisibility(View.VISIBLE);
                mBtnMotoComparison.setTextColor(ContextCompat.getColor(context, R.color.cmn_white));
            }
        });

        mBtnOtoComparison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayoutGrossWeight.setVisibility(View.GONE);
                mLayoutTurningCircle.setVisibility(View.VISIBLE);
                mLayoutGroundClearance.setVisibility(View.VISIBLE);
                resetStatus();
                mBtnOtoComparison.setBackgroundColor(ContextCompat.getColor(context, R.color.color_menu_click));
                mViewOto.setVisibility(View.VISIBLE);
                mBtnOtoComparison.setTextColor(ContextCompat.getColor(context, R.color.cmn_white));
            }
        });
        initBrandSpiner();

        mButtonComparison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mComparisonType == 0){
                    if(moto1 != null && moto2 != null){
                        String urlImage1 = IMAGE_URL + moto1.getCarImage();
                        PicassoLoader.getInstance(context).load(urlImage1).placeholder(R.drawable.bg_captcha).
                                error(R.drawable.bg_captcha).into(mImgVehical1);
                        String urlImage2 = IMAGE_URL + moto2.getCarImage();
                        PicassoLoader.getInstance(context).load(urlImage2).placeholder(R.drawable.bg_captcha).
                                error(R.drawable.bg_captcha).into(mImgVehical2);
                        fillMotoData();
                    }
                }else{

                }
            }
        });
        return rootView;
    }

    private void fillMotoData(){
        mTxtVehicalSize1.setText(moto1.getCarSize());
        mTxtVehicalSize2.setText(moto2.getCarSize());
        mTxtFuelCapacity1.setText(moto1.getCarFuelTankCapacity());
        mTxtFuelCapacity2.setText(moto2.getCarFuelTankCapacity());
        mTxtDisplacement1.setText(moto1.getCarEngine());
        mTxtDisplacement2.setText(moto2.getCarEngine());
        mTxtOutputCapacity1.setText(NumberFormater.twoDecimaFormat(Double.parseDouble(moto1.getCarPower())));
        mTxtOutputCapacity2.setText(NumberFormater.twoDecimaFormat(Double.parseDouble(moto2.getCarPower())));
        mTxtTorquePower1.setText(NumberFormater.twoDecimaFormat(Double.parseDouble(moto1.getCarMoment())));
        mTxtTorquePower2.setText(NumberFormater.twoDecimaFormat(Double.parseDouble(moto2.getCarMoment())));
//        mTxtGroundClearance1 = (TextView) rootView.findViewById(R.id.txt_ground_clearance_value1);
//        mTxtGroundClearance2 = (TextView) rootView.findViewById(R.id.txt_ground_clearance_value2);
        mTxtGrossWeight1.setText(moto1.getCarTurningCirclel());
        mTxtGrossWeight2.setText(moto2.getCarTurningCirclel());

        mTxtTypeOfVehical1.setText(moto1.getCarType());
        mTxtTypeOfVehical2.setText(moto2.getCarType());
        mTxtNumberOfGears1.setText(moto1.getCarGear());
        mTxtNumberOfGears2.setText(moto2.getCarGear());
    }

    private void fillCarData(){

    }

    private void resetStatus(){
        mBtnMotoComparison.setBackgroundColor(ContextCompat.getColor(context, R.color.color_menu_normal));
        mViewMoto.setVisibility(View.INVISIBLE);
        mBtnMotoComparison.setTextColor(ContextCompat.getColor(context, R.color.cmn_black));

        mBtnOtoComparison.setBackgroundColor(ContextCompat.getColor(context, R.color.color_menu_normal));
        mViewOto.setVisibility(View.INVISIBLE);
        mBtnOtoComparison.setTextColor(ContextCompat.getColor(context, R.color.cmn_black));
    }

    private void initBrandSpiner(){
        final ArrayList<MotobikeBrand> allMoto =  context.getListMoto();
        if(mComparisonType == 0){
            mSpinerBrand1.setAdapter(new BaseSpinerAdapter(context, R.layout.spinner_item, listMotoBrand));
            mSpinerBrand1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    listMoto1.clear();
                    listMotoName1.clear();
                    String motoBrand = listMotoBrand.get(i);
                    for(MotobikeBrand brand : allMoto){
                        if(brand.getCarBrand().trim().equals(motoBrand.trim())) {
                            listMoto1.add(brand);
                            listMotoName1.add(brand.getCarName());
                        }
                    }
                    mSpinerType1.setAdapter(new BaseSpinerAdapter(context, R.layout.spinner_item, listMotoName1));
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            mSpinerType1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(listMoto1!=null)moto1 = listMoto1.get(0);
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            mSpinerBrand2.setAdapter(new BaseSpinerAdapter(context, R.layout.spinner_item, listMotoBrand));
            mSpinerBrand2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    listMoto2.clear();
                    listMotoName2.clear();
                    String motoBrand = listMotoBrand.get(i);
                    for(MotobikeBrand brand : allMoto){
                        if(brand.getCarBrand().equals(motoBrand)) {
                            listMoto2.add(brand);
                            listMotoName2.add(brand.getCarName());
                        }
                    }
                    mSpinerType2.setAdapter(new BaseSpinerAdapter(context, R.layout.spinner_item, listMotoName2));
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            mSpinerType2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(listMoto2!=null)moto2 = listMoto2.get(0);
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }else{
            mSpinerBrand1.setAdapter(new BaseSpinerAdapter(context, R.layout.spinner_item, listCarBrand));
            mSpinerBrand2.setAdapter(new BaseSpinerAdapter(context, R.layout.spinner_item, listCarBrand));
        }
    }
}
