package com.codingtestwl.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.ConnectivityManager
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import java.text.Normalizer

object Utility {
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun getResizedBitmap(bm: Bitmap): Bitmap {
        val width = bm.width
        val height = bm.height
        val scaleWidth = 200.toFloat() / width
        val scaleHeight = 200.toFloat() / height
        val matrix = Matrix()
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight)
        // RECREATE THE NEW BITMAP
        return Bitmap.createBitmap(
            bm, 0, 0, width, height,
            matrix, false
        )
    }
     fun highlightText(searchString: String, originalText: String, heightColor: Int): CharSequence {
        var search: String? = searchString
        if (search != null && !search.equals("", ignoreCase = true)) {
            search = search.toLowerCase()
            val normalizedText = Normalizer.normalize(originalText, Normalizer.Form.NFD)
                .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "").toLowerCase()
            var start = normalizedText.indexOf(search)
            return if (start < 0) {
                originalText
            } else {
                val highlighted: Spannable = SpannableString(originalText)
                while (start >= 0) {
                    val spanStart = Math.min(start, originalText.length)
                    val spanEnd = Math.min(start + search.length, originalText.length)
                    highlighted.setSpan(
                        ForegroundColorSpan(heightColor),
                        spanStart,
                        spanEnd,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    start = normalizedText.indexOf(search, spanEnd)
                }
                highlighted
            }
        }
        return originalText
    }
}