package org.characterlab.android.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mandar.b on 7/9/2014.
 */
public class NewAssessmentViewModel implements Serializable {

    Map<Strength, Integer> strengthScores = new HashMap<Strength, Integer>();

    public NewAssessmentViewModel() {
        // init viewModel with 0 scores;
        for (Strength strength : Strength.values()) {
            strengthScores.put(strength, 4);
        }
    }

    public Map<Strength, Integer> getStrengthScores() {
        return strengthScores;
    }

}
