package org.characterlab.android.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseException;
import com.parse.ParseUser;

import org.characterlab.android.R;

public class LoginFragment extends Fragment {
    LoginFragmentListener mListener;

    public interface LoginFragmentListener {
        void onLoginFragmentSuccess(ParseUser user);
        void onLoginFragmentFailure(ParseException exception);
    }

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (LoginFragmentListener)activity;
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
    }
}
