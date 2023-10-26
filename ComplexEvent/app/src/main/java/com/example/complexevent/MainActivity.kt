package com.example.complexevent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.ProgressBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val InputText = findViewById<EditText>(R.id.editTextText)
        val UpdateText = findViewById<TextView>(R.id.textView2)
        val CheckBox = findViewById<CheckBox>(R.id.checkBox)
        var ProgressBar = findViewById<ProgressBar>(R.id.progressBar)
        var Progress = 10;

        button.setOnClickListener{
            if(CheckBox.isChecked){
                UpdateText.setText(InputText.text)
                ProgressBar.setProgress(Progress)
                Progress += 10
            }
        }
    }
}