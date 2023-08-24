package com.example.memesharing

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    lateinit var imgMeme:ImageView
    var currImgUrl :String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imgMeme = findViewById(R.id.imgMeme);
        loadmeme()
    }

    fun shareMeme(view: View) {
    val intent = Intent(Intent.ACTION_SEND)
        intent.type= "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"hey check this meme : $currImgUrl")
        val chooser = Intent.createChooser(intent,"share this meme using...")
        startActivity(chooser)
    }
    fun nextMeme(view: View) {
    loadmeme()
    }

    private  fun loadmeme(){
        val progress = findViewById<ProgressBar>(R.id.progress)
        progress.visibility=View.VISIBLE;
        val url = "https://meme-api.com/gimme"

// Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
                currImgUrl= response.getString("url")
                Glide.with(this).load(currImgUrl).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.visibility=View.GONE
                        return false
                    }

                }).into(imgMeme)
                // Display the first 500 characters of the response string.
            },
            { })



// Add the request to the RequestQueue.
        MySingleton.getInstance(this).requestQueue.add(jsonRequest)

    }
}