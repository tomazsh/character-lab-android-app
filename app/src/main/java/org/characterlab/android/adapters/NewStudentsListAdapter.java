package org.characterlab.android.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import org.characterlab.android.R;
import org.characterlab.android.models.NewStudentViewModel;
import org.characterlab.android.views.RoundedParseImageView;

import java.util.List;

/**
 * Created by mandar.b on 7/30/2014.
 */
public class NewStudentsListAdapter extends ArrayAdapter<NewStudentViewModel> {

    public interface NewStudentsListAdapterListener {
        public void onCameraClicked(int position);
    }

    private static class ViewHolder {
        RoundedParseImageView rpivNewStudentProfileImage;
        RoundedParseImageView rpivNewStudentCamera;
        EditText etNewStudentName;
        int position;
    }

    NewStudentsListAdapterListener mListener;

    public NewStudentsListAdapter(Context context, List<NewStudentViewModel> newStudents, NewStudentsListAdapterListener listener) {
        super(context, 0, newStudents);
        mListener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final NewStudentViewModel newStudent = getItem(position);

        final ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.new_student_list_item, parent, false);
            viewHolder.rpivNewStudentProfileImage = (RoundedParseImageView) convertView.findViewById(R.id.rpivNewStudentProfileImage);
            viewHolder.rpivNewStudentCamera = (RoundedParseImageView) convertView.findViewById(R.id.rpivNewStudentCamera);
            viewHolder.etNewStudentName = (EditText) convertView.findViewById(R.id.etNewStudentName);

            viewHolder.etNewStudentName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    newStudent.setStudentName(s.toString());
                }
            });

            viewHolder.rpivNewStudentCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onCameraClicked(position);
                }
            });

            convertView.setTag(viewHolder);
        }

        viewHolder.position = position;
        viewHolder.etNewStudentName.setText(newStudent.getStudentName());
        if (newStudent.getStudentImage() != null) {
            viewHolder.rpivNewStudentProfileImage.setImageBitmap(newStudent.getStudentImage());
        }

        return convertView;
    }

}
