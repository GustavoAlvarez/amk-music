package amktest.music.gustavo.amktest.Models;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by gustavoalvarez on 08/09/17.
 */

public class SharedPreferences {

    private static final String TAG = SharedPreferences.class.getSimpleName();

    public static final String ACCESS_TOKEN = "access_token";
    private static android.content.SharedPreferences sharedPreferences;

    public SharedPreferences(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getAccessToken() {
        return getValueString(ACCESS_TOKEN);
    }

    public void setValueString(String key, String value) {
        try {
            android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.apply();
        } catch (Exception e) {
            Log.e(TAG, "Error on 'setValueString'" + e.getMessage());
        }
    }

    public String getValueString(String key) {
        return sharedPreferences.getString(key, "");
    }

}
