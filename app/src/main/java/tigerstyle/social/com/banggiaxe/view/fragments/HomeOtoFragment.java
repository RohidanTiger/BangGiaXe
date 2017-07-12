package tigerstyle.social.com.banggiaxe.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import tigerstyle.social.com.banggiaxe.BaseFragment;
import tigerstyle.social.com.banggiaxe.R;
import tigerstyle.social.com.banggiaxe.customize.CustomSpinner;
import tigerstyle.social.com.banggiaxe.listener.SearchingListener;
import tigerstyle.social.com.banggiaxe.model.CarBrand;
import tigerstyle.social.com.banggiaxe.service.CarDataRequest;
import tigerstyle.social.com.banggiaxe.utils.ConnectivityReceiver;
import tigerstyle.social.com.banggiaxe.view.adapters.HomeCarAdapter;

/**
 * Created by billymobile on 12/28/16.
 */

public class HomeOtoFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<List<CarBrand>>,SearchingListener,
        ConnectivityReceiver.ConnectivityReceiverListener{

    RelativeLayout mRelativeLayout;
    private RecyclerView mRecyclerView;
    private CustomSpinner mBrandSpinner;
    private CustomSpinner mPriceSpinner;
    private CustomSpinner mCarTypeSpinner;
    private TextView mTxtResult;

    private HomeCarAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<CarBrand> carBrands = new ArrayList<>();

    private int brandSelect = 0;
    private int priceSelect = 0;
    private int typeSelect = 0;
    private List<String> listBrand;
    private List<String> listCarTpe;
    public static String ARG_OBJ_KEY = "arg-brand-obj";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home_car, null);

        mRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.rl);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mBrandSpinner = (CustomSpinner) rootView.findViewById(R.id.spiner_brand);
        mPriceSpinner = (CustomSpinner) rootView.findViewById(R.id.spiner_price);
        mCarTypeSpinner = (CustomSpinner) rootView.findViewById(R.id.spiner_car_type);
        mTxtResult = (TextView) rootView.findViewById(R.id.txt_result);

        // get data from resource
        listBrand = Arrays.asList(getResources().getStringArray(R.array.car_brand_array));
        listCarTpe = Arrays.asList(getResources().getStringArray(R.array.car_type_array));
        mAdapter = new HomeCarAdapter(context,carBrands);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        initSpiner();
        mAdapter.setOnItemClickListener(new HomeCarAdapter.OnItemClickListener() {
            @Override
            public void onClick(final CarBrand brand) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(HomeOtoFragment.ARG_OBJ_KEY,brand);
                context.pushFragments(new CarDetailFragment(),bundle,true,true);
                if (context.mInterstitialAd.isLoaded()) {
                    context.mInterstitialAd.show();
                } else {
                    context.requestNewInterstitial();
                }
//                context.mInterstitialAd.setAdListener(new AdListener() {
//                    @Override
//                    public void onAdClosed() {
//                        context.requestNewInterstitial();
//
//                    }
//                });
            }
        });

        context.getSupportLoaderManager().initLoader(2, null, this).forceLoad();
        return rootView;
    }

    @Override
    public Loader<List<CarBrand>> onCreateLoader(int id, Bundle args) {
        context.showLoading();
        return new CarDataRequest(context);
    }

    @Override
    public void onLoadFinished(Loader<List<CarBrand>> loader, List<CarBrand> data) {
        context.hideLoading();
        this.carBrands = (ArrayList<CarBrand>) data;
        context.setListCar(carBrands);
        mAdapter.setmDataSet(carBrands);
    }

    @Override
    public void onLoaderReset(Loader<List<CarBrand>> loader) {
        context.hideLoading();
        this.carBrands = new ArrayList<>();
        context.setListCar(carBrands);
        mAdapter.setmDataSet(carBrands);
    }

    @Override
    public void onResume(){
        super.onResume();
        context.setConnectivityListener(this);
        if(ConnectivityReceiver.isConnected()){
            mTxtResult.setVisibility(View.GONE);
        }else{
            Toast.makeText(context,context.getResources().getString(R.string.cmn_no_internet_access),Toast.LENGTH_LONG).show();
        }
        context.getSupportActionBar().setTitle(context.getResources().getString(R.string.cmn_oto_title));
        context.setHideActionBarSearchItem(true);
    }

    @Override
    public void onQueryTextChange(String newText) {
        ArrayList<CarBrand> listResult = new ArrayList<>();
        for(CarBrand brand : carBrands){
            if(brand.getCarName().toLowerCase().contains(newText.toLowerCase())){
                listResult.add(brand);
            }
        }
        if(listResult.size() > 0){
            mTxtResult.setVisibility(View.GONE);
        }else{
            mTxtResult.setVisibility(View.VISIBLE);
            mTxtResult.setText(context.getResources().getString(R.string.cmn_no_search_answer));
        }
        mAdapter.setmDataSet(listResult);
    }

    @Override
    public void cancelSearch() {
    }

    private void initSpiner(){
        mBrandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                brandSelect = i;
                mAdapter.setmDataSet(requestSearch());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mPriceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                priceSelect = i;
                mAdapter.setmDataSet(requestSearch());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        mCarTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeSelect = i;
                mAdapter.setmDataSet(requestSearch());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private ArrayList<CarBrand> requestSearch(){
        ArrayList<CarBrand> listResult = new ArrayList<>();
        String brandName = listBrand.get(brandSelect);
        if(brandSelect != 0){
            for(CarBrand brand : carBrands){
                if(brand.getCarBrand().trim().equals(brandName.trim())){
                    listResult.add(brand);
                }
            }
        }else{
            listResult = new ArrayList<>(carBrands);
        }
        Iterator iterator = listResult.iterator();
        switch (priceSelect){
            case 0:{
                break;
            }
            case 1:{
                while (iterator.hasNext()){
                    CarBrand brand = (CarBrand) iterator.next();
                    float price = Float.parseFloat(brand.getCarPrice());
                    if(price < 300f || price > 500.0f){
                        iterator.remove();
                    }
                }
                break;
            }
            case 2:{
                while (iterator.hasNext()){
                    CarBrand brand = (CarBrand) iterator.next();
                    float price = Float.parseFloat(brand.getCarPrice());
                    if(price < 500f || price > 800.0f){
                        iterator.remove();
                    }
                }
                break;
            }
            case 3:{
                while (iterator.hasNext()){
                    CarBrand brand = (CarBrand) iterator.next();
                    float price = Float.parseFloat(brand.getCarPrice().replace(".",""));
                    if(price < 800f || price > 1200.0f){
                        iterator.remove();
                    }
                }

                break;
            }
            case 4:{
                while (iterator.hasNext()){
                    CarBrand brand = (CarBrand) iterator.next();
                    float price = Float.parseFloat(brand.getCarPrice().replace(".",""));
                    if(price < 1200f || price > 1500.0f){
                        iterator.remove();
                    }
                }
                break;
            }
            case 5:{
                while (iterator.hasNext()){
                    CarBrand brand = (CarBrand) iterator.next();
                    float price = Float.parseFloat(brand.getCarPrice().replace(".",""));
                    if(price < 1500f || price > 2000.0f){
                        iterator.remove();
                    }
                }
                break;
            }
            case 6:{
                while (iterator.hasNext()){
                    CarBrand brand = (CarBrand) iterator.next();
                    float price = Float.parseFloat(brand.getCarPrice().replace(".",""));
                    if(price < 2000f || price > 2500.0f){
                        iterator.remove();
                    }
                }
                break;
            }
            case 7:{
                while (iterator.hasNext()){
                    CarBrand brand = (CarBrand) iterator.next();
                    float price = Float.parseFloat(brand.getCarPrice().replace(".",""));
                    if(price < 2500f || price > 3000.0f){
                        iterator.remove();
                    }
                }
                break;
            }
            case 8:{
                while (iterator.hasNext()){
                    CarBrand brand = (CarBrand) iterator.next();
                    float price = Float.parseFloat(brand.getCarPrice().replace(".",""));
                    if(price < 3000f || price > 4000.0f){
                        iterator.remove();
                    }
                }
                break;
            }
            case 9:{
                while (iterator.hasNext()){
                    CarBrand brand = (CarBrand) iterator.next();
                    float price = Float.parseFloat(brand.getCarPrice().replace(".",""));
                    if(price < 4000f || price > 7000.0f){
                        iterator.remove();
                    }
                }
                break;
            }
            case 10:{
                while (iterator.hasNext()){
                    CarBrand brand = (CarBrand) iterator.next();
                    float price = Float.parseFloat(brand.getCarPrice().replace(".",""));
                    if(price < 7000.0f){
                        iterator.remove();
                    }
                }
            }
        }

        ArrayList<CarBrand> finalResult = new ArrayList<>();
        String carType = listCarTpe.get(typeSelect);
        if(typeSelect != 0){
            for(CarBrand brand : listResult){
                if(brand.getCarType().trim().equals(carType.trim())){
                    finalResult.add(brand);
                }
            }
        }else{
            finalResult = listResult;
        }
        if(carBrands.size() > 0){
            if(finalResult.size() > 0){
                mTxtResult.setVisibility(View.GONE);
            }else{
                mTxtResult.setVisibility(View.VISIBLE);
                mTxtResult.setText(context.getResources().getString(R.string.cmn_no_search_answer));
            }
        }
        return finalResult;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(isConnected){
            mTxtResult.setVisibility(View.GONE);
            context.getSupportLoaderManager().initLoader(1, null, this).forceLoad();
        }else{
            Toast.makeText(context,context.getResources().getString(R.string.cmn_no_internet_access),Toast.LENGTH_LONG).show();
        }
    }
}
