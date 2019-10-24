package com.example.assignment3;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;


// NEED TO REMOVE CATEGORIES
public class ListItemDB {
    private static SQLiteDatabase dbInstance;
    private ListItemDB(){}


    // POS INT
    // TEXT 100 CHARS
    // TIME 10 CHARS
    // CATEGORY 10 CHARS
    // COLOR INT

    public static void init(Context context){
        if(dbInstance == null) {
            dbInstance = context.openOrCreateDatabase("Items.db", Context.MODE_PRIVATE, null);
            //resize stuff
            dbInstance.execSQL("CREATE TABLE IF NOT EXISTS ITEM(" +
                    "I_POS INTEGER," +
                    "I_TEXT varchar2(100)," +
                    "I_TIME varchar2(10)," +
                    "I_CATEGORY varchar2(10),"+
                    "I_COLOR INTEGER)");
        }
    }

    public static void commit(int pos, String text, String time, String category, int color){
        if(dbInstance != null){
            dbInstance.execSQL("INSERT INTO ITEM VALUES('"+pos+"', '"+text+"', '"+ time+"','"+ category+"', '"+color+"')");
        }
    }
    public static void update(int pos, String text, String time, String category, int color){
        if(dbInstance != null) {
            dbInstance.execSQL("UPDATE ITEM SET I_TEXT ='" + text + "', I_TIME = '" + time + "', I_CATEGORY = '" + category + "', I_COLOR = " + color + " WHERE I_POS = " + pos);
        }
    }
    public static void drop(int pos){
        if(dbInstance != null){
            dbInstance.execSQL("DROP FROM ITEM WHERE I_POS ="+pos);
        }
    }


    public static void setTime(int pos, String time){
        if(dbInstance != null) {
            dbInstance.execSQL("UPDATE ITEM SET I_TIME = '" + time + "' WHERE I_POS = " + pos);
        }
    }

    public static void setText(int pos, String text){
        if(dbInstance != null) {
            dbInstance.execSQL("UPDATE ITEM SET I_TEXT = '" + text + "' WHERE I_POS = " + pos);
        }
    }

    public static void setColor(int pos, int color){
        if(dbInstance != null) {
            dbInstance.execSQL("UPDATE ITEM SET I_COLOR = '" + color + "' WHERE I_POS = " + pos);
        }
    }




    public static int getColor(int pos){
        if(dbInstance != null) {
            String query = "SELECT I_COLOR FROM ITEM WHERE I_POS =  "+pos;
            Cursor c = dbInstance.rawQuery(query, null);
            if(c.getCount() > 0){
                c.moveToFirst();
                return c.getInt(0);
            }
        }
        return -1;
    }

    public static String getText(int pos){
        if(dbInstance != null) {
            String query = "SELECT I_TEXT FROM ITEM WHERE I_POS =  "+pos;
            Cursor c = dbInstance.rawQuery(query, null);
            if(c.getCount() > 0){
                c.moveToFirst();
                return c.getString(0);
            }
        }
        return null;
    }

    public static String getTime(int pos){
        if(dbInstance != null) {
            String query = "SELECT I_TIME FROM ITEM WHERE I_POS =  "+pos;
            Cursor c = dbInstance.rawQuery(query, null);
            if(c.getCount() > 0){
                c.moveToFirst();
                return c.getString(0);
            }
        }
        return null;
    }

    public static int getNumberOfRows(){
        if(dbInstance != null){
            int num = (int)DatabaseUtils.longForQuery(dbInstance, "SELECT COUNT(*) FROM ITEM", null);
            return num;
        }
        return -1;

    }



}
