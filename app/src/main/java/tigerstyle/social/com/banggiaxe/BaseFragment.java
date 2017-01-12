package tigerstyle.social.com.banggiaxe;


import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {
    public MainActivity context;
    public String TAG = null;
    public View rootView;
    private static ImageLoader mImageLoader;
    protected Typeface mTfLight;
    public FirebaseDatabase mFirebaseInstance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (MainActivity) getActivity();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        TAG = BaseFragment.class.getSimpleName();
        mImageLoader= ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(context));
        mTfLight = Typeface.createFromAsset(context.getAssets(), "OpenSans-Light.ttf");
    }

    public void showDialogNoNetwork() {

        new AlertDialog.Builder(context)
                .setTitle(R.string.app_name)
                .setMessage(R.string.noNetwork)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                Intent settings = new Intent(
                                        android.provider.Settings.ACTION_WIFI_SETTINGS);
                                settings.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(settings);
                                context.finish();
                            }
                        })
                .setNegativeButton(R.string.cmn_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                context.finish();
                            }
                        }).show();


    }


    // ========Common functions========//


    public void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public void showToast(final int idString) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, idString, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void showToast(final String message) {
        if (message != null) {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * Display Toast Message
     *
     * @param message
     * @param timeDisplay in miliseconds
     */

    public void showToast(String message, int timeDisplay) {
        if (message != null)
            Toast.makeText(context, message, timeDisplay).show();
    }


    public void showLoading() {
        context.showLoading();
    }


    public void showLoading(String message) {
        context.showLoading();
    }


    public void hideLoading() {
        context.hideLoading();
    }

    public boolean canPressBack() {
        return false;
    }

    protected void hideKeyboard(EditText editText) {
        try {
            ((InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    editText.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void showKeyboard(EditText edit) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edit, 0);
    }

    protected void hideKeyBoard() {
        try {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            // Check if no view has focus
            View v = context.getCurrentFocus();
            if (v != null) {
                inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

