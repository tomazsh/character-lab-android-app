package org.characterlab.android.models;

import android.graphics.Bitmap;

/**
 * Created by mandar.b on 7/30/2014.
 */
public class NewStudentViewModel {

    private Bitmap studentImage;
    private String studentName;

    public Bitmap getStudentImage() {
        return studentImage;
    }

    public void setStudentImage(Bitmap studentImage) {
        this.studentImage = studentImage;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
