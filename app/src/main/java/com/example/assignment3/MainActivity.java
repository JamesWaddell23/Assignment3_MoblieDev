package com.example.assignment3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
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
// change color names
// on rotate it clears the color for edit activity
// limit size and stop SQL injection crap

public class MainActivity extends AppCompatActivity {
    ListView mListView;
    ArrayList<String> itemsArray = new ArrayList<String>();
    TextAdapter mTextAdapter;
    List<TextView> textViews = new ArrayList<TextView>();
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
            //find id
            tvh.mTime = convertView.findViewById(R.id.timeTextView);
            tvh.mName = convertView.findViewById(R.id.nameTextView);
            tvh.mLayout = convertView.findViewById(R.id.backgroundLayout);

            new AsyncTask<TextViewHolder, Void, String>(){
                private TextViewHolder tvh;
                @Override
                protected String doInBackground(TextViewHolder... params) {
                    tvh = params[0];
                    return ListItemDB.getTime(tvh.mPos);
                }
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    tvh.mTime.setText(s);
                }
            }.execute(tvh);
            new AsyncTask<TextViewHolder, Void, String>(){
                private TextViewHolder tvh;
                @Override
                protected String doInBackground(TextViewHolder... params) {
                    tvh = params[0];
                    return ListItemDB.getText(tvh.mPos);
                }
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    tvh.mName.setText(s);
                }
            }.execute(tvh);
            new AsyncTask<TextViewHolder, Void, Integer>(){
                private TextViewHolder tvh;
                @Override
                protected Integer doInBackground(TextViewHolder... params) {
                    tvh = params[0];
                    return ListItemDB.getColor(tvh.mPos);
                }
                @Override
                protected void onPostExecute(Integer s) {
                    super.onPostExecute(s);
                    tvh.mLayout.getBackground().setColorFilter(ListItemDB.getColor(tvh.mPos), PorterDuff.Mode.SRC_IN);
                }
            }.execute(tvh);
            //tvh.mTime.setText(ListItemDB.getTime(tvh.mPos));
//            tvh.mName.setText(ListItemDB.getText(tvh.mPos));
            //tvh.mLayout.getBackground().setColorFilter(ListItemDB.getColor(tvh.mPos), PorterDuff.Mode.SRC_IN);
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

    @SuppressLint("StaticFieldLeak")
    void initDB(){
        //THREAD WORK
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
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
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                mTextAdapter.notifyDataSetChanged();

            }
        }.execute();
        //new InitDatabase().execute(this);
    }



//    private static class InitDatabase extends AsyncTask<MainActivity, Void, TextAdapter>{
//        @Override
//        protected TextAdapter doInBackground(MainActivity... params) {
//            MainActivity mainActivity = params[0];
//            if(ListItemDB.getNumberOfRows() <= 0) {
//                for (int i = 0; i < 24; i++) {
//                    if(i == 0) {
//                        ListItemDB.commit(i, "", "12:00am", "", mainActivity.getResources().getColor(R.color.Empty));
//                    }else if(i < 12){
//                        ListItemDB.commit(i, "", i+":00am", "", mainActivity.getResources().getColor(R.color.Empty));
//                    } else if(i == 12){
//                        ListItemDB.commit(i, "", "12:00pm", "", mainActivity.getResources().getColor(R.color.Empty));
//                    }else{
//                        ListItemDB.commit(i, "", (i-12)+":00pm", "", mainActivity.getResources().getColor(R.color.Empty));
//                    }
//                }
//            }
//            return mainActivity.mTextAdapter;
//        }
//
//        @Override
//        protected void onPostExecute(TextAdapter result) {
//            super.onPostExecute(result);
//            result.notifyDataSetChanged();
//        }
//    }





//    private static class ClearDatabase extends AsyncTask<MainActivity, Void, TextAdapter>{
//        @Override
//        protected TextAdapter doInBackground(MainActivity... params) {
//            //String str = params[0];
//            MainActivity mainActivity = params[0];
//            for(int i =0; i<24; i++) {
//                ListItemDB.update(i,  "", ListItemDB.getTime(i), "", mainActivity.getResources().getColor(R.color.Empty));
//            }
//            return mainActivity.mTextAdapter;
//        }
//
//        @Override
//        protected void onPostExecute(TextAdapter result) {
//            super.onPostExecute(result);
//            // do something with result
//            result.notifyDataSetChanged();
//        }
//    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListItemDB.init(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        //maybe make arrays for these
        // set categories
        pref.edit().putString("category1", "Food").apply();
        pref.edit().putString("category2", "Work").apply();
        pref.edit().putString("category3", "Sport").apply();
        pref.edit().putString("category4", "Other").apply();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mListView = findViewById(R.id.listView);
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
//            editIntent.putExtra("time", ListItemDB.getTime(p));
//            editIntent.putExtra("color", ListItemDB.getColor(p));
//            editIntent.putExtra("text", ListItemDB.getText(p));
            editIntent.putExtra("pos", p);
            startActivity(editIntent);
        });



        System.out.println("number of rows = "+ListItemDB.getNumberOfRows());
    }

    void updateCategories(){
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
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(UpdateService.UPDATE);
//        LocalBroadcastManager.getInstance(this).registerReceiver(br,intentFilter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressLint("StaticFieldLeak")
    public void clearAll(){
        //THREAD WORK
       // Intent intent = new Intent(this, UpdateService.class);
        //startService(intent);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                for(int i =0; i<24; i++) {
                    ListItemDB.update(i,  "", ListItemDB.getTime(i), "", getResources().getColor(R.color.Empty));
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                mTextAdapter.notifyDataSetChanged();

            }
        }.execute();
        //new ClearDatabase().execute(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit) {
            Intent categoryIntent = new Intent(this, CategoryActivity.class);
            startActivity(categoryIntent);
            return true;
        }else if(id == R.id.action_clear){
            System.out.println("testing");
            new ClearAllDialog().show(getSupportFragmentManager(), null);
            return true;
        }else if(id == R.id.action_help){
            Intent helpIntent = new Intent(this, HelpActivity.class);
            startActivity(helpIntent);
        }else if(id == R.id.action_about){
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
