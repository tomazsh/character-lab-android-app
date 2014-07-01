package org.characterlab.android.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.characterlab.android.R;
import org.characterlab.android.models.Strength;
import org.characterlab.android.models.Student;

public class StrengthDetailsFragment extends Fragment {
    Strength mStrength;
    StrengthDetailsFragmentListener mListener;

    public interface StrengthDetailsFragmentListener {
        void onStrengthDetailsStudentClick(Student student);
    }

    public StrengthDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_strength_details, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (StrengthDetailsFragmentListener)activity;
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
    }

    //region Getters and Setters

    public Strength getStrength() {
        return mStrength;
    }

    public void setStrength(Strength strength) {
        mStrength = strength;
        // TODO: Configure views with strength data
    }

    //endregion
}
