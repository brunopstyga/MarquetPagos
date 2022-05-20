package com.mercadopago.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.lang.StringBuilder


class DateMaskWatcher(val editText: EditText) : TextWatcher {
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

        val cleanValue = editable.replace(Regex("[ ]"), "")
        val newValue = StringBuilder(cleanValue)

        if (newValue.isNotEmpty()) {
            // Prevents more than one slash
            if (newValue.count { it == '/' } > 1) {
                oldValue = oldValue.replace(Regex("[ ]"), "")
                val originalSlashIndex = oldValue.indexOf("/")
                val newValueFirstSlashIndex = newValue.indexOfFirst { it == '/' }
                val newValueSecondSlashIndex = newValue.indexOfLast { it == '/' }
                newValue.deleteCharAt(
                    if (newValueFirstSlashIndex >= originalSlashIndex)
                        newValueSecondSlashIndex
                    else
                        newValueFirstSlashIndex
                )
                isDeleting = true
            }
            // Two digits to month and two digits to year
            val dateParts = newValue.split("/")
            if (dateParts.isNotEmpty() && dateParts[0].isNotBlank()) {
                val month = dateParts[0].toInt()
                if (month > 12 || !(dateParts[0][0] == '0' || dateParts[0][0] == '1') || (month == 0 && dateParts[0].length > 1)) {
                    newValue.clear()
                }
                else if (month in 1..12 && dateParts[0].length > 1) {
                    newValue.apply {
                        clear()
                        append(
                            if (dateParts.size == 2 && dateParts[1].isNotBlank()) {
                                val year = if (dateParts[1].length > 2)
                                    dateParts[1].substring(0, 2).toInt()
                                else
                                    dateParts[1].toInt()
                                String.format("%02d / %d", month, year)
                            } else {
                                cursorPos = 5
                                String.format("%02d / ", month)
                            }
                        )
                    }
                }
            }

            if (cleanValue[0] == '/') {
                newValue.clear()
            }
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

        if (cursorPos > editable.length)
            cursorPos = editable.length

        editText.setSelection(cursorPos)

        isRunning = false
    }
}