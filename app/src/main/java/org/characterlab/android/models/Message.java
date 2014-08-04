package org.characterlab.android.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.Serializable;

@ParseClassName("Messages")
public class Message extends ParseObject implements Serializable {
    private static final String AUTHOR_KEY = "Author";
    private static final String STUDENT_ID_KEY = "studentId";

    public Message() {
        super();
    }

    public ParseUser getAuthor() {
        ParseUser user = null;
        try {
            user = (ParseUser) getParseObject(AUTHOR_KEY);
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
        return user;
    }

    public void setAuthor(ParseUser author) {
        put(AUTHOR_KEY, author);
    }

    public String getStudentId() {
        return getString(STUDENT_ID_KEY);
    }

    public void setStudentId(String studentId) {
        put(STUDENT_ID_KEY, studentId);
    }

}

