package com.example.newactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.ImageView
import com.bumptech.glide.Glide

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pic_activity)

        val picLink = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE) as String
        var imageView = findViewById<ImageView>(R.id.picView)

        Glide.with(this).load(picLink).into(imageView)
    }
}