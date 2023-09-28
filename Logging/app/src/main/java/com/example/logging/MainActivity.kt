package com.example.logging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val TAG = "From EditText"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.plant(Timber.DebugTree())

        val buttonLog = findViewById<Button>(R.id.button_log)
        val buttonTimber = findViewById<Button>(R.id.button_timber)
        val input = findViewById<EditText>(R.id.editText)

        buttonLog.setOnClickListener {
            Log.v(TAG, input.text.toString())
        }

        buttonTimber.setOnClickListener {
            Timber.v(input.text.toString())
        }
    }
}