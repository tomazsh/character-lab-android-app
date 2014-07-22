package org.characterlab.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import org.characterlab.android.CharacterLabApplication;
import org.characterlab.android.R;
import org.characterlab.android.fragments.StrengthDetailsFragment;
import org.characterlab.android.helpers.ProgressBarHelper;
import org.characterlab.android.models.Strength;
import org.characterlab.android.models.StrengthInfo;
import org.characterlab.android.models.Student;

public class StrengthDetailsActivity extends FragmentActivity
        implements StrengthDetailsFragment.StrengthDetailsFragmentListener {
    public static final String STRENGTH_KEY = "strength";
    StrengthDetailsFragment mStrengthDetailsFragment;
    private ProgressBarHelper progressBarHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBarHelper = new ProgressBarHelper(this);
        setContentView(R.layout.activity_strength_details);

        progressBarHelper.setupProgressBarViews(this);

        if (savedInstanceState == null) {
            mStrengthDetailsFragment = new StrengthDetailsFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, mStrengthDetailsFragment)
                    .commit();
        }

        Strength strength = (Strength)getIntent().getSerializableExtra(STRENGTH_KEY);
        getActionBar().setTitle(strength.getName());

        StrengthInfo info = StrengthInfo.fromStrength(this, strength);
        mStrengthDetailsFragment.setStrengthInfo(info);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.strength_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_new_assessment) {
            startAddAssessmentActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startAddAssessmentActivity() {
        Intent intent = new Intent(StrengthDetailsActivity.this, NewAssessmentActivity.class);
        startActivity(intent);
    }

    //region StrengthDetailsFragmentListener

    public void onStrengthDetailsStudentClick(Student student) {
        Intent intent = new Intent(StrengthDetailsActivity.this, StudentDetailsActivity.class);
        CharacterLabApplication.putInCache(student.getObjectId(), student);
        intent.putExtra(StudentDetailsActivity.STUDENT_KEY, student.getObjectId());
        startActivity(intent);
    }

    public FragmentManager strengthDetailsFragmentManager() {
        return getSupportFragmentManager();
    }

    @Override
    public void dataRequestSent() {
        progressBarHelper.showProgressBar();
    }

    @Override
    public void dataReceived() {
        progressBarHelper.hideProgressBar();
    }


    //endregion
}
