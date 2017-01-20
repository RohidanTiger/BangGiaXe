package tigerstyle.social.com.banggiaxe.view.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
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
import tigerstyle.social.com.banggiaxe.view.adapters.BaseSpinerAdapter;
import tigerstyle.social.com.banggiaxe.view.adapters.HomeMotoAdapter;


/**
 * Created by nextophn on 8/8/15.
 */
public class HomeMotoFragment extends BaseFragment implements SearchingListener{
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

    public static String ARG_OBJ_KEY = "arg-brand-obj";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home_moto, null);
        mRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.rl);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mBrandSpinner = (CustomSpinner) rootView.findViewById(R.id.spiner_brand);
        mPriceSpinner = (CustomSpinner) rootView.findViewById(R.id.spiner_price);
        mBrandDeviationSpinner = (CustomSpinner) rootView.findViewById(R.id.spiner_deviation_price);

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
        requestMotobikeData();
        return rootView;
    }

    private void requestMotobikeData(){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(Contants.URL_XE_MAY).build();
        try {
            Response response = client.newCall(request).execute();
            String htmlContent = response.body().string();
            int startIndex = htmlContent.indexOf("var data = {");
            int lastIndex = htmlContent.indexOf("function showData(data, brand, brand_value, price_value)");
            String data = htmlContent.substring(startIndex+18,lastIndex-1);
            data = "{".concat(data);
            JSONObject jsonObj = new JSONObject(data);
            JSONArray vehicalListIndex = jsonObj.names();

            for(int i = 0; i < vehicalListIndex.length(); i++){
                JSONObject c = jsonObj.getJSONObject(vehicalListIndex.getString(i));

                String id = c.getString("carId");
                String name = c.getString("carName");
                String type = c.getString("carType");
                String brand = c.getString("carBrand");
                String carOrigin = c.getString("carOrigin");
                String price = c.getString("carPrice");
                String priceDeviation = c.getString("carPriceDeviation");
                String engine = c.getString("carEngine");
                String gear = c.getString("carGear");
                String power = c.getString("carPower");
                String moment = c.getString("carMoment");
                String size = c.getString("carSize");
                String fuelTankCapacity = c.getString("carFuelTankCapacity");
                String turningCircle = c.getString("carTurningCircle");
                String imgage = c.getString("carImage");
                MotobikeBrand motobikeBrand = new MotobikeBrand(id,name,type,brand,carOrigin,price,priceDeviation,engine,gear,power,moment,size,fuelTankCapacity,turningCircle,imgage);
                motobikeBrands.add(motobikeBrand);
            }
            Collections.sort(motobikeBrands, new Comparator<MotobikeBrand>() {
                @Override
                public int compare(MotobikeBrand m1, MotobikeBrand m2) {
                    return (Integer.parseInt(m1.getCarID()) > Integer.parseInt(m2.getCarID())) ? 1 : -1;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            hideLoading();
        } catch (JSONException e) {
            e.printStackTrace();
            hideLoading();
        }
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onQueryTextChange(String newText) {
        ArrayList<MotobikeBrand> listResult = new ArrayList<>();
        for(MotobikeBrand brand : motobikeBrands){
            if(brand.getCarName().toLowerCase().contains(newText.toLowerCase())){
                listResult.add(brand);
            }
        }
        mAdapter.setmDataSet(listResult);
    }

    @Override
    public void cancelSearch() {

    }

    private void initSpiner(){
//        mBrandAdapter = new BaseSpinerAdapter(getContext(), R.layout.spinner_item, listBrand);
//        mBrandSpinner.setAdapter(mBrandAdapter);
//
//        mPriceAdapter = new BaseSpinerAdapter(getContext(), R.layout.spinner_item, listPrice);
//        mPriceSpinner.setAdapter(mPriceAdapter);
//
        List<String> listDeviationPrice = Arrays.asList(getResources().getStringArray(R.array.price_array));
//        mPriceDeviationAdapter = new BaseSpinerAdapter(getContext(), R.layout.spinner_item, listDeviationPrice);
//        mBrandDeviationSpinner.setAdapter(mPriceDeviationAdapter);
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
        return listResult;
    }

    @Override
    public void onResume(){
        super.onResume();
        context.getSupportActionBar().setTitle(context.getResources().getString(R.string.cmn_moto_title));
        context.setHideActionBarSearchItem(true);
    }
}
