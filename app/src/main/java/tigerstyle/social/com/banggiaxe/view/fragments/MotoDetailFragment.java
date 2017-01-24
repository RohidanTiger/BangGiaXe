package tigerstyle.social.com.banggiaxe.view.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

import tigerstyle.social.com.banggiaxe.BaseFragment;
import tigerstyle.social.com.banggiaxe.R;
import tigerstyle.social.com.banggiaxe.customize.CustomSpinner;
import tigerstyle.social.com.banggiaxe.customize.SuffixTextView;
import tigerstyle.social.com.banggiaxe.model.MotobikeBrand;
import tigerstyle.social.com.banggiaxe.utils.NumberFormater;
import tigerstyle.social.com.banggiaxe.utils.PicassoLoader;
import tigerstyle.social.com.banggiaxe.view.dialog.AreaInforDialog;

import static tigerstyle.social.com.banggiaxe.config.Contants.IMAGE_URL;
import static tigerstyle.social.com.banggiaxe.config.Contants.MOTO_INSURANCE;
import static tigerstyle.social.com.banggiaxe.config.Contants.MOTO_NUMBER_PLATE_ZONE1_1;
import static tigerstyle.social.com.banggiaxe.config.Contants.MOTO_NUMBER_PLATE_ZONE1_2;
import static tigerstyle.social.com.banggiaxe.config.Contants.MOTO_NUMBER_PLATE_ZONE1_3;
import static tigerstyle.social.com.banggiaxe.config.Contants.MOTO_NUMBER_PLATE_ZONE2_1;
import static tigerstyle.social.com.banggiaxe.config.Contants.MOTO_NUMBER_PLATE_ZONE2_2;
import static tigerstyle.social.com.banggiaxe.config.Contants.MOTO_NUMBER_PLATE_ZONE2_3;
import static tigerstyle.social.com.banggiaxe.config.Contants.MOTO_NUMBER_PLATE_ZONE3;
import static tigerstyle.social.com.banggiaxe.config.Contants.ONE_MILLION;

/**
 * Created by billymobile on 12/21/16.
 */

public class MotoDetailFragment extends BaseFragment{
    private ImageView mImageHeader;
    private MotobikeBrand motobikeBrand;
    private TextView mTxtMotoName;
    private TextView mTxtBrandName;
    private TextView mTxtPrice;
    private TextView mTxtDeviationPrice;
    private TextView mTxtSizeValue;
    private TextView mTxtFuelCapacity;
    private TextView mTxtDisplacement;
    private TextView mTxtOutputCapacity;
    private TextView mTxtMoment;
    private TextView mTxtGrossWeight;
    private TextView mTxtOrigin;
    private TextView mTxtTypeVehical;
    private TextView mTxtNumberOfGears;

