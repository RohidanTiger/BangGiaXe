package tigerstyle.social.com.banggiaxe.view.fragments;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import tigerstyle.social.com.banggiaxe.BaseFragment;
import tigerstyle.social.com.banggiaxe.R;
import tigerstyle.social.com.banggiaxe.customize.CustomSpinner;
import tigerstyle.social.com.banggiaxe.customize.SpacesItemDecoration;
import tigerstyle.social.com.banggiaxe.listener.SearchingListener;
import tigerstyle.social.com.banggiaxe.model.ListOldCar;
import tigerstyle.social.com.banggiaxe.service.OldCarDataRequest;
import tigerstyle.social.com.banggiaxe.utils.ConnectivityReceiver;
import tigerstyle.social.com.banggiaxe.view.adapters.ListOldCarAdapter;
import tigerstyle.social.com.banggiaxe.view.adapters.OldCarAdapter;

/**
 * Created by quydv on 1/31/18.
 */

public class OldCarFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<List<ListOldCar>>,SearchingListener,
        ConnectivityReceiver.ConnectivityReceiverListener{

    private List<ListOldCar> listOldCars;
    private RecyclerView mRecyclerView;
    private ListOldCarAdapter mAdapter;

    private CustomSpinner mSpinnerBrand;

    private CustomSpinner mSpinnerCarType;

    private int brandSelect = 0;
    private int typeSelect = 0;

    private List<String> listBrand;
    private List<String> listCarTpe;

    private int show_ad = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home_old_car, null);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        mSpinnerBrand = (CustomSpinner) rootView.findViewById(R.id.spiner_brand);

        mSpinnerCarType = (CustomSpinner) rootView.findViewById(R.id.spiner_car_type);

        listBrand = Arrays.asList(getResources().getStringArray(R.array.car_brand_array));
        listCarTpe = Arrays.asList(getResources().getStringArray(R.array.car_type_array));

        listOldCars = new ArrayList<>();

        mAdapter = new ListOldCarAdapter(context,listOldCars);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(context, R.dimen.padding_small);
        mRecyclerView.addItemDecoration(itemDecoration);

        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setListener(new ListOldCarAdapter.OnItemClickListener() {
            @Override
            public void onClick(ListOldCar position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(OldCarDetailFragment.ARG_OBJ_KEY,position);
                context.pushFragments(new OldCarDetailFragment(),bundle,true,true);

                if (context.mInterstitialAd.isLoaded()) {
                    context.mInterstitialAd.show();
                } else {
                    if(show_ad < 3) show_ad++;
                    else {
                        show_ad = 0;
                        context.requestNewInterstitial();
                    }
                }
            }
        });

        initSpiner();

        context.getSupportLoaderManager().initLoader(3, null, this).forceLoad();
        return rootView;
    }

    public void setListOldCars(List<ListOldCar> listOldCars) {
        this.listOldCars = listOldCars;
    }

    private void initSpiner(){
        mSpinnerBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                brandSelect = i;
                mAdapter.setListOldCars(requestSearch());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpinnerCarType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeSelect = i;
                mAdapter.setListOldCars(requestSearch());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private ArrayList<ListOldCar> requestSearch(){
        ArrayList<ListOldCar> listResult = new ArrayList<>();
        String brandName = listBrand.get(brandSelect);
        if(brandSelect != 0){
            for(ListOldCar brand : listOldCars){
                if(brand.getCarBrand().trim().equals(brandName.trim())){
                    listResult.add(brand);
                }
            }
        }else{
            listResult = new ArrayList<>(listOldCars);
        }
        Iterator iterator = listResult.iterator();

        ArrayList<ListOldCar> finalResult = new ArrayList<>();
        String carType = listCarTpe.get(typeSelect);
        if(typeSelect != 0){
            for(ListOldCar brand : listResult){
                if(brand.getCarType().trim().equals(carType.trim())){
                    finalResult.add(brand);
                }
            }
        }else{
            finalResult = listResult;
        }
//        if(listOldCars.size() > 0){
//            if(finalResult.size() > 0){
//                mTxtResult.setVisibility(View.GONE);
//            }else{
//                mTxtResult.setVisibility(View.VISIBLE);
//                mTxtResult.setText(context.getResources().getString(R.string.cmn_no_search_answer));
//            }
//        }
        return finalResult;
    }

    @Override
    public Loader<List<ListOldCar>> onCreateLoader(int id, Bundle args) {
        context.showLoading();
        return new OldCarDataRequest(context);
    }

    @Override
    public void onLoadFinished(Loader<List<ListOldCar>> loader, List<ListOldCar> data) {
        context.hideLoading();
        listOldCars = data;
        mAdapter.setListOldCars(listOldCars);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<ListOldCar>> loader) {
        context.hideLoading();
    }

    @Override
    public void onQueryTextChange(String newText) {
        ArrayList<ListOldCar> listResult = new ArrayList<>();
        for(ListOldCar brand : listOldCars){
            if(brand.getCarName().toLowerCase().contains(newText.toLowerCase())){
                listResult.add(brand);
            }
        }
        mAdapter.setListOldCars(listResult);
    }

    @Override
    public void cancelSearch() {

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(isConnected){
            context.getSupportLoaderManager().initLoader(1, null, this).forceLoad();
        }else{
            Toast.makeText(context,context.getResources().getString(R.string.cmn_no_internet_access),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        context.setConnectivityListener(this);
        if(ConnectivityReceiver.isConnected()){
        }else{
            Toast.makeText(context,context.getResources().getString(R.string.cmn_no_internet_access),Toast.LENGTH_LONG).show();
        }
        context.getSupportActionBar().setTitle(context.getResources().getString(R.string.cmn_old_oto_title));
        context.setHideActionBarSearchItem(true);
    }
}
