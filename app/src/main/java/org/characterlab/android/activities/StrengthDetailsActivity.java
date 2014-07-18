package org.characterlab.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import org.characterlab.android.CharacterLabApplication;
import org.characterlab.android.R;
import org.characterlab.android.fragments.StrengthDetailsFragment;
import org.characterlab.android.models.Strength;
import org.characterlab.android.models.StrengthInfo;
import org.characterlab.android.models.Student;

public class StrengthDetailsActivity extends FragmentActivity
        implements StrengthDetailsFragment.StrengthDetailsFragmentListener {
    public static final String STRENGTH_KEY = "strength";
    StrengthDetailsFragment mStrengthDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strength_details);
        getActionBar().setDisplayHomeAsUpEnabled(true);

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

    //endregion
}
