package org.characterlab.android.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.characterlab.android.helpers.Utils;

/**
 * Created by mandar.b on 8/2/2014.
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Utils.scheduleAlarmForPolling(context);
        }
    }

}
