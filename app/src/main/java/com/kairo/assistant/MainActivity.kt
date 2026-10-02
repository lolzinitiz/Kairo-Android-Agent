package com.kairo.assistant

import android.app.Activity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import java.util.Locale

class MainActivity : Activity() {

    private lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        resultText = TextView(this).apply {
            text = "Kairo প্রস্তুত\n\nকথা বলার জন্য নিচের বাটনে চাপুন"
            textSize = 20f
            setPadding(40, 100, 40, 40)
        }

        val button = Button(this).apply {
            text = "🎤 Kairo-কে বলুন"
            setOnClickListener {
                startVoiceInput()
            }
        }

        val layout = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            addView(resultText)
            addView(button)
        }

        setContentView(layout)
    }

    private fun startVoiceInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            "bn-BD"
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            "Kairo-কে বলুন..."
        )

        try {
            startActivityForResult(intent, 100)
        } catch (e: Exception) {
            resultText.text = "Voice input চালু করা যাচ্ছে না।"
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            val results =
                data?.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                )

            resultText.text =
                "আপনি বলেছেন:\n${results?.firstOrNull() ?: ""}"
        }
    }
}
