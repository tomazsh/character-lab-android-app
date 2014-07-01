package org.characterlab.android.activities;

import android.app.Activity;
import android.os.Bundle;

import org.characterlab.android.R;
import org.characterlab.android.fragments.StudentDetailsFragment;
import org.characterlab.android.models.Student;

public class StudentDetailsActivity extends Activity {
    public static final String STUDENT_KEY = "student";
    StudentDetailsFragment mStudentDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        if (savedInstanceState == null) {
            mStudentDetailsFragment = new StudentDetailsFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, mStudentDetailsFragment)
                    .commit();
        }

        Student student = (Student)getIntent().getSerializableExtra(STUDENT_KEY);
        mStudentDetailsFragment.setStudent(student);
    }
}
