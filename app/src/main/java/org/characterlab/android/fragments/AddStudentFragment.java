package org.characterlab.android.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.characterlab.android.R;

/**
 * Created by mandar.b on 7/26/2014.
 */
public class AddStudentFragment extends Fragment {

    private ImageView ivNewStudentCameraButton;
    private AddStudentFragmentListener mListener;

    public interface AddStudentFragmentListener {
        public void onPhotoButtonPressed();
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
