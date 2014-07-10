package org.characterlab.android.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;

import org.characterlab.android.R;
import org.characterlab.android.helpers.ParseClient;
import org.characterlab.android.helpers.Utils;
import org.characterlab.android.models.Strength;
import org.characterlab.android.models.StrengthAssessment;
import org.characterlab.android.models.Student;
import org.characterlab.android.models.StudentDetailViewModel;
import org.characterlab.android.views.Bar;
import org.characterlab.android.views.BarGraph;

import java.util.ArrayList;
import java.util.List;

public class StudentDetailsFragment extends Fragment implements BarGraph.OnBarClickedListener {
    Student mStudent;
    List<StrengthAssessment> assessments;

    private BarGraph barGraph;
    private TextView tvStDetailsStrong;
    private ImageView ivStDetailsStrong;
    private TextView tvStDetailsWeak;
    private ImageView ivStDetailsWeak;
    private TextView tvStDetailsImproved;
    private ImageView ivStDetailsImproved;

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
        barGraph = (BarGraph) v.findViewById(R.id.bgStudentDetail);
        tvStDetailsStrong = (TextView) v.findViewById(R.id.tvStDetailsStrong);
        ivStDetailsStrong = (ImageView) v.findViewById(R.id.ivStDetailsStrong);
        tvStDetailsWeak = (TextView) v.findViewById(R.id.tvStDetailsWeak);
        ivStDetailsWeak = (ImageView) v.findViewById(R.id.ivStDetailsWeak);
        tvStDetailsImproved = (TextView) v.findViewById(R.id.tvStDetailsImproved);
        ivStDetailsImproved = (ImageView) v.findViewById(R.id.ivStDetailsImproved);

        ParseClient.getAllAssessmentsForStudent(mStudent,
                new FindCallback<StrengthAssessment>() {
                    public void done(List<StrengthAssessment> list, ParseException e) {
                        if (e == null) {
                            Log.d("debug", "Assessments size: " + list.size());
                            for (StrengthAssessment assessment : list) {
                                assessments.add(assessment);
                            }
                            StudentDetailViewModel viewModel = Utils.generateStudentDetailViewModel(assessments);
                            updateView(viewModel);
                        } else {
                            e.printStackTrace();
                        }

                    }
                }
        );

        return v;
    }

    private void updateView(StudentDetailViewModel viewModel) {

        tvStDetailsStrong.setText(viewModel.getStrongest().getName());
        ivStDetailsStrong.setImageResource(viewModel.getStrongest().getIconId());

        tvStDetailsWeak.setText(viewModel.getWeakest().getName());
        ivStDetailsWeak.setImageResource(viewModel.getWeakest().getIconId());

        tvStDetailsImproved.setText(viewModel.getMostImproved().getName());
        ivStDetailsImproved.setImageResource(viewModel.getMostImproved().getIconId());

        ArrayList<Bar> bars = new ArrayList<Bar>();
        for (Strength strength : Strength.values()) {
            int latestAssessmentValue = viewModel.getLatestAssessments().containsKey(strength) ?
                                        viewModel.getLatestAssessments().get(strength) : 0;

            int avgAssessmentValue = viewModel.getAvgAssessmentValues().containsKey(strength) ?
                                     viewModel.getAvgAssessmentValues().get(strength) : 0;

            Bar bar = new Bar();
            bar.setName(strength.getName());
            bar.setValue(latestAssessmentValue);
            bar.setAvgValue(avgAssessmentValue);
            bars.add(bar);
        }

        barGraph.setBars(bars);
        barGraph.setOnBarClickedListener(this);
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
