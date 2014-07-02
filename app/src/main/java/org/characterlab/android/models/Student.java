package org.characterlab.android.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;

@ParseClassName("Student")
public class Student extends ParseObject implements Serializable {
    public Student() {
        super("Student");
    }
}
