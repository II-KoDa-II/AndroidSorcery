package com.example.gson

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
import timber.log.Timber.Forest.plant
import java.io.IOException
import com.bumptech.glide.Glide
import kotlin.concurrent.thread


interface CellClickListener{
    fun onCellClickListener(link: String)
}

class MainActivity : AppCompatActivity(), CellClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        plant(Timber.DebugTree())

        data class Photo(
            val id: Long,
            val owner: String,
            val secret: String,
            val server: Int,
            val farm: Int,
            val title: String,
            val isFriend: Int,
            val isFamily: Int
        )

        data class PhotoPage(
            val page: Int,
            val pages: Int,
            val perPage: Int,
            val total: Int,
            val photo: Array<Photo>,
            val stat: String
        )

        data class Wrapper(
            val photos: PhotoPage
        )

        var links: Array<String> = arrayOf()

        thread{
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1")
                .build()
            try {
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        throw IOException(
                            "Query unsuccessful:" +
                                    " ${response.code} ${response.message}"
                        )
                    }
                    val content = response.body!!.string()
                    val gson = Gson()
                    val result = gson.fromJson(content, Wrapper::class.java)

                    for(i in 0..result.photos.photo.count() - 1){
                        links += ("https://farm" + result.photos.photo[i].farm + ".staticflickr.com/" + result.photos.photo[i].server + "/" + result.photos.photo[i].id + "_" + result.photos.photo[i].secret+ "_z.jpg")
                    }

                    runOnUiThread{
                        val recyclerView: RecyclerView = findViewById(R.id.rView)
                        recyclerView.layoutManager = GridLayoutManager(this, 2)
                        recyclerView.adapter = Adapter(this, links, this)
                    }
                }
            } catch (e: IOException) {
                println("Connection error: $e")
            }
        }
    }

    fun Context.copyToClipboard(text: CharSequence){
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
         clipboard.setPrimaryClip(clip)
    }

    override fun onCellClickListener(link: String) {
        copyToClipboard(link)
        Timber.i(link)
    }
}

class Adapter(private val context: Context, private val list: Array<String>, private val cellClickListener: CellClickListener) : RecyclerView.Adapter<Adapter.ViewHolder>(){
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.imageView)
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

        Glide.with(context).load(data).into(holder.image)

        holder.itemView.setOnClickListener{
            cellClickListener.onCellClickListener(data)
        }
    }
}



