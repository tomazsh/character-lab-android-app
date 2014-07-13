package org.characterlab.android.helpers;

import org.characterlab.android.models.Strength;
import org.characterlab.android.models.StrengthAssessment;
import org.characterlab.android.models.StudentDetailViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mandar.b on 7/8/2014.
 */
public class Utils {

    public static StudentDetailViewModel generateStudentDetailViewModel(List<StrengthAssessment> assessmentList) {

        StudentDetailViewModel viewModel = new StudentDetailViewModel();
        Map<Strength, List<StrengthAssessment>> assessmentsByStrength = new HashMap<Strength, List<StrengthAssessment>>();

        for (StrengthAssessment assessment : assessmentList) {
            Strength strength = assessment.getStrength();

            if (!assessmentsByStrength.containsKey(strength)) {
                assessmentsByStrength.put(strength, new ArrayList<StrengthAssessment>());
            }
            assessmentsByStrength.get(strength).add(assessment);
        }

        for (Strength strength : assessmentsByStrength.keySet()) {
            float avg = 0.0f;
            int maxGroupId = -1;
            int latestAssessment = 0;

            if (assessmentsByStrength.get(strength) != null &&
                !assessmentsByStrength.get(strength).isEmpty()) {

                for (StrengthAssessment assessment : assessmentsByStrength.get(strength)) {
                    avg += assessment.getScore();
                    if (assessment.getGroupId() > maxGroupId) {
                        maxGroupId = assessment.getGroupId();
                        latestAssessment = assessment.getScore();
                        viewModel.setLastAssessmentDate(assessment.getCreatedAt());
                    }
                }
                avg = avg / assessmentsByStrength.get(strength).size();
            }
            viewModel.putAvgAssessment(strength, (int)avg);
            viewModel.putLatestAssessment(strength, latestAssessment);
        }

        int strongest = Integer.MIN_VALUE;
        int weakest = Integer.MAX_VALUE;
        int mostImproved = Integer.MIN_VALUE;

        for (Strength strength : Strength.values()) {
            int avg = viewModel.getAvgAssessmentValues().containsKey(strength) ? viewModel.getAvgAssessmentValue(strength) : -1;
            int latest = viewModel.getLatestAssessments().containsKey(strength) ? viewModel.getLatestAssessmentValue(strength) : -1;

            if (latest > -1) {
                if (latest >= strongest) {
                    strongest = latest;
                    viewModel.setStrongest(strength);
                }

                if (latest <= weakest) {
                    weakest = latest;
                    viewModel.setWeakest(strength);
                }
            }

            if (avg > -1 && latest > -1) {
                if ((latest - avg) >= mostImproved) {
                    mostImproved = (latest - avg);
                    viewModel.setMostImproved(strength);
                }
            }
        }

        return viewModel;
    }

}
