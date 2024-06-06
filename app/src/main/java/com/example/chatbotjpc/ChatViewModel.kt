package com.example.chatbotjpc

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
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
          try {
              val chat = generativeModel.startChat(
                  history = messageList.map {
                      content(it.role){
                          text(it.message)
                      }
                  }.toList()
              )
              messageList.add(MessageModel(question,"user"))
              messageList.add(MessageModel("Typing....","model"))
              val response = chat.sendMessage(question)
              messageList.removeLast()
              messageList.add(MessageModel(response.text.toString(),"model"))
          }catch (e:Exception) {
              messageList.removeLast()
              messageList.add(MessageModel("Error : "+e.message.toString(),"model"))
          }
        }
    }
}