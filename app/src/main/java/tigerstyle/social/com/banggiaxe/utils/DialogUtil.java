package tigerstyle.social.com.banggiaxe.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import tigerstyle.social.com.banggiaxe.R;


public class DialogUtil {

    public static void showAlert(Activity activity, String text) {
        new AlertDialog.Builder(activity).setMessage(text).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with close
            }
        }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }

    public static void showYesNoDialog(final Context context, int titleId, int messageId, int OkTextId, int cancelTextId, int drawable,
                                       final DialogInterface.OnClickListener onOKClick) {
        if (context != null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//			if (titleId > 0)
//				alertDialog.setTitle(context.getString(titleId));
//
//			if (drawable > 0)
//				alertDialog.setIcon(drawable);
            alertDialog.setMessage(context.getString(messageId));
            alertDialog.setNegativeButton(cancelTextId, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.setPositiveButton(OkTextId, onOKClick);
            alertDialog.show();
        }
    }

    public static void showYesNoDialog(final Context context, int titleId, int messageId, int OkTextId, int cancelTextId, int drawable,
                                       final DialogInterface.OnClickListener onOKClick, final DialogInterface.OnClickListener onCancelClick) {
        if (context != null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            if (titleId > 0)
                alertDialog.setTitle(context.getString(titleId));
            if (drawable > 0)
                alertDialog.setIcon(drawable);
            alertDialog.setMessage(context.getString(messageId));
            alertDialog.setNegativeButton(cancelTextId, onCancelClick);
            alertDialog.setPositiveButton(OkTextId, onOKClick);
            AlertDialog dlg = alertDialog.create();

            dlg.setCanceledOnTouchOutside(false);

            dlg.show();
        }
    }

    public static void showYesNoDialog(final Context context, int messageId, int OkTextId, int cancelTextId,
                                       final DialogInterface.OnClickListener onOKClick, final DialogInterface.OnClickListener onCancelClick) {
        if (context != null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setMessage(context.getString(messageId));
            alertDialog.setNegativeButton(cancelTextId, onCancelClick);
            alertDialog.setPositiveButton(OkTextId, onOKClick);
            AlertDialog dlg = alertDialog.create();

            dlg.setCanceledOnTouchOutside(false);

            dlg.show();
        }
    }


    public static void showYesNoDialog(final Context context, int messageId, int OkTextId, int cancelTextId, final DialogInterface.OnClickListener onOKClick) {
        showYesNoDialog(context, R.string.app_name, messageId, OkTextId, cancelTextId, R.drawable.btn_home, onOKClick);
    }


}
