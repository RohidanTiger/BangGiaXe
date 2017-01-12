package tigerstyle.social.com.banggiaxe.customize;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import tigerstyle.social.com.banggiaxe.R;

/**
 * Created by billymobile on 12/21/16.
 */

public class SuffixTextView extends TextView {
    private float mRatio;
    private int mSuffixLength;

    public void setmRatio(float ratio){
        this.mRatio = ratio;
    }

    public void setmSuffixLength(int suffixLength){
        this.mSuffixLength = suffixLength;
    }

    public SuffixTextView(Context context) {
        super(context);
    }

    public SuffixTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SuffixTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.SuffixTextView);
        mRatio = typedArray.getFloat(R.styleable.SuffixTextView_ratio, 1f);
        mSuffixLength = typedArray.getInteger(R.styleable.SuffixTextView_suffix_length, 0);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        String origin = getText().toString();
        int originLength = origin.length();
        if(originLength == 0) return;
        SpannableString span = new SpannableString(origin);
        if(originLength - mSuffixLength > 0){
            span.setSpan(new RelativeSizeSpan(mRatio), originLength - mSuffixLength, originLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            span.setSpan(new StyleSpan(Typeface.ITALIC), originLength - mSuffixLength, originLength, 0);
        }
        setText(span);
        super.onDraw(canvas);
    }
}
