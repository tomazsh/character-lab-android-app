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
import org.characterlab.android.adapters.AssessmentCardsAdapter;
import org.characterlab.android.adapters.SmartFragmentStatePagerAdapter;

/**
 * Created by mandar.b on 7/9/2014.
 */
public class AssessmentCardsFragment extends Fragment {
    private FragmentActivity myContext;
    private SmartFragmentStatePagerAdapter adapterViewPager;
    private static int selectedItemIndex = -1;

    public static AssessmentCardsFragment newInstance(String studentId) {
        AssessmentCardsFragment fragment = new AssessmentCardsFragment();
        Bundle args = new Bundle();
        args.putString("studentId", studentId);
        fragment.setArguments(args);
        return fragment;
    }

    public AssessmentCardsFragment() {
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
        adapterViewPager = new AssessmentCardsAdapter(fragManager);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assessment_cards, container, false);
        ViewPager vpAssessmentCardsPager = (ViewPager) view.findViewById(R.id.vpAssessmentCardsPager);
        vpAssessmentCardsPager.setAdapter(adapterViewPager);
        if (selectedItemIndex >= 0) {
            vpAssessmentCardsPager.setCurrentItem(selectedItemIndex);
        }
        vpAssessmentCardsPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
}
