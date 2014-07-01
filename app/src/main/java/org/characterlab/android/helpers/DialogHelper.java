package org.characterlab.android.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

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
}
