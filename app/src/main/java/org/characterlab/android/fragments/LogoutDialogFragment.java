package org.characterlab.android.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.parse.ParseUser;

import org.characterlab.android.R;

public class LogoutDialogFragment extends DialogFragment {
    public LogoutDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    public interface LogoutFragmentListener {
        void onLogoutFragmentSuccess();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage(R.string.logout_warning_text);
        alertDialogBuilder.setPositiveButton(R.string.logout_action,  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            ParseUser.logOut();
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser == null) {
                try {
                    LogoutFragmentListener logoutFragmentListener = (LogoutFragmentListener) getActivity();
                    logoutFragmentListener.onLogoutFragmentSuccess();
                } catch (ClassCastException exception) {
                    exception.printStackTrace();
                }
            }
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.cancel_action, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return alertDialogBuilder.create();
    }
}