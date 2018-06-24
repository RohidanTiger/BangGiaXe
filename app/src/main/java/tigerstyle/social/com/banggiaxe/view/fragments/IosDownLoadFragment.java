package tigerstyle.social.com.banggiaxe.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import tigerstyle.social.com.banggiaxe.BaseFragment;
import tigerstyle.social.com.banggiaxe.R;

/**
 * Created by quydv on 4/3/18.
 */

public class IosDownLoadFragment extends BaseFragment {

    private Button mDownLoad;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ios_download, null);
        mDownLoad = (Button) rootView.findViewById(R.id.btn_download_ios);
        mDownLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl("https://itunes.apple.com/vn/app/b%E1%BA%A3ng-gi%C3%A1-xe-thi-b%E1%BA%B1ng-l%C3%A1i-xe/id1336162332?mt=8");
            }
        });
        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        context.getSupportActionBar().setTitle(context.getResources().getString(R.string.cmn_ios_version));
        context.setHideActionBarSearchItem(true);
    }

}
