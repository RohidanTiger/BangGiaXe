package tigerstyle.social.com.banggiaxe.view.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import tigerstyle.social.com.banggiaxe.MainActivity;
import tigerstyle.social.com.banggiaxe.R;
import tigerstyle.social.com.banggiaxe.model.MotobikeBrand;
import tigerstyle.social.com.banggiaxe.utils.PicassoLoader;

import static tigerstyle.social.com.banggiaxe.config.Contants.IMAGE_URL;


/**
 * Created by billymobile on 12/6/16.
 */

public class HomeMotoAdapter extends RecyclerView.Adapter {
    private ArrayList<MotobikeBrand> mDataSet;
    private MainActivity mContext;
    private OnItemClickListener listener;

    public HomeMotoAdapter(MainActivity context, ArrayList<MotobikeBrand> dataSet) {
        this.mDataSet = dataSet;
        this.mContext = context;
    }

    public void setmDataSet(ArrayList<MotobikeBrand> mData) {
        this.mDataSet = mData;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        listener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.home_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MotobikeBrand brand = mDataSet.get(position);
        if(position > 0 && position %10 == 0){
            ((ViewHolder) holder).layoutAd.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).mAdView.loadAd(mContext.adRequest);
        }else{
            ((ViewHolder) holder).layoutAd.setVisibility(View.GONE);
        }
        ((HomeMotoAdapter.ViewHolder) holder).textViewName.setText(brand.getCarName());
        ((HomeMotoAdapter.ViewHolder) holder).textViewBrand.setText(brand.getCarBrand());
        ((HomeMotoAdapter.ViewHolder) holder).textViewPrice.setText(brand.getCarPrice());
        ((HomeMotoAdapter.ViewHolder) holder).textViewDeviationPrice.setText(brand.getCarPriceDeviation());
        String urlImage = IMAGE_URL + brand.getCarImage();
        PicassoLoader.getInstance(mContext).load(urlImage).placeholder(R.drawable.bg_captcha).
                error(R.drawable.bg_captcha).into((((HomeMotoAdapter.ViewHolder) holder).imgVehical));
        ((HomeMotoAdapter.ViewHolder) holder).layoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(brand);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout layoutContent;
        public ImageView imgVehical;
        public TextView textViewName;
        public TextView textViewBrand;
        public TextView textViewPrice;
        public TextView textViewDeviationPrice;
        public RelativeLayout layoutAd;
        public AdView mAdView;

        public ViewHolder(View v) {
            super(v);
            layoutContent = (RelativeLayout) v.findViewById(R.id.layout_content);
            imgVehical = (ImageView) v.findViewById(R.id.imgVehical);
            textViewName = (TextView) v.findViewById(R.id.txtName);
            textViewBrand = (TextView) v.findViewById(R.id.txtBrand);
            textViewPrice = (TextView) v.findViewById(R.id.txtPrice);
            textViewDeviationPrice = (TextView) v.findViewById(R.id.txtDeviationPrice);
            layoutAd = (RelativeLayout) v.findViewById(R.id.layout_ad);
            mAdView = (AdView) v.findViewById(R.id.adView);
        }
    }

    public interface OnItemClickListener {
        void onClick(MotobikeBrand brand);
    }

//    private void setupRippleEffect(View view) {
//        MaterialRippleLayout.on(view)
//                .rippleColor(Color.parseColor("#FF0000"))
//                .rippleAlpha(0.2f)
//                .rippleHover(true)
//                .rippleOverlay(true)
//                .create();
//    }
}
