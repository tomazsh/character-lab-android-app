package org.characterlab.android.events;

import org.characterlab.android.activities.NewAssessmentActivity;

/**
 * Created by mandar.b on 7/29/2014.
 */
public class NewAssessmentAddedEvent {

    private String studentId;

    public NewAssessmentAddedEvent(String studentObjectId) {
        studentId = studentObjectId;
    }

    public String getStudentId() {
        return studentId;
    }
}
