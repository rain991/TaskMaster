package com.example.taskmaster.data.components.files

import android.os.Environment
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class FileDownloader {
    suspend fun downloadFile(fileUrl: String, fileName: String) {
        val downloadDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val outputFile = File(downloadDirectory, fileName)

        withContext(Dispatchers.IO) {
            try {

                val url = URL(fileUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                    Log.e("FileDownloader", "Server returned HTTP error ${connection.responseCode} ${connection.responseMessage}")
                    return@withContext
                }

                val inputStream = BufferedInputStream(connection.inputStream)
                val outputStream = FileOutputStream(outputFile)

                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }

                outputStream.flush()
                outputStream.close()
                inputStream.close()

                Log.d("FileDownloader", "File downloaded to: ${outputFile.absolutePath}")

            } catch (e: Exception) {
                Log.e("FileDownloader", "Error downloading file: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}