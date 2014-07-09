package org.characterlab.android.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseUser;

import org.characterlab.android.R;
import org.characterlab.android.adapters.AssessmentCardsAdapter;
import org.characterlab.android.adapters.CharacterCardsAdapter;
import org.characterlab.android.fragments.AssessmentCardsFragment;
import org.characterlab.android.fragments.CharacterCardsFragment;
import org.characterlab.android.fragments.LoginFragment;
import org.characterlab.android.fragments.LogoutDialogFragment;
import org.characterlab.android.fragments.StudentListFragment;
import org.characterlab.android.models.Student;

public class NewAssessmentActivity extends FragmentActivity
             implements StudentListFragment.StudentListFragmentListener,
                        AssessmentCardsFragment.AssessmentCardsFragmentListener {

    StudentListFragment mStudentListFragment;
    AssessmentCardsFragment mAssessmentCardsFragment;
    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_assessment);

        adapterViewPager = new AssessmentCardsAdapter(getSupportFragmentManager());
        showStudentListFragment();
    }

    //region Fragment Swapping

    private void showStudentListFragment() {
        if (mStudentListFragment == null) {
            mStudentListFragment = new StudentListFragment();
        }
        setContainerFragment(mStudentListFragment);
    }

    private void setContainerFragment(Fragment fragment) {
        if (fragment.isAdded() && !fragment.isDetached() && !fragment.isRemoving()) {
            return;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.newAssessmentContainer, fragment)
                .commit();
    }

    //endregion

    //region StudentListFragmentListener

    public void onStudentListItemClick(Student student) {
        if (mAssessmentCardsFragment == null) {
            mAssessmentCardsFragment = new AssessmentCardsFragment();
        }
        setContainerFragment(mAssessmentCardsFragment);
    }

    //endregion

    public FragmentPagerAdapter getAdapterViewPager() {
        return adapterViewPager;
    }
}
