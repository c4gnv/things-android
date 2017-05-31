package c4gnv.com.thingsregistry.util

import android.content.Context
import android.content.SharedPreferences

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.lang.reflect.Type
import java.util.ArrayList

import c4gnv.com.thingsregistry.net.model.Thing

object PrefsUtil {

    private val KEY_SHARED_PREFERENCES = "c4gnv.com.thingsregistry.sharedPreferences"
    private val KEY_SAVED_THINGS = "c4gnv.com.thingsregistry.savedThigns"

    fun addThing(context: Context, thing: Thing) {
        val things = getThings(context)
        things.add(thing)

        val sharedPreferences = getSharedPrefs(context)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(things)
        editor.putString(KEY_SAVED_THINGS, json)
        editor.commit()
    }

    fun getThings(context: Context): MutableList<Thing> {
        val sharedPreferences = getSharedPrefs(context)
        val json = sharedPreferences.getString(KEY_SAVED_THINGS, "[]")
        val gson = Gson()
        val thingListType = object : TypeToken<ArrayList<Thing>>() {

        }.type
        val things = gson.fromJson<List<Thing>>(json, thingListType)
        if (things != null) {
            return things as MutableList<Thing>
        } else {
            return ArrayList()
        }
    }

    fun deleteAllThings(context: Context) {
        val sharedPreferences = getSharedPrefs(context)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_SAVED_THINGS, "")
        editor.commit()
    }

    private fun getSharedPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }
}
