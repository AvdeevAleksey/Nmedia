package ru.netology.nmedia.utils

import android.content.Context
import android.widget.Toast

fun Context.showMyMessage(text: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(
        this,
        getText(text),
        length
    ).show()
}
