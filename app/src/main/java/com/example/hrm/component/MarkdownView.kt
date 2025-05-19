package com.example.hrm.component

import android.text.method.LinkMovementMethod
import android.util.TypedValue
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import io.noties.markwon.Markwon

@Composable
fun MarkdownView(
    markdownText: String,
    modifier: Modifier = Modifier,
    textSizeSp: Float = 16f
) {
    AndroidView(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        factory = { context ->
            TextView(context).apply {
                setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp)
                setLineSpacing(8f, 1.1f)
                setPadding(16, 16, 16, 16)
                setTextIsSelectable(true)
                movementMethod = LinkMovementMethod.getInstance()
            }
        },
        update = {
            val markwon = Markwon.create(it.context)
            markwon.setMarkdown(it, markdownText)
        }
    )
}

