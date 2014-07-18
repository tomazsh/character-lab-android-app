package org.characterlab.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import org.characterlab.android.CharacterLabApplication;
import org.characterlab.android.R;
import org.characterlab.android.fragments.StudentDetailsFragment;
import org.characterlab.android.models.Student;

public class StudentDetailsActivity extends FragmentActivity
             implements StudentDetailsFragment.StudentDetailsFragmentListener {

    public static final String STUDENT_KEY = "studentId";
    private Student mStudent;
    private StudentDetailsFragment mStudentDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        getActionBar().setDisplayHomeAsUpEnabled(true);

//        adapterViewPager = new StudentDetailsSummaryCardsAdapter(getSupportFragmentManager());
//        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getActionBar().setCustomView(R.layout.student_details_actionbar);

        String studentId = (String) getIntent().getSerializableExtra(STUDENT_KEY);
        mStudent = (studentId != null && !studentId.isEmpty()) ? (Student) CharacterLabApplication.readFromCache(studentId) : null;
        if  (mStudent != null) {
//            View v = getActionBar().getCustomView();
//            TextView abTvStudentDetailsName = (TextView) v.findViewById(R.id.abTvStudentDetailsName);
//            ParseImageView abPivStudentDetailsImage = (ParseImageView) v.findViewById(R.id.abPivStudentDetailsImage);
//            abTvStudentDetailsName.setText(mStudent.getName());
//            abPivStudentDetailsImage.setParseFile(mStudent.getProfileImage());
//            abPivStudentDetailsImage.loadInBackground();
              getActionBar().setTitle(mStudent.getName());
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

    @Override
    public void onMeasureStrengthClicked() {
        Intent newAssessmentIntent = new Intent(this, NewAssessmentActivity.class);
        newAssessmentIntent.putExtra("studentId", mStudent.getObjectId());
        startActivity(newAssessmentIntent);
    }
}
