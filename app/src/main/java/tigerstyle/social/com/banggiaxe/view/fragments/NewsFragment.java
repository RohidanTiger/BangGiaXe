package tigerstyle.social.com.banggiaxe.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tigerstyle.social.com.banggiaxe.BaseFragment;
import tigerstyle.social.com.banggiaxe.R;
import tigerstyle.social.com.banggiaxe.model.NewsObject;
import tigerstyle.social.com.banggiaxe.service.NewsRequest;
import tigerstyle.social.com.banggiaxe.view.adapters.NewsAdapter;

/**
 * Created by QuyDV on 7/7/17.
 */

public class NewsFragment extends BaseFragment{
    private RecyclerView mRecyclerView;
    private ArrayList<NewsObject> listNews;
    private RecyclerView.LayoutManager mLayoutManager;
    private NewsAdapter mAdapter;

    private LoaderManager.LoaderCallbacks<List<NewsObject>> newsDataListener = new LoaderManager.LoaderCallbacks<List<NewsObject>>() {
        @Override
        public Loader<List<NewsObject>> onCreateLoader(int id, Bundle args) {
            return new NewsRequest(context,"http://vnexpress.net/tin-tuc/oto-xe-may/page/1.html");
        }

        @Override
        public void onLoadFinished(Loader<List<NewsObject>> loader, List<NewsObject> data) {
            context.hideLoading();
            listNews.addAll(data);
            mAdapter.setmDataSet(listNews);
        }

        @Override
        public void onLoaderReset(Loader<List<NewsObject>> loader) {
            context.hideLoading();
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        listNews = new ArrayList<>();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_news, null);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mAdapter = new NewsAdapter(context,listNews);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onClick(NewsObject news) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(NewsDetailFragment.ARG_OBJ_KEY,news);
                context.pushFragments(new NewsDetailFragment(),bundle,true,true);
            }
        });
        context.getSupportLoaderManager().initLoader(5, null, newsDataListener).forceLoad();
        return rootView;
    }
}
