package tigerstyle.social.com.banggiaxe.service;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

import tigerstyle.social.com.banggiaxe.MainActivity;

/**
 * Created by QuyDV on 7/11/17.
 */

public class EquippedRequest extends AsyncTaskLoader<HashMap> {

    private MainActivity mContext;
    private String mUrl;

    public EquippedRequest(MainActivity context, String url) {
        super(context);
        this.mContext = context;
        this.mUrl = url;
    }

    @Override
    public HashMap loadInBackground() {
        Document doc = null;
        String result = "";
        HashMap<String,String> mapEquidment = new HashMap<>();

        try {
            doc = Jsoup.connect(mUrl).userAgent("Mozilla").ignoreContentType(true).timeout(10000).get();
            Element content = doc.getElementsByClass("trang_bi").first();

            Elements title = content.getElementsByClass("left_line");
            Elements value = content.getElementsByClass("right_line");

            for(int i = 0; i < value.size(); i++) mapEquidment.put(title.get(i).html(),value.get(i).html());

            return mapEquidment;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapEquidment;
    }
}
