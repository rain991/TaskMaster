package com.example.taskmaster.data.components.files

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat


// fun checkWriteToStoragePermission(context: Context): Boolean {
//    return ContextCompat.checkSelfPermission(
//        context,
//        Manifest.permission.WRITE_EXTERNAL_STORAGE
//    ) == PackageManager.PERMISSION_GRANTED
//}

 fun checkReadFromStoragePermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED
}