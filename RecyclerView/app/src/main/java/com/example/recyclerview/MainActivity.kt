package com.example.recyclerview

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

interface CellClickListener{
    fun onCellClickListener(color: ColorData)
}

data class ColorData(val colorName: String, val colorHex: Int)

class MainActivity : AppCompatActivity(), CellClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.rView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = Adapter(this, fetchList(), this)
    }

    private fun fetchList(): ArrayList<ColorData> {
        val colorList = arrayListOf<ColorData>()
        val color1 = ColorData("RED", Color.RED)
        val color2 = ColorData("GREEN", Color.GREEN)
        val color3 = ColorData("BLUE", Color.BLUE)
        val color4 = ColorData("BLACK", Color.BLACK)
        val color5 = ColorData("WHITE", Color.WHITE)
        colorList.add((color1))
        colorList.add((color2))
        colorList.add((color3))
        colorList.add((color4))
        colorList.add((color5))
        return colorList
    }

    override fun onCellClickListener(color: ColorData) {
        Toast.makeText(this, "IT'S " + color.colorName, Toast.LENGTH_SHORT).show()
    }
}

class Adapter(private val context: Context, private val list: ArrayList<ColorData>, private val cellClickListener: CellClickListener) : RecyclerView.Adapter<Adapter.ViewHolder>(){
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val color: View = view.findViewById(R.id.view)
        val textColor: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rview_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]

        holder.color.setBackgroundColor(data.colorHex)
        holder.textColor.text = data.colorName

        holder.itemView.setOnClickListener{
            cellClickListener.onCellClickListener(data)
        }
    }
}