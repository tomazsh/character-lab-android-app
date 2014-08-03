package org.characterlab.android.notifications;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParseUser;

import org.characterlab.android.CharacterLabApplication;
import org.characterlab.android.R;
import org.characterlab.android.activities.StudentDetailsActivity;
import org.characterlab.android.helpers.ParseClient;
import org.characterlab.android.models.Message;
import org.characterlab.android.models.Student;

import java.util.Scanner;

/**
 * Created by mandar.b on 8/2/2014.
 */
public class PollMessageService extends IntentService {

    private final String SHARED_PREF_FILE_NAME = "org.characterlab.android.prefs";
    private final String MESSAGE_ID = "msg_id";

    public PollMessageService() {
        super("PollMessageService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ParseUser loggedInUser = ParseUser.getCurrentUser();
        if (loggedInUser != null) {
            Message message = ParseClient.getLatestMessage(loggedInUser);
            if (message != null) {
                String lastConsumedMsgId = getLatestConsumedMessageId();
                if (lastConsumedMsgId == null ||
                    !lastConsumedMsgId.equalsIgnoreCase(message.getObjectId())) {

                    Student student = ParseClient.getStudent(message.getStudentId());
                    if (student != null) {
                        Intent notifyIntent = new Intent(getApplicationContext(), StudentDetailsActivity.class);
                        CharacterLabApplication.putInCache(student.getObjectId(), student);
                        notifyIntent.putExtra(StudentDetailsActivity.STUDENT_KEY, student.getObjectId());
                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                                                        0, notifyIntent, PendingIntent.FLAG_CANCEL_CURRENT);


                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
                        mBuilder = mBuilder.setSmallIcon(R.drawable.ic_launcher);
                        mBuilder = mBuilder.setContentTitle("New Assessment");
                        String name = message.getAuthor().getString("Name");
                        String messageText = name + " added assessment for " + student.getName();
                        mBuilder = mBuilder.setContentText(messageText);
                        mBuilder = mBuilder.setContentIntent(pendingIntent);

                        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(1, mBuilder.build());
                        saveLatestConsumedMessageId(message.getObjectId());
                    }

                }
            }
        }
    }

    private String getLatestConsumedMessageId() {
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREF_FILE_NAME, MODE_PRIVATE);
        return sharedPref.getString(MESSAGE_ID, null);
    }

    private void saveLatestConsumedMessageId(String messageId) {
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREF_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putString(MESSAGE_ID, messageId);
        prefEditor.commit();
    }

}
