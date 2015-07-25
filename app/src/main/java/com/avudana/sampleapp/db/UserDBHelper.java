package com.avudana.sampleapp.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.avudana.sampleapp.utilities.Logger;

public class UserDBHelper extends SQLiteOpenHelper {

    private static final String TAG = UserDBHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "User.db";

    private static final int DATABASE_VERSION = 1;

    public static final String USER_TABLE = "UserDB";

    public static final String COLUMN_USER_NAME = "USER_NAME";

    public static final String COLUMN_PASSWORD = "PASSWORD";

    public static final String COLUMN_PHONE_NO = "PHONE_NO";

    public static final String COLUMN_GENDER = "GENDER";

    private static final String CREATE_DB_STATEMENT = "create table " + USER_TABLE + "( "
            + COLUMN_USER_NAME + " TEXT PRIMARY KEY  NOT NULL, "
            + COLUMN_PASSWORD + " TEXT NOT NULL, "
            + COLUMN_GENDER + " TEXT NOT NULL, "
            + COLUMN_PHONE_NO + " TEXT NOT NULL);";

    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_DB_STATEMENT);
        } catch (SQLException e) {
            Logger.e(TAG, "SQLException", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Logger.i(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);

    }

}
