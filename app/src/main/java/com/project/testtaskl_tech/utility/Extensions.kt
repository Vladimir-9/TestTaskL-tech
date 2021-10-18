package com.project.testtaskl_tech.utility

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

fun Int.convertPixelFromDp(context: Context): Int {
    val density = context.resources.displayMetrics.densityDpi
    val pixelInDp = density / DisplayMetrics.DENSITY_DEFAULT
    return pixelInDp * this
}

fun Fragment.toast(message: String) {
    Toast.makeText(this.requireContext(), message, Toast.LENGTH_LONG).show()
}

fun <T : Fragment> T.withArguments(action: Bundle.() -> Unit): T {
    return apply {
        arguments = Bundle().apply(action)
    }
}

fun String.formattedDate(): String {
    var ourDate: String = this
    val defaultDate = "00-00-0000 00:00"
    return try {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val value = formatter.parse(ourDate)
        val dateFormatter = SimpleDateFormat("MM.dd.yyyy HH:mm", Locale.getDefault())
        ourDate = dateFormatter.format(value ?: defaultDate)
        ourDate
    } catch (e: Exception) {
        defaultDate
    }
}