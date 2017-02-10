package tigerstyle.social.com.banggiaxe.view.fragments;

import android.os.Bundle;
import android.os.Looper;
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

import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tigerstyle.social.com.banggiaxe.BaseFragment;
import tigerstyle.social.com.banggiaxe.R;
import tigerstyle.social.com.banggiaxe.config.Contants;
import tigerstyle.social.com.banggiaxe.customize.CustomSpinner;
import tigerstyle.social.com.banggiaxe.listener.SearchingListener;
import tigerstyle.social.com.banggiaxe.model.MotobikeBrand;
import tigerstyle.social.com.banggiaxe.service.MotoDataRequest;
import tigerstyle.social.com.banggiaxe.utils.ConnectivityReceiver;
import tigerstyle.social.com.banggiaxe.utils.Logger;
import tigerstyle.social.com.banggiaxe.view.adapters.BaseSpinerAdapter;
import tigerstyle.social.com.banggiaxe.view.adapters.HomeMotoAdapter;


/**
 * Created by nextophn on 8/8/15.
 */
public class HomeMotoFragment extends BaseFragment implements SearchingListener,LoaderManager.LoaderCallbacks<List<MotobikeBrand>>,
        ConnectivityReceiver.ConnectivityReceiverListener{
    RelativeLayout mRelativeLayout;
    private RecyclerView mRecyclerView;
    private CustomSpinner mBrandSpinner;
    private CustomSpinner mPriceSpinner;
    private CustomSpinner mBrandDeviationSpinner;

    private HomeMotoAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MotobikeBrand> motobikeBrands = new ArrayList<>();

    private BaseSpinerAdapter mBrandAdapter;
    private BaseSpinerAdapter mPriceAdapter;
    private BaseSpinerAdapter mPriceDeviationAdapter;
    private int brandSelect = 0;
    private int priceSelect = 0;
    private List<String> listBrand;
    private List<String> listPrice;
    private TextView mTxtResult;

    public static String ARG_OBJ_KEY = "arg-brand-obj";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home_moto, null);
        mRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.rl);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mBrandSpinner = (CustomSpinner) rootView.findViewById(R.id.spiner_brand);
        mPriceSpinner = (CustomSpinner) rootView.findViewById(R.id.spiner_price);
        mBrandDeviationSpinner = (CustomSpinner) rootView.findViewById(R.id.spiner_deviation_price);
        mTxtResult = (TextView) rootView.findViewById(R.id.txt_result);

        // get data from resource
        listBrand = Arrays.asList(getResources().getStringArray(R.array.brand_array));
        listPrice = Arrays.asList(getResources().getStringArray(R.array.price_array));

        initSpiner();
        mAdapter = new HomeMotoAdapter(context,motobikeBrands);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new HomeMotoAdapter.OnItemClickListener() {
            @Override
            public void onClick(MotobikeBrand brand) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(HomeMotoFragment.ARG_OBJ_KEY,brand);
                context.pushFragments(new MotoDetailFragment(),bundle,true,true);
            }
        });

        context.getSupportLoaderManager().initLoader(1, null, this).forceLoad();
        return rootView;
    }


    @Override
    public void onQueryTextChange(String newText) {
        ArrayList<MotobikeBrand> listResult = new ArrayList<>();
        for(MotobikeBrand brand : motobikeBrands){
            if(brand.getCarName().toLowerCase().contains(newText.toLowerCase())){
                listResult.add(brand);
            }
        }
        if(motobikeBrands.size() > 0){
            if(listResult.size() > 0){
                mTxtResult.setVisibility(View.GONE);
            }else{
                mTxtResult.setVisibility(View.VISIBLE);
                mTxtResult.setText(context.getResources().getString(R.string.cmn_no_search_answer));
            }
        }
        mAdapter.setmDataSet(listResult);
    }

    @Override
    public void cancelSearch() {

    }

    private void initSpiner(){
        List<String> listDeviationPrice = Arrays.asList(getResources().getStringArray(R.array.price_array));
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

        mBrandDeviationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private ArrayList<MotobikeBrand> requestSearch(){
        ArrayList<MotobikeBrand> listResult = new ArrayList<>();
        String brandName = listBrand.get(brandSelect);
        if(brandSelect != 0){
            for(MotobikeBrand brand : motobikeBrands){
                if(brand.getCarBrand().trim().equals(brandName.trim())){
                    listResult.add(brand);
                }
            }
        }else{
            listResult = new ArrayList<>(motobikeBrands);
        }
        Iterator iterator = listResult.iterator();
        switch (priceSelect){
            case 0:{
                break;
            }
            case 1:{
                while (iterator.hasNext()){
                    MotobikeBrand brand = (MotobikeBrand) iterator.next();
                    float price = Float.parseFloat(brand.getCarPrice());
                    if(price > 20.0f){
                        iterator.remove();
                    }
                }
                break;
            }
            case 2:{
                while (iterator.hasNext()){
                    MotobikeBrand brand = (MotobikeBrand) iterator.next();
                    float price = Float.parseFloat(brand.getCarPrice());
                    if(price < 20.0f || price > 30.0f){
                        iterator.remove();
                    }
                }
                break;
            }
            case 3:{
                while (iterator.hasNext()){
                    MotobikeBrand brand = (MotobikeBrand) iterator.next();
                    float price = Float.parseFloat(brand.getCarPrice());
                    if(price < 30.0f || price > 40.0f){
                        iterator.remove();
                    }
                }

                break;
            }
            case 4:{
                while (iterator.hasNext()){
                    MotobikeBrand brand = (MotobikeBrand) iterator.next();
                    float price = Float.parseFloat(brand.getCarPrice());
                    if(price < 40.0f || price > 50.0f){
                        iterator.remove();
                    }
                }
                break;
            }
            case 5:{
                while (iterator.hasNext()){
                    MotobikeBrand brand = (MotobikeBrand) iterator.next();
                    float price = Float.parseFloat(brand.getCarPrice());
                    if(price < 60.0f || price > 80.0f){
                        iterator.remove();
                    }
                }
                break;
            }
            case 6:{
                while (iterator.hasNext()){
                    MotobikeBrand brand = (MotobikeBrand) iterator.next();
                    float price = Float.parseFloat(brand.getCarPrice());
                    if(price < 80.0f){
                        iterator.remove();
                    }
                }
            }
        }
        if(motobikeBrands.size() > 0){
            if(listResult.size() > 0){
                mTxtResult.setVisibility(View.GONE);
            }else{
                mTxtResult.setVisibility(View.VISIBLE);
                mTxtResult.setText(context.getResources().getString(R.string.cmn_no_search_answer));
            }
        }
        return listResult;
    }

    @Override
    public void onResume(){
        super.onResume();
        context.getSupportActionBar().setTitle(context.getResources().getString(R.string.cmn_moto_title));
        context.setHideActionBarSearchItem(true);
        context.setConnectivityListener(this);
        if(ConnectivityReceiver.isConnected()){
            mTxtResult.setVisibility(View.GONE);
        }else{
            Toast.makeText(context,context.getResources().getString(R.string.cmn_no_internet_access),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public Loader<List<MotobikeBrand>> onCreateLoader(int id, Bundle args) {
        return new MotoDataRequest(context);
    }

    @Override
    public void onLoadFinished(Loader<List<MotobikeBrand>> loader, List<MotobikeBrand> data) {
        context.hideLoading();
        this.motobikeBrands = (ArrayList<MotobikeBrand>) data;
        context.setListMoto(motobikeBrands);
        mAdapter.setmDataSet(motobikeBrands);
    }

    @Override
    public void onLoaderReset(Loader<List<MotobikeBrand>> loader) {
        context.hideLoading();
        this.motobikeBrands = new ArrayList<>();
        context.setListMoto(motobikeBrands);
        mAdapter.setmDataSet(motobikeBrands);
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
