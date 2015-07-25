package com.avudana.sampleapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.avudana.sampleapp.models.User;
import com.avudana.sampleapp.utilities.Logger;


public class UserDBDao {

    private static final String TAG = UserDBDao.class.getSimpleName();
    private UserDBHelper dbHelper;

    public UserDBDao(Context ctxt) {

        dbHelper = new UserDBHelper(ctxt);
    }

    public long insertUser(User user) {

        SQLiteDatabase sqlDbWrite = null;
        long insertedId = -1;
        try {
            sqlDbWrite = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(UserDBHelper.COLUMN_USER_NAME, user.getUserName());
            values.put(UserDBHelper.COLUMN_GENDER, user.getGender());
            values.put(UserDBHelper.COLUMN_PHONE_NO, user.getPhoneNo());
            values.put(UserDBHelper.COLUMN_PASSWORD, user.getPassword());

            insertedId = sqlDbWrite.insert(UserDBHelper.USER_TABLE, null, values);

            Logger.i(TAG, "Inserted Id = " + insertedId);

        } catch (SQLException e) {
            Logger.e(TAG, "SQLException", e);
        } finally {
            if (sqlDbWrite != null) {
                sqlDbWrite.close();
            }
        }

        return insertedId;
    }

    public boolean authenticateUser(String userName, String password) {
        User user = new User();
        SQLiteDatabase sqlDbRead = null;
        Cursor cursor = null;
        String passwordFromDb = "";
        try {
            sqlDbRead = dbHelper.getReadableDatabase();

            cursor = sqlDbRead.query(UserDBHelper.USER_TABLE,
                    new String[]{UserDBHelper.COLUMN_PASSWORD}, UserDBHelper.COLUMN_USER_NAME + "= ?", new String[]{userName}, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    passwordFromDb = cursor.getString(cursor.getColumnIndex(UserDBHelper.COLUMN_PASSWORD));
                }
            }
        } catch (SQLException e) {
            Logger.e(TAG, "SQLException", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }

            if (sqlDbRead != null) {
                sqlDbRead.close();
            }
        }

        return password.equals(passwordFromDb);
    }


    public User getUserDetails(String userName) {
        User user = new User();
        SQLiteDatabase sqlDbRead = null;
        Cursor cursor = null;
        try {
            sqlDbRead = dbHelper.getReadableDatabase();

            cursor = sqlDbRead.query(UserDBHelper.USER_TABLE,
                    null, UserDBHelper.COLUMN_USER_NAME + "= ?", new String[]{userName}, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    convertCursorToUserBean(cursor, user);
                }
            }
        } catch (SQLException e) {
            Logger.e(TAG, "SQLException", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }

            if (sqlDbRead != null) {
                sqlDbRead.close();
            }
        }

        return user;
    }

    private void convertCursorToUserBean(Cursor cursor, User user) {
        user.setUserName(cursor.getString(cursor.getColumnIndex(UserDBHelper.COLUMN_USER_NAME)));
        user.setGender(cursor.getString(cursor.getColumnIndex(UserDBHelper.COLUMN_GENDER)));
        user.setPhoneNo(cursor.getString(cursor.getColumnIndex(UserDBHelper.COLUMN_PHONE_NO)));
    }

}
