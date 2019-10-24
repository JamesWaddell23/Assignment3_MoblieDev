package com.example.assignment3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

public class ClearAllDialog extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            return new AlertDialog.Builder(activity)
                    .setMessage("Do You Want To Clear All Events?")
                    .setPositiveButton("Yes", (di, i) -> activity.clearAll())
                    .setNegativeButton("No", null)
                    .create();
        }else{
            return super.onCreateDialog(savedInstanceState);
        }
    }
}
