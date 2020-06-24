package com.devta.unlu.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.devta.unlu.rest.data.response.Posts;

import java.util.ArrayList;

/**
 * Created on : Jun, 25, 2020 at 03:08
 * Author     : Divyanshu Tayal
 * Name       : devta-D
 * GitHub     : https://github.com/devta-D/
 * LinkedIn   : https://www.linkedin.com/in/divyanshu-tayal-4a95b2aa/
 */

public class FeedDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Feed.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + FeedContract.Entry.TABLE_NAME + " (" +
                    FeedContract.Entry._ID + " INTEGER PRIMARY KEY," +
                    FeedContract.Entry.COLUMN_NAME_ID + " TEXT," +
                    FeedContract.Entry.COLUMN_NAME_THUMBNAIL+ " TEXT," +
                    FeedContract.Entry.COLUMN_NAME_EVENT_NAME + " TEXT," +
                    FeedContract.Entry.COLUMN_NAME_EVENT_DATE + " INTEGER," +
                    FeedContract.Entry.COLUMN_NAME_EVENT_VIEWS + " INTEGER," +
                    FeedContract.Entry.COLUMN_NAME_EVENT_LIKES + " INTEGER," +
                    FeedContract.Entry.COLUMN_NAME_EVENT_SHARES + " INTEGER," +
                    FeedContract.Entry.COLUMN_NAME_PAGE_NUM + " INTEGER)";



    public FeedDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void refresh(Posts posts) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DELETE FROM " + FeedContract.Entry.TABLE_NAME);
        insertPosts(posts);
    }

    public void insertPosts (Posts posts) {
        if(posts == null || posts.getPosts() == null || posts.getPosts().isEmpty()) return;
        SQLiteDatabase db = this.getWritableDatabase();
        for(Posts.Post post : posts.getPosts()) {
            ContentValues contentValues = new ContentValues();
            //contentValues.put(FeedContract.Entry.COLUMN_NAME_ID, post.getId());
            contentValues.put(FeedContract.Entry.COLUMN_NAME_THUMBNAIL, post.getThumbnail_image());
            contentValues.put(FeedContract.Entry.COLUMN_NAME_EVENT_NAME, post.getEvent_name());
            contentValues.put(FeedContract.Entry.COLUMN_NAME_EVENT_DATE, post.getEvent_date());
            contentValues.put(FeedContract.Entry.COLUMN_NAME_EVENT_VIEWS, post.getViews());
            contentValues.put(FeedContract.Entry.COLUMN_NAME_EVENT_LIKES, post.getLikes());
            contentValues.put(FeedContract.Entry.COLUMN_NAME_EVENT_SHARES, post.getShares());
            contentValues.put(FeedContract.Entry.COLUMN_NAME_PAGE_NUM, posts.getPage());
            db.insertWithOnConflict(FeedContract.Entry.TABLE_NAME, null,
                    contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();
    }

    public Posts getPosts() {
        Posts postsData = new Posts();
        int maxPage = 0;
        ArrayList<Posts.Post> posts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + FeedContract.Entry.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){

            Posts.Post post = new Posts.Post();
            /*post.setId(cursor.getString(cursor.getColumnIndex(
                    FeedContract.Entry.COLUMN_NAME_ID)));*/
            post.setThumbnail_image(cursor.getString(cursor.getColumnIndex(
                    FeedContract.Entry.COLUMN_NAME_THUMBNAIL)));
            post.setEvent_name(cursor.getString(cursor.getColumnIndex(
                    FeedContract.Entry.COLUMN_NAME_EVENT_NAME)));
            post.setEvent_date(cursor.getInt(cursor.getColumnIndex(
                    FeedContract.Entry.COLUMN_NAME_EVENT_DATE)));
            post.setViews(cursor.getInt(cursor.getColumnIndex(
                    FeedContract.Entry.COLUMN_NAME_EVENT_VIEWS)));
            post.setLikes(cursor.getInt(cursor.getColumnIndex(
                    FeedContract.Entry.COLUMN_NAME_EVENT_LIKES)));
            post.setShares(cursor.getInt(cursor.getColumnIndex(
                    FeedContract.Entry.COLUMN_NAME_EVENT_SHARES)));
            maxPage = cursor.getInt(cursor.getColumnIndex(
                    FeedContract.Entry.COLUMN_NAME_PAGE_NUM));

            posts.add(post);
            cursor.moveToNext();
        }
        postsData.setPosts(posts);
        postsData.setPage(maxPage);
        cursor.close();
        return postsData;
    }
}
