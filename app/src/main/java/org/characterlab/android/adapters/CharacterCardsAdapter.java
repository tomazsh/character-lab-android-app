package org.characterlab.android.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import org.characterlab.android.fragments.CharacterCardFragment;
import org.characterlab.android.models.Strength;

/**
 * Created by tina on 7/8/14.
 */
public class CharacterCardsAdapter extends SmartFragmentStatePagerAdapter {

    private static int NUM_ITEMS = 7;

    public CharacterCardsAdapter(FragmentManager fragmentManager) {
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
                return CharacterCardFragment.newInstance(Strength.CURIOSITY);
            case 1:
                return CharacterCardFragment.newInstance(Strength.GRATITUDE);
            case 2:
                return CharacterCardFragment.newInstance(Strength.GRIT);
            case 3:
                return CharacterCardFragment.newInstance(Strength.OPTIMISM);
            case 4:
                return CharacterCardFragment.newInstance(Strength.SELF_CONTROL);
            case 5:
                return CharacterCardFragment.newInstance(Strength.SOCIAL_INTELLIGENCE);
            case 6:
                return CharacterCardFragment.newInstance(Strength.ZEST);

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
