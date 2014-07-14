package org.characterlab.android.models;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mandar.b on 7/8/2014.
 */
public class StudentDetailViewModel {

    private List<StrengthAssessment> sortedLatestAssessments;
    private Date lastAssessmentDate;
    private Map<Strength, Integer> latestAssessments;
    private Map<Strength, Integer> avgAssessmentValues;
    private List<StrengthAssessment> assessmentsDatewiseList;

    public StudentDetailViewModel() {
        latestAssessments = new HashMap<Strength, Integer>();
        avgAssessmentValues = new HashMap<Strength, Integer>();
    }

    public Map<Strength, Integer> getLatestAssessments() {
        return latestAssessments;
    }

    public void setLatestAssessments(Map<Strength, Integer> latestAssessments) {
        this.latestAssessments = latestAssessments;
    }

    public Map<Strength, Integer> getAvgAssessmentValues() {
        return avgAssessmentValues;
    }

    public void setAvgAssessmentValues(Map<Strength, Integer> avgAssessmentValues) {
        this.avgAssessmentValues = avgAssessmentValues;
    }

    public Date getLastAssessmentDate() {
        return lastAssessmentDate;
    }

    public void setLastAssessmentDate(Date lastAssessmentDate) {
        this.lastAssessmentDate = lastAssessmentDate;
    }

    public List<StrengthAssessment> getSortedLatestAssessments() {
        return sortedLatestAssessments;
    }

    public void setSortedLatestAssessments(List<StrengthAssessment> sortedLatestAssessments) {
        this.sortedLatestAssessments = sortedLatestAssessments;
    }

    public List<StrengthAssessment> getAssessmentsDatewiseList() {
        return assessmentsDatewiseList;
    }

    public void setAssessmentsDatewiseList(List<StrengthAssessment> assessmentsDatewiseList) {
        this.assessmentsDatewiseList = assessmentsDatewiseList;
    }


    // helpers
    public void putLatestAssessment(Strength strength, int assessedValue) {
        latestAssessments.put(strength, assessedValue);
    }

    public int getLatestAssessmentValue(Strength strength) {
        return latestAssessments.get(strength);
    }

    public void putAvgAssessment(Strength strength, int avgAssessedValue) {
        avgAssessmentValues.put(strength, avgAssessedValue);
    }

    public int getAvgAssessmentValue(Strength strength) {
        return avgAssessmentValues.get(strength);
    }

}
