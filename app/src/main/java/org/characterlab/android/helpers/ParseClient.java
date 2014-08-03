package org.characterlab.android.helpers;

import android.graphics.Bitmap;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.characterlab.android.models.Message;
import org.characterlab.android.models.NewAssessmentViewModel;
import org.characterlab.android.models.Strength;
import org.characterlab.android.models.StrengthAssessment;
import org.characterlab.android.models.Student;

import java.io.ByteArrayOutputStream;
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

    public static void createStudent(String name, Bitmap profileImage) { //}, SaveCallback callback) {
        try {
            ParseFile file = null;
            if (profileImage != null) {
                ByteArrayOutputStream opStream = new ByteArrayOutputStream();
                profileImage.compress(Bitmap.CompressFormat.PNG, 50, opStream);
                byte[] pngBytes = opStream.toByteArray();
                file = new ParseFile(System.currentTimeMillis() + ".png", pngBytes);
                file.saveInBackground();
            }

            Student student = new Student();
            student.setMaxGroupId(0);
            student.setName(name);

            if (file != null) {
                student.setProfileImage(file);
            }

//            student.saveInBackground(callback);
            student.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Message getLatestMessage(ParseUser loggedinUser) {
        try {
            ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
//            query.whereNotEqualTo("Author", loggedinUser);
            query.addDescendingOrder("createdAt");
            query.include("Author");
            return query.getFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Student getStudent(String objectId) {
        try {
            ParseQuery<Student> query = ParseQuery.getQuery(Student.class);
            query.whereEqualTo("objectId", objectId);
            return query.getFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveNotificationMessage(ParseUser loggedinUser, String studentId) {
        Message message = new Message();
        message.setAuthor(loggedinUser);
        message.setStudentId(studentId);
        message.saveInBackground();
    }

}
