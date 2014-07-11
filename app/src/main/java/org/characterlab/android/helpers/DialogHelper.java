package org.characterlab.android.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import org.characterlab.android.R;

public class DialogHelper {

    public static void showAlertDialog(Context context, int titleResource, int messageResource) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titleResource)
                .setMessage(messageResource)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }

    public static void showConfirmDialog(Context context, int titleResource, int messageResource, final DialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titleResource)
                .setMessage(messageResource)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onOk(dialog);
                    }
                })
                .setNegativeButton(R.string.cancel_action, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    public interface DialogListener {
        void onOk(DialogInterface dialog);
    }
}
