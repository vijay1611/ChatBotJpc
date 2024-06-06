package com.example.chatbotjpc


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatbotjpc.ui.theme.req
import com.example.chatbotjpc.ui.theme.res

@Composable
fun ChatPage(
    modifier: Modifier=Modifier,
    chatViewModel: ChatViewModel
) {

    Column(modifier) {
        TitleHead()
        MessageList(
            modifier=Modifier.weight(1f),
            messageList = chatViewModel.messageList)
        MessageInput(onMessageSent = {
            chatViewModel.sendMessage(it)
        })
    }

}


@Composable
fun MessageList(
    modifier: Modifier=Modifier,messageList:List<MessageModel>) {
if(messageList.isEmpty()){
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(painter = painterResource(id =  R.drawable.baseline_question_mark_24),
            contentDescription = null)
        Text(text = "Ask me something...")
        Text(text = "Created by Vijay.K 06-06-24...")
    }
}else{
    LazyColumn(
        modifier=modifier,
        reverseLayout = true
    ){
        items(messageList.reversed()){
            MessageRow(messageModel = it)
        }
    }
}

}

@Composable
fun MessageRow(messageModel: MessageModel) {
        val isModel = messageModel.role == "model"
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
      Box(modifier = Modifier.fillMaxWidth()){
          Box(modifier = Modifier
              .align(
                  if (isModel) {
                      Alignment.BottomStart
                  } else {
                      Alignment.BottomEnd
                  }
              )
              .padding(
                  start = if (isModel) 8.dp else 70.dp,
                  end = if (isModel) 70.dp else 8.dp,
                  top = 5.dp,
                  bottom = 5.dp
              )
              .clip(RoundedCornerShape(48f))
              .background(if (!isModel) req else res)
              .padding(16.dp)){
             SelectionContainer {
                 Text(text = messageModel.message,
                     fontWeight = FontWeight.W500,
                     color = Color.White
                 )
             }
          }
      }
    }
}

@Composable
fun TitleHead() {
    Box(modifier = Modifier
        .fillMaxWidth()
        //.background(MaterialTheme.colorScheme.primary)
        .background(MaterialTheme.colorScheme.primary)
    ){
        Text(
            modifier = Modifier.padding(16.dp),
            text = "GeminiAi ChatBot",
            fontSize = 22.sp,
            color = Color.White
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInput(onMessageSent : (String)-> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var message by remember {
        mutableStateOf("")
    }

    Row (modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
        ){
        OutlinedTextField(
            modifier = Modifier.weight(1f) ,
            value = message, onValueChange ={
            message=it
        })

        IconButton(onClick = {
            if(message.isNotEmpty()){
                onMessageSent(message)
                keyboardController?.hide()
                message=""
            }
        }) {
            Icon(imageVector = Icons.Default.Send , contentDescription ="Search")
        }

    }
}

@Composable
@Preview
fun PreViewPage() {
 ChatPage(chatViewModel = ChatViewModel())
}