package org.characterlab.android.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseException;
import com.parse.ParseUser;

import org.characterlab.android.CharacterLabApplication;
import org.characterlab.android.R;
import org.characterlab.android.fragments.CharacterCardsFragment;
import org.characterlab.android.fragments.LoginFragment;
import org.characterlab.android.fragments.LogoutDialogFragment;
import org.characterlab.android.fragments.StudentListFragment;
import org.characterlab.android.helpers.DialogHelper;
import org.characterlab.android.models.Strength;
import org.characterlab.android.models.Student;

public class MainActivity extends Activity
        implements LoginFragment.LoginFragmentListener,
        LogoutDialogFragment.LogoutFragmentListener,
        CharacterCardsFragment.CharacterCardsFragmentListener,
        StudentListFragment.StudentListFragmentListener {
    LoginFragment mLoginFragment;
    CharacterCardsFragment mCharacterCardsFragment;
    StudentListFragment mStudentListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseUser user = ParseUser.getCurrentUser();
        if (user == null) {
            showLoginFragment();
        } else {
            showCharacterCardsFragment();
        }
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
        setContainerFragment(mLoginFragment);
    }

    private void showCharacterCardsFragment() {
        if (mCharacterCardsFragment == null) {
            mCharacterCardsFragment = new CharacterCardsFragment();
        }
        getActionBar().show();
        setContainerFragment(mCharacterCardsFragment);
    }

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
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private void showLogoutDialog() {
        FragmentManager fm = getFragmentManager();
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
}
