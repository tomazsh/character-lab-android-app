package org.characterlab.android.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.characterlab.android.R;
import org.characterlab.android.models.Student;

public class StudentDetailsFragment extends Fragment {
    Student mStudent;

    public StudentDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_details, container, false);
    }

    //region Getters abd Setters

    public Student getStudent() {
        return mStudent;
    }

    public void setStudent(Student student) {
        mStudent = student;
        // TODO: Configure views with student data
    }

    //endregion
}
