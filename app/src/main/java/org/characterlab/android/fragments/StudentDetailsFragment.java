package org.characterlab.android.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;

import org.characterlab.android.R;
import org.characterlab.android.helpers.ParseClient;
import org.characterlab.android.models.Strength;
import org.characterlab.android.models.StrengthAssessment;
import org.characterlab.android.models.Student;
import org.characterlab.android.views.Bar;
import org.characterlab.android.views.BarGraph;

import java.util.ArrayList;
import java.util.List;

public class StudentDetailsFragment extends Fragment implements BarGraph.OnBarClickedListener {
    Student mStudent;
    List<StrengthAssessment> assessments;

    public StudentDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assessments = new ArrayList<StrengthAssessment>();
        Log.d("debug", "Details Frag Create, Student: " + mStudent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("debug", "Details Frag CreateView, Student: " + mStudent);
        View v =  inflater.inflate(R.layout.fragment_student_details, container, false);
        final BarGraph barGraph = (BarGraph) v.findViewById(R.id.bgStudentDetail);

        ParseClient.getLatestAssessmentsForStudent(mStudent,
                new FindCallback<StrengthAssessment>() {
                    public void done(List<StrengthAssessment> list, ParseException e) {
                        if (e == null) {
                            Log.d("debug", "Assessments size: " + list.size());
                            for (StrengthAssessment assessment : list) {
                                assessments.add(assessment);
                            }
                            setupGraph(barGraph);
                        } else {
                            e.printStackTrace();
                        }

                    }
                }
        );

        return v;
    }

    private void setupGraph(BarGraph barGraph) {
        if (assessments != null && !assessments.isEmpty()) {
            ArrayList<Bar> bars = new ArrayList<Bar>();
            for (StrengthAssessment assessment : assessments) {
                Bar bar = new Bar();
                bar.setName(assessment.getStrength().getName());
                bar.setValue(assessment.getScore());
                bars.add(bar);
            }

            barGraph.setBars(bars);
            barGraph.setOnBarClickedListener(this);
        }
    }

    //region Getters abd Setters

    public Student getStudent() {
        return mStudent;
    }

    public void setStudent(Student student) {
        mStudent = student;
    }

    @Override
    public void onClick(String name) {
        Toast.makeText(getActivity(), "Clicked: " + name, Toast.LENGTH_SHORT).show();
    }

    //endregion
}
