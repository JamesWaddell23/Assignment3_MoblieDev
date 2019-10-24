package com.example.assignment3;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class CategoryActivity extends AppCompatActivity {
    SharedPreferences pref;
    EditText et1,et2,et3,et4;
    Button b1,b2,b3,b4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        //textviews
        et1 =findViewById(R.id.et1);
        et2 =findViewById(R.id.et2);
        et3 =findViewById(R.id.et3);
        et4 =findViewById(R.id.et4);
        //buttons


        //change to pref
        et1.setText(pref.getString("category1", "DEFAULT"));
        et2.setText(pref.getString("category2", "DEFAULT"));
        et3.setText(pref.getString("category3", "DEFAULT"));
        et4.setText(pref.getString("category4", "DEFAULT"));






    }









    @Override
    public void onBackPressed() {
        super.onBackPressed();
        pref.edit().putString("category1", et1.getText().toString()).apply();
        pref.edit().putString("category2", et2.getText().toString()).apply();
        pref.edit().putString("category3", et3.getText().toString()).apply();
        pref.edit().putString("category4", et4.getText().toString()).apply();

    }

    //    void setPrefColor(int colorNumber, int i){
//        switch (i){
//            case 0:
//                pref.edit().putInt("color"+colorNumber, getResources().getColor(R.color.Red)).apply();
//                break;
//            case 1:
//                pref.edit().putInt("color"+colorNumber, getResources().getColor(R.color.Blue)).apply();
//                break;
//            case 2:
//                pref.edit().putInt("color"+colorNumber, getResources().getColor(R.color.Green)).apply();
//                break;
//            case 3:
//                pref.edit().putInt("color"+colorNumber, getResources().getColor(R.color.Orange)).apply();
//                break;
//            case 4:
//                pref.edit().putInt("color"+colorNumber, getResources().getColor(R.color.Purple)).apply();
//                break;
//            case 5:
//                pref.edit().putInt("color"+colorNumber, getResources().getColor(R.color.Pink)).apply();
//                break;
//            case 6:
//                pref.edit().putInt("color"+colorNumber, getResources().getColor(R.color.Yellow)).apply();
//                break;
//            case -1:
//                pref.edit().putInt("color"+colorNumber, getResources().getColor(R.color.colorPrimaryDark)).apply();
//                break;
//        }
//    }

}
