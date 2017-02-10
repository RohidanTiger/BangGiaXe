package tigerstyle.social.com.banggiaxe.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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

        Request request = new Request.Builder().url(mContext.getResources().getString(R.string.url_moto_data)).build();
        try {
            Response response = client.newCall(request).execute();
            String htmlContent = response.body().string();
            JSONObject jsonObj = new JSONObject(htmlContent);
            JSONArray vehicalListIndex = jsonObj.names();

            for (int i = 0; i < vehicalListIndex.length(); i++) {
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
                motoBrands.add(motobikeBrand);
            }
            Collections.sort(motoBrands, new Comparator<MotobikeBrand>() {
                @Override
                public int compare(MotobikeBrand m1, MotobikeBrand m2) {
                    return (Integer.parseInt(m1.getCarID()) > Integer.parseInt(m2.getCarID())) ? 1 : -1;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            mContext.hideLoading();
        } catch (JSONException e) {
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
