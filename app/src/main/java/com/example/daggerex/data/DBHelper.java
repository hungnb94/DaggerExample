package com.example.daggerex.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.daggerex.data.model.Hotgirl;
import com.example.daggerex.di.annotation.ApplicationContext;
import com.example.daggerex.di.annotation.DatabaseInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DBHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE_NAME = "hotgirls";
    public static final String USER_COLUMN_USER_ID = "id";
    public static final String USER_COLUMN_USER_NAME = "girl_name";
    public static final String USER_COLUMN_USER_AVATAR = "girl_avt";

    @Inject
    public DBHelper(@ApplicationContext Context context,
                    @DatabaseInfo String name,
                    @DatabaseInfo int version) {
        super(context, name, null, version);
    }

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + USER_TABLE_NAME + "("
            + USER_COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_COLUMN_USER_NAME + " VARCHAR(20), "
            + USER_COLUMN_USER_AVATAR + " VARCHAR(50))";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(db);
    }

    public Long insertUser(Hotgirl hotgirl) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(USER_COLUMN_USER_NAME, hotgirl.getName());
            values.put(USER_COLUMN_USER_AVATAR, hotgirl.getAvatar());
            return db.insert(USER_TABLE_NAME, null, values);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            if (db != null) db.close();
        }
    }

    public List<Hotgirl> getAll() throws Resources.NotFoundException, NullPointerException {
        Cursor cursor = null;
        List<Hotgirl> listGirl = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM " + USER_TABLE_NAME, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                int idIndex = cursor.getColumnIndex(USER_COLUMN_USER_ID);
                int nameIndex = cursor.getColumnIndex(USER_COLUMN_USER_NAME);
                int avatarIndex = cursor.getColumnIndex(USER_COLUMN_USER_AVATAR);
                do {
                    Hotgirl girl = new Hotgirl(cursor.getInt(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(avatarIndex));
                    listGirl.add(girl);
                } while (cursor.moveToNext());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (cursor != null) cursor.close();
        }
        return listGirl;
    }

    public Hotgirl findById(int id) throws NullPointerException {
        Cursor cursor = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT * FROM " + USER_TABLE_NAME
                    + " WHERE " + USER_COLUMN_USER_ID + " = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(USER_COLUMN_USER_ID);
                int nameIndex = cursor.getColumnIndex(USER_COLUMN_USER_NAME);
                int avatarIndex = cursor.getColumnIndex(USER_COLUMN_USER_AVATAR);
                return new Hotgirl(cursor.getInt(idIndex),
                        cursor.getString(nameIndex),
                        cursor.getString(avatarIndex));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (cursor != null) cursor.close();
        }
        return null;
    }

    public void clearDatabase() {
        getWritableDatabase().execSQL("DELETE FROM " + USER_TABLE_NAME);
    }
}
