package tigerstyle.social.com.banggiaxe.view.fragments;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tigerstyle.social.com.banggiaxe.BaseFragment;
import tigerstyle.social.com.banggiaxe.R;
import tigerstyle.social.com.banggiaxe.customize.SpacesItemDecoration;
import tigerstyle.social.com.banggiaxe.view.adapters.MenuExamAdapter;

/**
 * Created by billymobile on 1/11/17.
 */

public class ExamMenuFragment extends BaseFragment {

    public static String AGR_KEY = "EXAM_TYPE";
    public static int ARG_EXAM_A1_TYPE = 1;
    public static int ARG_EXAM_B2_TYPE = 2;

    public static String ARG_POSITION = "EXAM_POSITION";

    private RecyclerView mRecyclerMenu;
    private MenuExamAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int view_type = 1;

    private String[] array_exam = {"Đề 1","Đề 2","Đề 3","Đề 4","Đề 5","Đề 6","Đề 7",
            "Đề 8","Đề 9","Đề 10","Đề 11","Đề 12","Đề 13","Đề 14","Đề 15"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_exam_menu, null);
        mRecyclerMenu = (RecyclerView) rootView.findViewById(R.id.recyclerview_exam_menu);
        mAdapter = new MenuExamAdapter(array_exam,context);
        view_type = getArguments().getInt(AGR_KEY);
        mAdapter.setOnItemClickListener(new MenuExamAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putInt(ARG_POSITION,position);
                if(view_type == ARG_EXAM_A1_TYPE){
                    context.pushFragments(new ExamDetailFragment(),bundle,true,true);
                }else{
                    context.pushFragments(new B2ExamDetailFragment(),bundle,true,true);
                }
            }
        });
        mLayoutManager = new GridLayoutManager(context,3);
        mRecyclerMenu.setLayoutManager(mLayoutManager);
        //mRecyclerMenu.setItemAnimator(ItemAnimatorFactory.slidein());
        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(getContext(), R.dimen.padding_smaller);
        mRecyclerMenu.addItemDecoration(itemDecoration);
        mRecyclerMenu.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        context.getSupportActionBar().setTitle("Lý thuyết bằng A1");
        context.setHideActionBarSearchItem(true);
    }
}
