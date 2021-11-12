package com.example.fragrancecollection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
            COLUMN_FRAGRANCE_RELEASE_YEAR + " TEXT," +
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

    public boolean checkUserExists(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COLUMN_USER_ID};
        String selection = COLUMN_USER_EMAIL + " =?" + " AND " + COLUMN_USER_PASSWORD + " =?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(USER_TABLE, columns, selection, selectionArgs,
                null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0)
            return true;

        return false;
    }
}
