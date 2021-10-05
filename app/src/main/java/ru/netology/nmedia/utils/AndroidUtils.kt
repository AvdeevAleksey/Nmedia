package ru.netology.nmedia.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

object AndroidUtils {
    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken,0)
    }
}
fun Context.showMyMessage(text: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(
        this,
        getText(text),
        length
    ).show()
}
