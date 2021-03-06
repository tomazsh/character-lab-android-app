package org.characterlab.android.helpers;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.characterlab.android.models.NewAssessmentViewModel;
import org.characterlab.android.models.Strength;
import org.characterlab.android.models.StrengthAssessment;
import org.characterlab.android.models.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ParseClient {

    public static <T extends ParseObject> void getAll(Class<T> classObj, FindCallback<T> callback) {
        ParseQuery<T> query = ParseQuery.getQuery(classObj);
        query.findInBackground(callback);
    }

    public static <T extends ParseObject> List<T> getAllSync(Class<T> classObj) {
        try {
            ParseQuery<T> query = ParseQuery.getQuery(classObj);
            return query.find();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static StrengthAssessment getLatestStrengthScoreForStudentSync(Student student, Strength strength) {
        try {
            ParseQuery<StrengthAssessment> assessQuery = ParseQuery.getQuery(StrengthAssessment.class);
            assessQuery.whereEqualTo("Student", student);
            assessQuery.whereEqualTo("Strength", strength.toString());
            assessQuery.whereEqualTo("Group_id", student.getMaxGroupId());
            assessQuery.include("Student");
            return assessQuery.getFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<StrengthAssessment> getAssessmentsInGroupIdRange(Strength strength, List<Integer> groupIds) {
        try {
              ParseQuery<StrengthAssessment> assessQuery = ParseQuery.getQuery(StrengthAssessment.class);
              assessQuery.whereEqualTo("Strength", strength.toString());
              assessQuery.whereContainedIn("Group_id", groupIds);
              assessQuery.include("Student");
              return assessQuery.find();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
                        .addDescendingOrder("createdAt")
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

                student.setMaxGroupId(groupId);
                student.saveInBackground();
            }
        });
    }

    //    public static void getGoodStudentsForStrength(final Strength strength, FindCallback<Student> callback) {
//        ParseQuery<Student> query = ParseQuery.getQuery(Student.class);
//        query.findInBackground(new FindCallback<Student>() {
//            @Override
//            public void done(List<Student> students, ParseException e) {
//                if (e == null) {
//                    final ArrayList<StrengthAssessment> assessments = new ArrayList<StrengthAssessment>();
//                    for (Student student : students) {
//                        ParseQuery<StrengthAssessment> assessQuery = ParseQuery.getQuery(StrengthAssessment.class);
//                        assessQuery.whereEqualTo("Student", student);
//                        assessQuery.whereEqualTo("Strength", strength.toString());
//                        assessQuery.addDescendingOrder("Group_id");
//                        assessQuery.getFirstInBackground(new GetCallback<StrengthAssessment>() {
//                            @Override
//                            public void done(StrengthAssessment strengthAssessment, ParseException e) {
//                                synchronized (assessments) {
//                                    assessments.add(strengthAssessment);
//                                    assessments.notifyAll();
//                                }
//                            }
//                        });
//                    }
//
//                    synchronized (assessments) {
//                        while (assessments.size() < students.size()) {
//                            try {
//                                assessments.wait();
//                            } catch (Exception ex) {}
//                        }
//
//                        Collections.sort(assessments, new Comparator<StrengthAssessment>() {
//                            @Override
//                            public int compare(StrengthAssessment lhs, StrengthAssessment rhs) {
//                                return lhs.getScore() - rhs.getScore();
//                            }
//                        });
//                    }
//                }
//            }
//        });
//    }


}
