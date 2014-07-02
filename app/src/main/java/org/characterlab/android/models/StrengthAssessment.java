package org.characterlab.android.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;

@ParseClassName("Assessments")
public class StrengthAssessment extends ParseObject implements Serializable {
    private static final String GROUP_ID_KEY = "Group_id";
    private static final String SCORE_KEY = "Score";
    private static final String STRENGTH_KEY = "Strength";
    private static final String STUDENT_KEY = "Student";

    public StrengthAssessment() {
        super();
    }

    public StrengthAssessment(Student student, Strength strength, int groupId, int score) {
        super();
        setStudent(student);
        setStrength(strength);
        setGroupId(groupId);
        setScore(score);
    }

    public int getGroupId() {
        return getInt(GROUP_ID_KEY);
    }

    public void setGroupId(int groupId) {
        put(GROUP_ID_KEY, groupId);
    }

    public int getScore() {
        return getInt(SCORE_KEY);
    }

    public void setScore(int score) {
        put(SCORE_KEY, score);
    }

    public Strength getStrength() {
        Strength strength = null;
        try {
            strength = (Strength)getParseObject(STRENGTH_KEY);
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
        return strength;
    }

    public void setStrength(Strength strength) {
        put(STRENGTH_KEY, strength);
    }

    public Student getStudent() {
        Student student = null;
        try {
            student = (Student)getParseObject(STUDENT_KEY);
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
        return student;
    }

    public void setStudent(Student student) {
        put(STUDENT_KEY, student);
    }
}
