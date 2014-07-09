package org.characterlab.android.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.characterlab.android.R;

public class LoginFragment extends Fragment {
    LoginFragmentListener mListener;
    private Button btnLogin;
    private EditText etUserName;
    private EditText etPassword;


    public interface LoginFragmentListener {
        void onLoginFragmentSuccess(ParseUser user);
        void onLoginFragmentFailure(ParseException exception);
    }

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        this.setUpViews(view);
        return view;
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

    private void setUpViews(View view) {
        etUserName = (EditText) view.findViewById(R.id.etUserName);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(etUserName.getText().toString(), etPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if (parseUser != null) {
                            // dismiss keyboard
                            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                            etUserName.setText("");
                            etPassword.setText("");
                            mListener.onLoginFragmentSuccess(parseUser);

                        } else {
                            mListener.onLoginFragmentFailure(e);
                        }
                    }
                });
            }
        });
    }
}
