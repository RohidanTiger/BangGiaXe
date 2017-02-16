package tigerstyle.social.com.banggiaxe.view.fragments;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import tigerstyle.social.com.banggiaxe.BaseFragment;
import tigerstyle.social.com.banggiaxe.R;
import tigerstyle.social.com.banggiaxe.customize.SpacesItemDecoration;
import tigerstyle.social.com.banggiaxe.listener.DataChangeListener;
import tigerstyle.social.com.banggiaxe.model.Question;
import tigerstyle.social.com.banggiaxe.utils.ConnectivityReceiver;
import tigerstyle.social.com.banggiaxe.utils.Logger;
import tigerstyle.social.com.banggiaxe.view.adapters.MenuExamAdapter;

import static tigerstyle.social.com.banggiaxe.config.Contants.NUMBER_QUESTION;
import static tigerstyle.social.com.banggiaxe.config.Contants.NUMBER_QUESTION_MOTO;

/**
 * Created by billymobile on 1/11/17.
 */
/* 1=1, 2=2, 3=1+2, 4=3, 5=1+3, 6=2+3, 8=4, 9=1+4, 12 = 3+4*/
public class ExamMenuFragment extends BaseFragment implements ConnectivityReceiver.ConnectivityReceiverListener {

    public static String AGR_KEY = "EXAM_TYPE";
    public static int ARG_EXAM_A1_TYPE = 1;
    public static int ARG_EXAM_B2_TYPE = 2;

    public static String ARG_POSITION = "EXAM_POSITION";
    public static String ARG_QUESTION = "LIST_QUESTION";

    private RecyclerView mRecyclerMenu;
    private MenuExamAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int view_type = 1;
    private ArrayList<Question> listQuestion = new ArrayList<>();
    private ArrayList<String> questionPosition = new ArrayList<>();
    private AdView mAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_exam_menu, null);
        mRecyclerMenu = (RecyclerView) rootView.findViewById(R.id.recyclerview_exam_menu);
        mAdView = (AdView) rootView.findViewById(R.id.adView);
        mAdView.loadAd(context.adRequest);
        view_type = getArguments().getInt(AGR_KEY);
        if(view_type == ARG_EXAM_A1_TYPE){
            mAdapter = new MenuExamAdapter(8,context);
        }else{
            mAdapter = new MenuExamAdapter(15,context);
        }
        mAdapter.setOnItemClickListener(new MenuExamAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putInt(ARG_POSITION,position);
                if(view_type == ARG_EXAM_A1_TYPE){
                    ArrayList<Question> ques = new ArrayList<Question>();
                    for(int i = 0; i< NUMBER_QUESTION_MOTO; i++){
                        String[] indexs= questionPosition.get((position*NUMBER_QUESTION_MOTO)+i).split("\\|");
                        int index = Integer.parseInt(indexs[2]);
                        ques.add(listQuestion.get(index));
                    }
                    bundle.putParcelableArrayList(ARG_QUESTION,ques);
                    context.pushFragments(new ExamDetailFragment(),bundle,true,true);
                    context.getSupportActionBar().setTitle(context.getResources().getString(R.string.cmn_a1_title));
                }else{
                    ArrayList<Question> ques = new ArrayList<Question>();
                    for(int i = 0; i< NUMBER_QUESTION; i++){
                        String[] indexs= questionPosition.get((position*NUMBER_QUESTION)+i).split("\\|");
                        int index = Integer.parseInt(indexs[2]);
                        ques.add(listQuestion.get(index));
                    }
                    bundle.putParcelableArrayList(ARG_QUESTION,ques);
                    context.pushFragments(new B2ExamDetailFragment(),bundle,true,true);
                    context.getSupportActionBar().setTitle(context.getResources().getString(R.string.cmn_b2_title));
                }
            }
        });
        mLayoutManager = new GridLayoutManager(context,3);
        mRecyclerMenu.setLayoutManager(mLayoutManager);
        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(getContext(), R.dimen.padding_smaller);
        mRecyclerMenu.addItemDecoration(itemDecoration);
        mRecyclerMenu.setAdapter(mAdapter);
        requestIndex();
        requestQuestionList(new DataChangeListener() {
            @Override
            public void onDataChange(ArrayList<Question> questions) {
                context.hideLoading();
                listQuestion = questions;
            }
        });
        return rootView;
    }

    private void requestQuestionList(final DataChangeListener listener){
        context.showLoading();
        final ArrayList listQues = new ArrayList();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("question/questions.txt")));

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                String[] ques = mLine.split("\\|");
                Question q = new Question();
                q.setQuestion(ques[0]);
                String answer = "";
                answer = answer.concat(ques[1]).concat("--").concat(ques[2]);
                if(ques[3] != null && ques[3].length() > 0) answer = answer.concat("--").concat(ques[3]);
                if(ques[4] != null && ques[4].length() > 0) answer = answer.concat("--").concat(ques[4]);
                q.setAnswers(answer);
                if(ques[5] != null && ques[5].length() > 0) {
                    q.setImage(ques[5]);
                }else{
                    q.setImage("none");
                }
                switch (Integer.parseInt(ques[6])){
                    case 1:{
                        q.setResult("1");
                        break;
                    }
                    case 2:{
                        q.setResult("2");
                        break;
                    }
                    case 3:{
                        q.setResult("1-2");
                        break;
                    }
                    case 4:{
                        q.setResult("3");
                        break;
                    }
                    case 5:{
                        q.setResult("1-3");
                        break;
                    }
                    case 6:{
                        q.setResult("2-3");
                        break;
                    }
                    case 8:{
                        q.setResult("4");
                        break;
                    }
                    case 9:{
                        q.setResult("1-4");
                        break;
                    }
                    case 12:{
                        q.setResult("3-4");
                        break;
                    }
                }
                listQues.add(q);
                listener.onDataChange(listQues);
                Logger.d("Question",ques[0]);
            }
        } catch (IOException e) {
            context.hideLoading();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    context.hideLoading();
                }
            }
        }
    }

    private void requestIndex(){
        BufferedReader reader = null;
        if(view_type == 0){
            try {
                reader = new BufferedReader(
                        new InputStreamReader(context.getAssets().open("question/a1.txt")));

                String mLine;
                while ((mLine = reader.readLine()) != null) {
                    questionPosition.add(mLine);
                }
            } catch (IOException e) {
                context.hideLoading();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        context.hideLoading();
                    }
                }
            }
        }else{
            try {
                reader = new BufferedReader(
                        new InputStreamReader(context.getAssets().open("question/b2.txt")));

                String mLine;
                while ((mLine = reader.readLine()) != null) {
                    questionPosition.add(mLine);
                }
            } catch (IOException e) {
                context.hideLoading();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        context.hideLoading();
                    }
                }
            }
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onResume(){
        super.onResume();
        if(view_type == ARG_EXAM_A1_TYPE){
            context.getSupportActionBar().setTitle(context.getResources().getString(R.string.cmn_a1_title));
        }else{
            context.getSupportActionBar().setTitle(context.getResources().getString(R.string.cmn_b2_title));
        }
        if(ConnectivityReceiver.isConnected()){
            mAdView.setVisibility(View.VISIBLE);
        }else{
            mAdView.setVisibility(View.GONE);
        }
        context.setHideActionBarSearchItem(true);
        setHasOptionsMenu(true);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(isConnected){
            mAdView.setVisibility(View.VISIBLE);
        }else{
            mAdView.setVisibility(View.GONE);
        }
    }
}
