package org.characterlab.android.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.characterlab.android.fragments.StrengthDetailsTextCardFragment;
import org.characterlab.android.fragments.StrengthDetailsVideoCardFragment;
import org.characterlab.android.models.StrengthInfo;
import org.characterlab.android.models.StrengthInfoItem;

import java.util.List;

public class StrengthDetailsCardsAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 7;
    private List<StrengthInfoItem> mBuildItems;
    private StrengthDetailsCardListener mListener;

    public interface StrengthDetailsCardListener {
        void onCardItemClick(StrengthInfoItem item);
    }

    public StrengthDetailsCardsAdapter(FragmentManager fragmentManager,
                                       StrengthDetailsCardListener listener) {
        super(fragmentManager);
        mListener = listener;
    }

    @Override
    public float getPageWidth (int position)
    {
        return 0.93f;
    }

    @Override
    public int getCount() {
        return mBuildItems.size();
    }

    @Override
    public Fragment getItem(int position) {
        StrengthInfoItem.Type itemType = mBuildItems.get(position).getType();
        if (itemType == StrengthInfoItem.Type.TEXT) {
            StrengthDetailsTextCardFragment fragment =
                    StrengthDetailsTextCardFragment.newInstance(position, mBuildItems.size(),
                            mBuildItems.get(position));
            fragment.setListener(mListener);
            return fragment;
        } else if (itemType == StrengthInfoItem.Type.VIDEO) {
            return StrengthDetailsVideoCardFragment.newInstance(position, mBuildItems.size(), mBuildItems.get(position));
        } else {
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Character " + (position + 1);
    }

    public void setBuildItems(List<StrengthInfoItem> buildItems) {
        mBuildItems = buildItems;
        notifyDataSetChanged();
    }
}
