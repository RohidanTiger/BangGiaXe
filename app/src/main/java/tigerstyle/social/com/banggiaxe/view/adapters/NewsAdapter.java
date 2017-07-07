package tigerstyle.social.com.banggiaxe.view.adapters;

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
import tigerstyle.social.com.banggiaxe.model.NewsObject;
import tigerstyle.social.com.banggiaxe.utils.PicassoLoader;

/**
 * Created by QuyDV on 7/7/17.
 */

public class NewsAdapter extends RecyclerView.Adapter{
    private ArrayList<NewsObject> mDataSet;
    private MainActivity mContext;
    private OnItemClickListener listener;

    public NewsAdapter(MainActivity context, ArrayList<NewsObject> dataSet) {
        this.mDataSet = dataSet;
        this.mContext = context;
    }

    public void setmDataSet(ArrayList<NewsObject> mDataSet) {
        this.mDataSet = mDataSet;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        listener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_news, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final NewsObject news = mDataSet.get(position);
        if(position > 0 && (position+1) %10 == 0){
            ((ViewHolder) holder).layoutAd.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).mAdView.loadAd(mContext.adRequest);
        }else{
            ((ViewHolder) holder).layoutAd.setVisibility(View.GONE);
        }
        ((ViewHolder) holder).textTitle.setText(news.getTitle());
        ((ViewHolder) holder).textDesc.setText(news.getDesc());

        PicassoLoader.getInstance(mContext).load(news.getUrlImage()).placeholder(R.drawable.bg_captcha).
                error(R.drawable.bg_captcha).into((((HomeMotoAdapter.ViewHolder) holder).imgVehical));
        ((ViewHolder) holder).layoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(news);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout layoutContent;
        public ImageView imgNews;
        public TextView textTitle;
        public TextView textDesc;
        public RelativeLayout layoutAd;
        public AdView mAdView;

        public ViewHolder(View v) {
            super(v);
            layoutContent = (RelativeLayout) v.findViewById(R.id.layout_content);
            imgNews = (ImageView) v.findViewById(R.id.imgNews);
            textTitle = (TextView) v.findViewById(R.id.txt_title);
            textDesc = (TextView) v.findViewById(R.id.txt_desc);
            layoutAd = (RelativeLayout) v.findViewById(R.id.layout_ad);
            mAdView = (AdView) v.findViewById(R.id.adView);
        }
    }


    public interface OnItemClickListener {
        void onClick(NewsObject news);
    }
}
