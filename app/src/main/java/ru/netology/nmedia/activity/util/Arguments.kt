package ru.netology.nmedia.activity.util

import android.os.Bundle
import ru.netology.nmedia.dto.models.Post
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object StringArg : ReadWriteProperty<Bundle, String?> {
    override fun getValue(thisRef: Bundle, property: KProperty<*>): String? =
        thisRef.getString(property.name)


    override fun setValue(thisRef: Bundle, property: KProperty<*>, value: String?) {
        thisRef.putString(property.name, value)
    }
}

object PostArg: ReadWriteProperty<Bundle, Post?> {
    override fun getValue(thisRef: Bundle, property: KProperty<*>): Post? =
        thisRef.getSerializable(property.name) as? Post


    override fun setValue(thisRef: Bundle, property: KProperty<*>, value: Post?) {
        thisRef.putSerializable(property.name, value)
    }

}