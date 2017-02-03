package tigerstyle.social.com.banggiaxe.service;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import tigerstyle.social.com.banggiaxe.MainActivity;
import tigerstyle.social.com.banggiaxe.model.CarBrand;

/**
 * Created by billymobile on 2/3/17.
 */

public class OtoDataRequest {
    private MainActivity mContext;
    private DatabaseReference mFirebaseDatabase;

    public OtoDataRequest(MainActivity context, DatabaseReference firebaseDatabase) {
        this.mContext = context;
        this.mFirebaseDatabase = firebaseDatabase;
    }


    public void requestData(final DataChangeListener listener){
        mContext.showLoading();
        final String[] lisData = {""};
        final ArrayList<CarBrand> carBrands = new ArrayList<>();
        Query query = mFirebaseDatabase.child("car");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mContext.hideLoading();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    final String question = postSnapshot.getValue(String.class);
                    lisData[0] = lisData[0].concat(question);
                }
                JSONObject jsonObj = null;
                try {
                    jsonObj = new JSONObject(lisData[0]);
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
                    listener.onDataChange(carBrands);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                mContext.hideLoading();
            }
        });
    }

    public interface DataChangeListener{
        void onDataChange(ArrayList<CarBrand> data);
    }
}
