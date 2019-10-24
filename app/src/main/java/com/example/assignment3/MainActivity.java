package com.example.assignment3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
// TODO
//change color names
// put data base stuff on separate thread
// on rotate it clears the color for edit activity
// limit size and stop SQL injection crap

public class MainActivity extends AppCompatActivity {
    ListView mListView;
    ArrayList<String> itemsArray = new ArrayList<String>();
    TextAdapter mTextAdapter;
    List<TextView> textViews = new ArrayList<TextView>();
    List<ImageView> images = new ArrayList<ImageView>();

    //maybe make a class for these
//    List<String> listOfNames = new ArrayList<String>();
//    List<Integer> listOfColors = new ArrayList<Integer>();
//    List<String> listOfTimes = new ArrayList<String>();

    SharedPreferences pref;



    private class TextAdapter extends ArrayAdapter<String>{
        class TextViewHolder{
            TextView mTime, mName;
            ConstraintLayout mLayout;
            int mPos;
        }
        //this might not be needed
        public TextAdapter(Context context, int r1, int r2, List<String> list) {
            super(context, r1, r2, list);
        }

        @Override
        public void add(String object){
            super.add(object);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @SuppressLint({"StaticFieldLeak", "ViewHolder"})
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextViewHolder tvh;
            convertView = getLayoutInflater().inflate(R.layout.color_listitem, parent, false);
            tvh = new TextViewHolder();
            tvh.mPos = position;
            tvh.mTime = convertView.findViewById(R.id.timeTextView);
            tvh.mName = convertView.findViewById(R.id.nameTextView);
            tvh.mLayout = convertView.findViewById(R.id.backgroundLayout);
            //database way
            tvh.mTime.setText(ListItemDB.getTime(tvh.mPos));
            tvh.mName.setText(ListItemDB.getText(tvh.mPos));
            tvh.mLayout.getBackground().setColorFilter(ListItemDB.getColor(tvh.mPos), PorterDuff.Mode.SRC_IN);

            return convertView;
        }

    }
    public void init(){
        mTextAdapter = new TextAdapter(this, R.layout.color_listitem, R.id.timeTextView, itemsArray);
        mListView.setAdapter(mTextAdapter);

    }

    public void initList(){
        for (int i = 0; i < 24; i++) {
            mTextAdapter.add("");
        }
    }

    void initDB(){
        // first time set up
        if(ListItemDB.getNumberOfRows() <= 0) {
            for (int i = 0; i < 24; i++) {
                if(i == 0) {
                    ListItemDB.commit(i, "", "12:00am", "", getResources().getColor(R.color.Empty));
                }else if(i < 12){
                    ListItemDB.commit(i, "", i+":00am", "", getResources().getColor(R.color.Empty));
                } else if(i == 12){
                    ListItemDB.commit(i, "", "12:00pm", "", getResources().getColor(R.color.Empty));
                }else{
                    ListItemDB.commit(i, "", (i-12)+":00pm", "", getResources().getColor(R.color.Empty));
                }
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListItemDB.init(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        //maybe make arrays for these
        // set colors
//        pref.edit().putInt("color1", getResources().getColor(R.color.Orange)).apply();
//        pref.edit().putInt("color2", getResources().getColor(R.color.Blue)).apply();
//        pref.edit().putInt("color3", getResources().getColor(R.color.Purple)).apply();
//        pref.edit().putInt("color4", getResources().getColor(R.color.Green)).apply();
        // set categories
        pref.edit().putString("category1", "Food").apply();
        pref.edit().putString("category2", "Work").apply();
        pref.edit().putString("category3", "Sport").apply();
        pref.edit().putString("category4", "Other").apply();




        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mListView = findViewById(R.id.listView);
        images.add(findViewById(R.id.icon1));
        images.add(findViewById(R.id.icon2));
        images.add(findViewById(R.id.icon3));
        images.add(findViewById(R.id.icon4));

        textViews.add(findViewById(R.id.category1));
        textViews.add(findViewById(R.id.category2));
        textViews.add(findViewById(R.id.category3));
        textViews.add(findViewById(R.id.category4));
        updateCategories();

        init();
        initDB();
        initList();
        mListView.setOnItemClickListener((a,v, p, i)->{
            Intent editIntent = new Intent(this, EditActivity.class);
            editIntent.putExtra("time", ListItemDB.getTime(p));
            editIntent.putExtra("color", ListItemDB.getColor(p));
            editIntent.putExtra("text", ListItemDB.getText(p));
            editIntent.putExtra("pos", p);
            startActivity(editIntent);
        });



        System.out.println("number of rows = "+ListItemDB.getNumberOfRows());
    }

    void updateCategories(){
        //set colors
        images.get(0).setColorFilter(pref.getInt("color1", 8));
        images.get(1).setColorFilter(pref.getInt("color2", 8));
        images.get(2).setColorFilter(pref.getInt("color3", 8));
        images.get(3).setColorFilter(pref.getInt("color4", 8));

        //set categories
        textViews.get(0).setText(pref.getString("category1", "DEFAULT"));
        textViews.get(1).setText(pref.getString("category2", "DEFAULT"));
        textViews.get(2).setText(pref.getString("category3", "DEFAULT"));
        textViews.get(3).setText(pref.getString("category4", "DEFAULT"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume() called.");
        updateCategories();
        mTextAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void clearAll(){
        // just for testing purposes [change me]
        for(int i =0; i<24; i++) {
            //ListItemDB.setColor(i, getResources().getColor(R.color.Empty));
            ListItemDB.update(i,  "", ListItemDB.getTime(i), "", getResources().getColor(R.color.Empty));
        }
        mTextAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            Intent settingsIntent = new Intent(this, CategoryActivity.class);
            startActivity(settingsIntent);
            return true;
        }else if(id == R.id.action_clear){
            System.out.println("testing");
            new ClearAllDialog().show(getSupportFragmentManager(), null);
            return true;
        }else if(id == R.id.action_help){

        }else if(id == R.id.action_about){

        }

        return super.onOptionsItemSelected(item);
    }
}
