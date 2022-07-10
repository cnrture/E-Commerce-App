package com.canerture.e_commerce_app.common.util

import android.text.Editable
import android.text.TextWatcher

class CreditCardTextFormatter(
    private var separator: String = " - ",
    private var divider: Int = 5
) : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        if (s == null) {
            return
        }
        val oldString = s.toString()
        val newString = getNewString(oldString)
        if (newString != oldString) {
            s.replace(0, oldString.length, getNewString(oldString))
        }
    }

    private fun getNewString(value: String): String {

        var newString = value.replace(separator, "")

        var divider = this.divider
        while (newString.length >= divider) {
            newString = newString.substring(
                0,
                divider - 1
            ) + this.separator + newString.substring(divider - 1)
            divider += this.divider + separator.length - 1
        }
        return newString
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
}