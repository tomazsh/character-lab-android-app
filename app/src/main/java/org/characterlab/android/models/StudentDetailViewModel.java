package org.characterlab.android.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mandar.b on 7/8/2014.
 */
public class StudentDetailViewModel {

    private Strength strongest;
    private Strength weakest;
    private Strength mostImproved;
    private Map<Strength, Integer> latestAssessments;
    private Map<Strength, Integer> avgAssessmentValues;

    public StudentDetailViewModel() {
        latestAssessments = new HashMap<Strength, Integer>();
        avgAssessmentValues = new HashMap<Strength, Integer>();
    }

    public Strength getStrongest() {
        return strongest;

    }

    public void setStrongest(Strength strongest) {
        this.strongest = strongest;
    }

    public Strength getWeakest() {
        return weakest;
    }

    public void setWeakest(Strength weakest) {
        this.weakest = weakest;
    }

    public Strength getMostImproved() {
        return mostImproved;
    }

    public void setMostImproved(Strength mostImproved) {
        this.mostImproved = mostImproved;
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
