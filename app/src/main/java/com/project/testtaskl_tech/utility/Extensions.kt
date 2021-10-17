package com.project.testtaskl_tech.utility

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.fragment.app.Fragment

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