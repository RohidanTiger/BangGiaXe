package tigerstyle.social.com.banggiaxe.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tigerstyle.social.com.banggiaxe.MainActivity;
import tigerstyle.social.com.banggiaxe.R;
import tigerstyle.social.com.banggiaxe.model.CarBrand;
import tigerstyle.social.com.banggiaxe.utils.PicassoLoader;

import static tigerstyle.social.com.banggiaxe.config.Contants.IMAGE_URL;

/**
 * Created by billymobile on 12/29/16.
 */

public class HomeCarAdapter extends RecyclerView.Adapter {
    private ArrayList<CarBrand> mDataSet;
    private MainActivity mContext;
    private OnItemClickListener listener;

    public HomeCarAdapter(MainActivity context, ArrayList<CarBrand> dataSet) {
        this.mDataSet = dataSet;
        this.mContext = context;
    }

    public void setmDataSet(ArrayList<CarBrand> mData) {
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
        final CarBrand brand = mDataSet.get(position);
        ((HomeCarAdapter.ViewHolder) holder).textViewName.setText(brand.getCarName());
        ((HomeCarAdapter.ViewHolder) holder).textViewBrand.setText(brand.getCarBrand());
        ((HomeCarAdapter.ViewHolder) holder).textViewPrice.setText(brand.getCarPrice());
        ((HomeCarAdapter.ViewHolder) holder).textViewDeviationPrice.setText(brand.getCarPriceDeviation());
        String urlImage = IMAGE_URL + brand.getCarImage();
        PicassoLoader.getInstance(mContext).load(urlImage).placeholder(R.drawable.bg_captcha).
                error(R.drawable.bg_captcha).into((((HomeCarAdapter.ViewHolder) holder).imgVehical));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
        public ImageView imgVehical;
        public TextView textViewName;
        public TextView textViewBrand;
        public TextView textViewPrice;
        public TextView textViewDeviationPrice;

        public ViewHolder(View v) {
            super(v);
            imgVehical = (ImageView) v.findViewById(R.id.imgVehical);
            textViewName = (TextView) v.findViewById(R.id.txtName);
            textViewBrand = (TextView) v.findViewById(R.id.txtBrand);
            textViewPrice = (TextView) v.findViewById(R.id.txtPrice);
            textViewDeviationPrice = (TextView) v.findViewById(R.id.txtDeviationPrice);
        }
    }

    public interface OnItemClickListener {
        void onClick(CarBrand brand);
    }

}
