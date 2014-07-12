package org.characterlab.android.helpers;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.characterlab.android.models.NewAssessmentViewModel;
import org.characterlab.android.models.Strength;
import org.characterlab.android.models.StrengthAssessment;
import org.characterlab.android.models.Student;

import java.util.List;
import java.util.Map;

public class ParseClient {

    public static <T extends ParseObject> void getAll(Class<T> classObj, FindCallback<T> callback) {
        ParseQuery<T> query = ParseQuery.getQuery(classObj);
        query.findInBackground(callback);
    }

    public static void getGoodStudentsForStrength(Strength strength, FindCallback<Student> callback) {
        ParseQuery<Student> query = ParseQuery.getQuery(Student.class);
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

    public static void getLatestAssessmentForStudent(Student student, FindCallback<StrengthAssessment> callback) {
        ParseQuery<StrengthAssessment> query =
                ParseQuery.getQuery(StrengthAssessment.class)
                        .whereEqualTo("Student", student)
                        .addDescendingOrder("Group_id")
                        .setLimit(1);
        query.findInBackground(callback);
    }

    public static void getAllAssessmentsForStudent(Student student, FindCallback<StrengthAssessment> callback) {
        ParseQuery<StrengthAssessment> query =
                ParseQuery.getQuery(StrengthAssessment.class)
                        .whereEqualTo("Student", student)
                        .setLimit(1000);
        query.findInBackground(callback);
    }

    public static void getStrengthScoreHistoryForStudent(Student student, Strength strength, FindCallback<StrengthAssessment> callback) {
        ParseQuery<StrengthAssessment> query =
                ParseQuery.getQuery(StrengthAssessment.class)
                        .whereEqualTo("Student", student)
                        .whereEqualTo("Strength", strength.toString())
                        .addAscendingOrder("createdAt")
                        .setLimit(1000);
        query.findInBackground(callback);
    }

    public static void saveStudentAssessment(final NewAssessmentViewModel assessmentsModel, final Student student) {
        getLatestAssessmentForStudent(student, new FindCallback<StrengthAssessment>() {
            @Override
            public void done(List<StrengthAssessment> strengthAssessments, ParseException e) {
                int groupId = 1;
                if (strengthAssessments != null && !strengthAssessments.isEmpty()) {
                    groupId = strengthAssessments.get(0).getGroupId();
                    groupId++;
                }

                Map<Strength, Integer> scoreMap = assessmentsModel.getStrengthScores();
                for (Strength strength : scoreMap.keySet()) {
                    StrengthAssessment assessment = new StrengthAssessment();
                    assessment.setGroupId(groupId);
                    assessment.setScore(scoreMap.get(strength));
                    assessment.setStudent(student);
                    assessment.setStrength(strength);
                    assessment.saveInBackground();
                }
            }
        });
    }

}
