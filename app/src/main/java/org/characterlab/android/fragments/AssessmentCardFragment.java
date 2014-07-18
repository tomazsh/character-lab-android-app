package org.characterlab.android.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.characterlab.android.R;
import org.characterlab.android.models.Strength;
import org.characterlab.android.models.StrengthInfo;

/**
 * Created by mandar.b on 7/9/2014.
 */
public class AssessmentCardFragment  extends Fragment {
    private Strength strength;
    private StrengthInfo strengthInfo;
    private AssessmentCardFragmentListener mListener;

    public AssessmentCardFragment() {
    }

    public interface AssessmentCardFragmentListener {
        void onStrenthScoreSet(Strength strength, int score);
    }

    public static AssessmentCardFragment newInstance(Strength strength) {
        AssessmentCardFragment fragmentAssessmentCard = new AssessmentCardFragment();
        Bundle args = new Bundle();
        args.putString("strength", strength.toString());
        fragmentAssessmentCard.setArguments(args);
        return fragmentAssessmentCard;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String strengthStr = getArguments().getString("strength");
        strength = Strength.valueOf(strengthStr);
        strengthInfo = StrengthInfo.fromStrength(getActivity(), strength);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assessment_card, container, false);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvAssessmentCardTitle);
        tvTitle.setText(strength.getName());
        TextView tvAssessmentCardDescription = (TextView) view.findViewById(R.id.tvAssessmentCardDescription);
        String description = TextUtils.join("\n", strengthInfo.getAssessmentQuestions());
        tvAssessmentCardDescription.setText(description);
        ImageView ivIcon = (ImageView) view.findViewById(R.id.ivAssessmentCardIcon);
        ivIcon.setImageResource(strength.getIconCircleId());

        final TextView tvAssessmentCardScore = (TextView) view.findViewById(R.id.tvAssessmentCardScore);

        SeekBar sbAssessmentCardScore = (SeekBar) view.findViewById(R.id.sbAssessmentCardScore);
        sbAssessmentCardScore.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvAssessmentCardScore.setText(String.valueOf(progress));
                mListener.onStrenthScoreSet(strength, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (AssessmentCardFragmentListener) activity;
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
    }

}

