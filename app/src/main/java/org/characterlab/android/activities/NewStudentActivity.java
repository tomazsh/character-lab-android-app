package org.characterlab.android.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.SaveCallback;

import org.characterlab.android.R;
import org.characterlab.android.events.StudentAddedEvent;
import org.characterlab.android.fragments.AddStudentCompletionFragment;
import org.characterlab.android.fragments.AddStudentFragment;
import org.characterlab.android.helpers.ParseClient;
import org.characterlab.android.helpers.ProgressBarHelper;
import org.characterlab.android.models.NewStudentViewModel;
import org.characterlab.android.models.StrengthAssessment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class NewStudentActivity extends FragmentActivity implements AddStudentFragment.AddStudentFragmentListener {

    private AddStudentFragment addStudentFragment;
    private ProgressBarHelper progressBarHelper;
    private int mPosition = -1;

    MenuItem addStudentMenuItem;

    public final String APP_TAG = "CharacterLab";
    public String photoFileName = "student_profile_photo.jpg";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBarHelper = new ProgressBarHelper(this);
        setContentView(R.layout.activity_new_student);

        progressBarHelper.setupProgressBarViews(this);
        if (savedInstanceState == null) {
            showAddStudentFragment();
        }
    }

    private void showAddStudentFragment() {
        if (addStudentFragment == null) {
            addStudentFragment = new AddStudentFragment();
        }
        setContainerFragment(addStudentFragment);
    }

    private void setContainerFragment(android.support.v4.app.Fragment fragment) {
        if (fragment.isAdded() && !fragment.isDetached() && !fragment.isRemoving()) {
            return;
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.flNewStudentContainer, fragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_student, menu);
        addStudentMenuItem = menu.findItem(R.id.save_new_student);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_new_student) {
            List<NewStudentViewModel> studentsToSaveToParse = new ArrayList<NewStudentViewModel>();
            List<NewStudentViewModel> newStudents = addStudentFragment.getNewStudentsList();
            for (NewStudentViewModel newStudent : newStudents) {
                if (newStudent.getStudentName() != null &&
                    !newStudent.getStudentName().isEmpty()) {
                    studentsToSaveToParse.add(newStudent);
                }
            }

            if (studentsToSaveToParse.size() > 0) {
                new CreateNewStudentsTask().execute(studentsToSaveToParse);
            } else {
                showAlertDialog();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // startregion photo capture

    @Override
    public void onPhotoButtonPressed(int position) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName + position)); // set the image file name
        // Start the image capture intent to take photo
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        mPosition = position;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK && mPosition != -1) {
                Uri takenPhotoUri = getPhotoFileUri(photoFileName + mPosition);
                // by this point we have the camera photo on disk
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 6;
                Bitmap studentImage = BitmapFactory.decodeFile(takenPhotoUri.getPath(), options);
                addStudentFragment.useCapturedPhoto(studentImage, mPosition);
                mPosition = -1;
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Returns the Uri for a photo stored on disk given the fileName
    public Uri getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
    }

    private void createStudentInParse(NewStudentViewModel newStudent) {
        ParseClient.createStudent(newStudent.getStudentName(), newStudent.getStudentImage()); //, new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e == null) {
//                    EventBus.getDefault().post(new StudentAddedEvent());
//                } else {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    // endregion photo capture

    private void showAlertDialog() {
        String dialogMsg = "Incomplete data. No students will be added.";

        final ContextThemeWrapper context = new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light_Dialog);

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.custom_confirmation_dialog, null);
        TextView deleteDialogMsg = (TextView) v.findViewById(R.id.deleteDialogMsg);
        deleteDialogMsg.setText(dialogMsg);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    class CreateNewStudentsTask extends AsyncTask<List<NewStudentViewModel>, Void, String> {
        protected void onPreExecute() {
            progressBarHelper.showProgressBar();
        }

        protected String doInBackground(List<NewStudentViewModel>... newStudents) {
            List<NewStudentViewModel> studentList = newStudents[0];

            for (NewStudentViewModel newStudent : studentList) {
                createStudentInParse(newStudent);
            }

            EventBus.getDefault().post(new StudentAddedEvent());
            return String.valueOf(studentList.size());
        }

        protected void onPostExecute(String result) {
            progressBarHelper.hideProgressBar();
            Toast.makeText(getApplicationContext(), result + " student added", Toast.LENGTH_SHORT).show();
            finish();
        }
    }





}
