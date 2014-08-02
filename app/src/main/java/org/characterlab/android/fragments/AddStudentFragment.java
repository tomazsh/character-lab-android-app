package org.characterlab.android.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nhaarman.listviewanimations.itemmanipulation.AnimateDismissAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingRightInAnimationAdapter;

import org.characterlab.android.R;
import org.characterlab.android.adapters.NewStudentsListAdapter;
import org.characterlab.android.models.NewStudentViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mandar.b on 7/26/2014.
 */
public class AddStudentFragment extends Fragment implements NewStudentsListAdapter.NewStudentsListAdapterListener {

    ListView lvNewStudents;
    TextView tvAddAnontherStudent;

    List<NewStudentViewModel> newStudentsList;
    NewStudentsListAdapter newStudentsListAdapter;
    AnimateDismissAdapter mAnimateDismissAdapter;

    AddStudentFragmentListener mListener;

    public interface AddStudentFragmentListener {
        public void onPhotoButtonPressed(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newStudentsList = new ArrayList<NewStudentViewModel>();
        newStudentsListAdapter = new NewStudentsListAdapter(getActivity(), newStudentsList, this);
        addBlankStudentViewModelItem();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_student, container, false);

        lvNewStudents = (ListView) v.findViewById(R.id.lvNewStudents);
//        lvNewStudents.setAdapter(newStudentsListAdapter);
        tvAddAnontherStudent = (TextView) v.findViewById(R.id.tvAddAnontherStudent);

        tvAddAnontherStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBlankStudentViewModelItem();
            }
        });

//        SwingRightInAnimationAdapter swingRightInAnimationAdapter = new SwingRightInAnimationAdapter(newStudentsListAdapter);
//        swingRightInAnimationAdapter.setAbsListView(lvNewStudents);
//        lvNewStudents.setAdapter(swingRightInAnimationAdapter);

        SwingRightInAnimationAdapter swingRightInAnimationAdapter = new SwingRightInAnimationAdapter(newStudentsListAdapter);
        swingRightInAnimationAdapter.setAbsListView(lvNewStudents);

        mAnimateDismissAdapter = new AnimateDismissAdapter(swingRightInAnimationAdapter, new OnDismissCallback() {
            @Override
            public void onDismiss(AbsListView listView, int[] reverseSortedPositions) {
                for (int position: reverseSortedPositions) {
                    newStudentsListAdapter.remove(position);
                    newStudentsListAdapter.notifyDataSetChanged();
                }
            }
        });

        mAnimateDismissAdapter.setAbsListView(lvNewStudents);
        lvNewStudents.setAdapter(mAnimateDismissAdapter);

        return v;
    }

    private void addBlankStudentViewModelItem() {
        NewStudentViewModel newStudentViewModel = new NewStudentViewModel();
        newStudentsListAdapter.add(newStudentViewModel);
        newStudentsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCameraClicked(int position) {
        mListener.onPhotoButtonPressed(position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (AddStudentFragmentListener) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public void useCapturedPhoto(Bitmap photoBitmap, int position) {
        NewStudentViewModel newStudentViewModel = newStudentsListAdapter.getItem(position);
        newStudentViewModel.setStudentImage(photoBitmap);
        newStudentsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeItem(int position) {
        Log.d("Index ", " is " + position);
        if (position >= newStudentsListAdapter.size()) {
            return;
        }

        List<Integer> positionsToDismiss = new ArrayList<Integer>();
        positionsToDismiss.add(position);

        mAnimateDismissAdapter.animateDismiss(positionsToDismiss);
//        newStudentsListAdapter.remove(position);
//        newStudentsListAdapter.notifyDataSetChanged();
    }

    public List<NewStudentViewModel> getNewStudentsList() {
        return newStudentsList;
    }

}
