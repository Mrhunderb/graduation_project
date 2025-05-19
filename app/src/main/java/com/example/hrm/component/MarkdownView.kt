package com.example.hrm.component

import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import io.noties.markwon.Markwon

@Composable
fun MarkdownView(markdownText: String) {
    AndroidView(factory = { context ->
        TextView(context).apply {
            setPadding(16, 16, 16, 16)
        }
    }, update = {
        val markwon = Markwon.create(it.context)
        markwon.setMarkdown(it, markdownText)
    })
}
