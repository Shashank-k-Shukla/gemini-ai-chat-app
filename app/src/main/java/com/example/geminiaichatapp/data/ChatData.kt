package com.example.geminiaichatapp.data

import android.graphics.Bitmap
import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ChatData {
    private const val TAG : String = "ChatData"
    private const val MODEL_NAME = "gemini-pro"
    private const val API_KEY = "AIzaSyAyD_2MlATET7b0Z7M6Qbt_lpeFQmuuxl4"

    suspend fun getResponse(prompt : String) : Chat {

        val generativeModel = GenerativeModel(
            modelName = MODEL_NAME,
            apiKey = API_KEY
        )

        try {
            val response = withContext(Dispatchers.IO){
                generativeModel.generateContent(prompt)
            }
            print(response) /// log print

            return Chat(
                prompt = response.text ?: "getting error to generate response",
                bitmap = null,
                isFromUser = false
            )

        } catch (e : Exception) {
            Log.e(TAG, e.message.toString())
            return Chat(
                prompt = e.message ?: "getting error to generate response",
                bitmap = null,
                isFromUser = false
            )
        }
    }

    // Multi-model (text and image)
    suspend fun getResponseWithImage(prompt: String, bitmap: Bitmap): Chat {
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro-vision", apiKey = API_KEY
        )

        try {

            val inputContent = content {
                image(bitmap)
                text(prompt)
            }

            val response = withContext(Dispatchers.IO) {
                generativeModel.generateContent(inputContent)
            }

            return Chat(
                prompt = response.text ?: "error",
                bitmap = null,
                isFromUser = false
            )

        } catch (e: Exception) {
            return Chat(
                prompt = e.message ?: "error",
                bitmap = null,
                isFromUser = false
            )
        }
    }
}