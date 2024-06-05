package com.example.chatbotjpc

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class ChatViewModel:ViewModel() {

    val messageList by lazy{
        mutableStateListOf<MessageModel>()
    }

    val generativeModel = GenerativeModel(
        // The Gemini 1.5 models are versatile and work with most use cases
        modelName = "gemini-pro",
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = Constants.api_key
    )

    fun sendMessage(question:String){
        viewModelScope.launch {
            val chat = generativeModel.startChat()
            messageList.add(MessageModel(question,"user"))
            val response = chat.sendMessage(question)
            messageList.add(MessageModel(response.text.toString(),"model"))
        }
    }
}