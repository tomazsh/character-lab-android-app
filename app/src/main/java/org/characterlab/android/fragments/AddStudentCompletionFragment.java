package org.characterlab.android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.characterlab.android.R;

/**
 * Created by mandar.b on 7/27/2014.
 */
public class AddStudentCompletionFragment extends Fragment {

    private String studentName;

    public static AddStudentCompletionFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("studentName", name);

        AddStudentCompletionFragment completionFragment = new AddStudentCompletionFragment();
        completionFragment.setArguments(args);
        return completionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = this.getArguments();
        studentName = args.getString("studentName");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_student_completion, container, false);

        TextView tvAddStudentCompletionName = (TextView) v.findViewById(R.id.tvAddStudentCompletionName);
        tvAddStudentCompletionName.setText(studentName);
        return v;
    }

}
