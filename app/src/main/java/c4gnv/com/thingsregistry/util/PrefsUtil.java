package c4gnv.com.thingsregistry.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import c4gnv.com.thingsregistry.net.model.Thing;

public class PrefsUtil {

    private static final String KEY_SHARED_PREFERENCES = "c4gnv.com.thingsregistry.sharedPreferences";
    private static final String KEY_SAVED_THINGS = "c4gnv.com.thingsregistry.savedThigns";

    public static void addThing(Context context, Thing thing) {
        List<Thing> things = getThings(context);
        things.add(thing);

        SharedPreferences sharedPreferences = getSharedPrefs(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(things);
        editor.putString(KEY_SAVED_THINGS, json);
        editor.commit();
    }

    public static List<Thing> getThings(Context context) {
        SharedPreferences sharedPreferences = getSharedPrefs(context);
        String json = sharedPreferences.getString(KEY_SAVED_THINGS, "[]");
        Gson gson = new Gson();
        Type thingListType = new TypeToken<ArrayList<Thing>>(){}.getType();
        List<Thing> things = gson.fromJson(json, thingListType);
        if (things != null) {
            return things;
        } else {
            return new ArrayList<>();
        }
    }

    public static void deleteAllThings(Context context) {
        SharedPreferences sharedPreferences = getSharedPrefs(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_SAVED_THINGS, "");
        editor.commit();
    }

    private static SharedPreferences getSharedPrefs(Context context) {
        return context.getSharedPreferences(KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }
}
