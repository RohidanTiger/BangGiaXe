package tigerstyle.social.com.banggiaxe.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tigerstyle.social.com.banggiaxe.MainActivity;
import tigerstyle.social.com.banggiaxe.R;
import tigerstyle.social.com.banggiaxe.model.MotobikeBrand;

/**
 * Created by billymobile on 2/3/17.
 */

public class MotoDataRequest extends AsyncTaskLoader<List<MotobikeBrand>>{
    private MainActivity mContext;

    public MotoDataRequest(MainActivity context) {
        super(context);
        this.mContext = context;
    }
    @Override
    public void onStartLoading() {
        if (takeContentChanged()) {
            forceLoad();
            mContext.showLoading();
        }
    }


    @Override
    public List<MotobikeBrand> loadInBackground() {
        ArrayList<MotobikeBrand> motoBrands = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        try {
            Request request = new Request.Builder().url(mContext.getResources().getString(R.string.new_url_moto_data)).build();
            Response response = client.newCall(request).execute();
            String htmlContent = response.body().string();
            Log.d("MotoData",htmlContent);
            //JSONObject jsonObj = new JSONObject(htmlContent);
            JSONArray vehicalListIndex = new JSONArray(htmlContent);

            for (int i = 0; i < vehicalListIndex.length(); i++) {
                try {
                    //JSONObject c = jsonObj.getJSONObject(vehicalListIndex.getString(i));
                    JSONObject c = vehicalListIndex.getJSONObject(i);
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
                    motoBrands.add(motobikeBrand);
                }catch (JSONException e) {
                    e.printStackTrace();
                    mContext.hideLoading();
                }
            }
            Collections.sort(motoBrands, new Comparator<MotobikeBrand>() {
                @Override
                public int compare(MotobikeBrand m1, MotobikeBrand m2) {
                    return (Integer.parseInt(m1.getCarID()) > Integer.parseInt(m2.getCarID())) ? 1 : -1;
                }
            });

            Log.d("MotoData",vehicalListIndex.length()+"");
//            for(MotobikeBrand motobikeBrand : motoBrands){
//                if(motobikeBrand.getCarBrand().equalsIgnoreCase("honda")) {
//                    int id = Integer.parseInt(motobikeBrand.getCarID());
//                    motobikeBrand.setCarID(String.valueOf(id-150));
//                }else if(motobikeBrand.getCarBrand().equalsIgnoreCase("yamaha")) {
//                    int id = Integer.parseInt(motobikeBrand.getCarID());
//                    motobikeBrand.setCarID(String.valueOf(id+50));
//                }else if(motobikeBrand.getCarBrand().equalsIgnoreCase("suzuki")) {
//                    int id = Integer.parseInt(motobikeBrand.getCarID());
//                    motobikeBrand.setCarID(String.valueOf(id+100));
//                }else if(motobikeBrand.getCarBrand().equalsIgnoreCase("piaggio")) {
//                    int id = Integer.parseInt(motobikeBrand.getCarID());
//                    motobikeBrand.setCarID(String.valueOf(id+150));
//                }else if(motobikeBrand.getCarBrand().equalsIgnoreCase("vespa")) {
//                    int id = Integer.parseInt(motobikeBrand.getCarID());
//                    motobikeBrand.setCarID(String.valueOf(id+200));
//                }
//            }

            String tata = new Gson().toJson(motoBrands);
        } catch (IOException e) {
            e.printStackTrace();
            mContext.hideLoading();
        } catch (Exception e) {
            e.printStackTrace();
            mContext.hideLoading();
        }
        return motoBrands;
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
        mContext.hideLoading();
    }
}
