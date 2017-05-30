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

        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(things);
        editor.putString(KEY_SAVED_THINGS, json);
        editor.commit();
    }

    public static List<Thing> getThings(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(KEY_SAVED_THINGS, "[]");
        Gson gson = new Gson();
        Type thingListType = new TypeToken<ArrayList<Thing>>(){}.getType();
        return gson.fromJson(json, thingListType);
    }
}
