package tigerstyle.social.com.banggiaxe.view.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tigerstyle.social.com.banggiaxe.BaseFragment;
import tigerstyle.social.com.banggiaxe.R;
import tigerstyle.social.com.banggiaxe.customize.CustomSpinner;
import tigerstyle.social.com.banggiaxe.customize.SuffixTextView;
import tigerstyle.social.com.banggiaxe.model.CarBrand;
import tigerstyle.social.com.banggiaxe.model.ListOldCar;
import tigerstyle.social.com.banggiaxe.model.OldCarObject;
import tigerstyle.social.com.banggiaxe.utils.NumberFormater;
import tigerstyle.social.com.banggiaxe.utils.PicassoLoader;
import tigerstyle.social.com.banggiaxe.view.dialog.AreaInforDialog;

import static tigerstyle.social.com.banggiaxe.config.Contants.IMAGE_URL;
import static tigerstyle.social.com.banggiaxe.config.Contants.INSPECTION_FEE;
import static tigerstyle.social.com.banggiaxe.config.Contants.INSURANCE1;
import static tigerstyle.social.com.banggiaxe.config.Contants.INSURANCE2;
import static tigerstyle.social.com.banggiaxe.config.Contants.INSURANCE3;
import static tigerstyle.social.com.banggiaxe.config.Contants.NUMBER_PLATE_FEE1;
import static tigerstyle.social.com.banggiaxe.config.Contants.NUMBER_PLATE_FEE2;
import static tigerstyle.social.com.banggiaxe.config.Contants.NUMBER_PLATE_FEE3;
import static tigerstyle.social.com.banggiaxe.config.Contants.ONE_MILLION;
import static tigerstyle.social.com.banggiaxe.config.Contants.ROAD_TOLLS;
import static tigerstyle.social.com.banggiaxe.config.Contants.SHARE_URL;

/**
 * Created by quydv on 2/7/18.
 */

public class OldCarDetailFragment extends BaseFragment {
    private ImageView mImageHeader;
    private TextView mTxtMotoName;
    private TextView mTxtBrandName;
    private SuffixTextView mTxtPrice;
    private TextView mTxtSizeValue;
    private TextView mTxtFuelCapacity;
    private TextView mTxtDisplacement;
    private TextView mTxtOutputCapacity;
    private TextView mTxtTypeVehical;
    private TextView mTxtNumberOfGears;
    private TextView mTxtDeviationPrice2;
    private SuffixTextView mTxtRegistrationTitle;
    private TextView mTxtRegistrationFee;
    private TextView mTxtRoadTolls;
    private TextView mTxtInsurance;
    private TextView mTxtNumberPlate;
    private TextView mTxtInspectionFee;
    private TextView mTxtTotalCost;
    private SuffixTextView mTxtAreaTitle;
    private CustomSpinner mSpinnerArea;
    private CustomSpinner mSpinnerVersion;
    private AdView mAdView;

    private ListOldCar oldCar;
    private String version;
    private long price;

