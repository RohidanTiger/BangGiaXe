package tigerstyle.social.com.banggiaxe.service;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tigerstyle.social.com.banggiaxe.model.NewsObject;

/**
 * Created by QuyDV on 7/7/17.
 */

public class NewsRequest extends AsyncTaskLoader<List<NewsObject>> {
    private Context mContext;
    private String mUrl;

    public NewsRequest(Context context, String url) {
        super(context);
        this.mContext = context;
        mUrl = url;
    }

    @Override
    public void onStartLoading() {
        if (takeContentChanged()) {
            forceLoad();
        }
    }

    @Override
    public List<NewsObject> loadInBackground() {
        Document doc = null;
        ArrayList<NewsObject> listNews = new ArrayList<>();

        try {
            doc = Jsoup.connect(mUrl).userAgent("Mozilla").ignoreContentType(true).timeout(10000).get();
            Elements content = doc.getElementsByClass("block_image_news width_common");
            for (int i = 0; i < content.size(); i++){
                String link = content.get(i).getElementsByClass("thumb").first().getElementsByAttribute("href").attr("href");
                String image = content.get(i).getElementsByClass("thumb").first().getElementsByAttribute("src").attr("src");
                String title = content.get(i).getElementsByClass("thumb").first().getElementsByAttribute("alt").attr("alt");
                String desc = content.get(i).getElementsByClass("news_lead").html();
                NewsObject news = new NewsObject(link,image,title,desc);
                listNews.add(news);
            }
            return listNews;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listNews;
    }
}
