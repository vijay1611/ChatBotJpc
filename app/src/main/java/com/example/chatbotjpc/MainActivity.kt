package com.example.chatbotjpc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.chatbotjpc.ui.theme.ChatBotJpcTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val chatBotViewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        setContent {
            ChatBotJpcTheme {
                // A surface container using the 'background' color from the theme
               Scaffold(
                   modifier = Modifier.fillMaxSize()
               ) {innerPadding->

                   ChatPage(modifier = Modifier.padding(innerPadding),chatBotViewModel)
               }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChatBotJpcTheme {
        Greeting("Android")
    }
}