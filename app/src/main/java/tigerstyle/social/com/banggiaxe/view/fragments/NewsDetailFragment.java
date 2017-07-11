package tigerstyle.social.com.banggiaxe.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.select.Elements;

import tigerstyle.social.com.banggiaxe.BaseFragment;
import tigerstyle.social.com.banggiaxe.R;
import tigerstyle.social.com.banggiaxe.model.NewsObject;
import tigerstyle.social.com.banggiaxe.service.NewsDetailRequest;

/**
 * Created by QuyDV on 7/10/17.
 */

public class NewsDetailFragment extends BaseFragment {

    private WebView mWebView;
    public static String ARG_OBJ_KEY = "arg-news-obj";
    private NewsObject newsObject;

    private LoaderManager.LoaderCallbacks<Elements> newsDataListener = new LoaderManager.LoaderCallbacks<Elements>() {
        @Override
        public Loader<Elements> onCreateLoader(int id, Bundle args) {
            return new NewsDetailRequest(context,newsObject.getUrlDetail());
        }

        @Override
        public void onLoadFinished(Loader<Elements> loader, Elements data) {
            context.hideLoading();
            mWebView.loadDataWithBaseURL(null,data.html(), "text/html", "utf-8",null);

        }

        @Override
        public void onLoaderReset(Loader<Elements> loader) {
            context.hideLoading();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_news_detail, null);
        mWebView = (WebView) rootView.findViewById(R.id.web_detail);
        newsObject = (NewsObject) getArguments().getSerializable(ARG_OBJ_KEY);
        context.getSupportLoaderManager().initLoader(6, null, newsDataListener).forceLoad();
        return rootView;
    }

    public class MyWebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.loadUrl(url);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        context.setHideActionBarSearchItem(false);
        context.getSupportActionBar().setTitle(context.getResources().getString(R.string.cmn_news));
        setHasOptionsMenu(true);
    }
}
