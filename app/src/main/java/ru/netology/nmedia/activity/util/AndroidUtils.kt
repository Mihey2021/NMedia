package ru.netology.nmedia.activity.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.constraintlayout.widget.Group

object AndroidUtils {
    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun clearTextAndHideKeyboard(contentView: TextView) {
        with(contentView) {
            text = ""
            clearFocus()
            hideKeyboard(this)
        }
    }

    fun hideCancelEditGroup(group: Group) {
        group.visibility = View.GONE
    }

    fun showCancelEditGroup(group: Group) {
        group.visibility = View.VISIBLE
    }
}