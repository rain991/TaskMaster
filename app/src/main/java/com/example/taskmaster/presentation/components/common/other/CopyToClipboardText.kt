package com.example.taskmaster.presentation.components.common.other

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.taskmaster.R

@Composable
fun TextButtonWithClipboard(text: String) {
    val context = LocalContext.current
    Column(modifier = Modifier.clickable {
        copyTextToClipboard(context, text)
        Toast.makeText(context, context.getString(R.string.text_button_with_clipboard_message), Toast.LENGTH_SHORT).show()
    }) {
        Text(text = text)
    }
}

private fun copyTextToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("text", text)
    clipboard.setPrimaryClip(clip)
}