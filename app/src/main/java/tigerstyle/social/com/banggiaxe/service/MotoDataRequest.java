package tigerstyle.social.com.banggiaxe.service;

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

import tigerstyle.social.com.banggiaxe.MainActivity;
import tigerstyle.social.com.banggiaxe.model.MotobikeBrand;

/**
 * Created by billymobile on 2/3/17.
 */

public class MotoDataRequest{
    private MainActivity mContext;
    private DatabaseReference mFirebaseDatabase;

    public MotoDataRequest(MainActivity context, DatabaseReference firebaseDatabase) {
        this.mContext = context;
        this.mFirebaseDatabase = firebaseDatabase;
    }


    public void requestData(final DataChangeListener listener){
        mContext.showLoading();
        final String[] lisData = {""};
        final ArrayList<MotobikeBrand> motobikeBrands = new ArrayList<>();
        Query query = mFirebaseDatabase.child("moto");
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
                    listener.onDataChange(motobikeBrands);
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
        void onDataChange(ArrayList<MotobikeBrand> data);
    }
}
