package com.catnip.rockpaperscissorchapter6and7.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

object DateConverter {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val formatter = SimpleDateFormat("EEEE, dd MMMM yyyy")

    fun convert(date: String): String {
        return formatter.format(parser.parse(date))
    }
}