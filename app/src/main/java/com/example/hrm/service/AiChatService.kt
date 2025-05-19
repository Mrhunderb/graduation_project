package com.example.hrm.service

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletionChunk
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIHost
import kotlinx.coroutines.flow.Flow
import com.aallam.openai.api.http.Timeout
import kotlin.time.Duration.Companion.seconds

@OptIn(BetaOpenAI::class)
class AiChatService {

    private val openai = OpenAI(
        host = OpenAIHost("https://api.deepseek.com/v1/"),
        token = "sk-b0f00d21928c48c895db9bdce139c705",
        timeout = Timeout(socket = 60.seconds),
    )

    /**
     * 提问 AI，返回流式响应。
     * @param systemPrompt AI 的系统角色说明，例如建议背景或任务。
     * @param userInput 用户提问内容。
     */
    fun askQuestion(
        systemPrompt: String,
        userInput: String
    ): Flow<ChatCompletionChunk> {
        val request = ChatCompletionRequest(
            model = ModelId("deepseek-chat"),
            messages = listOf(
                ChatMessage(ChatRole.System, systemPrompt),
                ChatMessage(ChatRole.User, userInput)
            )
        )
        return openai.chatCompletions(request)
    }
}
