package org.characterlab.android.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by mandar.b on 8/2/2014.
 */
public class AlarmReceiver extends BroadcastReceiver {

    public static final int REQUEST_CODE = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, PollMessageService.class);
        i.putExtra("foo", "Mandar");
        context.startService(i);
    }

}
