package com.example.geminiaichatapp

import android.graphics.Bitmap
import com.example.geminiaichatapp.data.Chat

data class ChatState(
    val chatList: MutableList<Chat> = mutableListOf(),
    val prompt : String = "",
    val bitmap: Bitmap? = null

)