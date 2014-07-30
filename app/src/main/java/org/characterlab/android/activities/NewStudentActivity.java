package org.characterlab.android.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.SaveCallback;

import org.characterlab.android.R;
import org.characterlab.android.events.StudentAddedEvent;
import org.characterlab.android.fragments.AddStudentCompletionFragment;
import org.characterlab.android.fragments.AddStudentFragment;
import org.characterlab.android.helpers.ParseClient;
import org.characterlab.android.helpers.ProgressBarHelper;

import java.io.File;

import de.greenrobot.event.EventBus;

public class NewStudentActivity extends FragmentActivity implements AddStudentFragment.AddStudentFragmentListener {

    private AddStudentFragment addStudentFragment;
    private AddStudentCompletionFragment addStudentCompletionFragment;
    private ProgressBarHelper progressBarHelper;

    private ImageView ivStudentPhoto;
    private ImageView ivNewStudentCameraButton;

    private String studentName;
    private Bitmap studentImage = null;
    private boolean studentSaved = false;

    MenuItem addStudentMenuItem;
    MenuItem doneMenuItem;

    public final String APP_TAG = "CharacterLab";
    public String photoFileName = "student_profile_photo.jpg";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBarHelper = new ProgressBarHelper(this);
        setContentView(R.layout.activity_new_student);

        progressBarHelper.setupProgressBarViews(this);
        ivStudentPhoto = (ImageView) findViewById(R.id.ivStudentPhoto);

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

    private void showAddStudentCompletionFragment() {
        if (addStudentCompletionFragment == null) {
            addStudentCompletionFragment = AddStudentCompletionFragment.newInstance(studentName);
        }
        studentSaved = true;
        invalidateOptionsMenu();
        setContainerFragment(addStudentCompletionFragment);
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
        doneMenuItem = menu.findItem(R.id.done);

        if (!studentSaved) {
            addStudentMenuItem.setVisible(true);
            addStudentMenuItem.setEnabled(false);
            doneMenuItem.setVisible(false);
        } else {
            addStudentMenuItem.setVisible(false);
            doneMenuItem.setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_new_student) {
            progressBarHelper.showProgressBar();
            saveFileInParse();
            return true;
        } else if (id == R.id.done) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // startregion photo capture

    @Override
    public void onPhotoButtonPressed() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName)); // set the image file name
        // Start the image capture intent to take photo
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri takenPhotoUri = getPhotoFileUri(photoFileName);
                // by this point we have the camera photo on disk
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 5;
                studentImage = BitmapFactory.decodeFile(takenPhotoUri.getPath(), options);
                // Load the taken image into a preview
                ImageView ivPreview = (ImageView) findViewById(R.id.ivStudentPhoto);
                ivPreview.setImageBitmap(studentImage);
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

    private void saveFileInParse() {
        ParseClient.createStudent(studentName, studentImage, new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "Student Created", Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new StudentAddedEvent());
                    showAddStudentCompletionFragment();
                    progressBarHelper.hideProgressBar();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to create student", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    // endregion photo capture

    @Override
    public void onStudentNameChanged(String name) {
        studentName = name;
        if (addStudentMenuItem == null) {
            return;
        }

        if (name != null && !name.isEmpty()) {
            addStudentMenuItem.setEnabled(true);
        } else {
            addStudentMenuItem.setEnabled(false);
        }
    }

}
