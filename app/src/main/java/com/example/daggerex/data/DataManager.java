package com.example.daggerex.data;

import android.content.res.Resources;
import com.example.daggerex.data.model.Hotgirl;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class DataManager {
    private DBHelper dbHelper;
    private SharePrefsHelper sharePrefsHelper;

    @Inject
    public DataManager(DBHelper dbHelper, SharePrefsHelper sharePrefsHelper) {
        this.dbHelper = dbHelper;
        this.sharePrefsHelper = sharePrefsHelper;
    }

    public Long add(Hotgirl hotgirl) {
        return dbHelper.insertUser(hotgirl);
    }

    public List<Hotgirl> getAll () throws Resources.NotFoundException, NullPointerException {
        return dbHelper.getAll();
    }

    public Hotgirl findById (int id) throws NullPointerException {
        return dbHelper.findById(id);
    }

    public void clearDatabase() {
        dbHelper.clearDatabase();
    }

    public void saveAccessToken(String accessToken) {
        sharePrefsHelper.put(SharePrefsHelper.PREF_KEY_ACCESS_TOKEN, accessToken);
    }

    public String getAccessToken() {
        return sharePrefsHelper.get(SharePrefsHelper.PREF_KEY_ACCESS_TOKEN, null);
    }
}
