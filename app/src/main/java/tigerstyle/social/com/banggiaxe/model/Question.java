package tigerstyle.social.com.banggiaxe.model;

import android.graphics.Bitmap;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by billymobile on 1/11/17.
 */

@IgnoreExtraProperties
public class Question {
    private String answers;
    private String image;
    private String question;
    private String result;
    private Bitmap mResoure;

    public Question(){

    }
    public Question(String answers, String result, String question, String image) {
        this.answers = answers;
        this.result = result;
        this.question = question;
        this.image = image;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Bitmap getmResoure() {
        return mResoure;
    }

    public void setmResoure(Bitmap mResoure) {
        this.mResoure = mResoure;
    }
}
