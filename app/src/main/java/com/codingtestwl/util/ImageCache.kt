package com.codingtestwl.util

import android.graphics.Bitmap
import android.util.LruCache


class ImageCache {
    private val lru: LruCache<Any, Any> = LruCache<Any, Any>(1024)
    private fun getLru(): LruCache<Any, Any> {
        return lru
    }

    fun saveBitmapToCache(key: String?, bitmap: Bitmap?) {
        try {
            instance!!.getLru().put(key, bitmap)
        } catch (e: Exception) {
        }
    }

    fun retrieveBitmapFromCache(key: String?): Bitmap? {
        try {

            return instance!!.getLru().get(key) as Bitmap

        } catch (e: Exception) {
        }
        return null
    }

    companion object {
        var instance: ImageCache? = null
            get() {
                if (field == null) {
                    field = ImageCache()
                }
                return field
            }
            private set
    }

}