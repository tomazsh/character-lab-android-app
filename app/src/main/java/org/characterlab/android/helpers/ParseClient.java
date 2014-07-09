package org.characterlab.android.helpers;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.characterlab.android.models.StrengthAssessment;
import org.characterlab.android.models.Student;

import java.util.List;

public class ParseClient {

    public static <T extends ParseObject> void getAll(Class<T> classObj, FindCallback<T> callback) {
        ParseQuery<T> query = ParseQuery.getQuery(classObj);
        query.findInBackground(callback);
    }

    public static void getLatestAssessmentsForStudent(Student student, FindCallback<StrengthAssessment> callback) {
        ParseQuery<StrengthAssessment> query =
                ParseQuery.getQuery(StrengthAssessment.class)
                .whereEqualTo("Student", student)
                .addDescendingOrder("Group_id")
                .setLimit(7);
        query.findInBackground(callback);
    }

}
