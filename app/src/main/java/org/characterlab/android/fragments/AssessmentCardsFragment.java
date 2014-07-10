package org.characterlab.android.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseImageView;

import org.characterlab.android.CharacterLabApplication;
import org.characterlab.android.R;
import org.characterlab.android.models.Strength;
import org.characterlab.android.models.Student;

/**
 * Created by mandar.b on 7/9/2014.
 */
public class AssessmentCardsFragment extends Fragment {
    AssessmentCardsFragmentListener mListener;

    public interface AssessmentCardsFragmentListener {
        FragmentPagerAdapter getAdapterViewPager();
    }

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
        super.onAttach(activity);
        try {
            mListener = (AssessmentCardsFragmentListener) activity;
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
        View view = inflater.inflate(R.layout.fragment_assessment_cards, container, false);
        ViewPager vpAssessmentCardsPager = (ViewPager) view.findViewById(R.id.vpAssessmentCardsPager);
        vpAssessmentCardsPager.setAdapter(mListener.getAdapterViewPager());
        return view;
    }
}
