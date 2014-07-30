package org.characterlab.android.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import org.characterlab.android.CharacterLabApplication;
import org.characterlab.android.R;
import org.characterlab.android.events.StudentDeletedEvent;
import org.characterlab.android.fragments.StudentDetailsFragment;
import org.characterlab.android.helpers.ParseClient;
import org.characterlab.android.helpers.ProgressBarHelper;
import org.characterlab.android.models.StrengthAssessment;
import org.characterlab.android.models.Student;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class StudentDetailsActivity extends FragmentActivity
             implements StudentDetailsFragment.StudentDetailsFragmentListener {

    public static final String STUDENT_KEY = "studentId";
    private Student mStudent;
    private StudentDetailsFragment mStudentDetailsFragment;
    private ProgressBarHelper progressBarHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBarHelper = new ProgressBarHelper(this);
        setContentView(R.layout.activity_student_details);

        progressBarHelper.setupProgressBarViews(this);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.student_details_action_new_assessment) {
            onMeasureStrengthClicked();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void deleteStudent() {
        dataRequestSent();
        ParseClient.getAllAssessmentsForStudent(mStudent, new FindCallback<StrengthAssessment>() {
            @Override
            public void done(final List<StrengthAssessment> assessments, ParseException e) {
                mStudent.deleteInBackground(new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "Student Deleted", Toast.LENGTH_SHORT).show();

                            List<ParseObject> objectsToDel = new ArrayList<ParseObject>();
                            objectsToDel.addAll(assessments);
                            StrengthAssessment.deleteAllInBackground(objectsToDel, new DeleteCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e != null) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            dataReceived();
                            EventBus.getDefault().post(new StudentDeletedEvent());
                            finish();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void dataRequestSent() {
        progressBarHelper.showProgressBar();
    }

    @Override
    public void dataReceived() {
        progressBarHelper.hideProgressBar();
    }

}
