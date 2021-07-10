package com.example.covidtracker

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan


class spanableDelta(langName: String, langColor: String, start: Int) : SpannableString(langName) {
    init {
        setSpan(
            ForegroundColorSpan(Color.parseColor(langColor)),
            start,
            langName.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}