    // Group 2
    private TextView mTxtArea;
    private CustomSpinner mSpinnerArea;
    private TextView mTxtDeviationPrice2;
    private TextView mTxtRegistrationFee;
    private SuffixTextView mTxtRegistrationTitle;
    private TextView mTxtPlateNumberValue;
    private TextView mTxtInsurance;
    private SuffixTextView mTxtTotalPrice;
    private double deviationPrice;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail, null);
        mImageHeader = (ImageView) rootView.findViewById(R.id.img_vehical);
        mTxtMotoName = (TextView) rootView.findViewById(R.id.txtVehicalName);
        mTxtBrandName = (TextView) rootView.findViewById(R.id.txtBrand);
        mTxtPrice = (TextView) rootView.findViewById(R.id.txtPrice);
        mTxtDeviationPrice = (TextView) rootView.findViewById(R.id.txtDeviationPrice);
        mTxtSizeValue = (TextView) rootView.findViewById(R.id.txt_size_value);
        mTxtFuelCapacity = (TextView) rootView.findViewById(R.id.txt_fuel_capacity_value);
        mTxtDisplacement = (TextView) rootView.findViewById(R.id.txt_displacement_value);
        mTxtOutputCapacity = (TextView) rootView.findViewById(R.id.txt_output_capacity_value);
        mTxtMoment = (TextView) rootView.findViewById(R.id.txt_torque_power_value);
        mTxtGrossWeight = (TextView) rootView.findViewById(R.id.txt_gross_weight_value);
        mTxtOrigin = (TextView) rootView.findViewById(R.id.txt_origin_value);
        mTxtTypeVehical = (TextView) rootView.findViewById(R.id.txt_type_of_vehical_value);
        mTxtNumberOfGears = (TextView) rootView.findViewById(R.id.txt_number_of_gears_value);

        //Group 2
        mTxtArea = (TextView) rootView.findViewById(R.id.txt_area_select);
        mSpinnerArea = (CustomSpinner) rootView.findViewById(R.id.spinner_area);
        mTxtDeviationPrice2 = (TextView) rootView.findViewById(R.id.txt_deviation_price_value);
        mTxtRegistrationTitle = (SuffixTextView) rootView.findViewById(R.id.txt_registration_fee);
        mTxtRegistrationFee = (TextView) rootView.findViewById(R.id.txt_registration_fee_value);
        mTxtPlateNumberValue = (TextView) rootView.findViewById(R.id.txt_number_plate_value);
        mTxtInsurance = (TextView) rootView.findViewById(R.id.txt_insurance_value);
        mTxtTotalPrice = (SuffixTextView) rootView.findViewById(R.id.txt_total_cost_value);

        motobikeBrand = (MotobikeBrand) getArguments().getSerializable(HomeMotoFragment.ARG_OBJ_KEY);
        deviationPrice = Double.parseDouble(motobikeBrand.getCarPriceDeviation());
        fillData();
        calculateTotalCost();

        return rootView;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    private void fillData(){
        String urlImage = IMAGE_URL + motobikeBrand.getCarImage();
        PicassoLoader.getInstance(context).load(urlImage).placeholder(R.drawable.bg_captcha).
                error(R.drawable.bg_captcha).into(mImageHeader);
        mTxtMotoName.setText(motobikeBrand.getCarName());
        mTxtBrandName.setText(motobikeBrand.getCarBrand());
        mTxtPrice.setText(motobikeBrand.getCarPrice());
        mTxtDeviationPrice.setText(motobikeBrand.getCarPriceDeviation());
        mTxtSizeValue.setText(motobikeBrand.getCarSize());
        mTxtFuelCapacity.setText(motobikeBrand.getCarFuelTankCapacity());
        mTxtDisplacement.setText(motobikeBrand.getCarEngine());
        mTxtOutputCapacity.setText(NumberFormater.twoDecimaFormat(Double.parseDouble(motobikeBrand.getCarPower())));
        mTxtMoment.setText(NumberFormater.twoDecimaFormat(Double.parseDouble(motobikeBrand.getCarMoment())));
        mTxtGrossWeight.setText(motobikeBrand.getCarTurningCirclel());
        mTxtOrigin.setText(motobikeBrand.getCarOrigin());
        mTxtTypeVehical.setText(motobikeBrand.getCarGear());
        mTxtNumberOfGears.setText(motobikeBrand.getCarGear());
    }

    // Fill data to calculating cost group
    private void calculateTotalCost(){
        Arrays.asList(getResources().getStringArray(R.array.brand_array));
        mTxtArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AreaInforDialog dialog = new AreaInforDialog(context,Arrays.asList(getResources().getStringArray(R.array.area_array)),
                        Arrays.asList(getResources().getStringArray(R.array.area_content_array)));
                dialog.show();
            }
        });
        mTxtDeviationPrice2.setText(NumberFormater.longFormat((long) (deviationPrice * ONE_MILLION)));
        mTxtInsurance.setText(NumberFormater.longFormat(MOTO_INSURANCE));
        final long[] registrationFee = new long[1];
        final long[] numberPateFee = new long[1];
        mSpinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 0){
                    mTxtRegistrationTitle.setText("Phí trước bạ (5%)");
                    registrationFee[0] = (long) (deviationPrice * 5 / 100 * ONE_MILLION);
                    mTxtRegistrationFee.setText(NumberFormater.longFormat(registrationFee[0]));
                    if(deviationPrice* ONE_MILLION <= 15000000){
                        numberPateFee[0] = MOTO_NUMBER_PLATE_ZONE1_1;
                    }else if(deviationPrice* ONE_MILLION > 15000000 && deviationPrice* ONE_MILLION <= 40000000){
                        numberPateFee[0] = MOTO_NUMBER_PLATE_ZONE1_2;
                    }else {
                        numberPateFee[0] = MOTO_NUMBER_PLATE_ZONE1_3;
                    }
                    mTxtPlateNumberValue.setText(NumberFormater.longFormat(numberPateFee[0]));
                    long totalCost = (long) (deviationPrice*ONE_MILLION + MOTO_INSURANCE + registrationFee[0] + numberPateFee[0]);
                    mTxtTotalPrice.setText(NumberFormater.longFormat(totalCost).concat("đ"));
                }else if(position == 1){
                    mTxtRegistrationTitle.setText("Phí trước bạ (2%)");
                    registrationFee[0] = (long) (deviationPrice * 2 / 100 * ONE_MILLION);
                    mTxtRegistrationFee.setText(NumberFormater.longFormat(registrationFee[0]));
                    if(deviationPrice * ONE_MILLION <= 15000000){
                        numberPateFee[0] = MOTO_NUMBER_PLATE_ZONE2_1;
                    }else if(deviationPrice * ONE_MILLION > 15000000 && deviationPrice* ONE_MILLION <= 40000000){
                        numberPateFee[0] = MOTO_NUMBER_PLATE_ZONE2_2;
                    }else {
                        numberPateFee[0] = MOTO_NUMBER_PLATE_ZONE2_3;
                    }
                    mTxtPlateNumberValue.setText(NumberFormater.longFormat(numberPateFee[0]));
                    long totalCost = (long) (deviationPrice*ONE_MILLION + MOTO_INSURANCE + registrationFee[0] + numberPateFee[0]);
                    mTxtTotalPrice.setText(NumberFormater.longFormat(totalCost).concat("đ"));
                }else{
                    mTxtRegistrationTitle.setText("Phí trước bạ (2%)");
                    registrationFee[0] = (long) (deviationPrice * 2 / 100 * ONE_MILLION);
                    mTxtRegistrationFee.setText(NumberFormater.longFormat(registrationFee[0]));
                    numberPateFee[0] = MOTO_NUMBER_PLATE_ZONE3;
                    mTxtPlateNumberValue.setText(NumberFormater.longFormat(numberPateFee[0]));
                    long totalCost = (long) (deviationPrice*ONE_MILLION + MOTO_INSURANCE + registrationFee[0] + numberPateFee[0]);
                    mTxtTotalPrice.setText(NumberFormater.longFormat(totalCost).concat("đ"));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        context.setHideActionBarSearchItem(false);
        context.getSupportActionBar().setTitle(context.getResources().getString(R.string.cmn_detail_information));
        setHasOptionsMenu(true);
    }
}
