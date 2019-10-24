package com.example.assignment3;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class UpdateService extends IntentService {
    final static String UPDATE = "UPDATE";
    public UpdateService(){
        super("UpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        for(int i =0; i<24; i++) {
            ListItemDB.update(i,  "", ListItemDB.getTime(i), "", getResources().getColor(R.color.Empty));
        }
        Intent updateIntent = new Intent();
        updateIntent.setAction(UPDATE);
        updateIntent.putExtra("result", "completed");
        LocalBroadcastManager.getInstance(this).sendBroadcast(updateIntent);



    }
}
