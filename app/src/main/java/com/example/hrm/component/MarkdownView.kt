package com.example.hrm.component

import android.text.method.LinkMovementMethod
import android.util.TypedValue
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import io.noties.markwon.Markwon

@Composable
fun MarkdownView(
    markdownText: String,
    modifier: Modifier = Modifier,
    textSizeSp: Float = 16f
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = "DeepSeek趋势分析:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            color = Color.Black
        )
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // 内边距写在这里，避免父级溢出
            factory = { context ->
                TextView(context).apply {
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp)
                    setLineSpacing(8f, 1.1f)
                    setTextIsSelectable(true)
                    movementMethod = LinkMovementMethod.getInstance()
                    setTextColor(android.graphics.Color.BLACK)
                }
            },
            update = {
                val markwon = Markwon.create(it.context)
                markwon.setMarkdown(it, markdownText)
            }
        )
    }
}

