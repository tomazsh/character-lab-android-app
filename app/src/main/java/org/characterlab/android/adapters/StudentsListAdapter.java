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
import org.characterlab.android.views.RoundedParseImageView;

import java.util.List;

public class StudentsListAdapter extends ArrayAdapter<Student> {

    private static class ViewHolder {
        RoundedParseImageView rpivListStudentImage;
        TextView tvListStudentName;
        int position;
    }

    public StudentsListAdapter(Context context, List<Student> students) {
        super(context, 0, students);
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        Student student = getItem(position);
//
//        final ViewHolder viewHolder;
//        if (convertView != null) {
//            viewHolder = (ViewHolder) convertView.getTag();
//        } else {
//            viewHolder = new ViewHolder();
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            convertView = inflater.inflate(R.layout.student_list_item, parent, false);
//            viewHolder.rpivListStudentImage = (RoundedParseImageView) convertView.findViewById(R.id.rpivListStudentImage);
//            viewHolder.tvListStudentName = (TextView) convertView.findViewById(R.id.tvListStudentName);
//            convertView.setTag(viewHolder);
//        }
//
//        String currentItemNameInConvertView = viewHolder.tvListStudentName.getText().toString();
//        if (!currentItemNameInConvertView.equalsIgnoreCase(student.getName())) {
//            viewHolder.tvListStudentName.setText(student.getName());
//            Log.d("test", "~~~~~ Name: " + student.getName());
//            viewHolder.rpivListStudentImage.setImageResource(R.drawable.ic_placeholder);
//            viewHolder.rpivListStudentImage.loadParseFileImageInBackground(student.getProfileImage());
//        }
//
//        return convertView;
//    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Student student = getItem(position);

        final ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.student_list_item, parent, false);
            viewHolder.rpivListStudentImage = (RoundedParseImageView) convertView.findViewById(R.id.rpivListStudentImage);
            viewHolder.tvListStudentName = (TextView) convertView.findViewById(R.id.tvListStudentName);
            convertView.setTag(viewHolder);
        }

        viewHolder.position = position;
        viewHolder.tvListStudentName.setText(student.getName());
        viewHolder.rpivListStudentImage.setImageResource(R.drawable.ic_placeholder);
        viewHolder.rpivListStudentImage.loadParseFileImageInBackground(student.getProfileImage(), new GetDataCallback() {
            public void done(byte[] data, ParseException e) {
                if (e == null) {
                    if (viewHolder.position == position) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                        viewHolder.rpivListStudentImage.setImageBitmap(bmp);
                        viewHolder.rpivListStudentImage.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        return convertView;
    }


}
