package org.characterlab.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.characterlab.android.R;
import org.characterlab.android.models.Student;

import java.util.List;

/**
 * Created by mandar.b on 7/2/2014.
 */
public class StudentsListAdapter extends ArrayAdapter<Student> {

    ImageView ivListStudentImage;
    TextView tvListStudentName;

    public StudentsListAdapter(Context context, List<Student> students) {
        super(context, 0, students);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Student student = getItem(position);

        View v;
        if (convertView != null) {
            v = convertView;
        } else {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.student_list_item, parent, false);
        }

        ivListStudentImage = (ImageView) v.findViewById(R.id.ivListStudentImage);
        tvListStudentName = (TextView) v.findViewById(R.id.tvListStudentName);

        // Todo: Change after model is checked in.
        tvListStudentName.setText((String)student.get("Name"));
        return v;
    }
}
