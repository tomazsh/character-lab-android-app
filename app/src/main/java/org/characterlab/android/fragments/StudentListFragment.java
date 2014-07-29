package org.characterlab.android.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;

import org.characterlab.android.R;
import org.characterlab.android.adapters.StudentsListAdapter;
import org.characterlab.android.helpers.DataLoadListener;
import org.characterlab.android.helpers.ParseClient;
import org.characterlab.android.helpers.ProgressBarHelper;
import org.characterlab.android.models.Student;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    StudentListFragmentListener mListener;

    ListView lvStudentsList;
    SwipeRefreshLayout swipeRefreshLayout;

    List<Student> studentsList;
    StudentsListAdapter studentsListAdapter;

    Set<String> studentIdCache;

    public interface StudentListFragmentListener extends DataLoadListener {
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
        studentIdCache = new HashSet<String>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_student_list, container, false);

        mListener.dataRequestSent();

        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.pencil_yellow, R.color.aquamarine, R.color.pencil_yellow, R.color.aquamarine);

        lvStudentsList = (ListView) v.findViewById(R.id.lvStudentsList);
        lvStudentsList.setAdapter(studentsListAdapter);
        setupHandlers();
        loadStudentsDataFromParse();

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
                        studentIdCache.add(student.getObjectId());
                    }
                    studentsListAdapter.notifyDataSetChanged();
                    mListener.dataReceived();
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        mListener.dataRequestSent();
        ParseClient.getAll(Student.class, new FindCallback<Student>() {
            @Override
            public void done(List<Student> studentList, com.parse.ParseException e) {
                if (e == null) {
                    if (studentList.size() != studentsListAdapter.getCount()) {
                        Set<String> studentIdsOnServer = new HashSet<String>();
                        for (Student student : studentList) {
                            studentIdsOnServer.add(student.getObjectId());
                            if (!studentIdCache.contains(student.getObjectId())) {
                                studentsListAdapter.add(student);
                                studentIdCache.add(student.getObjectId());
                            }
                        }

                        Set<Student> studentsToRemove = new HashSet<Student>();
                        for (int i = 0; i < studentsListAdapter.getCount(); i++) {
                            Student student = studentsListAdapter.getItem(i);
                            if (!studentIdsOnServer.contains(student.getObjectId())) {
                                studentsToRemove.add(student);
                            }
                        }

                        for (Student student : studentsToRemove) {
                            studentsListAdapter.remove(student);
                            studentIdCache.remove(student);
                        }

                        studentsListAdapter.notifyDataSetChanged();
                    }
                    mListener.dataReceived();
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });

    }

}
