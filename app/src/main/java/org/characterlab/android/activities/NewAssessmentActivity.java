package org.characterlab.android.activities;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseImageView;

import org.characterlab.android.CharacterLabApplication;
import org.characterlab.android.R;
import org.characterlab.android.adapters.AssessmentCardsAdapter;
import org.characterlab.android.fragments.AssessmentCardFragment;
import org.characterlab.android.fragments.AssessmentCardsFragment;
import org.characterlab.android.fragments.StudentListFragment;
import org.characterlab.android.helpers.DialogHelper;
import org.characterlab.android.helpers.ParseClient;
import org.characterlab.android.models.NewAssessmentViewModel;
import org.characterlab.android.models.Strength;
import org.characterlab.android.models.Student;

public class NewAssessmentActivity extends FragmentActivity
             implements StudentListFragment.StudentListFragmentListener,
                        AssessmentCardsFragment.AssessmentCardsFragmentListener,
                        AssessmentCardFragment.AssessmentCardFragmentListener{

    StudentListFragment mStudentListFragment;
    AssessmentCardsFragment mAssessmentCardsFragment;
    FragmentPagerAdapter adapterViewPager;
    boolean displaySaveMenu = false;

    NewAssessmentViewModel viewModel;
    Student mStudent;

    Button btnNewAssessmentSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_assessment);

        viewModel = new NewAssessmentViewModel();
        adapterViewPager = new AssessmentCardsAdapter(getSupportFragmentManager());
        showStudentListFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!displaySaveMenu) {
            return super.onCreateOptionsMenu(menu);
        } else {
            getMenuInflater().inflate(R.menu.new_assessment_menu, menu);
            return true;
        }
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        MenuItem actionViewItem = menu.findItem(R.id.miNewAssessmentSave);
//        if (actionViewItem != null) {
//            View v = actionViewItem.getActionView();
//            btnNewAssessmentSave = (Button) v.findViewById(R.id.btnNewAssessmentSave);
//            btnNewAssessmentSave.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//            });
//        }
//        return super.onPrepareOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.miNewAssessmentSave) {
//            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
            DialogHelper.showConfirmDialog(this, R.string.new_assessment_confirmation_title, R.string.new_assessment_confirmation_msg, new DialogHelper.DialogListener() {
                @Override
                public void onOk(DialogInterface dialog) {
                    dialog.dismiss();
                    ParseClient.saveStudentAssessment(viewModel, mStudent);
                    finish();
                }
            });
        }
        return true;
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
        if  (student != null) {
            mStudent = student;
            getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getActionBar().setCustomView(R.layout.student_details_actionbar);
            displaySaveMenu = true;
            invalidateOptionsMenu();

            View v = getActionBar().getCustomView();
            TextView abTvStudentDetailsName = (TextView) v.findViewById(R.id.abTvStudentDetailsName);
            ParseImageView abPivStudentDetailsImage = (ParseImageView) v.findViewById(R.id.abPivStudentDetailsImage);
            abTvStudentDetailsName.setText(student.getName());
            abPivStudentDetailsImage.setParseFile(student.getProfileImage());
            abPivStudentDetailsImage.loadInBackground();

            CharacterLabApplication.putInCache(student.getObjectId(), student);
            if (mAssessmentCardsFragment == null) {
                mAssessmentCardsFragment = AssessmentCardsFragment.newInstance(student.getObjectId());
            }
            setContainerFragment(mAssessmentCardsFragment);
        }
    }

    //endregion

    public FragmentPagerAdapter getAdapterViewPager() {
        return adapterViewPager;
    }

    @Override
    public void onStrenthScoreSet(Strength strength, int score) {
        viewModel.getStrengthScores().put(strength, score);
    }

}
