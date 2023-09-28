package com.example.attributes

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input = findViewById<EditText>(R.id.editText)
        val black = findViewById<Button>(R.id.black)
        val red = findViewById<Button>(R.id.red)
        val sp8 = findViewById<Button>(R.id.sp8)
        val sp24 = findViewById<Button>(R.id.sp24)
        val white = findViewById<Button>(R.id.white)
        val yellow = findViewById<Button>(R.id.yellow)

        black.setOnClickListener {
            input.setTextColor(Color.parseColor("#000000"))
        }
        red.setOnClickListener {
            input.setTextColor(Color.parseColor("#FF0000"))
        }
        sp8.setOnClickListener {
            input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8f)
        }
        sp24.setOnClickListener {
            input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
        }
        white.setOnClickListener {
            input.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
        yellow.setOnClickListener {
            input.setBackgroundColor(Color.parseColor("#FFFF00"))
        }
    }
}