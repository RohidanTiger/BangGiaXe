package tigerstyle.social.com.banggiaxe.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;

import java.util.List;

import tigerstyle.social.com.banggiaxe.MainActivity;
import tigerstyle.social.com.banggiaxe.R;
import tigerstyle.social.com.banggiaxe.model.ListOldCar;
import tigerstyle.social.com.banggiaxe.utils.PicassoLoader;

import static tigerstyle.social.com.banggiaxe.config.Contants.IMAGE_URL;

/**
 * Created by quydv on 2/7/18.
 */

public class ListOldCarAdapter extends RecyclerView.Adapter{
    private MainActivity mContext;
    private List<ListOldCar> listOldCars;
    private OnItemClickListener listener;

    public ListOldCarAdapter(MainActivity mContext, List<ListOldCar> listOldCars) {
        this.mContext = mContext;
        this.listOldCars = listOldCars;
    }



    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setListOldCars(List<ListOldCar> listOldCars) {
        this.listOldCars = listOldCars;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_header, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ListOldCar key = listOldCars.get(position);
        ((ViewHolder) holder).txtNameValue.setText(key.getCarName());
        ((ViewHolder) holder).txtBrandValue.setText(key.getCarBrand());
        ((ViewHolder) holder).txtTypeValue.setText(key.getCarType());

        if(position > 0 && (position+1) %10 == 0){
            ((ViewHolder) holder).layoutAd.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).mAdView.loadAd(mContext.adRequest);
        }else{
            ((ViewHolder) holder).layoutAd.setVisibility(View.GONE);
        }

        ((ViewHolder) holder).layoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(key);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOldCars.size();
    }

    public interface OnItemClickListener {
        void onClick(ListOldCar position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout layoutContent;
        final TextView txtNameValue;
        final TextView txtBrandValue;
        final TextView txtTypeValue;
        public RelativeLayout layoutAd;
        public AdView mAdView;

        public ViewHolder(View v) {
            super(v);
            layoutContent = (RelativeLayout) v.findViewById(R.id.layout_content);
            this.txtNameValue = (TextView) itemView.findViewById(R.id.txt_name_value);
            this.txtBrandValue = (TextView) itemView.findViewById(R.id.txt_brand_value);
            txtTypeValue = (TextView) itemView.findViewById(R.id.txt_type_value);
            mAdView = (AdView) v.findViewById(R.id.adView);
            layoutAd = (RelativeLayout) v.findViewById(R.id.layout_ad);
        }
    }
}
