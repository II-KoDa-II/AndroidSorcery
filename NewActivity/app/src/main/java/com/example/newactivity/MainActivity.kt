package com.example.newactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var buttonShowPic = findViewById<Button>(R.id.btn_show_pic)
        buttonShowPic.setOnClickListener{
            val intent = Intent(this, SecondActivity::class.java)
            var picLink = "https://cdn.discordapp.com/attachments/595142399190368257/1034150042451320913/unknown.png?ex=6589b5c7&is=657740c7&hm=b409a14a2b28c7b31b2a370c81d9c17eb7484178836dc705807fe10e42e82b5a&"
            intent.putExtra(EXTRA_MESSAGE, picLink)
            startActivity(intent)
        }
    }
}