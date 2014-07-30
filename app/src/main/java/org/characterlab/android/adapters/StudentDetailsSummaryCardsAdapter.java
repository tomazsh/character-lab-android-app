package org.characterlab.android.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.characterlab.android.fragments.ImprovementTipsCardFragment;
import org.characterlab.android.fragments.WeakStrengthCardFragment;
import org.characterlab.android.models.Strength;
import org.characterlab.android.models.StrengthAssessment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mandar.b on 7/11/2014.
 */
public class StudentDetailsSummaryCardsAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 4;
    List<StrengthAssessment> assessmentList;
    Map<Integer, Fragment> itemsMap = new HashMap<Integer, Fragment>();

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
//            case 0:
//                return StrongStrengthCardFragment.newInstance(1, NUM_ITEMS, assessmentList.get(0).getStrength(), assessmentList.get(1).getStrength());

            case 0: {
                WeakStrengthCardFragment weakStrengthCardFragment = WeakStrengthCardFragment.newInstance(1, NUM_ITEMS, assessmentList.get(listSize - 1).getStrength(), assessmentList.get(listSize - 2).getStrength(), assessmentList.get(listSize - 3).getStrength());
                itemsMap.put(1, weakStrengthCardFragment);
                return weakStrengthCardFragment;
            }
            case 1: {
                ImprovementTipsCardFragment improvementTipsCardFragment = ImprovementTipsCardFragment.newInstance(2, NUM_ITEMS, assessmentList.get(listSize - 1).getStrength());
                itemsMap.put(2, improvementTipsCardFragment);
                return improvementTipsCardFragment;
            }
            case 2: {
                ImprovementTipsCardFragment improvementTipsCardFragment2 = ImprovementTipsCardFragment.newInstance(3, NUM_ITEMS, assessmentList.get(listSize - 2).getStrength());
                itemsMap.put(3, improvementTipsCardFragment2);
                return improvementTipsCardFragment2;
            }
            case 3: {
                ImprovementTipsCardFragment improvementTipsCardFragment3 = ImprovementTipsCardFragment.newInstance(4, NUM_ITEMS, assessmentList.get(listSize - 3).getStrength());
                itemsMap.put(4, improvementTipsCardFragment3);
                return improvementTipsCardFragment3;
            }
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

    public void refreshItems() {
        if (itemsMap.isEmpty()) {
            return;
        }

        int listSize = assessmentList.size();
        WeakStrengthCardFragment weakStrengthCardFragment = (WeakStrengthCardFragment)itemsMap.get(1);
        weakStrengthCardFragment.resetView(1, NUM_ITEMS, assessmentList.get(listSize - 1).getStrength(), assessmentList.get(listSize - 2).getStrength(), assessmentList.get(listSize - 3).getStrength());

        for (int i = 2, j = 1; i < 5; i++, j++) {
            Strength strength = assessmentList.get(listSize - j).getStrength();
            ImprovementTipsCardFragment improvementTipsCardFragment = (ImprovementTipsCardFragment)itemsMap.get(i);
            improvementTipsCardFragment.resetView(i, NUM_ITEMS, strength);
        }

    }

}
