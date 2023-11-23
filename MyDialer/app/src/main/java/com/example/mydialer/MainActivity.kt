package com.example.mydialer

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import timber.log.Timber
import timber.log.Timber.Forest.plant
import java.io.BufferedReader
import java.io.File
import java.lang.Exception


data class Contact(
    val name: String,
    val phone: String,
    val type: String
)

class MainActivity : AppCompatActivity() {

    private companion object {
        private const val STORAGE_PERMISSION_CODE = 100
        private const val TAG = "PERMISSION_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        plant(Timber.DebugTree())


        if (checkPermission())
        {
            var ContactList: Array<Contact> = arrayOf()
            val gson = Gson()

            val pathName = Environment.getExternalStorageDirectory()
                .toString() + "/" + Environment.DIRECTORY_DOWNLOADS + "/phones.json"

            val bufferedReader: BufferedReader = File(pathName).bufferedReader()
            val inputString = bufferedReader.use { it.readText() }

            ContactList = gson.fromJson(inputString, ContactList::class.java)

            val recyclerView: RecyclerView = findViewById(R.id.rView)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = Adapter(this, ContactList)

            val button: Button = findViewById(R.id.btn_search)
            button.setOnClickListener{
                val editText: EditText = findViewById(R.id.et_search)
                val srcText: String = editText.text.toString()

                if (srcText == "" || srcText == " "){
                    recyclerView.adapter = Adapter(this, ContactList)
                } else {
                    var SortedList: Array<Contact> = arrayOf()
                    for(i in 0..ContactList.count() - 1){
                        if(ContactList[i].name.contains(srcText) || ContactList[i].type.contains(srcText) || ContactList[i].phone.contains(srcText)){
                            SortedList += ContactList[i]
                        }
                    }
                    recyclerView.adapter = Adapter(this, SortedList)
                }
            }
        }
        else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Timber.d(TAG, "requestPermission: try")
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                val uri = Uri.fromParts("package", this.packageName, null)
                intent.data = uri
            }
            catch (e: Exception) {
                Timber.e(TAG, "requestPermission: ", e)
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
            }
        }
        else {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE),
                STORAGE_PERMISSION_CODE
            )
        }
    }

    private fun checkPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        }
        else {
            val write = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val read = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty()) {
                val write = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val read = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (write && read) {
                    Timber.d(TAG, "onRequestPermissionResult: External Storage Permission granted")
                }
                else {
                    Timber.d(TAG, "onRequestPermissionsResult: External Storage Permission denied")
                }
            }
        }
    }
}

class Adapter(private val context: Context, private val list: Array<Contact>) : RecyclerView.Adapter<Adapter.ViewHolder>(){
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name: TextView = view.findViewById(R.id.textName)
        val phone: TextView = view.findViewById(R.id.textPhone)
        val type: TextView = view.findViewById(R.id.textType)
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

        holder.name.text = data.name
        holder.phone.text = data.phone
        holder.type.text = data.type
    }
}