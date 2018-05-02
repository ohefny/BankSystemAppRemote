package b.b.b.bankaccount.newapi;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by Elshaarawy-Touch on 11-Oct-17.
 */

public class PreferenceUtil {
    private Context context;
    private SharedPreferences sharedPreference;


    public PreferenceUtil(Context context) {
        this.context = context;
        this.sharedPreference = context.getSharedPreferences(DefaultKeys.DEFAULT_SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }

    public PreferenceUtil(Context context, String sharedPreferenceName) {
        this.context = context;
        this.sharedPreference = context.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE);
    }

    public boolean editValue(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(key, value);
        boolean back = editor.commit();
        return back;
    }

    public boolean editValue(String key, String value) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(key, value);
        boolean back = editor.commit();
        return back;
    }

    public boolean editValue(String key, float value) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putFloat(key, value);
        boolean back = editor.commit();
        return back;
    }

    public boolean editValue(String key, int value) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putInt(key, value);
        boolean back = editor.commit();
        return back;
    }

    public boolean editValue(String key, long value) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putLong(key, value);
        boolean back = editor.commit();
        return back;
    }

    public boolean getBoolean(String key) {
        return sharedPreference.getBoolean(key, false);
    }

    public String getString(String key) {
        return sharedPreference.getString(key, null);
    }

    public String getString(String key, String defValue) {
        return sharedPreference.getString(key, defValue);
    }

    public float getFloat(String key) {
        return sharedPreference.getFloat(key, 0.0f);
    }

    public int getInt(String key) {
        return sharedPreference.getInt(key, 0);
    }

    public long getLong(String key) {
        return sharedPreference.getLong(key, 0);
    }

    public Set<String> getStringSet(String key) {
        return sharedPreference.getStringSet(key, null);
    }


    public interface DefaultKeys {
        String DEFAULT_SHARED_PREFERENCE = "DefaultPreference";
        String PREFS_ACCOUNT_NUM = "account_num";
        String PREF_USER_PASS = "user_pass";
    }

}
