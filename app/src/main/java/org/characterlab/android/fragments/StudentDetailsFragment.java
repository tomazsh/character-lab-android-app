package org.characterlab.android.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;

import org.characterlab.android.R;
import org.characterlab.android.adapters.MeasurementRecordsListAdapter;
import org.characterlab.android.adapters.StudentDetailsSummaryCardsAdapter;
import org.characterlab.android.helpers.ParseClient;
import org.characterlab.android.helpers.Utils;
import org.characterlab.android.models.Strength;
import org.characterlab.android.models.StrengthAssessment;
import org.characterlab.android.models.Student;
import org.characterlab.android.models.StudentDetailViewModel;
import org.characterlab.android.views.Bar;
import org.characterlab.android.views.BarGraph;
import org.characterlab.android.views.LineView;
import org.characterlab.android.views.RoundedParseImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StudentDetailsFragment extends Fragment implements BarGraph.OnBarClickedListener {
    Student mStudent;
    List<StrengthAssessment> assessments;
    List<StrengthAssessment> assessmentsDateWiseList;

    private BarGraph barGraph;
    private RoundedParseImageView rpivStDet;
    private TextView tvLastMeasuredValue;
    private ScrollView svStDet;
    private LinearLayout llStDetMeasureStrength;
    private ListView lvStDetMeasurementRecord;
    ViewPager vpStDetPager;
    StudentDetailsSummaryCardsAdapter adapter;
    MeasurementRecordsListAdapter measurementRecordsListAdapter;

    private StudentDetailsFragmentListener listener;

    public StudentDetailsFragment() {
    }

    public interface StudentDetailsFragmentListener {
        void onMeasureStrengthClicked();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assessments = new ArrayList<StrengthAssessment>();
        adapter = new StudentDetailsSummaryCardsAdapter(getActivity().getSupportFragmentManager());
        Log.d("debug", "Details Frag Create, Student: " + mStudent);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (StudentDetailsFragmentListener) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("debug", "Details Frag CreateView, Student: " + mStudent);
        View v =  inflater.inflate(R.layout.fragment_student_details, container, false);
        barGraph = (BarGraph) v.findViewById(R.id.bgStudentDetail);
        rpivStDet = (RoundedParseImageView) v.findViewById(R.id.rpivStDet);
        tvLastMeasuredValue = (TextView) v.findViewById(R.id.tvLastMeasuredValue);
        svStDet = (ScrollView) v.findViewById(R.id.svStDet);
        llStDetMeasureStrength = (LinearLayout) v.findViewById(R.id.llStDetMeasureStrength);
        vpStDetPager = (ViewPager) v.findViewById(R.id.vpStDetPager);
        lvStDetMeasurementRecord = (ListView) v.findViewById(R.id.lvStDetMeasurementRecord);

        llStDetMeasureStrength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMeasureStrengthClicked();
            }
        });

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

        rpivStDet.loadParseFileImageInBackground(mStudent.getProfileImage());

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.US);
        tvLastMeasuredValue.setText(dateFormat.format(viewModel.getLastAssessmentDate()));

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
            bar.setColor(getResources().getColor(R.color.graph_bar_yellow));
            bar.setAvgColor(getResources().getColor(R.color.aquamarine));
            bars.add(bar);
        }

        barGraph.setBars(bars);
        barGraph.setOnBarClickedListener(this);

        adapter.setAssessmentList(viewModel.getSortedLatestAssessments());
        vpStDetPager.setClipToPadding(false);
        vpStDetPager.setPageMargin(12);
        vpStDetPager.setAdapter(adapter);

        assessmentsDateWiseList = viewModel.getAssessmentsDatewiseList();
        measurementRecordsListAdapter = new MeasurementRecordsListAdapter(getActivity(), assessmentsDateWiseList);
        lvStDetMeasurementRecord.setAdapter(measurementRecordsListAdapter);

        svStDet.scrollTo(0,0);
    }

    //region Getters abd Setters

    public Student getStudent() {
        return mStudent;
    }

    public void setStudent(Student student) {
        mStudent = student;
    }

    //endregion

    @Override
    public void onClick(String name) {
        final Strength strength = Strength.fromName(name);
        ParseClient.getStrengthScoreHistoryForStudent(mStudent, strength,
                new FindCallback<StrengthAssessment>() {
                    public void done(List<StrengthAssessment> list, ParseException e) {
                        if (e == null) {
                            showLineGraphDialog(list, strength);
                        } else {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    private void showLineGraphDialog(List<StrengthAssessment> assessments, Strength strength) {
        final ContextThemeWrapper context = new ContextThemeWrapper(getActivity(),
                android.R.style.Theme_Holo_Light_Dialog);

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.fragment_strength_history, null);

        ImageView ivHistoryStrengthLogo = (ImageView) v.findViewById(R.id.ivHistoryStrengthLogo);
        TextView tvHistoryStrengthTitle = (TextView) v.findViewById(R.id.tvHistoryStrengthTitle);
        TextView tvHistoryAvgScore = (TextView) v.findViewById(R.id.tvHistoryAvgScore);
        LineView lineView = (LineView) v.findViewById(R.id.line_view);

        ivHistoryStrengthLogo.setImageResource(strength.getIconCircleId());
        tvHistoryStrengthTitle.setText(strength.getName());
        float avgScore = 0.0f;

        if (assessments != null && !assessments.isEmpty()) {
            ArrayList<String> labels = new ArrayList<String>();
            ArrayList<Integer> values = new ArrayList<Integer>();

            for (StrengthAssessment assessment : assessments) {
                Date createdAt = assessment.getCreatedAt();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d", Locale.US);
                String label = dateFormat.format(createdAt);
                labels.add(label);
                values.add(assessment.getScore());

                avgScore += assessment.getScore();
            }
            avgScore /= assessments.size();
            lineView.setBottomTextList(labels);
            lineView.setDrawDotLine(true);
            lineView.setShowPopup(LineView.SHOW_POPUPS_NONE);

            ArrayList<ArrayList<Integer>> dataLists = new ArrayList<ArrayList<Integer>>();
            dataLists.add(values);
            lineView.setDataList(dataLists);
        }
        tvHistoryAvgScore.setText(String.format("%.2f", avgScore));

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(v)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create().show();
    }

}
