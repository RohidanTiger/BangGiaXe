package tigerstyle.social.com.banggiaxe.view.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import static tigerstyle.social.com.banggiaxe.config.Contants.EXAM_TIME;
import static tigerstyle.social.com.banggiaxe.view.fragments.ExamMenuFragment.ARG_POSITION;

/**
 * Created by billymobile on 1/13/17.
 */

public class B2ExamDetailFragment extends BaseFragment{
    private ArrayList<Question> listQuestion;
    private TextView mTxtQuestionIndex;
    private Button mBtnPrevious;
    private Button mBtnNext;
    private Button mBtnSubmit;
    private TextView mTxtQuestion;
    private ImageView mImageDetail;
    private int currentQuestion = 0;
    private String[] arrayResult= new String[30];
    private LinearLayout mLayoutAnswer;
    private RelativeLayout mLayoutTime;
    private TextView mTxtTime;
    // Group1
    private RelativeLayout mLayoutAnswer1;
    private AppCompatCheckBox mCheckBox1;

    //Group2
    private RelativeLayout mLayoutAnswer2;
    private AppCompatCheckBox mCheckBox2;

    //Group3
    private RelativeLayout mLayoutAnswer3;
    private AppCompatCheckBox mCheckBox3;

    //Group4
    private RelativeLayout mLayoutAnswer4;
    private AppCompatCheckBox mCheckBox4;

    private int positionExam;
    private int viewMode = 0;
    public static String VIEW_MODE = "view-mode";
    public static int NORMAL_MODE = 0;

