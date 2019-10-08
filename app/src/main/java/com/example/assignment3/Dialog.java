package com.example.assignment3;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Dialog extends AppCompatActivity {
    TextView textView;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        textView = findViewById(R.id.textView);
        linearLayout = findViewById(R.id.dialogLayout);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            textView.setText(extras.get("text").toString());
            //System.out.println(extras.getInt("color"));
            linearLayout.setBackgroundColor(extras.getInt("color"));
        }

    }




    /**
     * closes the activity when back button is pressed
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }




}
