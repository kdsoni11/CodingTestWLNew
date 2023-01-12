package com.codingtestwl.network

import android.graphics.BitmapFactory
import com.codingtestwl.list.model.Model
import com.codingtestwl.list.model.ResponseModel
import com.codingtestwl.util.ImageCache
import com.codingtestwl.util.Utility
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class Network {

    /*Creating fun for getting response json from api*/
    fun getListFromApi(pageNo: Int): ResponseModel {
        val urlStr = "https://picsum.photos/v2/list?page=$pageNo&limit=20"
        val responseModel = ResponseModel()
        var iStream: InputStream? = null
        var urlConnection: HttpURLConnection? = null

        try {
            val url = URL(urlStr)

            // Creating an http connection to communicate with url
            urlConnection = url.openConnection() as HttpURLConnection

            // Connecting to url
            urlConnection.connect()

            // Reading data from url
            iStream = urlConnection.inputStream
            val br = BufferedReader(InputStreamReader(iStream))
            val sb = StringBuffer()
            var line: String? = ""
            while (br.readLine().also { line = it } != null) {
                sb.append(line)
            }
            val data = sb.toString()

            //Close reader instance
            br.close()

            //Parsing json into mutable list
            val type = object : TypeToken<List<Model?>?>() {}.type
            val productList: MutableList<Model> = Gson().fromJson(data, type)

            //Bundle response into single model
            responseModel.mutableList = productList
            responseModel.errorMsg = ""
            responseModel.status = true
        } catch (e: Exception) {
            //Set false status and error message
            responseModel.errorMsg = e.toString()
            responseModel.status = false
        } finally {
            //Close and disconnect InputStream and HttpURLConnection after task complete
            iStream?.close()
            urlConnection!!.disconnect()
        }

        //Return response into single model
        return responseModel
    }

    fun downloadImageFromUrl(urlStr: String): Any {

        val url: URL? = mStringToURL(urlStr)

        val connection: HttpURLConnection?
        try {
            connection = url?.openConnection() as HttpURLConnection
            connection.connect()
            val inputStream: InputStream = connection.inputStream
            val bufferedInputStream = BufferedInputStream(inputStream)
            val bitmap = Utility.getResizedBitmap(BitmapFactory.decodeStream(bufferedInputStream))
            val cache = ImageCache.instance
            cache?.saveBitmapToCache(urlStr,bitmap)
            return bitmap
        } catch (e: IOException) {
            return "Error on downloading image from server"
        }

    }

    // Function to convert string to URL
    private fun mStringToURL(string: String): URL? {
        try {
            return URL(string)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return null
    }
}