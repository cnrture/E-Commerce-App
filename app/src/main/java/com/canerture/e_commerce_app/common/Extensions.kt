package com.canerture.e_commerce_app.common

import android.content.Context
import android.util.Patterns
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.showSnackbar(msg: String) {
    Snackbar.make(this, msg, 1500).show()
}

fun TextInputEditText.isNullorEmpty(errorString: String): Boolean {
    val textInputLayout = this.parent.parent as TextInputLayout
    return if (text.toString().trim().isNotEmpty()) {
        textInputLayout.isErrorEnabled = false
        true
    } else {
        textInputLayout.error = errorString
        false
    }
}

fun TextInputEditText.isValidEmail(errorString: String): Boolean {
    val textInputLayout = this.parent.parent as TextInputLayout
    return if (Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()) {
        textInputLayout.isErrorEnabled = false
        true
    } else {
        textInputLayout.error = errorString
        false
    }
}

fun AutoCompleteTextView.checkMonthYear(value: Int, errorString: String): Boolean {
    val textInputLayout = this.parent.parent as TextInputLayout
    return if (value != 0) {
        textInputLayout.isErrorEnabled = false
        true
    } else {
        textInputLayout.error = errorString
        false
    }
}

fun View.margin(bottom: Int? = null) {
    layoutParams<ViewGroup.MarginLayoutParams> {
        bottom?.run { bottomMargin = dpToPx(this) }
    }
}

inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
    if (layoutParams is T) block(layoutParams as T)
}

fun View.dpToPx(dp: Int): Int = context.dpToPx(dp)
fun Context.dpToPx(dp: Int): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics)
        .toInt()