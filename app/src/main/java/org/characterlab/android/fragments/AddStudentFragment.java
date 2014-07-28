package org.characterlab.android.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import org.characterlab.android.R;

/**
 * Created by mandar.b on 7/26/2014.
 */
public class AddStudentFragment extends Fragment {

    private ImageView ivNewStudentCameraButton;
    private EditText etNewStudentName;
    private AddStudentFragmentListener mListener;

    public interface AddStudentFragmentListener {
        public void onPhotoButtonPressed();
        public void onStudentNameChanged(String name);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_student, container, false);
        ivNewStudentCameraButton = (ImageView) v.findViewById(R.id.ivNewStudentCameraButton);
        ivNewStudentCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onPhotoButtonPressed();
            }
        });

        etNewStudentName = (EditText) v.findViewById(R.id.etNewStudentName);
        etNewStudentName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mListener.onStudentNameChanged(s.toString());
            }
        });

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (AddStudentFragmentListener) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

}
