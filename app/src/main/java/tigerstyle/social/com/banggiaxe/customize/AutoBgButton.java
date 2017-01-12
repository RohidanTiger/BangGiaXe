package tigerstyle.social.com.banggiaxe.customize;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.Button;

import tigerstyle.social.com.banggiaxe.R;

public class AutoBgButton extends Button {

    protected int drawableWidth;
    protected DrawablePositions drawablePosition;
    protected int iconPadding;

    // Cached to prevent allocation during onLayout
    Rect bounds;

    private enum DrawablePositions {
        NONE,
        LEFT,
        RIGHT
    }


    public AutoBgButton(Context context) {
        super(context);
        bounds = new Rect();
    }

    public AutoBgButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        bounds = new Rect();
        applyAttributes(attrs);
    }

    public AutoBgButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        bounds = new Rect();
        applyAttributes(attrs);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setBackgroundDrawable(Drawable d) {
        // Replace the original background drawable (e.g. image) with a
        // LayerDrawable that
        // contains the original drawable.
        AutoBackgroundDrawable layer = new AutoBackgroundDrawable(d);
        super.setBackgroundDrawable(layer);
    }

    protected void applyAttributes(AttributeSet attrs) {
        // Slight contortion to prevent allocating in onLayout
        if (null == bounds) {
            bounds = new Rect();
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IconButton);
        int paddingId = typedArray.getDimensionPixelSize(R.styleable.IconButton_iconPadding, 0);
        setIconPadding(paddingId);
        typedArray.recycle();
    }

    public void setIconPadding(int padding) {
        iconPadding = padding;
        requestLayout();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        Paint textPaint = getPaint();
        String text = getText().toString();
        textPaint.getTextBounds(text, 0, text.length(), bounds);

        int textWidth = bounds.width();
        int contentWidth = drawableWidth + iconPadding + textWidth;

        int contentLeft = (int) ((getWidth() / 2.0) - (contentWidth / 2.0));
        setCompoundDrawablePadding(-contentLeft + iconPadding);
        switch (drawablePosition) {
            case LEFT:
                setPadding(contentLeft, 0, 0, 0);
                break;
            case RIGHT:
                setPadding(0, 0, contentLeft, 0);
                break;
            default:
                setPadding(0, 0, 0, 0);
        }
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        if (null != left) {
            drawableWidth = left.getIntrinsicWidth();
            drawablePosition = DrawablePositions.LEFT;
        } else if (null != right) {
            drawableWidth = right.getIntrinsicWidth();
            drawablePosition = DrawablePositions.RIGHT;
        } else {
            drawablePosition = DrawablePositions.NONE;
        }
        requestLayout();
    }


    /**
     * The stateful LayerDrawable used by this button.
     */
    private class AutoBackgroundDrawable extends LayerDrawable {
        // The color filter to apply when the button is pressed
        protected ColorFilter _pressedFilter = new LightingColorFilter(Color.LTGRAY, 1);
        // Alpha value when the button is disabled
        protected int _disabledAlpha = 100;
        // Alpha value when the button is enabled
        protected int _fullAlpha = 255;

        public AutoBackgroundDrawable(Drawable d) {
            super(new Drawable[]{d});
        }

        @Override
        protected boolean onStateChange(int[] states) {
            boolean enabled = false;
            boolean pressed = false;

            for (int state : states) {
                if (state == android.R.attr.state_enabled)
                    enabled = true;
                else if (state == android.R.attr.state_pressed)
                    pressed = true;
            }

            mutate();
            if (enabled && pressed) {
                setColorFilter(_pressedFilter);
            } else if (!enabled) {
                setColorFilter(null);
                setAlpha(_disabledAlpha);
            } else {
                setColorFilter(null);
                setAlpha(_fullAlpha);
            }

            invalidateSelf();

            return super.onStateChange(states);
        }

        @Override
        public boolean isStateful() {
            return true;
        }
    }
}
