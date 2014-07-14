package org.characterlab.android.helpers;

import org.characterlab.android.models.Strength;
import org.characterlab.android.models.StrengthAssessment;
import org.characterlab.android.models.StudentDetailViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

        List<StrengthAssessment> sortedAssessments = new ArrayList<StrengthAssessment>();
        for (Strength strength : assessmentsByStrength.keySet()) {
            float avg = 0.0f;
            int maxGroupId = -1;
//            int latestAssessment = 0;
            StrengthAssessment latestAssessment = null;

            if (assessmentsByStrength.get(strength) != null &&
                !assessmentsByStrength.get(strength).isEmpty()) {

                for (StrengthAssessment assessment : assessmentsByStrength.get(strength)) {
                    avg += assessment.getScore();
                    if (assessment.getGroupId() > maxGroupId) {
                        maxGroupId = assessment.getGroupId();
                        latestAssessment = assessment;
                    }
                }
                avg = avg / assessmentsByStrength.get(strength).size();
            }
            viewModel.putAvgAssessment(strength, (int)avg);
            viewModel.putLatestAssessment(strength, latestAssessment.getScore());
            viewModel.setLastAssessmentDate(latestAssessment.getCreatedAt());
            sortedAssessments.add(latestAssessment);
        }

        List<StrengthAssessment> assessmentsDatewiseList = assessmentsByStrength.get(Strength.GRATITUDE);
        Collections.sort(assessmentsDatewiseList, new StrengthAssessmentDatewiseComparator());
        Collections.sort(sortedAssessments, new StrengthAssessmentScorewiseComparator());
        viewModel.setSortedLatestAssessments(sortedAssessments);
        viewModel.setAssessmentsDatewiseList(assessmentsDatewiseList);

        return viewModel;
    }

    static class StrengthAssessmentScorewiseComparator implements Comparator<StrengthAssessment> {
        @Override
        public int compare(StrengthAssessment lhs, StrengthAssessment rhs) {
            return rhs.getScore() - lhs.getScore();
        }
    }

    static class StrengthAssessmentDatewiseComparator implements Comparator<StrengthAssessment> {
        @Override
        public int compare(StrengthAssessment lhs, StrengthAssessment rhs) {
            return (int) (rhs.getCreatedAt().getTime() - lhs.getCreatedAt().getTime());
        }
    }


}
