package com.example.task81c.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.task81c.model.User;
import com.example.task81c.model.Video;
import com.example.task81c.util.Util;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE = "CREATE TABLE " + Util.USER_TABLE_NAME + "(" + Util.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + Util.USER_NAME + " TEXT , " + Util.USER_FULL_NAME + " TEXT , " + Util.USER_PASSWORD + " TEXT)";

        sqLiteDatabase.execSQL(CREATE_USER_TABLE);

        String CREATE_VIDEO_TABLE = "CREATE TABLE " + Util.VIDEO_TABLE_NAME + "(" + Util.VIDEO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + Util.VIDEO_URL + " TEXT , " + Util.USER_ID + " INTEGER, FOREIGN KEY (" + Util.USER_ID + ") REFERENCES " + Util.USER_TABLE_NAME
                + "(" + Util.VIDEO_ID + ") )";

        sqLiteDatabase.execSQL(CREATE_VIDEO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_TABLE = "DROP TABLE IF EXISTS";

        sqLiteDatabase.execSQL(DROP_TABLE, new String[]{Util.VIDEO_TABLE_NAME});
        sqLiteDatabase.execSQL(DROP_TABLE, new String[]{Util.USER_TABLE_NAME});

        onCreate(sqLiteDatabase);
    }

    public long InsertUser (User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.USER_FULL_NAME, user.getUserFullName());
        contentValues.put(Util.USER_NAME, user.getUserName());
        contentValues.put(Util.USER_PASSWORD, user.getUserPassword());

        long newRowId = db.insert(Util.USER_TABLE_NAME, null, contentValues);
        db.close();
        return newRowId;
    }

    public long InsertVideo (Video video)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.USER_ID, video.getUserId());
        contentValues.put(Util.VIDEO_URL, video.getVideoURL());

        long newRowId = db.insert(Util.VIDEO_TABLE_NAME, null, contentValues);
        db.close();
        return newRowId;
    }

    public boolean UserExists(String userName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.USER_TABLE_NAME, new String[]{Util.USER_ID}, Util.USER_NAME + "=?",
                new String[]{userName}, null, null, null);

        int numberOfRows = cursor.getCount();

        db.close();

        if(numberOfRows > 0)
        {
            return true;
        }

        else
        {
            return false;
        }
    }

    public boolean VideoExists(String url, int userId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.VIDEO_TABLE_NAME, new String[]{Util.VIDEO_ID}, Util.VIDEO_URL + "=? AND "
                        + Util.USER_ID + "=?",
                new String[]{url, String.valueOf(userId)}, null, null, null);

        int numberOfRows = cursor.getCount();

        db.close();

        if(numberOfRows > 0)
        {
            return true;
        }

        else
        {
            return false;
        }
    }


    public Integer GetUser(String userName, String userPassword)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.USER_TABLE_NAME, new String[]{Util.USER_ID}, Util.USER_NAME + "=? and " + Util.USER_PASSWORD + "=?",
                new String[]{userName, userPassword}, null, null, null);

        Integer userId = null;
        if(cursor.moveToFirst())
        {
            userId = cursor.getInt(0);
        }

        cursor.close();
        db.close();
        return userId;
    }

    public ArrayList<Video> GetVideos(int userId){

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + Util.VIDEO_ID + ", " + Util.USER_ID + ", " + Util.VIDEO_URL + " FROM " + Util.VIDEO_TABLE_NAME + " WHERE " + Util.USER_ID + " =?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        ArrayList<Video> videos = new ArrayList<Video>();
        while(cursor.moveToNext()){
            Video video = new Video(cursor.getInt(0), cursor.getInt(1), cursor.getString(2));
            videos.add(video);
        }
        cursor.close();
        db.close();
        return videos;
    }
}
