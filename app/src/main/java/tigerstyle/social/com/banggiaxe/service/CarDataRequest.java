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

                String id = c.getString("carId");
                String name = c.getString("carName");
                String type = c.getString("carType");
                String brand = c.getString("carBrand");
                String carOrigin = c.getString("carOrigin");
                String price = c.getString("carPrice");
                String priceDeviation = c.getString("carPriceDeviation");
                JSONArray turnovers = c.getJSONArray("carTurnover");
                JSONArray turnover1 = turnovers.getJSONArray(0);
                int[] turnoverVal1 = new int[turnover1.length()];
                for(int j=0 ; j < turnover1.length() ; j++){
                    turnoverVal1[j] = turnover1.getInt(j);
                }
                JSONArray turnover2 = turnovers.getJSONArray(1);
                int[] turnoverVal2 = new int[turnover2.length()];
                for(int j=0 ; j < turnover2.length() ; j++){
                    turnoverVal2[j] = turnover2.getInt(j);
                }

                String engine = c.getString("carEngine");
                String gear = c.getString("carGear");
                String power = c.getString("carPower");
                String moment = c.getString("carMoment");
                String size = c.getString("carSize");
                String fuelTankCapacity = c.getString("carFuelTankCapacity");
                String groundClearance = c.getString("carGroundClearance");
                String[] competitors = c.getString("carCompetitors").split(",");
                String turningCircle = c.getString("carTurningCircle");
                String imgage = c.getString("carImage");
                String shareUrl = c.getString("shareUrl");

                CarBrand carBrand = new CarBrand(id,name,type,brand,carOrigin,price,priceDeviation,
                                        turnoverVal1,turnoverVal2,engine,gear,power,moment,size,
                                        fuelTankCapacity,groundClearance,competitors,turningCircle,imgage,shareUrl);
                carBrands.add(carBrand);
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
