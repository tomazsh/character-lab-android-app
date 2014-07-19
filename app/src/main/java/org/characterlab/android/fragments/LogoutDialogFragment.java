package org.characterlab.android.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_out, container);
        Button signOutButton = (Button) view.findViewById(R.id.btnSignOut);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();
                if (currentUser == null) {
                    try {
                        LogoutFragmentListener logoutFragmentListener = (LogoutFragmentListener) getActivity();
                        logoutFragmentListener.onLogoutFragmentSuccess();
                        getDialog().dismiss();
                    } catch (ClassCastException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });
         Button cancelButton = (Button) view.findViewById(R.id.btnCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        return view;
    }
}