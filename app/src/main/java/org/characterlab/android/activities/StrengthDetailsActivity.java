package org.characterlab.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.characterlab.android.R;
import org.characterlab.android.fragments.StrengthDetailsFragment;
import org.characterlab.android.models.Strength;
import org.characterlab.android.models.Student;

public class StrengthDetailsActivity extends Activity
        implements StrengthDetailsFragment.StrengthDetailsFragmentListener {
    public static final String STRENGTH_KEY = "strength";
    StrengthDetailsFragment mStrengthDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strength_details);
        if (savedInstanceState == null) {
            mStrengthDetailsFragment = new StrengthDetailsFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, mStrengthDetailsFragment)
                    .commit();
        }

        Strength strength = (Strength)getIntent().getSerializableExtra(STRENGTH_KEY);
        mStrengthDetailsFragment.setStrength(strength);
    }

    //region StudentListFragmentListener

    public void onStrengthDetailsStudentClick(Student student) {
        Intent intent = new Intent(StrengthDetailsActivity.this, StudentDetailsActivity.class);
//        intent.putExtra(StudentDetailsActivity.STUDENT_KEY, student);
        startActivity(intent);
    }

    //endregion
}
