package org.characterlab.android.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;

import org.characterlab.android.CharacterLabApplication;
import org.characterlab.android.R;
import org.characterlab.android.fragments.StudentDetailsFragment;
import org.characterlab.android.helpers.ParseClient;
import org.characterlab.android.models.StrengthAssessment;
import org.characterlab.android.models.Student;

import java.util.List;

public class StudentDetailsActivity extends Activity {
    public static final String STUDENT_KEY = "studentId";
    StudentDetailsFragment mStudentDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.student_details_actionbar);

        String studentId = (String) getIntent().getSerializableExtra(STUDENT_KEY);
        Student student = (studentId != null && !studentId.isEmpty()) ? (Student) CharacterLabApplication.readFromCache(studentId) : null;
        if  (student != null) {
            View v = getActionBar().getCustomView();
            TextView abTvStudentDetailsName = (TextView) v.findViewById(R.id.abTvStudentDetailsName);
            ParseImageView abPivStudentDetailsImage = (ParseImageView) v.findViewById(R.id.abPivStudentDetailsImage);
            abTvStudentDetailsName.setText(student.getName());
            abPivStudentDetailsImage.setParseFile(student.getProfileImage());
            abPivStudentDetailsImage.loadInBackground();

            Log.d("debug", "Student in details: " + student.toString());
        }

        if (savedInstanceState == null) {
            mStudentDetailsFragment = new StudentDetailsFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, mStudentDetailsFragment)
                    .commit();
        }

        mStudentDetailsFragment.setStudent(student);
    }

}
