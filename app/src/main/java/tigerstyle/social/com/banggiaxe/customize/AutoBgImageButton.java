package tigerstyle.social.com.banggiaxe.customize;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageButton;

public class AutoBgImageButton extends ImageButton {

	public AutoBgImageButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public AutoBgImageButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AutoBgImageButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setImageResource(int resId) {
		super.setImageResource(resId);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		if (e.getAction() == MotionEvent.ACTION_DOWN) {
			this.setAlpha((int) (0.8 * 255));
		} else if (e.getAction() == MotionEvent.ACTION_UP) {
			this.setAlpha((int) (1.0 * 255));
		}

		return super.onTouchEvent(e);

	}

}
