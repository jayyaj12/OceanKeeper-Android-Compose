package com.aos.core.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

object FileUtil {
    suspend fun handleImageUri(context: Context, uri: Uri): File? {
        when (uri.scheme) {
            "content", "file" -> {
                return transUriToTempFile(context, uri)
            }
            "https" -> {
                val convertedUri = convertImageUrlToUri(context, uri.toString())
                if (convertedUri != null) {
                    return transUriToTempFile(context, convertedUri)
                } else {
                    Timber.e("이미지 변환 실패")
                }
            }
            else -> Timber.e("지원하지 않는 URI 형식: $uri")
        }
        return null
    }

    private suspend fun convertImageUrlToUri(context: Context, imageUrl: String): Uri? {
        val bitmap = getBitmapFromUrl(imageUrl) ?: return null
        return bitmapToUri(context, bitmap)
    }

    private suspend fun getBitmapFromUrl(imageUrl: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(imageUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()

                val inputStream = connection.inputStream
                BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    private fun bitmapToUri(context: Context, bitmap: Bitmap): Uri? {
        return try {
            val file = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
            val outputStream = FileOutputStream(file)

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            Uri.fromFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun transUriToTempFile(context: Context, uri: Uri): File? {
        return if (uri.scheme == "file") {
            File(uri.path!!)
        } else {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val tempFile = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")

            tempFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
            tempFile
        }
    }

    suspend fun saveUriStringToFile(context: Context, url: String): File? {
        return withContext(Dispatchers.IO) {
            handleImageUri(context, Uri.parse(url))
        }
    }
}