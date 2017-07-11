package tigerstyle.social.com.banggiaxe.service;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import tigerstyle.social.com.banggiaxe.MainActivity;

/**
 * Created by QuyDV on 7/10/17.
 */

public class NewsDetailRequest extends AsyncTaskLoader<Elements> {
    private MainActivity mContext;
    private String mUrl;

    public NewsDetailRequest(MainActivity context, String mUrl) {
        super(context);
        this.mContext = context;
        this.mUrl = mUrl;
    }

    @Override
    public void onStartLoading() {
        mContext.showLoading();
        forceLoad();
    }

    @Override
    public Elements loadInBackground() {
        Document doc = null;

        try {
            doc = Jsoup.connect(mUrl).userAgent("Mozilla").ignoreContentType(true).timeout(10000).get();
            Elements content = doc.getElementsByClass("fck_detail width_common block_ads_connect");
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
