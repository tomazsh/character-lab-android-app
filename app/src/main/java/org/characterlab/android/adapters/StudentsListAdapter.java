package org.characterlab.android.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;

import org.characterlab.android.R;
import org.characterlab.android.models.Student;

import java.util.List;

public class StudentsListAdapter extends ArrayAdapter<Student> {

    private static class ViewHolder {
        ParseImageView pivListStudentImage;
        TextView tvListStudentName;
    }

    public StudentsListAdapter(Context context, List<Student> students) {
        super(context, 0, students);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Student student = getItem(position);

        final ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.student_list_item, parent, false);
            viewHolder.pivListStudentImage = (ParseImageView) convertView.findViewById(R.id.pivListStudentImage);
            viewHolder.tvListStudentName = (TextView) convertView.findViewById(R.id.tvListStudentName);
            convertView.setTag(viewHolder);
        }

        viewHolder.tvListStudentName.setText(student.getName());
        ParseFile profileImageFile = student.getProfileImage();
        if (profileImageFile != null) {
            viewHolder.pivListStudentImage.setParseFile(profileImageFile);
            viewHolder.pivListStudentImage.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    viewHolder.pivListStudentImage.setVisibility(View.VISIBLE);
                    Log.d("debug", "Data len: " + data.length);
                }
            });
        }

        return convertView;
    }
}
