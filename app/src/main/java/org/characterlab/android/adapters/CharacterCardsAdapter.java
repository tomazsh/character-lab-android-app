package org.characterlab.android.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.characterlab.android.R;
import org.characterlab.android.fragments.CharacterCardFragment;

/**
 * Created by tina on 7/8/14.
 */
public class CharacterCardsAdapter extends FragmentPagerAdapter {

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
                return CharacterCardFragment.newInstance(0, "Curiosity", "Curiosity is the search for information for its own sake.", R.drawable.curiosity);
            case 1:
                return CharacterCardFragment.newInstance(1, "Gratitude", "Gratitude refers to appreciation for the benefits we receive from others.", R.drawable.gratitude);
            case 2:
                return CharacterCardFragment.newInstance(2, "Grit", "Grit is perseverance and passion for long-term goals.", R.drawable.grit);
            case 3:
                return CharacterCardFragment.newInstance(2, "Optimism", "Optimism is the expectation that the future holds positive possibilities.", R.drawable.optimism);
            case 4:
                return CharacterCardFragment.newInstance(2, "Self-Control", "Self-control is the capacity to regulate thoughts, feelings, or behaviors when they conflict with valued goals.", R.drawable.self_control);
            case 5:
                return CharacterCardFragment.newInstance(2, "Social Intelligence", "Social intelligence refers to awareness of other people’s motives and feelings.", R.drawable.social_intelligence);
            case 6:
                return CharacterCardFragment.newInstance(2, "Zest", "Zest is an approach to life filled with excitement and energy.", R.drawable.zest);

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
