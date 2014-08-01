package org.characterlab.android.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
        lvNewStudents.setAdapter(newStudentsListAdapter);
        tvAddAnontherStudent = (TextView) v.findViewById(R.id.tvAddAnontherStudent);

        tvAddAnontherStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBlankStudentViewModelItem();
            }
        });

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

    public List<NewStudentViewModel> getNewStudentsList() {
        return newStudentsList;
    }

}
