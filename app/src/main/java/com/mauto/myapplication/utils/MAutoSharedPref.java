package com.mauto.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mauto.myapplication.home.model.PaymentMethod;
import com.mauto.myapplication.home.model.SwapLocationData;
import com.mauto.myapplication.login.model.Profile;
import com.mauto.myapplication.swapingHistory.model.SwapingArrayResponse;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public class MAutoSharedPref {
    @Nullable
    private static MAutoSharedPref appPrefrence = null;
    @NonNull
    private final Context mContext;
    private Gson gson;
    @Nullable
    private SharedPreferences preferences = null;

    private MAutoSharedPref(@NonNull Context context) {
        preferences =
                context.getSharedPreferences("MAUTO_SALES_SHARED_PREF", Context.MODE_PRIVATE);
        this.mContext = context;
    }

    public static MAutoSharedPref getAppPrefs(@NonNull Context context) {
        synchronized (MAutoSharedPref.class) {
            if (appPrefrence == null) appPrefrence = new MAutoSharedPref(context);
        }

        return appPrefrence;
    }

    public static MAutoSharedPref getStactionList(@NonNull Context context) {
        synchronized (MAutoSharedPref.class) {
            if (appPrefrence == null) appPrefrence = new MAutoSharedPref(context);
        }

        return appPrefrence;
    }

    public void saveStringValue(@NonNull String key, String value) {
        SharedPreferences.Editor editor = Objects.requireNonNull(preferences).edit();
        editor.putString(key, value);
        editor.apply();

    }

    public void savestactionid(@NonNull String key, String value) {
        SharedPreferences.Editor editor = Objects.requireNonNull(preferences).edit();
        editor.putString(key, value);
        editor.apply();

    }


    public void saveStringSet(@NonNull String key, Set<String> setValue) {
        SharedPreferences.Editor editor = Objects.requireNonNull(preferences).edit();
        editor.putStringSet(key, setValue);
        editor.apply();
    }


    public Set<String> getStringSet(@NonNull String key) {
        return Objects.requireNonNull(preferences).getStringSet(key, null);
    }

    public int getIntValue(String key) {
        return Objects.requireNonNull(preferences).getInt(key, -1);
    }

    public void saveIntValue(String key, int value) {
        SharedPreferences.Editor editor = Objects.requireNonNull(preferences).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void saveLongValue(String key, Long value) {
        SharedPreferences.Editor editor = Objects.requireNonNull(preferences).edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public long getLongValue(String key) {
        return Objects.requireNonNull(preferences).getLong(key, -1);
    }

    public void putDouble(final String key, final double value) {
        SharedPreferences.Editor editor = Objects.requireNonNull(preferences).edit();
        editor.putLong(key, Double.doubleToRawLongBits(value));
        editor.apply();
    }

    public double getDouble(final String key, final double defaultValue) {
        return Double.longBitsToDouble(Objects.requireNonNull(preferences).getLong(key, Double.doubleToLongBits(defaultValue)));
    }

    public String getStringValue(String key) {
        if (null == preferences)
            return "";
        return preferences.getString(key, "");
    }

    public void saveBooleanValue(String key, boolean value) {
        SharedPreferences.Editor editor = Objects.requireNonNull(preferences).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBooleanValue(String key) {
        return Objects.requireNonNull(preferences).getBoolean(key, false);
    }

    private Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    //To save Hash Map
    public void saveMap(Map<String, String> inputMap, String key) {

        if (preferences != null && inputMap != null) {
            String hashMapString = getGson().toJson(inputMap);
            //save in shared prefs
            preferences.edit().putString(key, hashMapString).apply();
        }
    }

    public Map<String, String> getMap(String key) {
        Map<String, String> outputMap = new HashMap<>();
        try {
            //get from shared prefs
            String storedHashMapString = preferences.getString(key, (new JSONObject()).toString());
            java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>() {
            }.getType();
            return getGson().fromJson(storedHashMapString, type);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputMap;
    }


    public void saveArrayList(List<String> list, String key) {
        if (list != null) {
            SharedPreferences.Editor editor = Objects.requireNonNull(preferences).edit();
            String json = getGson().toJson(list);
            editor.putString(key, json);
            editor.apply();
        }
    }

    public ArrayList<String> getArrayList(String key) {
        String json = preferences.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return getGson().fromJson(json, type);
    }



    public void saveProfileInfo(Profile modelList) {
        if(modelList != null) {
            SharedPreferences.Editor editor = Objects.requireNonNull(preferences).edit();
            String json = getGson().toJson(modelList);
            editor.putString("PROFILE_DETAILS", json);
            editor.apply();
        }
    }

    @Nullable public Profile getProfileInfo() {
        String json = Objects.requireNonNull(preferences).getString("PROFILE_DETAILS", "");
        if (TextUtils.isEmpty(json) ) {
            return null;
        } else {
            Type founderListType = new TypeToken<Profile>(){}.getType();

            return getGson().fromJson(json, founderListType);
        }
    }


    public void savePayType(ArrayList<PaymentMethod> modelList) {
        if(modelList != null) {
            SharedPreferences.Editor editor = Objects.requireNonNull(preferences).edit();
            String json = getGson().toJson(modelList);
            editor.putString("SavePayType", json);
            editor.apply();
        }
    }

    @Nullable public ArrayList<PaymentMethod> getPayType() {
        String json = Objects.requireNonNull(preferences).getString("SavePayType", "");
        if (TextUtils.isEmpty(json) ) {
            return null;
        } else {
            Type founderListType = new TypeToken<ArrayList<PaymentMethod>>(){}.getType();

            return getGson().fromJson(json, founderListType);
        }
    }



    public void saveLocationlist(ArrayList<SwapLocationData> modelList) {
        if(modelList != null) {
            SharedPreferences.Editor editor = Objects.requireNonNull(preferences).edit();
            String json = getGson().toJson(modelList);
            editor.putString("SavePayType", json);
            editor.apply();
        }
    }


    public void saveSwapinHistory(ArrayList<SwapingArrayResponse> modelList) {
        if(modelList != null) {
            SharedPreferences.Editor editor = Objects.requireNonNull(preferences).edit();
            String json = getGson().toJson(modelList);
            editor.putString("SavePayType", json);
            editor.apply();
        }
    }

    @Nullable public ArrayList<SwapingArrayResponse>getSwapHistory() {
        String json = Objects.requireNonNull(preferences).getString("SavePayType", "");
        if (TextUtils.isEmpty(json) ) {
            return null;
        } else {
            Type founderListType = new TypeToken<ArrayList<SwapingArrayResponse>>(){}.getType();

            return getGson().fromJson(json, founderListType);
        }
    }

    @Nullable public ArrayList<SwapLocationData>getDarction() {
        String json = Objects.requireNonNull(preferences).getString("SavePayType", "");
        if (TextUtils.isEmpty(json) ) {
            return null;
        } else {
            Type founderListType = new TypeToken<ArrayList<SwapLocationData>>(){}.getType();

            return getGson().fromJson(json, founderListType);
        }
    }



    /**
     * Clear particular value in SharedPreference
     */
    public void clearSharedPrefs(String key) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * Clear all value in SharedPreference normally calls when logout method called
     */
    public void clearAllSharedPrefs() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }


}
