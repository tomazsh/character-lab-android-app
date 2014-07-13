package org.characterlab.android.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.characterlab.android.R;
import org.characterlab.android.adapters.CharacterCardsAdapter;
import org.characterlab.android.adapters.SmartFragmentStatePagerAdapter;

public class CharacterCardsFragment extends Fragment {
    private FragmentActivity myContext;
    private SmartFragmentStatePagerAdapter adapterViewPager;
    private int selectedItemIndex = 0;
    private final String SELECTED_ITEM_INDEX_KEY = "selectedItemIndex";

    public interface CharacterCardsFragmentListener {
        SmartFragmentStatePagerAdapter getAdapterViewPager();
    }

    public CharacterCardsFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragManager = myContext.getSupportFragmentManager();
        adapterViewPager = new CharacterCardsAdapter(fragManager);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character_cards, container, false);
        ViewPager vpPager = (ViewPager) view.findViewById(R.id.vpPager);
        vpPager.setClipToPadding(false);
        vpPager.setPageMargin(12);
        vpPager.setAdapter(adapterViewPager);
        if (savedInstanceState != null) {
            selectedItemIndex = savedInstanceState.getInt(SELECTED_ITEM_INDEX_KEY);
            vpPager.setCurrentItem(selectedItemIndex);
        }
        vpPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                selectedItemIndex = position;
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_INDEX_KEY, selectedItemIndex);
    }
}
