package tigerstyle.social.com.banggiaxe.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import tigerstyle.social.com.banggiaxe.BaseFragment;
import tigerstyle.social.com.banggiaxe.R;
import tigerstyle.social.com.banggiaxe.listener.DataChangeListener;
import tigerstyle.social.com.banggiaxe.model.Question;
import tigerstyle.social.com.banggiaxe.utils.Logger;

import static tigerstyle.social.com.banggiaxe.view.fragments.ExamMenuFragment.ARG_POSITION;

/**
 * Created by billymobile on 1/13/17.
 */

public class B2ExamDetailFragment extends BaseFragment{
    private ArrayList<Question> listQuestion;
    private RelativeLayout mBtnPrevious;
    private RelativeLayout mBtnNext;
    private Button mBtnSubmit;
    private TextView mTxtQuestion;
    private ImageView mImageDetail;
    private int currentQuestion = 0;
    private String[] arrayResult= new String[30];

    // Group1
    private RelativeLayout mLayoutAnswer1;
    private CheckBox mCheckBox1;

    //Group2
    private RelativeLayout mLayoutAnswer2;
    private CheckBox mCheckBox2;

    //Group3
    private RelativeLayout mLayoutAnswer3;
    private CheckBox mCheckBox3;

    //Group4
    private RelativeLayout mLayoutAnswer4;
    private CheckBox mCheckBox4;

    private int positionExam;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_exam_detail, null);
        mBtnPrevious = (RelativeLayout) rootView.findViewById(R.id.btn_previous_question);
        mBtnNext = (RelativeLayout) rootView.findViewById(R.id.btn_next_question);
        mBtnSubmit = (Button) rootView.findViewById(R.id.btn_submit_answer);
        mTxtQuestion = (TextView) rootView.findViewById(R.id.txt_question);
        mImageDetail = (ImageView) rootView.findViewById(R.id.img_question);

        positionExam = getArguments().getInt(ARG_POSITION);

        // Group1
        mLayoutAnswer1 = (RelativeLayout) rootView.findViewById(R.id.layout_answer1);
        mCheckBox1     = (CheckBox)  rootView.findViewById(R.id.checkbox_1);

        // Group2
        mLayoutAnswer2 = (RelativeLayout) rootView.findViewById(R.id.layout_answer2);
        mCheckBox2     = (CheckBox)  rootView.findViewById(R.id.checkbox_2);

        // Group3
        mLayoutAnswer3 = (RelativeLayout) rootView.findViewById(R.id.layout_answer3);
        mCheckBox3     = (CheckBox)  rootView.findViewById(R.id.checkbox_3);

        // Group4
        mLayoutAnswer4 = (RelativeLayout) rootView.findViewById(R.id.layout_answer4);
        mCheckBox4     = (CheckBox)  rootView.findViewById(R.id.checkbox_4);

        context.setHideActionBarSearchItem(false);
        context.getSupportActionBar().setTitle("Đề 1");
        setHasOptionsMenu(true);

        requestQuestionList(new DataChangeListener() {
            @Override
            public void onDataChange(ArrayList<Question> questions) {
                context.hideLoading();
            }
        });
        listQuestion = new ArrayList<>();

        return rootView;
    }

    private void requestQuestionList(final DataChangeListener listener){
        context.showLoading();
        final ArrayList lisQues = new ArrayList();
        String content = "";

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("question/".concat(String.valueOf(positionExam+1).concat(".json")))));

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                content = content.concat(mLine);
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

        JSONObject jsonObj;
        try {
            jsonObj = new JSONObject(content).getJSONObject("de"+positionExam);
            JSONArray vehicalListIndex = jsonObj.names();

            for(int i = 0; i < vehicalListIndex.length(); i++){
                JSONObject c = jsonObj.getJSONObject(vehicalListIndex.getString(i));

            }
//            Collections.sort(motobikeBrands, new Comparator<MotobikeBrand>() {
//                @Override
//                public int compare(MotobikeBrand m1, MotobikeBrand m2) {
//                    return (Integer.parseInt(m1.getCarID()) > Integer.parseInt(m2.getCarID())) ? 1 : -1;
//                }
//            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Logger.i("Question",content);
    }
}
