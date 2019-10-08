package com.example.assignment3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
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

    private class TextAdapter extends ArrayAdapter<String>{
        //public TextViewHolder holder;
        class TextViewHolder{
            TextView mTextView;
            int mPos;
        }
        public TextAdapter(Context context, int r1, int r2, List<String> list) {
            super(context, r1, r2, list);
        }


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
            tvh.mTextView = convertView.findViewById(R.id.itemTextView);
            tvh.mTextView.setText(listOfNames.get(tvh.mPos));
            tvh.mTextView.setBackgroundColor(listOfColors.get(tvh.mPos));
            return convertView;
        }

    }
    public void init(){

        //mAdapter = new ArrayAdapter<>(this, R.layout.color_listitem, R.id.itemTextView, itemsArray);
        mTextAdapter = new TextAdapter(this, R.layout.color_listitem, R.id.itemTextView, itemsArray);
        mListView.setAdapter(mTextAdapter);
    }

    public void initTime(){
        mTextAdapter.addNameAndColor("12:00am", getResources().getColor(R.color.Food));
        for(int i=1; i<12; i++){
            mTextAdapter.addNameAndColor(i+":00am", getResources().getColor(R.color.Work));
        }
        mTextAdapter.addNameAndColor("12:00pm", getResources().getColor(R.color.Other));
        for(int i=1; i<12; i++){
            mTextAdapter.addNameAndColor(i+":00pm", getResources().getColor(R.color.Sport));
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
        FloatingActionButton fab = findViewById(R.id.actionButton);
        final TextView t = findViewById(R.id.itemTextView);
        initTime();

        mListView.setOnItemClickListener((a,v, p, i)->{
            //listOfColors.set(p, Color.WHITE);
            //mTextAdapter.notifyDataSetChanged();
            Intent dialog = new Intent(this, Dialog.class);
            //System.out.println("testing: "+listOfColors.get(p));
            dialog.putExtra("color", listOfColors.get(p));
            dialog.putExtra("text", listOfNames.get(p));
            startActivity(dialog);


        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextAdapter.add("testing");
            }
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