    public static String ARG_OBJ_KEY = "arg-brand-obj";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_old_car_detail, null);

        oldCar = (ListOldCar) getArguments().getSerializable(ARG_OBJ_KEY);

        mImageHeader = (ImageView) rootView.findViewById(R.id.img_vehical);
        mTxtMotoName = (TextView) rootView.findViewById(R.id.txtVehicalName);
        mTxtBrandName = (TextView) rootView.findViewById(R.id.txtBrand);
        mTxtPrice = (SuffixTextView) rootView.findViewById(R.id.txtPrice);
        mTxtSizeValue = (TextView) rootView.findViewById(R.id.txt_size_value);
        mTxtFuelCapacity = (TextView) rootView.findViewById(R.id.txt_fuel_capacity_value);
        mTxtDisplacement = (TextView) rootView.findViewById(R.id.txt_displacement_value);
        mTxtOutputCapacity = (TextView) rootView.findViewById(R.id.txt_output_capacity_value);
        mTxtTypeVehical = (TextView) rootView.findViewById(R.id.txt_type_of_vehical_value);
        mTxtNumberOfGears = (TextView) rootView.findViewById(R.id.txt_number_of_gears_value);
        mTxtDeviationPrice2 = (TextView) rootView.findViewById(R.id.txt_deviation_price_value);
        mTxtRegistrationTitle = (SuffixTextView) rootView.findViewById(R.id.txt_registration_fee);
        mTxtRegistrationFee = (TextView) rootView.findViewById(R.id.txt_registration_fee_value);
        mTxtRoadTolls = (TextView) rootView.findViewById(R.id.txt_road_tolls_value);
        mTxtInsurance = (TextView) rootView.findViewById(R.id.txt_insurance_value);
        mTxtNumberPlate = (TextView) rootView.findViewById(R.id.txt_number_plate_value);
        mTxtInspectionFee = (TextView) rootView.findViewById(R.id.txt_inspection_fee_value);
        mTxtTotalCost = (TextView) rootView.findViewById(R.id.txt_total_cost_value);
        mTxtAreaTitle = (SuffixTextView) rootView.findViewById(R.id.txt_area_select);
        mSpinnerArea = (CustomSpinner) rootView.findViewById(R.id.spinner_area);
        mSpinnerVersion = (CustomSpinner) rootView.findViewById(R.id.spiner_version);

        initSpinner();

        mAdView = (AdView) rootView.findViewById(R.id.adView);
        mAdView.loadAd(context.adRequest);
        return rootView;
    }

    private void initSpinner(){
        List data = new ArrayList<>();

        for(OldCarObject oldCarObject : this.oldCar.getListVersions()){
            String version = oldCarObject.getVersion();
            data.add(version);
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item,data); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerVersion.setAdapter(spinnerArrayAdapter);

        mSpinnerVersion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fillData(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void fillData(int position) {
        if(oldCar == null) return;
        version = oldCar.getListVersions().get(position).getVersion();
        OldCarObject oldCarObject = oldCar.getListVersions().get(position);

        String urlImage = IMAGE_URL + oldCarObject.getImage();
        PicassoLoader.getInstance(context).load(urlImage).placeholder(R.drawable.bg_captcha).
                error(R.drawable.bg_captcha).into(mImageHeader);
        String priceString = oldCarObject.getCurent_price().replaceAll("\\.","").trim();
        price = Long.parseLong(priceString);
        mTxtMotoName.setText(oldCar.getCarName());
        mTxtBrandName.setText(oldCar.getCarBrand());
        mTxtPrice.setText(NumberFormater.longFormat(price* ONE_MILLION).concat("đ"));

        mTxtSizeValue.setText(oldCarObject.getCar_size());
        mTxtFuelCapacity.setText(oldCarObject.getTank_capacity());
        mTxtDisplacement.setText(oldCarObject.getCar_engine());
        mTxtOutputCapacity.setText(oldCarObject.getCar_power());
        mTxtTypeVehical.setText(oldCar.getCarType());
        mTxtNumberOfGears.setText(oldCarObject.getCar_gear());

        fillTotalCost(oldCarObject);

    }

    private void fillTotalCost(OldCarObject oldCarObject){
        //setUpSpinner();

        new LoadPompetitor().execute(SHARE_URL.concat(oldCar.getShareUrl()));

        mTxtAreaTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AreaInforDialog dialog = new AreaInforDialog(context, Arrays.asList(getResources().getStringArray(R.array.area_array)),
                        Arrays.asList(getResources().getStringArray(R.array.area_content_array)));
                dialog.show();
            }
        });
        mTxtDeviationPrice2.setText(NumberFormater.longFormat(price * ONE_MILLION));
        final long[] registrationFee = {price * ONE_MILLION / 100};
        final long roadTolls = ROAD_TOLLS;
        mTxtRoadTolls.setText(NumberFormater.longFormat(ROAD_TOLLS));

        final long insurance;
        if(this.oldCar.getCarType().equals("Pick-up")){
            insurance = INSURANCE3;
            mTxtInsurance.setText(NumberFormater.longFormat(INSURANCE3));
        }else if(this.oldCar.getCarType().equals("SUV") && this.oldCar.getCarType().equals("Honda")){
            insurance = INSURANCE2;
            mTxtInsurance.setText(NumberFormater.longFormat(INSURANCE2));
        }else{
            insurance = INSURANCE1;
            mTxtInsurance.setText(NumberFormater.longFormat(INSURANCE1));
        }
        final long[] numberPlate = new long[1];
        final long inspection_fee = INSPECTION_FEE;
        mTxtInspectionFee.setText(NumberFormater.longFormat(INSPECTION_FEE));

        updateFee(0,registrationFee,numberPlate,roadTolls,insurance,inspection_fee);

        mSpinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateFee(i,registrationFee,numberPlate,roadTolls,insurance,inspection_fee);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        mSpinnerArea.setSelection(0, false);
    }

    private void updateTotalCost(long deviationPrice,long registrationFee, long roadTolls,
                                 long insurance, long numberPlate, long inspectionFee){
        long total_cost = deviationPrice* ONE_MILLION + registrationFee
                + roadTolls + insurance + numberPlate + inspectionFee;
        mTxtTotalCost.setText(NumberFormater.longFormat(total_cost).concat("đ"));
    }

    private void updateFee(int i, long[] registrationFee, long[] numberPlate,
                           long roadTolls, long insurance, long inspection_fee){
        if(i == 0){
            registrationFee[0] = price * ONE_MILLION/100*12;
            mTxtRegistrationFee.setText(NumberFormater.longFormat(registrationFee[0]));
            numberPlate[0] = NUMBER_PLATE_FEE1;
            mTxtRegistrationTitle.setText("Phí trước bạ (12%)");
            mTxtNumberPlate.setText(NumberFormater.longFormat(NUMBER_PLATE_FEE1));
            updateTotalCost(price,registrationFee[0],roadTolls,insurance,numberPlate[0],inspection_fee);
        }else if(i == 1){
            registrationFee[0] = price * ONE_MILLION/10;
            mTxtRegistrationFee.setText(NumberFormater.longFormat(registrationFee[0]));
            numberPlate[0] = NUMBER_PLATE_FEE2;
            mTxtRegistrationTitle.setText("Phí trước bạ (10%)");
            mTxtNumberPlate.setText(NumberFormater.longFormat(NUMBER_PLATE_FEE2));
            updateTotalCost(price,registrationFee[0],roadTolls,insurance,numberPlate[0],inspection_fee);
        }else {
            registrationFee[0] = price * ONE_MILLION/10;
            mTxtRegistrationFee.setText(NumberFormater.longFormat(registrationFee[0]));
            numberPlate[0] = NUMBER_PLATE_FEE3;
            mTxtRegistrationTitle.setText("Phí trước bạ (10%)");
            mTxtNumberPlate.setText(NumberFormater.longFormat(NUMBER_PLATE_FEE3));
            updateTotalCost(price,registrationFee[0],roadTolls,insurance,numberPlate[0],inspection_fee);
        }
    }

    public class LoadPompetitor extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... strings) {
            Document doc = null;
            String result = "";

            try {
                doc = Jsoup.connect(strings[0]).userAgent("Mozilla").ignoreContentType(true).timeout(10000).get();
                Elements content = doc.getElementsByClass("item_slider");

                for(Element element : content){
                    Elements img = element.getElementsByClass("img_doithu");
                    Elements title = element.getElementsByClass("title_doithu");
                    Elements detail = element.getElementsByClass("detail_competitor");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        context.setHideActionBarSearchItem(false);
        context.getSupportActionBar().setTitle(context.getResources().getString(R.string.cmn_detail_information));
        setHasOptionsMenu(true);
    }

}
