package com.example.assignment3;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
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
        checkButtons();
        linearLayout = findViewById(R.id.infoLayout);
        //old way with intent
//        if(extras != null) {
//            textView.setText(extras.get("time").toString());
//            editText.setText(extras.get("text").toString());
//            linearLayout.getBackground().setColorFilter(ListItemDB.getColor(extras.getInt("color")), PorterDuff.Mode.SRC_IN);
//        }
        //new way data base [Maybe on separate thread
        textView.setText(ListItemDB.getTime(extras.getInt("pos")));
        editText.setText(ListItemDB.getText(extras.getInt("pos")));

        //set category buttons text
        categoryButton1.setText(pref.getString("category1", "DEFAULT"));
        categoryButton2.setText(pref.getString("category2", "DEFAULT"));
        categoryButton3.setText(pref.getString("category3", "DEFAULT"));
        categoryButton4.setText(pref.getString("category4", "DEFAULT"));




        linearLayout.getBackground().setColorFilter(ListItemDB.getColor(extras.getInt("pos")), PorterDuff.Mode.SRC_IN);

        categoryButton1.setOnClickListener(v->{
            linearLayout.getBackground().setColorFilter(pref.getInt("color1", 8), PorterDuff.Mode.SRC_IN);
        });
        categoryButton2.setOnClickListener(v->{
            linearLayout.getBackground().setColorFilter(pref.getInt("color2", 8), PorterDuff.Mode.SRC_IN);
        });
        categoryButton3.setOnClickListener(v->{
            linearLayout.getBackground().setColorFilter(pref.getInt("color3", 8), PorterDuff.Mode.SRC_IN);
        });
        categoryButton4.setOnClickListener(v->{
            linearLayout.getBackground().setColorFilter(pref.getInt("color4", 8), PorterDuff.Mode.SRC_IN);
        });

        clearButton.setOnClickListener(v ->{
            ListItemDB.update(extras.getInt("pos"),"",extras.get("time").toString(), getSelectedCategory(),getResources().getColor(R.color.Empty));
            this.finish();
        });

        okButton.setOnClickListener(v->{
            createEvent();
        });


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


    void checkButtons(){
        if(extras != null) {
            if (extras.getInt("color") == pref.getInt("color1", 8)) {
                categoryButton1.setChecked(true);
            } else if (extras.getInt("color") == pref.getInt("color2", 8)) {
                categoryButton2.setChecked(true);
            } else if (extras.getInt("color") == pref.getInt("color3", 8)) {
                categoryButton3.setChecked(true);
            } else if(extras.getInt("color") == pref.getInt("color4", 8)) {
                categoryButton4.setChecked(true);
            }
        }
    }

    int getSelectedColor(){
        if(categoryButton1.isChecked()){
            return pref.getInt("color1", 8);
        }else if(categoryButton2.isChecked()){
            return pref.getInt("color2", 8);
        }else if(categoryButton3.isChecked()){
            return pref.getInt("color3", 8);
        }else if(categoryButton4.isChecked()){
            return pref.getInt("color4", 8);
        }
        return getResources().getColor(R.color.Empty);
    }

    void createEvent(){
        if(extras != null) {
            //pref.edit().putInt("current_position", extras.getInt("pos")).apply();
            //pref.edit().putString("text", editText.getText().toString()).apply();
            ListItemDB.update(extras.getInt("pos"),editText.getText().toString(),extras.get("time").toString(), getSelectedCategory(),getSelectedColor());
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




}
