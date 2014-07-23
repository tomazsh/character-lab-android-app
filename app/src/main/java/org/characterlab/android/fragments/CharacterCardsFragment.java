package org.characterlab.android.fragments;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.characterlab.android.R;
import org.characterlab.android.adapters.CharacterCardsAdapter;
import org.characterlab.android.adapters.SmartFragmentStatePagerAdapter;
import org.characterlab.android.views.PageIndicator;

public class CharacterCardsFragment extends Fragment {
    private FragmentActivity myContext;
    private SmartFragmentStatePagerAdapter adapterViewPager;
    private PageIndicator pageIndicator;
    private int selectedItemIndex = 0;
    private final String SELECTED_ITEM_INDEX_KEY = "selectedItemIndex";
    private final int NUM_DOTS = 7;

    public interface CharacterCardsFragmentListener {
        SmartFragmentStatePagerAdapter getAdapterViewPager();
    }

    public CharacterCardsFragment() {
    }

    public int getSelectedChardIndex() {
        return selectedItemIndex;
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
        vpPager.setAdapter(adapterViewPager);
        vpPager.setPageTransformer(false, new ShrinkPageTransformer());
        pageIndicator = (PageIndicator) view.findViewById(R.id.pageIndicator);
        if (savedInstanceState != null) {
            selectedItemIndex = savedInstanceState.getInt(SELECTED_ITEM_INDEX_KEY);
            vpPager.setCurrentItem(selectedItemIndex);
        }
        vpPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                selectedItemIndex = position;
                pageIndicator.setActiveDot(position);
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

    private class ShrinkPageTransformer implements ViewPager.PageTransformer {
        private float MIN_SCALE = 0.85f;
        private float MIN_COLOR_SCALE = 0.5f;
        private float X_TRANSLATION = 50f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) {        // [-inf, -1]
                view.setAlpha(1);
                view.setTranslationX(0);
            } else if (position <= 0) { // (-1,0]
                view.setTranslationX(X_TRANSLATION);
                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setAlpha(MIN_COLOR_SCALE + (1 - MIN_COLOR_SCALE) * (1 - Math.abs(position)));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            } else if (position < 1) { // (0,1]
                view.setAlpha(MIN_COLOR_SCALE + (1 - MIN_COLOR_SCALE) * (1 - Math.abs(position)));
                view.setTranslationX(X_TRANSLATION);
                float scale = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scale);
                view.setScaleY(scale);
            } else if (position >= 1) {
                view.setAlpha(MIN_COLOR_SCALE + (1 - MIN_COLOR_SCALE) * (1 - Math.abs(position)));
                view.setTranslationX(X_TRANSLATION);
                view.setScaleX(MIN_SCALE);
                view.setScaleY(MIN_SCALE);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        setupDots();
    }

    private void setupDots() {
        pageIndicator.setTotalNoOfDots(NUM_DOTS);
        pageIndicator.setActiveDot(selectedItemIndex);
        pageIndicator.setDotSpacing(20);
    }
}
