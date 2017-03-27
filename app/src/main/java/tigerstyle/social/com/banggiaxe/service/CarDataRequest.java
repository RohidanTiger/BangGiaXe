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
import tigerstyle.social.com.banggiaxe.config.Contants;
import tigerstyle.social.com.banggiaxe.model.CarBrand;
import tigerstyle.social.com.banggiaxe.utils.ConnectivityReceiver;

/**
 * Created by billymobile on 12/29/16.
 */

public class CarDataRequest extends AsyncTaskLoader<List<CarBrand>> {
    private MainActivity mContext;
    public CarDataRequest(MainActivity context) {
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
    public List<CarBrand> loadInBackground() {
        ArrayList<CarBrand> carBrands = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(mContext.getResources().getString(R.string.url_car_data)).build();
        try {
            Response response = client.newCall(request).execute();
            String htmlContent = response.body().string();
            JSONObject jsonObj = new JSONObject(htmlContent);
            JSONArray vehicalListIndex = jsonObj.names();

            for (int i = 0; i < vehicalListIndex.length(); i++) {

                JSONObject c = jsonObj.getJSONObject(vehicalListIndex.getString(i));
                JSONArray vehicalListName = c.names();

                for (int j = 0; j < vehicalListName.length(); j++) {
                    JSONObject item = c.getJSONObject(vehicalListName.getString(j));
                    String id = item.getString("carId");
                    String name = item.getString("carName");
                    String type = item.getString("carType");
                    String brand = item.getString("carBrand");
                    String carOrigin = item.getString("carOrigin");
                    String price = item.getString("carPrice");
                    String priceDeviation = item.getString("carPriceDeviation");
                    /*JSONArray turnovers = item.getJSONArray("carTurnover");
                    JSONArray turnover1 = turnovers.getJSONArray(0);
                    int[] turnoverVal1 = new int[turnover1.length()];
                    for(int h=0 ; h < turnover1.length() ; h++){
                        turnoverVal1[h] = turnover1.getInt(h);
                    }
                    JSONArray turnover2 = turnovers.getJSONArray(1);
                    int[] turnoverVal2 = new int[turnover2.length()];
                    for(int h=0 ; h < turnover2.length() ; h++){
                        turnoverVal2[h] = turnover2.getInt(h);
                    }*/

                    String engine = item.getString("carEngine");
                    String gear = item.getString("carGear");
                    String power = item.getString("carPower");
                    String moment = item.getString("carMoment");
                    String size = item.getString("carSize");
                    String fuelTankCapacity = item.getString("carFuelTankCapacity");
                    String groundClearance = item.getString("carGroundClearance");
                    String[] competitors = item.getString("carCompetitors").split(",");
                    String turningCircle = item.getString("carTurningCircle");
                    String imgage = item.getString("carImage");
                    String shareUrl = item.getString("shareUrl");

                    CarBrand carBrand = new CarBrand(id,name,type,brand,carOrigin,price,priceDeviation,engine,gear,
                            power,moment,size, fuelTankCapacity,groundClearance,competitors,turningCircle,imgage,shareUrl);
                    carBrands.add(carBrand);
                }


            }
            Collections.sort(carBrands, new Comparator<CarBrand>() {
                @Override
                public int compare(CarBrand m1, CarBrand m2) {
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
        return carBrands;
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
        mContext.hideLoading();
    }
}
