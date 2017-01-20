package tigerstyle.social.com.banggiaxe.view.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

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

import static tigerstyle.social.com.banggiaxe.config.Contants.NUMBER_QUESTION;

/**
 * Created by billymobile on 1/17/17.
 */

public class ResultFragment extends BaseFragment{
    public static String ARG_QUESTIONS = "arg-position-exam";
    public static String ARG_ANSWERS   = "arg-list-answer";
    public static String ARG_EXAM_TYPE = "arg_exam_type";

    private PieChart mPieChart;
    private RelativeLayout mBtnRetry;
    private RelativeLayout mBtnWatchResult;
    private RelativeLayout mBtnGotoMenu;

    private int positionExam;
    private int mRightNumber = 0;
    private int mExamType;

    private ArrayList<Question> listQuestion;
    private String mArrayAnswer[];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_result, null);
        mArrayAnswer = getArguments().getStringArray(ARG_ANSWERS);
        positionExam = getArguments().getInt(ARG_QUESTIONS);
        mExamType = getArguments().getInt(ARG_EXAM_TYPE);
        listQuestion = new ArrayList<>();

        mPieChart = (PieChart)rootView.findViewById(R.id.chart);
        mBtnRetry = (RelativeLayout) rootView.findViewById(R.id.btn_retry);
        mBtnWatchResult = (RelativeLayout) rootView.findViewById(R.id.btn_watch_result);
        mBtnGotoMenu = (RelativeLayout) rootView.findViewById(R.id.btn_goto_menu);

        requestQuestionList(new DataChangeListener() {
            @Override
            public void onDataChange(ArrayList<Question> questions) {
                context.hideLoading();
                listQuestion = questions;
                drawChart();
            }
        });
        handleButtonClick();
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

            for(int i = 0; i < NUMBER_QUESTION; i++){
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
    }

    private void drawChart(){
        for(int i = 0; i < NUMBER_QUESTION; i++){
            String answer = listQuestion.get(i).getResult();
            answer = answer.replaceAll("-","");
            if(answer.equals(mArrayAnswer[i])){
                mRightNumber++;
            }
        }

        ArrayList<PieEntry> yVals = new ArrayList<PieEntry>();
        yVals.add(new PieEntry(mRightNumber,"Trả lời đúng",0));
        yVals.add(new PieEntry(NUMBER_QUESTION - mRightNumber,"Trả lời chưa chính xác",1));

        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.COLORFUL_COLORS) colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS) colors.add(c);

        PieDataSet dataSet = new PieDataSet(yVals, "");
        dataSet.setColors(colors);
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        PieData data = new PieData();
        data.setDataSet(dataSet);

        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        mPieChart.invalidate();
        Description description = new Description();
        description.setText("");
        mPieChart.setDescription(description);
        mPieChart.setExtraBottomOffset(10);

        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setXEntrySpace(7f);
        l.setTextSize(12);
    }

    private void handleButtonClick(){
        mBtnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.popFragments(false);
            }
        });

        mBtnWatchResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.popFragments(false);
                Bundle bundle = new Bundle();
                if(mExamType != 1){
                    bundle.putInt(ResultFragment.ARG_QUESTIONS,positionExam);
                    bundle.putSerializable(ResultFragment.ARG_ANSWERS,mArrayAnswer);
                    bundle.putInt(B2ExamDetailFragment.VIEW_MODE,1);
                    context.pushFragments(new B2ExamDetailFragment(),bundle,true,true);
                }else{
                    bundle.putInt(ResultFragment.ARG_QUESTIONS,positionExam);
                    context.pushFragments(new ExamDetailFragment(),bundle,true,true);
                }

            }
        });

        mBtnGotoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.popFragments(true);
            }
        });
    }
}
