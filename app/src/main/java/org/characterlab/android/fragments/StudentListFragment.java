package org.characterlab.android.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.characterlab.android.R;
import org.characterlab.android.models.Student;

public class StudentListFragment extends Fragment {
    StudentListFragmentListener mListener;

    public interface StudentListFragmentListener {
        void onStudentListItemClick(Student student);
    }

    public StudentListFragment() {
        // test
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_list, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (StudentListFragmentListener)activity;
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
    }
}
