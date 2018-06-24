package tigerstyle.social.com.banggiaxe.view.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tigerstyle.social.com.banggiaxe.MainActivity;
import tigerstyle.social.com.banggiaxe.R;
import tigerstyle.social.com.banggiaxe.model.MotobikeBrand;
import tigerstyle.social.com.banggiaxe.utils.PicassoLoader;

import static tigerstyle.social.com.banggiaxe.config.Contants.IMAGE_URL;

/**
 * Created by billymobile on 1/3/17.
 */

public class MotoCompetitorAdapter extends PagerAdapter {
    private List<MotobikeBrand> mDataSet;
    private MainActivity mContext;
    private OnItemClickListener listener;

    public MotoCompetitorAdapter(MainActivity context, List<MotobikeBrand> dataSet){
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
    public int getCount() {
        return mDataSet.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Context context = container.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final ViewGroup view = (ViewGroup) layoutInflater.inflate(R.layout.item_pompetitor, container, false);
        final MotobikeBrand brand = mDataSet.get(position);
        ImageView imgVehical = (ImageView) view.findViewById(R.id.imgVehical);;
        TextView textViewName = (TextView) view.findViewById(R.id.txt_car_name);

        textViewName.setText(brand.getCarName());
        String urlImage = IMAGE_URL + brand.getCarImage();
        PicassoLoader.getInstance(mContext).load(urlImage).placeholder(R.drawable.bg_captcha).
                error(R.drawable.bg_captcha).into(imgVehical);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(brand);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    public interface OnItemClickListener {
        void onClick(MotobikeBrand brand);
    }
}
