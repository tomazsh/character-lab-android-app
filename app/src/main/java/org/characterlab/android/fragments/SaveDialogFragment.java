package org.characterlab.android.fragments;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;
import com.plattysoft.leonids.ParticleSystem;

import org.characterlab.android.R;
import org.characterlab.android.events.NewAssessmentAddedEvent;
import org.characterlab.android.helpers.ParseClient;
import org.characterlab.android.models.NewAssessmentViewModel;
import org.characterlab.android.models.Student;

import de.greenrobot.event.EventBus;

public class SaveDialogFragment extends DialogFragment {

    String studentId;

    public SaveDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    public static SaveDialogFragment newInstance(NewAssessmentViewModel assessmentsModel, Student student) {
        SaveDialogFragment frag = new SaveDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("assessmentModel", assessmentsModel);
        args.putSerializable("student", student);
        args.putString("studentId", student.getObjectId());
        frag.setArguments(args);
        return frag;
    }

    public interface SavedFragmentListener {
        void onSaveFragmentSuccess();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        studentId = getArguments().getString("studentId");

        View view = inflater.inflate(R.layout.fragment_save_dialog, container);
        final TextView tvSave = (TextView) view.findViewById(R.id.tvSave);
        final ImageView ivSave = (ImageView) view.findViewById(R.id.ivSave);
        final Student student = (Student)getArguments().getSerializable("student");
        final NewAssessmentViewModel viewModel = (NewAssessmentViewModel)getArguments().getSerializable("assessmentModel");
        tvSave.setText(getString(R.string.save_warning_text, student.getName()));

        final Button saveButton = (Button) view.findViewById(R.id.btnSave);
        final Button cancelButton = (Button) view.findViewById(R.id.btnCancel);
        final Button dismissButton = (Button) view.findViewById(R.id.btnDismiss);
        final Button shareButton = (Button) view.findViewById(R.id.btnShare);
        final LinearLayout llSave = (LinearLayout) view.findViewById(R.id.llSave);
        final LinearLayout llShareDimiss = (LinearLayout) view.findViewById(R.id.llShareDimiss);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseClient.saveStudentAssessment(viewModel, student);
                AnimatorSet set0 = new AnimatorSet();
                set0.playTogether(
                        ObjectAnimator.ofFloat(ivSave, "alpha", 1.0f, 0.0f)
                                .setDuration(200),
                        ObjectAnimator.ofFloat(ivSave, "scaleX", 1.0f, 0.0f)
                                .setDuration(200),
                        ObjectAnimator.ofFloat(ivSave, "scaleY", 1.0f, 0.0f)
                                .setDuration(200),
                        ObjectAnimator.ofFloat(cancelButton, "alpha", 1.0f, 0.0f)
                                .setDuration(200),
                        ObjectAnimator.ofFloat(tvSave, "alpha", 1.0f, 0.0f)
                                .setDuration(200),
                        ObjectAnimator.ofFloat(saveButton, "alpha", 1.0f, 0.0f)
                                .setDuration(200)
                );
                set0.start();
                set0.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        ivSave.setImageResource(R.drawable.completed);
                        tvSave.setText(getString(R.string.saved_confirmation));
                        saveButton.setVisibility(View.GONE);
                        cancelButton.setVisibility(View.GONE);
                        llShareDimiss.setVisibility(View.VISIBLE);
                        dismissButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                EventBus.getDefault().post(new NewAssessmentAddedEvent(studentId));
                                SavedFragmentListener savedFragmentListener = (SavedFragmentListener) getActivity();
                                savedFragmentListener.onSaveFragmentSuccess();
                            }
                        });
                        shareButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ParseClient.saveNotificationMessage(ParseUser.getCurrentUser(), studentId);
                                Toast.makeText(getActivity(), "Notification Sent", Toast.LENGTH_SHORT).show();

                                EventBus.getDefault().post(new NewAssessmentAddedEvent(studentId));
                                SavedFragmentListener savedFragmentListener = (SavedFragmentListener) getActivity();
                                savedFragmentListener.onSaveFragmentSuccess();
                            }
                        });

                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(
                                ObjectAnimator.ofFloat(ivSave, "alpha", 0.0f, 1.0f)
                                        .setDuration(200),
                                ObjectAnimator.ofFloat(ivSave, "scaleX", 0.0f, 1.0f)
                                        .setDuration(200),
                                ObjectAnimator.ofFloat(ivSave, "scaleY", 0.0f, 1.0f)
                                        .setDuration(200),
                                ObjectAnimator.ofFloat(tvSave, "alpha", 0.0f, 1.0f)
                                        .setDuration(200),
                                ObjectAnimator.ofFloat(dismissButton, "alpha", 0.0f, 1.0f)
                                        .setDuration(200),
                                ObjectAnimator.ofFloat(shareButton, "alpha", 0.0f, 1.0f)
                                        .setDuration(200)
                        );
                        set.start();
                        set.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                new ParticleSystem(getActivity(), 200, R.drawable.star_pink, 8000)
                                        .setSpeedRange(0.2f, 0.5f)
                                        .oneShot(llSave, 200);
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        return view;
    }
}