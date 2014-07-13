package org.characterlab.android.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.characterlab.android.fragments.AssessmentCardFragment;
import org.characterlab.android.fragments.StrongStrengthCardFragment;
import org.characterlab.android.fragments.WeakStrengthCardFragment;
import org.characterlab.android.models.Strength;

/**
 * Created by mandar.b on 7/11/2014.
 */
public class StudentDetailsSummaryCardsAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 5;

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
        switch (position) {
            case 0:
                return StrongStrengthCardFragment.newInstance(0, Strength.CURIOSITY, Strength.GRATITUDE);
            case 1:
                return WeakStrengthCardFragment.newInstance(0, Strength.GRIT, Strength.SELF_CONTROL, Strength.SOCIAL_INTELLIGENCE);
            case 2:
                return StrongStrengthCardFragment.newInstance(0, Strength.SOCIAL_INTELLIGENCE, Strength.ZEST);
            case 3:
                return StrongStrengthCardFragment.newInstance(0, Strength.OPTIMISM, Strength.GRATITUDE);
            case 4:
                return StrongStrengthCardFragment.newInstance(0, Strength.CURIOSITY, Strength.GRATITUDE);
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Character " + (position + 1);
    }

}