    private CountDownTimer countDownTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_exam_detail, null);
        mTxtQuestionIndex = (TextView) rootView.findViewById(R.id.txt_question_index);
        mBtnPrevious = (Button) rootView.findViewById(R.id.btn_previous_question);
        mBtnNext = (Button) rootView.findViewById(R.id.btn_next_question);
        mBtnSubmit = (Button) rootView.findViewById(R.id.btn_submit_answer);
        mTxtQuestion = (TextView) rootView.findViewById(R.id.txt_question);
        mImageDetail = (ImageView) rootView.findViewById(R.id.img_question);
        mLayoutTime = (RelativeLayout) rootView.findViewById(R.id.layout_time);

        positionExam = getArguments().getInt(ARG_POSITION);
        viewMode = getArguments().getInt(VIEW_MODE);
        if(viewMode != NORMAL_MODE) arrayResult = getArguments().getStringArray(ResultFragment.ARG_ANSWERS);

        mLayoutAnswer = (LinearLayout) rootView.findViewById(R.id.layout_answer);
        mTxtTime = (TextView) rootView.findViewById(R.id.txt_time);
        // Group1
        mLayoutAnswer1 = (RelativeLayout) rootView.findViewById(R.id.layout_answer1);
        mCheckBox1     = (AppCompatCheckBox)  rootView.findViewById(R.id.checkbox_1);

        // Group2
        mLayoutAnswer2 = (RelativeLayout) rootView.findViewById(R.id.layout_answer2);
        mCheckBox2     = (AppCompatCheckBox)  rootView.findViewById(R.id.checkbox_2);

        // Group3
        mLayoutAnswer3 = (RelativeLayout) rootView.findViewById(R.id.layout_answer3);
        mCheckBox3     = (AppCompatCheckBox)  rootView.findViewById(R.id.checkbox_3);

        // Group4
        mLayoutAnswer4 = (RelativeLayout) rootView.findViewById(R.id.layout_answer4);
        mCheckBox4     = (AppCompatCheckBox)  rootView.findViewById(R.id.checkbox_4);

        context.setHideActionBarSearchItem(false);
        context.getSupportActionBar().setTitle("Đề 1");
        setHasOptionsMenu(true);

        listQuestion = new ArrayList<>();
        requestQuestionList(new DataChangeListener() {
            @Override
            public void onDataChange(ArrayList<Question> questions) {
                context.hideLoading();
                listQuestion = questions;
                fillData(listQuestion.get(currentQuestion));
                initCountDownTimer();
            }
        });

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentQuestion++;
                fillData(listQuestion.get(currentQuestion));
            }
        });
        mBtnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentQuestion--;
                fillData(listQuestion.get(currentQuestion));
            }
        });
        updateAnswers();
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ResultFragment.ARG_ANSWERS,arrayResult);
                bundle.putInt(ResultFragment.ARG_QUESTIONS,positionExam);
                context.popFragments(false);
                context.pushFragments(new ResultFragment(),bundle,true,true);
            }
        });
        return rootView;
    }

    private void requestQuestionList(final DataChangeListener listener){
        context.showLoading();
        final ArrayList listQues = new ArrayList();
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
            jsonObj = new JSONObject(content);

            for(int i = 0; i < 30; i++){
                JSONObject c = jsonObj.getJSONObject("cau".concat(String.valueOf(i+1)));
                Question q = new Question();
                q.setQuestion(c.getString("question"));
                q.setImage(c.getString("image"));
                q.setAnswers(c.getString("answers"));
                q.setResult(c.getString("result"));
                listQues.add(q);
            }
            listener.onDataChange(listQues);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Logger.i("Question",content);
    }

    private void fillData(Question question){
        mTxtQuestionIndex.setText(String.valueOf(currentQuestion + 1));
        mBtnNext.setVisibility((currentQuestion == 29) ? View.INVISIBLE: View.VISIBLE);
        mBtnPrevious.setVisibility((currentQuestion == 0) ? View.INVISIBLE: View.VISIBLE);
        String answer[] = question.getAnswers().split("--");
        mTxtQuestion.setText(question.getQuestion());
        if(!question.getImage().equals("none")){
            mImageDetail.setVisibility(View.VISIBLE);
            String mDrawableName = "b".concat(question.getImage());
            int resID = getResources().getIdentifier(mDrawableName , "drawable", context.getPackageName());
            mImageLoader.displayImage("drawable://"+resID, mImageDetail);
        }else{
            mImageDetail.setVisibility(View.GONE);
        }
        mCheckBox1.setText(answer[0]);
        mCheckBox2.setText(answer[1]);
        if(answer.length == 2){
            mLayoutAnswer3.setVisibility(View.GONE);
            mLayoutAnswer4.setVisibility(View.GONE);
        }else if(answer.length == 3){
            mCheckBox3.setText(answer[2]);
            mLayoutAnswer3.setVisibility(View.VISIBLE);
            mLayoutAnswer4.setVisibility(View.GONE);
        }else{
            mCheckBox4.setText(answer[3]);
            mLayoutAnswer3.setVisibility(View.VISIBLE);
            mLayoutAnswer4.setVisibility(View.VISIBLE);
        }

        String result = arrayResult[currentQuestion];
        if(result != null){
            mCheckBox1.setChecked(result.contains("1"));
            mCheckBox2.setChecked(result.contains("2"));
            mCheckBox3.setChecked(result.contains("3"));
            mCheckBox4.setChecked(result.contains("4"));
        }else{
            mCheckBox1.setChecked(false);
            mCheckBox2.setChecked(false);
            mCheckBox3.setChecked(false);
            mCheckBox4.setChecked(false);
        }

        if(viewMode != NORMAL_MODE){
            mCheckBox1.setEnabled(false);
            mCheckBox2.setEnabled(false);
            mCheckBox3.setEnabled(false);
            mCheckBox4.setEnabled(false);
            String res = question.getResult();
            mCheckBox1.setTextColor((res.contains("1") ? ContextCompat.getColor(context, R.color.cmn_price2) : Color.GRAY));
            mCheckBox2.setTextColor((res.contains("2") ? ContextCompat.getColor(context, R.color.cmn_price2) : Color.GRAY));
            mCheckBox3.setTextColor((res.contains("3") ? ContextCompat.getColor(context, R.color.cmn_price2) : Color.GRAY));
            mCheckBox4.setTextColor((res.contains("4") ? ContextCompat.getColor(context, R.color.cmn_price2) : Color.GRAY));
        }
    }

    private void updateAnswers(){
        final String[] result = {""};
        mCheckBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                result[0] = "";
                result[0] = result[0].concat((mCheckBox1.isChecked())?"1":"");
                result[0] = result[0].concat((mCheckBox2.isChecked())?"2":"");
                result[0] = result[0].concat((mCheckBox3.isChecked())?"3":"");
                result[0] = result[0].concat((mCheckBox4.isChecked())?"4":"");
                arrayResult[currentQuestion] = result[0];
            }
        });
        mCheckBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                result[0] = "";
                result[0] = result[0].concat((mCheckBox1.isChecked())?"1":"");
                result[0] = result[0].concat((mCheckBox2.isChecked())?"2":"");
                result[0] = result[0].concat((mCheckBox3.isChecked())?"3":"");
                result[0] = result[0].concat((mCheckBox4.isChecked())?"4":"");
                arrayResult[currentQuestion] = result[0];
            }
        });
        mCheckBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                result[0] = "";
                result[0] = result[0].concat((mCheckBox1.isChecked())?"1":"");
                result[0] = result[0].concat((mCheckBox2.isChecked())?"2":"");
                result[0] = result[0].concat((mCheckBox3.isChecked())?"3":"");
                result[0] = result[0].concat((mCheckBox4.isChecked())?"4":"");
                arrayResult[currentQuestion] = result[0];
            }
        });
        mCheckBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                result[0] = "";
                result[0] = result[0].concat((mCheckBox1.isChecked())?"1":"");
                result[0] = result[0].concat((mCheckBox2.isChecked())?"2":"");
                result[0] = result[0].concat((mCheckBox3.isChecked())?"3":"");
                result[0] = result[0].concat((mCheckBox4.isChecked())?"4":"");
                arrayResult[currentQuestion] = result[0];
            }
        });
    }

    private void initCountDownTimer(){
        if(viewMode == NORMAL_MODE){
            countDownTimer = new CountDownTimer(EXAM_TIME,1000) {
                @Override
                public void onTick(long l) {
                    l = l/1000;
                    String mins;
                    if(l/60 < 10){
                        mins = "0".concat(String.valueOf(l/60));
                    }else{
                        mins = String.valueOf(l/60);
                    }

                    String secs;
                    if(l%60 < 10){
                        secs = "0".concat(String.valueOf(l%60));
                    }else{
                        secs = String.valueOf(l%60);
                    }

                    mTxtTime.setText(mins.concat(":").concat(secs));
                }
                @Override
                public void onFinish() {

                }
            }.start();
        }else{
            mLayoutTime.setVisibility(View.GONE);
            mBtnSubmit.setVisibility(View.GONE);
        }
    }
}
