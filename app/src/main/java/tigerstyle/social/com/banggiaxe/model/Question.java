package tigerstyle.social.com.banggiaxe.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by billymobile on 1/11/17.
 */

public class Question implements Parcelable{
    private String answers;
    private String image;
    private String question;
    private String result;

    public Question(){

    }
    public Question(String answers, String result, String question, String image) {
        this.answers = answers;
        this.result = result;
        this.question = question;
        this.image = image;
    }

    protected Question(Parcel in) {
        answers = in.readString();
        image = in.readString();
        question = in.readString();
        result = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(answers);
        parcel.writeString(image);
        parcel.writeString(question);
        parcel.writeString(result);
    }
}
