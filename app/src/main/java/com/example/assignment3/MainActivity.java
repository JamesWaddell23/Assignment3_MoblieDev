package com.example.assignment3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView mListView;
    //ArrayAdapter<String> mAdapter;
    ArrayList<String> itemsArray = new ArrayList<String>();
    TextAdapter mTextAdapter;
    List<String> listOfNames = new ArrayList<String>();
    List<Integer> listOfColors = new ArrayList<Integer>();
    List<String> listOfTimes = new ArrayList<String>();

    private class TextAdapter extends ArrayAdapter<String>{
        //public TextViewHolder holder;
        class TextViewHolder{
            TextView mTime, mName;
            ConstraintLayout mLayout;
            //View mView;
            int mPos;
        }
        //this might not be needed
        public TextAdapter(Context context, int r1, int r2, List<String> list) {
            super(context, r1, r2, list);
        }

        //change back to add once color is set up
        public void addNameAndColor(String object, int color) {
            super.add(object);
            listOfNames.add(object);
            listOfColors.add(color);

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
            tvh.mTime.setText(listOfTimes.get(tvh.mPos));
            tvh.mName.setText(listOfNames.get(tvh.mPos));
            tvh.mLayout.getBackground().setColorFilter(listOfColors.get(tvh.mPos), PorterDuff.Mode.SRC_IN);
            return convertView;
        }

    }
    public void init(){

        //mAdapter = new ArrayAdapter<>(this, R.layout.color_listitem, R.id.itemTextView, itemsArray);
        mTextAdapter = new TextAdapter(this, R.layout.color_listitem, R.id.timeTextView, itemsArray);
        mListView.setAdapter(mTextAdapter);
    }

    public void initTime(){
        listOfTimes.add("12:00 am");
        for(int i=1; i<12; i++) {
            listOfTimes.add(i+":00 am");
        }
        listOfTimes.add("12:00 pm");
        for(int i = 1; i<12; i++) {
            listOfTimes.add(i+":00 pm");
        }

        //sample stuff [CHANGE ME]
        mTextAdapter.addNameAndColor("Snack with Bob", getResources().getColor(R.color.Food));
        for(int i=1; i<12; i++){
            mTextAdapter.addNameAndColor(i+":Meeting with Joe", getResources().getColor(R.color.Work));
        }
        mTextAdapter.addNameAndColor("Game with Steve", getResources().getColor(R.color.Other));
        for(int i=1; i<12; i++){
            mTextAdapter.addNameAndColor(i+":Football with Jeff", getResources().getColor(R.color.Sport));
        }

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mListView = findViewById(R.id.listView);
        init();
        //FloatingActionButton fab = findViewById(R.id.actionButton);
        final TextView t = findViewById(R.id.timeTextView);
        initTime();

        mListView.setOnItemClickListener((a,v, p, i)->{
            Intent dialog = new Intent(this, Dialog.class);
            dialog.putExtra("time", listOfTimes.get(p));
            dialog.putExtra("color", listOfColors.get(p));
            dialog.putExtra("text", listOfNames.get(p));
            startActivity(dialog);


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
