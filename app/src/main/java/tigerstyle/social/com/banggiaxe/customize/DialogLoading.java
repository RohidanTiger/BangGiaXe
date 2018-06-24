package tigerstyle.social.com.banggiaxe.customize;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import tigerstyle.social.com.banggiaxe.R;


/**
 * Created by nextophn on 5/9/15.
 */
public class DialogLoading extends Dialog {

    public DialogLoading(Context context) {
        super(context);
        setCancelable(true);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading_custom);
    }
}

