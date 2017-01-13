package tigerstyle.social.com.banggiaxe.view.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import tigerstyle.social.com.banggiaxe.BaseFragment;
import tigerstyle.social.com.banggiaxe.R;
import tigerstyle.social.com.banggiaxe.listener.DataChangeListener;
import tigerstyle.social.com.banggiaxe.model.Question;
import tigerstyle.social.com.banggiaxe.utils.PicassoLoader;

/**
 * Created by billymobile on 1/11/17.
 */

public class ExamDetailFragment extends BaseFragment {

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

    private DatabaseReference mFirebaseDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_exam_detail, null);
        mBtnPrevious = (RelativeLayout) rootView.findViewById(R.id.btn_previous_question);
        mBtnNext = (RelativeLayout) rootView.findViewById(R.id.btn_next_question);
        mBtnSubmit = (Button) rootView.findViewById(R.id.btn_submit_answer);
        mTxtQuestion = (TextView) rootView.findViewById(R.id.txt_question);
        mImageDetail = (ImageView) rootView.findViewById(R.id.img_question);

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

        mFirebaseDatabase = mFirebaseInstance.getReference();
        listQuestion = new ArrayList<>();
        requestData(new DataChangeListener() {
            @Override
            public void onDataChange(ArrayList<Question> questions) {
                context.hideLoading();
                listQuestion = questions;
                fillData(listQuestion.get(currentQuestion));
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
        return rootView;
    }

    private void requestData(final DataChangeListener listener){
        context.showLoading();
        final ArrayList lisQues = new ArrayList();
        Query query = mFirebaseDatabase.child("de1");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    final Question question = postSnapshot.getValue(Question.class);
                    lisQues.add(question);
                }
                listener.onDataChange(lisQues);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                context.hideLoading();
            }
        });
    }

    private void fillData(Question question){
        mBtnNext.setVisibility((currentQuestion == 29) ? View.GONE: View.VISIBLE);
        mBtnPrevious.setVisibility((currentQuestion == 0) ? View.GONE: View.VISIBLE);
        String answer[] = question.getAnswers().split("--");
        mTxtQuestion.setText(question.getQuestion());
        if(!question.getImage().equals("none")){
            mImageDetail.setVisibility(View.VISIBLE);
            PicassoLoader.getInstance(context).load(question.getImage()).placeholder(R.drawable.bg_captcha).
                    error(R.drawable.bg_captcha).into(mImageDetail);
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
            mCheckBox3.setText(answer[3]);
            mLayoutAnswer3.setVisibility(View.VISIBLE);
            mLayoutAnswer4.setVisibility(View.VISIBLE);
        }
    }
}
