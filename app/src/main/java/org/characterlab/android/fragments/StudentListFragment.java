package org.characterlab.android.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import org.characterlab.android.R;
import org.characterlab.android.adapters.StudentsListAdapter;
import org.characterlab.android.helpers.ParseClient;
import org.characterlab.android.models.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentListFragment extends Fragment {
    StudentListFragmentListener mListener;

    ListView lvStudentsList;
    List<Student> studentsList;
    StudentsListAdapter studentsListAdapter;
//    ParseQueryAdapter<Student> parseQueryStudentAdapter;

    public interface StudentListFragmentListener {
        void onStudentListItemClick(Student student);
    }

    public StudentListFragment() {
        // test
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        studentsList = new ArrayList<Student>();
        studentsListAdapter = new StudentsListAdapter(getActivity(), studentsList);
//        parseQueryStudentAdapter = new ParseQueryAdapter<Student>(getActivity(), Student.class);//, R.layout.student_list_item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_student_list, container, false);
        lvStudentsList = (ListView) v.findViewById(R.id.lvStudentsList);
        lvStudentsList.setAdapter(studentsListAdapter);
        setupHandlers();
        loadStudentsDataFromParse();

//        parseQueryStudentAdapter.setTextKey("Name");
//        parseQueryStudentAdapter.setImageKey("ProfileImage");
//        lvStudentsList.setAdapter(parseQueryStudentAdapter);

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (StudentListFragmentListener) activity;
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
    }

    private void setupHandlers() {
        lvStudentsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onStudentListItemClick(studentsListAdapter.getItem(position));
            }
        });
    }

    private void loadStudentsDataFromParse() {
        ParseClient.getAll(Student.class, new FindCallback<Student>() {
            @Override
            public void done(List<Student> studentList, com.parse.ParseException e) {
                if (e == null) {
                    for (Student student : studentList) {
                        //Log.d("debug", "Student Name: " + student.getName());
                        studentsListAdapter.add(student);
                    }
                    studentsListAdapter.notifyDataSetChanged();
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });
    }

}
