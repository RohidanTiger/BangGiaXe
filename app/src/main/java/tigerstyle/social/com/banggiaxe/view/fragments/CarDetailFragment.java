package tigerstyle.social.com.banggiaxe.view.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tigerstyle.social.com.banggiaxe.BaseFragment;
import tigerstyle.social.com.banggiaxe.R;
import tigerstyle.social.com.banggiaxe.customize.CarouselPageTransformer;
import tigerstyle.social.com.banggiaxe.customize.CustomSpinner;
import tigerstyle.social.com.banggiaxe.customize.PagerContainer;
import tigerstyle.social.com.banggiaxe.customize.SuffixTextView;
import tigerstyle.social.com.banggiaxe.customize.TransformableViewPager;
import tigerstyle.social.com.banggiaxe.model.CarBrand;
import tigerstyle.social.com.banggiaxe.utils.NumberFormater;
import tigerstyle.social.com.banggiaxe.utils.PicassoLoader;
import tigerstyle.social.com.banggiaxe.view.adapters.CompetitorAdapter;
import tigerstyle.social.com.banggiaxe.view.adapters.HomeCarAdapter;
import tigerstyle.social.com.banggiaxe.view.dialog.AreaInforDialog;
import tigerstyle.social.com.banggiaxe.view.dialog.ComparisonDialog;

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

/**
 * Created by billymobile on 12/30/16.
 */

public class CarDetailFragment extends BaseFragment implements OnChartValueSelectedListener {
    private ImageView mImageHeader;
    private CarBrand carBrand;
    private TextView mTxtMotoName;
    private TextView mTxtBrandName;
    private SuffixTextView mTxtPrice;
    private SuffixTextView mTxtDeviationPrice;
    private TextView mTxtSizeValue;
    private TextView mTxtFuelCapacity;
    private TextView mTxtDisplacement;
    private TextView mTxtOutputCapacity;
    private TextView mTxtMoment;
    private TextView mTxtGrossWeight;
    private TextView mTxtOrigin;
    private TextView mTxtTypeVehical;
    private TextView mTxtNumberOfGears;
    private BarChart mChart;
    private TextView mTxtDeviationPrice2;
    private SuffixTextView mTxtRegistrationTitle;
    private TextView mTxtRegistrationFee;
    private TextView mTxtRoadTolls;
    private TextView mTxtInsurance;
    private TextView mTxtNumberPlate;
    private TextView mTxtGroundClearance;
    private TextView mTxtInspectionFee;
    private TextView mTxtTotalCost;
    private SuffixTextView mTxtAreaTitle;
    private CustomSpinner mSpinnerArea;
    private LinearLayout mLayoutPompetitors;
    private AdView mAdView;

