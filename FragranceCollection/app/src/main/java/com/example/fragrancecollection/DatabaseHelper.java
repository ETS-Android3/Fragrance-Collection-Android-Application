package com.example.fragrancecollection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FragranceCollectionDb";
    private static final String USER_TABLE = "user";
    private static final String FRAGRANCE_TABLE = "fragrance";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_FRAGRANCE_ID = "fragrance_id";
    private static final String COLUMN_FRAGRANCE_USERID = "user_id";
    private static final String COLUMN_FRAGRANCE_NAME = "name";
    private static final String COLUMN_FRAGRANCE_PERFUMER = "perfumer";
    private static final String COLUMN_FRAGRANCE_RELEASE_YEAR = "release_year";
    private static final String COLUMN_FRAGRANCE_NOTES = "notes";
    private static final String COLUMN_FRAGRANCE_IMAGE = "image";



    private static final String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE + "(" +
            COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_USER_NAME + " TEXT," +
            COLUMN_USER_EMAIL + " TEXT," +
            COLUMN_USER_PASSWORD + " TEXT" + ");";

    private static final String CREATE_FRAGRANCE_TABLE = "CREATE TABLE " + FRAGRANCE_TABLE + "(" +
            COLUMN_FRAGRANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_FRAGRANCE_USERID + " INTEGER," +
            COLUMN_FRAGRANCE_NAME + " TEXT," +
            COLUMN_FRAGRANCE_PERFUMER + " TEXT," +
            COLUMN_FRAGRANCE_RELEASE_YEAR + " INTEGER," +
            COLUMN_FRAGRANCE_NOTES + " TEXT," +
            COLUMN_FRAGRANCE_IMAGE + " BLOB, " +
            "FOREIGN KEY(" + COLUMN_FRAGRANCE_USERID + ") REFERENCES " + USER_TABLE + "(" + COLUMN_USER_ID + "));";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_FRAGRANCE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FRAGRANCE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);

        onCreate(sqLiteDatabase);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        db.insert(USER_TABLE, null, values);
        db.close();
    }

    public boolean checkUserExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COLUMN_USER_ID};
        String selection = COLUMN_USER_EMAIL + " =?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(USER_TABLE, columns, selection, selectionArgs,
                null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0)
            return true;

        return false;
    }

    public int checkUserExists(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COLUMN_USER_ID};
        String selection = COLUMN_USER_EMAIL + " =?" + " AND " + COLUMN_USER_PASSWORD + " =?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(USER_TABLE, columns, selection, selectionArgs,
                null, null, null);

        int cursorCount = cursor.getCount();

        if (cursorCount > 0) {
            cursor.moveToNext();
            int currentLoggedUserId = cursor.getInt(0);
            cursor.close();
            db.close();
            return currentLoggedUserId;
        }

        cursor.close();
        db.close();
        return 0;
    }

    public void addFragrance(Fragrance fragrance) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FRAGRANCE_USERID, fragrance.getUserId());
        values.put(COLUMN_FRAGRANCE_NAME, fragrance.getName());
        values.put(COLUMN_FRAGRANCE_PERFUMER, fragrance.getPerfumer());
        values.put(COLUMN_FRAGRANCE_RELEASE_YEAR, fragrance.getReleaseYear());
        values.put(COLUMN_FRAGRANCE_NOTES, fragrance.getNotes());
        values.put(COLUMN_FRAGRANCE_IMAGE, fragrance.getImage());

        db.insert(FRAGRANCE_TABLE, null, values);
        db.close();
    }

    public ArrayList<Fragrance> getAllFragrances(int userId) {
        SQLiteDatabase db = getReadableDatabase();

        String selection = COLUMN_FRAGRANCE_USERID + " =?";
        String loggedUserId = userId + "";
        String[] selectionArgs = {loggedUserId};

        Cursor cursor = db.query(FRAGRANCE_TABLE, null, selection, selectionArgs, null, null, null);
        ArrayList<Fragrance> fragrances = new ArrayList<Fragrance>();
        Fragrance fragrance;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                fragrance = new Fragrance();
                fragrance.setFragranceId(cursor.getInt(0));
                fragrance.setUserId(cursor.getInt(1));
                fragrance.setName(cursor.getString(2));
                fragrance.setPerfumer(cursor.getString(3));
                fragrance.setReleaseYear(cursor.getInt(4));
                fragrance.setNotes(cursor.getString(5));
                fragrance.setImage(cursor.getBlob(6));
                fragrances.add(fragrance);
            }
        }
        cursor.close();
        db.close();
        return fragrances;
    }

    public void updateFragrance(Fragrance fragrance) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FRAGRANCE_ID, fragrance.getFragranceId());
        values.put(COLUMN_FRAGRANCE_USERID, fragrance.getUserId());
        values.put(COLUMN_FRAGRANCE_NAME, fragrance.getName());
        values.put(COLUMN_FRAGRANCE_PERFUMER, fragrance.getPerfumer());
        values.put(COLUMN_FRAGRANCE_RELEASE_YEAR, fragrance.getReleaseYear());
        values.put(COLUMN_FRAGRANCE_NOTES, fragrance.getNotes());
        values.put(COLUMN_FRAGRANCE_IMAGE, fragrance.getImage());

        db.update(FRAGRANCE_TABLE, values, "fragrance_id = ?",
                new String[]{String.valueOf(fragrance.getFragranceId())});
        db.close();

    }

    public void deleteFragrance(Fragrance fragrance) {
        SQLiteDatabase db = getWritableDatabase();
        String queryString = "DELETE FROM " + FRAGRANCE_TABLE + " WHERE " + COLUMN_FRAGRANCE_ID + " = "
             + fragrance.getFragranceId();
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
        cursor.close();
        db.close();
    }
}
