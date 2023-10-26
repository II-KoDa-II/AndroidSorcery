package com.example.nestedlayouts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var count = 1
        var idCount = 0

        val viewsHorizontal: MutableList<TextView> = mutableListOf()
        val viewsVertical: MutableList<TextView> = mutableListOf()
        val viewsConstraint: MutableList<TextView> = mutableListOf()

        val textView3 = findViewById<TextView>(R.id.textView3)
        val textView4 = findViewById<TextView>(R.id.textView4)
        val textView5 = findViewById<TextView>(R.id.textView5)

        val textView6 = findViewById<TextView>(R.id.textView6)
        val textView7 = findViewById<TextView>(R.id.textView7)
        val textView8 = findViewById<TextView>(R.id.textView8)

        val textView9 = findViewById<TextView>(R.id.textView9)
        val textView10 = findViewById<TextView>(R.id.textView10)
        val textView11 = findViewById<TextView>(R.id.textView11)

        viewsHorizontal.add(textView3)
        viewsHorizontal.add(textView4)
        viewsHorizontal.add(textView5)
        viewsVertical.add(textView6)
        viewsVertical.add(textView7)
        viewsVertical.add(textView8)
        viewsConstraint.add(textView9)
        viewsConstraint.add(textView10)
        viewsConstraint.add(textView11)

        val buttonBlackText = findViewById<Button>(R.id.button_handler)
        buttonBlackText.setOnClickListener{
            for (TextView in viewsHorizontal){
                TextView.setText(null)
            }

            for (TextView in viewsVertical){
                TextView.setText(null);
            }

            for (TextView in viewsConstraint){
                TextView.setText(null);
            }

            count += 1
            idCount = idCount + 1

            if (idCount == 3){
                idCount = 0
            }

            viewsHorizontal[idCount].setText(count.toString())
            viewsVertical[idCount].setText(count.toString())
            viewsConstraint[idCount].setText(count.toString())
        }
    }
}
