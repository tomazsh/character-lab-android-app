package org.characterlab.android.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.characterlab.android.R;
import org.characterlab.android.adapters.StudentsListAdapter;
import org.characterlab.android.models.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentListFragment extends Fragment {
    StudentListFragmentListener mListener;

    ListView lvStudentsList;
    List<Student> studentsList;
    StudentsListAdapter studentsListAdapter;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_student_list, container, false);
        lvStudentsList = (ListView) v.findViewById(R.id.lvStudentsList);
        lvStudentsList.setAdapter(studentsListAdapter);
        setupHandlers();
        loadFakeData();
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (StudentListFragmentListener)activity;
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

    // Todo: remove this and attach to Parse backend.
    private void loadFakeData() {
        Student student1 = new Student();
        student1.put("Name", "Craig");
        studentsListAdapter.add(student1);

        Student student2 = new Student();
        student2.put("Name", "Charles");
        studentsListAdapter.add(student2);

        Student student3 = new Student();
        student3.put("Name", "Cindy");
        studentsListAdapter.add(student3);
    }


}
