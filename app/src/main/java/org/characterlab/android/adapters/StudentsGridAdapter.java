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

public class StudentsGridAdapter extends ArrayAdapter<Student> {
    private static class ViewHolder {
        ParseImageView imageView;
        TextView nameTextView;
    }

    public StudentsGridAdapter(Context context, List<Student> students) {
        super(context, 0, students);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Student student = getItem(position);

        final ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder)convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.student_grid_item, parent, false);
            viewHolder.imageView = (ParseImageView)convertView.findViewById(R.id.image_view);
            viewHolder.nameTextView = (TextView)convertView.findViewById(R.id.name_text_view);
            convertView.setTag(viewHolder);
        }

        viewHolder.nameTextView.setText(student.getName());
        ParseFile profileImageFile = student.getProfileImage();
        if (profileImageFile != null) {
            viewHolder.imageView.setParseFile(profileImageFile);
            viewHolder.imageView.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    viewHolder.imageView.setVisibility(View.VISIBLE);
                }
            });
        }

        return convertView;
    }
}
