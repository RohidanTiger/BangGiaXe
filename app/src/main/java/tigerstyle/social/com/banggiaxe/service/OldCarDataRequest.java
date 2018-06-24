package tigerstyle.social.com.banggiaxe.service;

import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tigerstyle.social.com.banggiaxe.MainActivity;
import tigerstyle.social.com.banggiaxe.R;
import tigerstyle.social.com.banggiaxe.model.ListOldCar;
import tigerstyle.social.com.banggiaxe.model.OldCarObject;

/**
 * Created by billymobile on 12/29/16.
 */

public class OldCarDataRequest extends AsyncTaskLoader<List<ListOldCar>> {
    private MainActivity mContext;

    public OldCarDataRequest(MainActivity context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public void onStartLoading() {
        if (takeContentChanged()) {
            forceLoad();
            //mContext.showLoading();
        }
    }

    @Override
    public List<ListOldCar> loadInBackground() {
        ArrayList<ListOldCar> carBrands = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(mContext.getResources().getString(R.string.new_url_old_oto_data)).build();
        try {
            Response response = client.newCall(request).execute();
            String htmlContent = response.body().string();
            JSONObject jsonObj = new JSONObject(htmlContent).getJSONObject("list_cars_by_brand");
            JSONArray vehicalListIndex = jsonObj.names();

            for (int i = 0; i < vehicalListIndex.length(); i++) {
                JSONObject object = jsonObj.getJSONObject(vehicalListIndex.getString(i));
                JSONArray arrayJo = object.names();

                for (int j = 0; j < arrayJo.length(); j++) {
                    JSONObject listCar = object.getJSONObject(arrayJo.getString(j));

                    String carID = listCar.getString("carId");
                    String carName = listCar.getString("carName");
                    String carBrand = listCar.getString("carBrand");
                    String carType = listCar.getString("carType");
                    String shareUrl = listCar.getString("shareUrl");

                    ListOldCar listOldCar = new ListOldCar(carID,carName,carType,carBrand,
                            shareUrl,getOldCarFromObject(listCar.getJSONObject("listVersions")));

                    carBrands.add(listOldCar);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
            mContext.hideLoading();
        } catch (JSONException e) {
            e.printStackTrace();
            mContext.hideLoading();
        }
        return carBrands;
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
        mContext.hideLoading();
    }

    private List<OldCarObject> getOldCarFromObject(JSONObject object) {
        List<OldCarObject> carObjectList = new ArrayList<>();

        JSONArray arrayVersion = object.names();
        try {
            for (int i = 0; i < arrayVersion.length(); i++) {

                JSONObject carObject = object.optJSONObject(arrayVersion.getString(i));

                String version = carObject.getString("version");
                String image = carObject.getString("image");
                String curent_price = carObject.getString("curent_price");
                String car_origin = carObject.getString("car_origin");
                String car_size = carObject.getString("car_size");
                String tank_capacity = carObject.getString("tank_capacity");
                String car_engine = carObject.getString("car_engine");
                String car_power = carObject.getString("car_power");
                String car_gear = carObject.getString("car_gear");

                OldCarObject oldCar = new OldCarObject(version,image,curent_price,car_origin,
                        car_size,tank_capacity,car_engine,car_power,car_gear);

                carObjectList.add(oldCar);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return carObjectList;
    }
}
