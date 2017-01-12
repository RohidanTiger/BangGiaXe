package tigerstyle.social.com.banggiaxe.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import tigerstyle.social.com.banggiaxe.BaseDialog;
import tigerstyle.social.com.banggiaxe.R;
import tigerstyle.social.com.banggiaxe.view.adapters.AreaInforAdapter;

/**
 * Created by billymobile on 1/9/17.
 */

public class AreaInforDialog extends BaseDialog{
    private List<String> mTitle = new ArrayList<>();
    private List<String> mContent = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private Context mContext;
    private ImageView mImgClose;

    public AreaInforDialog(Context context, List<String> titles, List<String> contents) {
        super(context);
        mContext = context;
        mTitle = titles;
        mContent = contents;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_area_infor);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerInformation);
        AreaInforAdapter informationAdapter =new AreaInforAdapter(mContext, mTitle,mContent);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(informationAdapter);
        mImgClose = (ImageView) findViewById(R.id.imgClose);
        mImgClose.bringToFront();
        mImgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
