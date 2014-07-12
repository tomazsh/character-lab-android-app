package org.characterlab.android.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.characterlab.android.R;

public class CharacterCardsFragment extends Fragment {
    CharacterCardsFragmentListener mListener;

    public interface CharacterCardsFragmentListener {
        FragmentPagerAdapter getAdapterViewPager();
    }

    public CharacterCardsFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (CharacterCardsFragmentListener)activity;
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character_cards, container, false);
        ViewPager vpPager = (ViewPager) view.findViewById(R.id.vpPager);
        vpPager.setClipToPadding(false);
        vpPager.setPageMargin(12);
        vpPager.setAdapter(mListener.getAdapterViewPager());

        return view;
    }
}
