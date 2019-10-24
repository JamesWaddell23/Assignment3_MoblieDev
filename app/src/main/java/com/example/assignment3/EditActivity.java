package com.example.assignment3;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

//rename this class
public class EditActivity extends AppCompatActivity {
    TextView textView;
    EditText editText;
    RadioButton categoryButton1, categoryButton2, categoryButton3, categoryButton4;
    RadioGroup rg;
    LinearLayout linearLayout;
    Bundle extras;
    Button clearButton, okButton;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        extras = getIntent().getExtras();
        setContentView(R.layout.activity_edit);
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        //init radio buttons
        categoryButton1 = findViewById(R.id.workButton);
        categoryButton2 = findViewById(R.id.foodButton);
        categoryButton3 = findViewById(R.id.sportButton);
        categoryButton4 = findViewById(R.id.otherButton);

        okButton = findViewById(R.id.okButton);
        clearButton = findViewById(R.id.deleteButton);
        rg = findViewById(R.id.radioGroup);
        initSelectButtons();
        linearLayout = findViewById(R.id.infoLayout);

       setTimeColorAndName();
        //set category buttons text
        categoryButton1.setText(pref.getString("category1", "DEFAULT"));
        categoryButton2.setText(pref.getString("category2", "DEFAULT"));
        categoryButton3.setText(pref.getString("category3", "DEFAULT"));
        categoryButton4.setText(pref.getString("category4", "DEFAULT"));




        categoryButton1.setOnClickListener(v->{
            linearLayout.getBackground().setColorFilter(getResources().getColor(R.color.Orange), PorterDuff.Mode.SRC_IN);
        });
        categoryButton2.setOnClickListener(v->{
            linearLayout.getBackground().setColorFilter(getResources().getColor(R.color.Blue), PorterDuff.Mode.SRC_IN);
        });
        categoryButton3.setOnClickListener(v->{
            linearLayout.getBackground().setColorFilter(getResources().getColor(R.color.Purple), PorterDuff.Mode.SRC_IN);
        });
        categoryButton4.setOnClickListener(v->{
            linearLayout.getBackground().setColorFilter(getResources().getColor(R.color.Green), PorterDuff.Mode.SRC_IN);
        });

        clearButton.setOnClickListener(v ->{
            linearLayout.getBackground().setColorFilter(getResources().getColor(R.color.Empty), PorterDuff.Mode.SRC_IN);
            editText.setText("");
            this.finish();
        });

        okButton.setOnClickListener(v->{
            createEvent();
        });


    }

    @SuppressLint("StaticFieldLeak")
    void setTimeColorAndName(){
        //set the time
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return ListItemDB.getTime(extras.getInt("pos"));
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                textView.setText(result);

            }
        }.execute();

        //set the event name
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return ListItemDB.getText(extras.getInt("pos"));
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                editText.setText(result);

            }
        }.execute();

        //set the color
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                return ListItemDB.getColor(extras.getInt("pos"));
            }
            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                linearLayout.getBackground().setColorFilter(result, PorterDuff.Mode.SRC_IN);

            }
        }.execute();
    }



    //change me
    String getSelectedCategory(){
        if(categoryButton1.isChecked()){
            return getResources().getString(R.string.work_name);
        }else if(categoryButton2.isChecked()){
            return getResources().getString(R.string.food_name);
        }else if(categoryButton3.isChecked()){
            return getResources().getString(R.string.sport_name);
        }else if(categoryButton4.isChecked()){
            return getResources().getString(R.string.other_name);
        }else{
            return "";
        }
    }


    void initSelectButtons(){
        if(extras != null) {
            if (extras.getInt("color") == getResources().getColor(R.color.Orange)) {
                categoryButton1.setChecked(true);
            } else if (extras.getInt("color") == getResources().getColor(R.color.Blue)) {
                categoryButton2.setChecked(true);
            } else if (extras.getInt("color") == getResources().getColor(R.color.Purple)) {
                categoryButton3.setChecked(true);
            } else if(extras.getInt("color") == getResources().getColor(R.color.Green)) {
                categoryButton4.setChecked(true);
            }
        }
    }

    int getSelectedColor(){
        if(categoryButton1.isChecked()){
            return getResources().getColor(R.color.Orange);
        }else if(categoryButton2.isChecked()){
            return getResources().getColor(R.color.Blue);
        }else if(categoryButton3.isChecked()){
            return getResources().getColor(R.color.Purple);
        }else if(categoryButton4.isChecked()){
            return getResources().getColor(R.color.Green);
        }
        return getResources().getColor(R.color.Empty);
    }

    @SuppressLint("StaticFieldLeak")
    void createEvent(){
        if(extras != null) {
            //pref.edit().putInt("current_position", extras.getInt("pos")).apply();
            //pref.edit().putString("text", editText.getText().toString()).apply();
            //THREAD WORK
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    ListItemDB.update(extras.getInt("pos"),editText.getText().toString(),textView.getText().toString(), getSelectedCategory(),getSelectedColor());
                    return null;
                }
                @Override
                protected void onPostExecute(Void result) {
                    super.onPostExecute(result);
                }
            }.execute();

        }
        this.finish();
    }



    /**
     * closes the activity when back button is pressed
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        createEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println();
        setTimeColorAndName();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        System.out.println("onConfig()");
        setTimeColorAndName();

    }
}
