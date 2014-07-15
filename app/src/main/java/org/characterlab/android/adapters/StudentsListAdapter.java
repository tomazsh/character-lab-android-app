package org.characterlab.android.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.makeramen.RoundedImageView;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import org.characterlab.android.R;
import org.characterlab.android.models.Student;

import java.util.List;

public class StudentsListAdapter extends ArrayAdapter<Student> {

    private static class ViewHolder {
        RoundedImageView pivListStudentImage;
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
            viewHolder.pivListStudentImage = (RoundedImageView) convertView.findViewById(R.id.pivListStudentImage);
            viewHolder.tvListStudentName = (TextView) convertView.findViewById(R.id.tvListStudentName);
            convertView.setTag(viewHolder);
        }

        viewHolder.tvListStudentName.setText(student.getName());
        ParseFile profileImageFile = student.getProfileImage();
        if (profileImageFile != null) {
            profileImageFile.getDataInBackground(new GetDataCallback() {
                public void done(byte[] data, ParseException e) {
                    if (e == null) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                        viewHolder.pivListStudentImage.setImageBitmap(bmp);
                        viewHolder.pivListStudentImage.setVisibility(View.VISIBLE);
                        Log.d("debug", "Data len: " + data.length);
                    } else {
                        Log.d("debug", "There was a problem downloading the data.");
                    }
                }
            });
        }

        return convertView;
    }
}
