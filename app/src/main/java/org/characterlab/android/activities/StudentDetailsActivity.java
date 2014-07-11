package org.characterlab.android.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;

import org.characterlab.android.CharacterLabApplication;
import org.characterlab.android.R;
import org.characterlab.android.fragments.StrengthHistoryFragment;
import org.characterlab.android.fragments.StudentDetailsFragment;
import org.characterlab.android.helpers.ParseClient;
import org.characterlab.android.models.Strength;
import org.characterlab.android.models.StrengthAssessment;
import org.characterlab.android.models.Student;
import org.characterlab.android.views.BarGraph;

import java.util.List;

public class StudentDetailsActivity extends FragmentActivity  implements StudentDetailsFragment.StudentDetailsFragmentListener {
    public static final String STUDENT_KEY = "studentId";
    private Student mStudent;

    private StudentDetailsFragment mStudentDetailsFragment;
    private StrengthHistoryFragment mStrengthHistoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.student_details_actionbar);

        String studentId = (String) getIntent().getSerializableExtra(STUDENT_KEY);
        mStudent = (studentId != null && !studentId.isEmpty()) ? (Student) CharacterLabApplication.readFromCache(studentId) : null;
        if  (mStudent != null) {
            View v = getActionBar().getCustomView();
            TextView abTvStudentDetailsName = (TextView) v.findViewById(R.id.abTvStudentDetailsName);
            ParseImageView abPivStudentDetailsImage = (ParseImageView) v.findViewById(R.id.abPivStudentDetailsImage);
            abTvStudentDetailsName.setText(mStudent.getName());
            abPivStudentDetailsImage.setParseFile(mStudent.getProfileImage());
            abPivStudentDetailsImage.loadInBackground();
        }

        if (savedInstanceState == null) {
            showStudentDetailFragment();
        }
    }

    // TODO: handle backstack
    private void setContainerFragment(Fragment fragment) {
        if (fragment.isAdded() && !fragment.isDetached() && !fragment.isRemoving()) {
            return;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.studentDetailsContainer, fragment)
                .commit();
    }

    private void showStudentDetailFragment() {
        if (mStudentDetailsFragment == null) {
            mStudentDetailsFragment = new StudentDetailsFragment();
        }
        mStudentDetailsFragment.setStudent(mStudent);
        setContainerFragment(mStudentDetailsFragment);
    }

    private void showStrengthHistoryFragment(Strength strength) {
        if (mStrengthHistoryFragment == null) {
            mStrengthHistoryFragment = new StrengthHistoryFragment();
        }
        mStrengthHistoryFragment.setStudent(mStudent);
        mStrengthHistoryFragment.setStrength(strength);
        setContainerFragment(mStrengthHistoryFragment);
    }

    @Override
    public void onBarGraphClick(String strengthName) {
        Strength strength = Strength.fromName(strengthName);
        showStrengthHistoryFragment(strength);
    }
}
