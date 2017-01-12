package tigerstyle.social.com.banggiaxe.service;

import java.util.List;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import tigerstyle.social.com.banggiaxe.MainActivity;
import tigerstyle.social.com.banggiaxe.model.Question;
import tigerstyle.social.com.banggiaxe.utils.Logger;

/**
 * Created by billymobile on 1/12/17.
 */

public class QuestionRequest extends AsyncTaskLoader<List<Question>>{
    private MainActivity mContext;
    private Query mPosition;
    public QuestionRequest(MainActivity context, Query position) {
        super(context);
        this.mContext = context;
        this.mPosition = position;
    }

    @Override
    public void onStartLoading() {
        mContext.showLoading();
    }

    @Override
    public List<Question> loadInBackground() {
        mPosition.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Question question = postSnapshot.getValue(Question.class);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Logger.d("CancelRead","Come here");
            }
        });
        return null;
    }
}
