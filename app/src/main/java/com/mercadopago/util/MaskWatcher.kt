package com.mercadopago.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.lang.StringBuilder


class MaskWatcher(val editText: EditText) : TextWatcher {
    private var isRunning = false
    private var isDeleting = false
    private var cursorPos = 0
    private var oldValue = ""

    override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
        if (!isRunning) {
            isDeleting = count > after
            oldValue = charSequence.toString()
        }
    }

    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
        if (!isRunning) {
            cursorPos = start
        }
    }

    override fun afterTextChanged(editable: Editable) {
        if (isRunning)
            return

        // Prevents rerun after editable change
        isRunning = true

        val cleanValue = editable.replace(Regex("[$ ]"), "")
        val newValue = StringBuilder(cleanValue)

        if (newValue.isNotEmpty()) {
            // Prevents more than one dot
            if (newValue.count { it == '.' } > 1) {
                oldValue = oldValue.replace(Regex("[$ ]"), "")
                val originalDotIndex = oldValue.indexOf(".")
                val newValueFirstDotIndex = newValue.indexOfFirst { it == '.' }
                val newValueSecondDotIndex = newValue.indexOfLast { it == '.' }
                newValue.deleteCharAt(
                    if (newValueFirstDotIndex >= originalDotIndex)
                        newValueSecondDotIndex
                    else
                        newValueFirstDotIndex
                )
                isDeleting = true
            }
            // Only two digits after dot
            val digits = newValue.split(".")
            if (digits.size > 1 && digits[1].length > 2) {
                newValue.deleteCharAt(newValue.length - 1)
                isDeleting = true
            }
            // Single dot add padding zero
            if (cleanValue[0] == '.') {
                if (isDeleting) {
                    if (newValue.length <= 3) {
                        newValue.clear()
                    }
                } else {
                    newValue.insert(0, "0")
                }
            } else {
                // Remove leading zeros
                newValue.apply {
                    val n = replace(Regex("^0+"), "")
                    clear()
                    if (n.isNotEmpty() && n[0] == '.')
                        append('0')
                    append(n)
                }
            }
            // Inserts currency symbol
            newValue.insert(0, "$ ")
        }

        editable.apply {
            clear()
            append(newValue)
        }

        // Relocates cursor
        cursorPos = when {
            newValue.isEmpty() -> 0
            !isDeleting -> cursorPos + 1
            else -> cursorPos
        }

        if (cursorPos > editable.length || cursorPos < 3)
            cursorPos = editable.length

        editText.setSelection(cursorPos)

        isRunning = false
    }
}