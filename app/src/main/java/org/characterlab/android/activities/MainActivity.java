package org.characterlab.android.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseException;
import com.parse.ParseUser;

import org.characterlab.android.CharacterLabApplication;
import org.characterlab.android.R;
import org.characterlab.android.fragments.CharacterCardFragment;
import org.characterlab.android.fragments.CharacterCardsFragment;
import org.characterlab.android.fragments.LoginFragment;
import org.characterlab.android.fragments.LogoutDialogFragment;
import org.characterlab.android.fragments.StudentListFragment;
import org.characterlab.android.helpers.DialogHelper;
import org.characterlab.android.helpers.ProgressBarHelper;
import org.characterlab.android.models.Strength;
import org.characterlab.android.models.Student;

public class MainActivity extends FragmentActivity
        implements LoginFragment.LoginFragmentListener,
        LogoutDialogFragment.LogoutFragmentListener,
        CharacterCardFragment.CharacterCardFragmentListener,
        StudentListFragment.StudentListFragmentListener {
    LoginFragment mLoginFragment;
    CharacterCardsFragment mCharacterCardsFragment;
    private final String CHARACTER_CARDS_FRAGMENT_TAG = "charactercardsfragmenttag";
    StudentListFragment mStudentListFragment;
    ProgressBarHelper progressBarHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBarHelper = new ProgressBarHelper(this);
        setContentView(R.layout.activity_main);

        progressBarHelper.setupProgressBarViews(this);

        ParseUser user = ParseUser.getCurrentUser();
        if (user == null) {
            showLoginFragment();
        } else {
            if (savedInstanceState != null) { // saved instance state, fragment may exist
                // on savedInstanceState don't recreate fragments,
                // look up the instance that already exists by tag
                mCharacterCardsFragment = (CharacterCardsFragment)
                        getSupportFragmentManager().findFragmentByTag(CHARACTER_CARDS_FRAGMENT_TAG);
            }
            showCharacterCardsFragment();
        }

        getActionBar().setTitle("");
    }

    //region Options Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            showLogoutDialog();
            return true;
        } else if (id == R.id.action_character_cards) {
            showCharacterCardsFragment();
            return true;
        } else if (id == R.id.action_student_list) {
            showStudentListFragment();
            return true;
        } else if (id == R.id.action_new_assessment) {
            startAddAssessmentActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region Fragment Swapping

    private void showLoginFragment() {
        if (mLoginFragment == null) {
            mLoginFragment = new LoginFragment();
        }
        getActionBar().hide();
        setContainerFragment(mLoginFragment, "lf");
    }

    private void showCharacterCardsFragment() {
        if (mCharacterCardsFragment == null) {
            mCharacterCardsFragment = new CharacterCardsFragment();
        }
        getActionBar().show();
        setContainerFragment(mCharacterCardsFragment, "ccf");
    }

    private void showStudentListFragment() {
        if (mStudentListFragment == null) {
            mStudentListFragment = new StudentListFragment();
        }
        setContainerFragment(mStudentListFragment, "slf");
    }

    private void setContainerFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (!fragment.isAdded()) {
            transaction.add(R.id.container, fragment, tag);
        } else {
            transaction.show(fragment);
        }

        if (mCharacterCardsFragment != null && mCharacterCardsFragment.isVisible() && fragment != mCharacterCardsFragment) {
            transaction.hide(mCharacterCardsFragment);
        }

        if (mStudentListFragment != null && mStudentListFragment.isVisible() && fragment != mStudentListFragment) {
            transaction.hide(mStudentListFragment);
        }

        if (mLoginFragment != null && mLoginFragment.isVisible() && fragment != mLoginFragment) {
            transaction.hide(mLoginFragment);
        }

        transaction.commit();
    }

    private void showLogoutDialog() {
        FragmentManager fm = getSupportFragmentManager();
        LogoutDialogFragment alertDialog = new LogoutDialogFragment();
        alertDialog.show(fm, "fragment_alert");
    }
    //endregion

    //region LoginFragmentListener

    public void onLoginFragmentSuccess(ParseUser user) {
        showCharacterCardsFragment();
    }

    public void onLoginFragmentFailure(ParseException exception) {
        DialogHelper.showAlertDialog(this, R.string.login_error_title, R.string.login_error_message);
    }

    //endregion

    //region LogoutFragmentListener
    public void onLogoutFragmentSuccess() {
        showLoginFragment();
    }
    //endregion

    //region CharacterCardsFragmentListener

    public void onStrengthCardClick(Strength strength) {
        Intent intent = new Intent(MainActivity.this, StrengthDetailsActivity.class);
        intent.putExtra(StrengthDetailsActivity.STRENGTH_KEY, strength);
        startActivity(intent);
    }

    //endregion

    //region StudentListFragmentListener

    public void onStudentListItemClick(Student student) {
        Intent intent = new Intent(MainActivity.this, StudentDetailsActivity.class);
        CharacterLabApplication.putInCache(student.getObjectId(), student);
        intent.putExtra(StudentDetailsActivity.STUDENT_KEY, student.getObjectId());
        startActivity(intent);
    }

    //endregion

    private void startAddAssessmentActivity() {
        Intent intent = new Intent(MainActivity.this, NewAssessmentActivity.class);
        startActivity(intent);
    }

    private void startStrengthActivity(Strength strength) {
        Intent intent = new Intent(MainActivity.this, StrengthDetailsActivity.class);
        intent.putExtra(StrengthDetailsActivity.STRENGTH_KEY, strength);
        startActivity(intent);
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
