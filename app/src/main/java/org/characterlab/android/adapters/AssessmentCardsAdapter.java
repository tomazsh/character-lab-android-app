package org.characterlab.android.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.characterlab.android.fragments.AssessmentCardFragment;
import org.characterlab.android.models.Strength;

/**
 * Created by mandar.b on 7/9/2014.
 */
public class AssessmentCardsAdapter extends FragmentPagerAdapter  {

    private static int NUM_ITEMS = 7;

    public AssessmentCardsAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public float getPageWidth (int position)
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
        switch (position) {
            case 0:
                return createAssessmentCardFragment(Strength.CURIOSITY);
            case 1:
                return createAssessmentCardFragment(Strength.GRATITUDE);
            case 2:
                return createAssessmentCardFragment(Strength.GRIT);
            case 3:
                return createAssessmentCardFragment(Strength.OPTIMISM);
            case 4:
                return createAssessmentCardFragment(Strength.SELF_CONTROL);
            case 5:
                return createAssessmentCardFragment(Strength.SOCIAL_INTELLIGENCE);
            case 6:
                return createAssessmentCardFragment(Strength.ZEST);
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Character " + (position + 1);
    }

    private AssessmentCardFragment createAssessmentCardFragment(Strength strength) {
        AssessmentCardFragment cardFragment = AssessmentCardFragment.newInstance(strength);
        return cardFragment;
    }


}

