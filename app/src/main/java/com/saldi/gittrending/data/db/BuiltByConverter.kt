package com.saldi.gittrending.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saldi.gittrending.data.model.BuiltBy
import java.lang.reflect.Type
import java.util.*


class BuiltByConverter {
    var gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<BuiltBy?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type =
            object : TypeToken<List<BuiltBy?>?>() {}.type
        return gson.fromJson<List<BuiltBy?>>(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<BuiltBy?>?): String? {
        return gson.toJson(someObjects)
    }
}