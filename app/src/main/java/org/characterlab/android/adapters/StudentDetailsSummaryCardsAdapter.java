package org.characterlab.android.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.characterlab.android.fragments.AssessmentCardFragment;
import org.characterlab.android.fragments.ImprovementTipsCardFragment;
import org.characterlab.android.fragments.StrongStrengthCardFragment;
import org.characterlab.android.fragments.WeakStrengthCardFragment;
import org.characterlab.android.models.Strength;
import org.characterlab.android.models.StrengthAssessment;

import java.util.List;

/**
 * Created by mandar.b on 7/11/2014.
 */
public class StudentDetailsSummaryCardsAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 5;
    List<StrengthAssessment> assessmentList;

    public StudentDetailsSummaryCardsAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public float getPageWidth(int position)
    {
        return 0.93f;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        // Todo: handle assessmentList size properly instead of just looking up items by index
        int listSize = assessmentList.size();
        switch (position) {
            case 0:
                return StrongStrengthCardFragment.newInstance(1, NUM_ITEMS, assessmentList.get(0).getStrength(), assessmentList.get(1).getStrength());
            case 1: {
                return WeakStrengthCardFragment.newInstance(2, NUM_ITEMS, assessmentList.get(listSize - 1).getStrength(), assessmentList.get(listSize - 2).getStrength(), assessmentList.get(listSize - 3).getStrength());
            }
            case 2:
                return ImprovementTipsCardFragment.newInstance(3, NUM_ITEMS, assessmentList.get(listSize - 1).getStrength());
            case 3:
                return ImprovementTipsCardFragment.newInstance(4, NUM_ITEMS, assessmentList.get(listSize - 2).getStrength());
            case 4:
                return ImprovementTipsCardFragment.newInstance(5, NUM_ITEMS, assessmentList.get(listSize - 3).getStrength());
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Character " + (position + 1);
    }

    public void setAssessmentList(List<StrengthAssessment> assessmentList) {
        this.assessmentList = assessmentList;
    }

    public List<StrengthAssessment> getAssessmentList() {

        return assessmentList;
    }

}
