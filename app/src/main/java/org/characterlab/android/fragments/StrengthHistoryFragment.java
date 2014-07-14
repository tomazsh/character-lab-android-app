package org.characterlab.android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;

import org.characterlab.android.R;
import org.characterlab.android.helpers.ParseClient;
import org.characterlab.android.models.Strength;
import org.characterlab.android.models.StrengthAssessment;
import org.characterlab.android.models.Student;
import org.characterlab.android.views.LineView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by mandar.b on 7/10/2014.
 */
public class StrengthHistoryFragment extends Fragment {
    private Student mStudent;
    private Strength mStrength;

    ImageView ivHistoryStrengthLogo;
    TextView tvHistoryStrengthTitle;
    TextView tvHistoryStrengthDescr;
    TextView tvHistoryAvgScore;
    LineView lineView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_strength_history, container, false);
        ivHistoryStrengthLogo = (ImageView) v.findViewById(R.id.ivHistoryStrengthLogo);
        tvHistoryStrengthTitle = (TextView) v.findViewById(R.id.tvHistoryStrengthTitle);
        tvHistoryAvgScore = (TextView) v.findViewById(R.id.tvHistoryAvgScore);
        lineView = (LineView) v.findViewById(R.id.line_view);

        ParseClient.getStrengthScoreHistoryForStudent(mStudent, mStrength,
                new FindCallback<StrengthAssessment>() {
                    public void done(List<StrengthAssessment> list, ParseException e) {
                        if (e == null) {
                            updateView(list);
                        } else {
                            e.printStackTrace();
                        }
                    }
                }
        );

        return v;
    }

    private void updateView(List<StrengthAssessment> assessments) {
        ivHistoryStrengthLogo.setImageResource(mStrength.getIconCircleId());
        tvHistoryStrengthTitle.setText(mStrength.getName());
        tvHistoryStrengthDescr.setText(mStrength.getDescriptionId());
        float avgScore = 0.0f;

        if (assessments != null && !assessments.isEmpty()) {
            ArrayList<String> labels = new ArrayList<String>();
            ArrayList<Integer> values = new ArrayList<Integer>();

            for (StrengthAssessment assessment : assessments) {
                Date createdAt = assessment.getCreatedAt();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d", Locale.US);
                String label = dateFormat.format(createdAt);
                labels.add(label);
                values.add(assessment.getScore());

                avgScore += assessment.getScore();
            }
            avgScore /= assessments.size();
            lineView.setBottomTextList(labels);
            lineView.setDrawDotLine(true);
            lineView.setShowPopup(LineView.SHOW_POPUPS_NONE);

            ArrayList<ArrayList<Integer>> dataLists = new ArrayList<ArrayList<Integer>>();
            dataLists.add(values);
            lineView.setDataList(dataLists);
        }
        tvHistoryAvgScore.setText(String.format("%.2f", avgScore));
    }

    public void setStudent(Student student) {
        this.mStudent = student;
    }

    public void setStrength(Strength strength) {
        this.mStrength = strength;
    }

}