    BarDataSet set1, set2;
    private List<String> listMonth;
    private ArrayList<CarBrand> listCar;
    PagerContainer mContainer;
    TransformableViewPager pager;
    private long price;
    private long deviationPrice;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_car_detail, null);
        mImageHeader = (ImageView) rootView.findViewById(R.id.img_vehical);
        mTxtMotoName = (TextView) rootView.findViewById(R.id.txtVehicalName);
        mTxtBrandName = (TextView) rootView.findViewById(R.id.txtBrand);
        mTxtPrice = (SuffixTextView) rootView.findViewById(R.id.txtPrice);
        mTxtDeviationPrice = (SuffixTextView) rootView.findViewById(R.id.txtDeviationPrice);
        mTxtSizeValue = (TextView) rootView.findViewById(R.id.txt_size_value);
        mTxtFuelCapacity = (TextView) rootView.findViewById(R.id.txt_fuel_capacity_value);
        mTxtDisplacement = (TextView) rootView.findViewById(R.id.txt_displacement_value);
        mTxtOutputCapacity = (TextView) rootView.findViewById(R.id.txt_output_capacity_value);
        mTxtMoment = (TextView) rootView.findViewById(R.id.txt_torque_power_value);
        mTxtGrossWeight = (TextView) rootView.findViewById(R.id.txt_gross_weight_value);
        mTxtGroundClearance = (TextView) rootView.findViewById(R.id.txt_ground_clearance_value);
        mTxtOrigin = (TextView) rootView.findViewById(R.id.txt_origin_value);
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
        mLayoutPompetitors = (LinearLayout) rootView.findViewById(R.id.layout_pompetitors);
        mSpinnerArea = (CustomSpinner) rootView.findViewById(R.id.spinner_area);

        mChart = (BarChart) rootView.findViewById(R.id.chart);

        mContainer = (PagerContainer) rootView.findViewById(R.id.pager_container);
        pager = mContainer.getViewPager();

        listMonth = Arrays.asList(getResources().getStringArray(R.array.month_array));
        listCar = context.getListCar();

        carBrand = (CarBrand) getArguments().getParcelable(HomeOtoFragment.ARG_OBJ_KEY);
        fillData();
        fillTotalCost();
        //fillChartData();
        showPompetitor();
        mAdView = (AdView) rootView.findViewById(R.id.adView);
        mAdView.loadAd(context.adRequest);
        return rootView;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    private void fillData() {
        String urlImage = IMAGE_URL + carBrand.getCarImage();
        PicassoLoader.getInstance(context).load(urlImage).placeholder(R.drawable.bg_captcha).
                error(R.drawable.bg_captcha).into(mImageHeader);
        String priceString = carBrand.getCarPrice().replaceAll("\\.","").trim();
        price = Long.parseLong(priceString);
        deviationPrice = Long.parseLong(carBrand.getCarPriceDeviation().replaceAll("\\.",""));
        mTxtMotoName.setText(carBrand.getCarName());
        mTxtBrandName.setText(carBrand.getCarBrand());
        mTxtPrice.setText(NumberFormater.longFormat(price* ONE_MILLION).concat("đ"));
        mTxtDeviationPrice.setText(NumberFormater.longFormat(deviationPrice* ONE_MILLION).concat("đ"));
        mTxtSizeValue.setText(carBrand.getCarSize());
        mTxtFuelCapacity.setText(carBrand.getCarFuelTankCapacity());
        mTxtDisplacement.setText(carBrand.getCarEngine());
        mTxtOutputCapacity.setText(carBrand.getCarPower());
        mTxtMoment.setText(carBrand.getCarMoment());
        mTxtGrossWeight.setText(carBrand.getCarTurningCirclel());
        mTxtGroundClearance.setText(carBrand.getCarGroundClearance());
        mTxtOrigin.setText(carBrand.getCarOrigin());
        mTxtTypeVehical.setText(carBrand.getCarType());
        mTxtNumberOfGears.setText(carBrand.getCarGear());
    }

    private void fillChartData() {
        float groupSpace = 0.1f;
        float barSpace = 0.02f; // x4 DataSet
        float barWidth = 0.4f;

        mChart.setPinchZoom(false);

        mChart.setDrawBarShadow(false);

        mChart.setDrawGridBackground(false);

        mChart.setFitBars(true);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setTypeface(mTfLight);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTypeface(mTfLight);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setAxisMinimum(0f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(listMonth));
        xAxis.setLabelRotationAngle(60);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(6);
        xAxis.setDrawGridLines(false);


        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(mTfLight);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        mChart.getAxisRight().setEnabled(false);

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
        for (int i = 0; i < 12; i++) {
            yVals1.add(new BarEntry(i, carBrand.getCarTurnover1()[i]));
            yVals2.add(new BarEntry(i, carBrand.getCarTurnover2()[i]));
        }
        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) mChart.getData().getDataSetByIndex(1);

            set1.setValues(yVals1);
            set2.setValues(yVals2);

            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();

        } else {
            set1 = new BarDataSet(yVals1, "2015");
            set1.setColor(Color.rgb(104, 241, 175));
            set2 = new BarDataSet(yVals2, "2016");
            set2.setColor(Color.rgb(164, 228, 251));

            dataSets.add(set1);dataSets.add(set2);
            BarData data = new BarData(set1, set2);
            data.setValueFormatter(new LargeValueFormatter());
            data.setValueTypeface(mTfLight);
            mChart.setData(data);
        }
        mChart.getXAxis().setAxisMinimum(0);
        mChart.getBarData().setBarWidth(barWidth);
        mChart.groupBars(0, groupSpace, barSpace);
        mChart.invalidate();

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
    }

    @Override
    public void onNothingSelected() {
    }

    private void setUpSpinner(){
        ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>
                (context, R.layout.spinner_item,Arrays.asList(getResources().getStringArray(R.array.area_array)));
        areaAdapter.setDropDownViewResource(R.layout.spinner_item);
        mSpinnerArea.setAdapter(areaAdapter);
    }

    private void fillTotalCost(){
        //setUpSpinner();
        mTxtAreaTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AreaInforDialog dialog = new AreaInforDialog(context,Arrays.asList(getResources().getStringArray(R.array.area_array)),
                        Arrays.asList(getResources().getStringArray(R.array.area_content_array)));
                dialog.show();
            }
        });
        mTxtDeviationPrice2.setText(NumberFormater.longFormat(deviationPrice * ONE_MILLION));
        final long[] registrationFee = {deviationPrice * ONE_MILLION / 100};
        final long roadTolls = ROAD_TOLLS;
        mTxtRoadTolls.setText(NumberFormater.longFormat(ROAD_TOLLS));

        final long insurance;
        if(this.carBrand.getCarType().equals("Pick-up")){
            insurance = INSURANCE3;
            mTxtInsurance.setText(NumberFormater.longFormat(INSURANCE3));
        }else if(this.carBrand.getCarType().equals("SUV") && this.carBrand.getCarBrand().equals("Honda")){
            insurance = INSURANCE2;
            mTxtInsurance.setText(NumberFormater.longFormat(INSURANCE2));
        }else{
            insurance = INSURANCE1;
            mTxtInsurance.setText(NumberFormater.longFormat(INSURANCE1));
        }
        final long[] numberPlate = new long[1];
        final long inspection_fee = INSPECTION_FEE;
        mTxtInspectionFee.setText(NumberFormater.longFormat(INSPECTION_FEE));

        mSpinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    registrationFee[0] = deviationPrice * ONE_MILLION/100*12;
                    mTxtRegistrationFee.setText(NumberFormater.longFormat(registrationFee[0]));
                    numberPlate[0] = NUMBER_PLATE_FEE1;
                    mTxtRegistrationTitle.setText("Phí trước bạ (12%)");
                    mTxtNumberPlate.setText(NumberFormater.longFormat(NUMBER_PLATE_FEE1));
                    updateTotalCost(deviationPrice,registrationFee[0],roadTolls,insurance,numberPlate[0],inspection_fee);
                }else if(i == 1){
                    registrationFee[0] = deviationPrice * ONE_MILLION/10;
                    mTxtRegistrationFee.setText(NumberFormater.longFormat(registrationFee[0]));
                    numberPlate[0] = NUMBER_PLATE_FEE2;
                    mTxtRegistrationTitle.setText("Phí trước bạ (10%)");
                    mTxtNumberPlate.setText(NumberFormater.longFormat(NUMBER_PLATE_FEE2));
                    updateTotalCost(deviationPrice,registrationFee[0],roadTolls,insurance,numberPlate[0],inspection_fee);
                }else {
                    registrationFee[0] = deviationPrice * ONE_MILLION/10;
                    mTxtRegistrationFee.setText(NumberFormater.longFormat(registrationFee[0]));
                    numberPlate[0] = NUMBER_PLATE_FEE3;
                    mTxtRegistrationTitle.setText("Phí trước bạ (10%)");
                    mTxtNumberPlate.setText(NumberFormater.longFormat(NUMBER_PLATE_FEE3));
                    updateTotalCost(deviationPrice,registrationFee[0],roadTolls,insurance,numberPlate[0],inspection_fee);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void updateTotalCost(long deviationPrice,long registrationFee, long roadTolls,
                                 long insurance, long numberPlate, long inspectionFee){
        long total_cost = deviationPrice* ONE_MILLION + registrationFee
                          + roadTolls + insurance + numberPlate + inspectionFee;
        mTxtTotalCost.setText(NumberFormater.longFormat(total_cost).concat("đ"));
    }

    private void showPompetitor(){
        pager.setOffscreenPageLimit(5);
        pager.setPageTransformer(true, new CarouselPageTransformer());

        //If hardware acceleration is enabled, you should also remove
        // clipping on the pager for its children.
        pager.setClipChildren(false);
        pager.setPageMargin(0);
        String[] arrayID = this.carBrand.getCarCompetitors();
        if(arrayID.length > 0){
            ArrayList<CarBrand> listPompetitor = new ArrayList<>();
            if(arrayID.length > 0){
                for(int i = 0; i < arrayID.length; i++){
                    for(CarBrand brand : listCar){
                        if(brand.getCarID().equals(arrayID[i].trim())){
                            listPompetitor.add(brand);
                            break;
                        }
                    }
                }
            }

            CompetitorAdapter adapter = new CompetitorAdapter(context,listPompetitor);
            pager.setAdapter(adapter);
            adapter.setOnItemClickListener(new HomeCarAdapter.OnItemClickListener() {
                @Override
                public void onClick(CarBrand brand) {
                    ComparisonDialog dialog = new ComparisonDialog(context,carBrand,brand);
                    dialog.show();
                }
            });
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
