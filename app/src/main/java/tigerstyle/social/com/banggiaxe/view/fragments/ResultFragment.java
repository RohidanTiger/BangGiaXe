package tigerstyle.social.com.banggiaxe.view.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.ads.AdView;
import java.util.ArrayList;
import tigerstyle.social.com.banggiaxe.BaseFragment;
import tigerstyle.social.com.banggiaxe.R;
import tigerstyle.social.com.banggiaxe.model.Question;
import tigerstyle.social.com.banggiaxe.utils.ConnectivityReceiver;

/**
 * Created by billymobile on 1/17/17.
 */

public class ResultFragment extends
        BaseFragment implements ConnectivityReceiver.ConnectivityReceiverListener{
    public static String ARG_QUESTIONS = "arg-position-exam";
    public static String ARG_ANSWERS   = "arg-list-answer";
    public static String ARG_EXAM_TYPE = "arg_exam_type";

    private PieChart mPieChart;
    private RelativeLayout mBtnRetry;
    private RelativeLayout mBtnWatchResult;
    private RelativeLayout mBtnGotoMenu;
    private TextView mTxtPercent;

    private int mRightNumber = 0;
    private int mExamType;

    private ArrayList<Question> listQuestion;
    private String mArrayAnswer[];
    private AdView mAdView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_result, null);
        listQuestion = new ArrayList<>();
        mArrayAnswer = getArguments().getStringArray(ARG_ANSWERS);
        listQuestion = getArguments().getParcelableArrayList(ARG_QUESTIONS);
        mExamType = getArguments().getInt(ARG_EXAM_TYPE);

        mPieChart = (PieChart)rootView.findViewById(R.id.chart);
        mTxtPercent = (TextView) rootView.findViewById(R.id.txt_percen);
        mBtnRetry = (RelativeLayout) rootView.findViewById(R.id.btn_retry);
        mBtnWatchResult = (RelativeLayout) rootView.findViewById(R.id.btn_watch_result);
        mBtnGotoMenu = (RelativeLayout) rootView.findViewById(R.id.btn_goto_menu);
        mAdView = (AdView) rootView.findViewById(R.id.adView);
        mAdView.loadAd(context.adRequest);

        drawChart();
        handleButtonClick();
        return rootView;
    }

//    private void requestQuestionList(final DataChangeListener listener){
//        context.showLoading();
//        final ArrayList listQues = new ArrayList();
//        String content = "";
//
//        BufferedReader reader = null;
//        try {
//            reader = new BufferedReader(
//                    new InputStreamReader(context.getAssets().open("question/".concat(String.valueOf(positionExam+1).concat(".json")))));
//
//            String mLine;
//            while ((mLine = reader.readLine()) != null) {
//                content = content.concat(mLine);
//            }
//        } catch (IOException e) {
//            context.hideLoading();
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    context.hideLoading();
//                }
//            }
//        }
//
//        JSONObject jsonObj;
//        try {
//            jsonObj = new JSONObject(content);
//
//            for(int i = 0; i < NUMBER_QUESTION; i++){
//                JSONObject c = jsonObj.getJSONObject("cau".concat(String.valueOf(i+1)));
//                Question q = new Question();
//                q.setQuestion(c.getString("question"));
//                q.setImage(c.getString("image"));
//                q.setAnswers(c.getString("answers"));
//                q.setResult(c.getString("result"));
//                listQues.add(q);
//            }
//            listener.onDataChange(listQues);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    private void drawChart(){
        for(int i = 0; i < listQuestion.size(); i++){
            String answer = listQuestion.get(i).getResult();
            answer = answer.replaceAll("-","");
            if(answer.equals(mArrayAnswer[i])){
                mRightNumber++;
            }
        }

        float percent = (100f * mRightNumber / listQuestion.size());
        mTxtPercent.setText(String.valueOf((int)percent).concat("%"));

        ArrayList<PieEntry> yVals = new ArrayList<PieEntry>();
        yVals.add(new PieEntry(mRightNumber,"Trả lời đúng",0));
        yVals.add(new PieEntry(listQuestion.size() - mRightNumber,"Trả lời chưa chính xác",1));

        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.COLORFUL_COLORS) colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS) colors.add(c);

        PieDataSet dataSet = new PieDataSet(yVals, "");
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.BLACK);
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
                    bundle.putParcelableArrayList(ExamMenuFragment.ARG_QUESTION,listQuestion);
                    bundle.putSerializable(ResultFragment.ARG_ANSWERS,mArrayAnswer);
                    bundle.putInt(B2ExamDetailFragment.VIEW_MODE,1);
                    context.pushFragments(new B2ExamDetailFragment(),bundle,true,true);
                }else{
                    bundle.putParcelableArrayList(ExamMenuFragment.ARG_QUESTION,listQuestion);
                    bundle.putSerializable(ResultFragment.ARG_ANSWERS,mArrayAnswer);
                    bundle.putInt(ExamDetailFragment.VIEW_MODE,1);
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

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onResume(){
        super.onResume();
        context.setHideActionBarSearchItem(false);
        context.getSupportActionBar().setTitle("Kết quả");
        setHasOptionsMenu(true);
    }
}
