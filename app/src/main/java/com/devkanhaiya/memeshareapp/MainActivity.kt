package com.devkanhaiya.memeshareapp

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var currentImageUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        load()


    }
fun  load(){
    progressBar.visibility=View.VISIBLE

     currentImageUrl = "https://meme-api.herokuapp.com/gimme"

    val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,currentImageUrl,null,Response.Listener { response ->

        val image = response.getString("url")


        Glide.with(this).load(image).listener(object: RequestListener<Drawable>{
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.visibility=View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.visibility=View.GONE
                return false
            }
        } ).into(imageView)
    },Response.ErrorListener { error ->

        Toast.makeText(this,"Error"+error,Toast.LENGTH_LONG)
    })

    MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
}

    fun share(view: View) {
        val intent =Intent(Intent.ACTION_SEND)
        intent.type= "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey Guys CheckOut This Meme $currentImageUrl")
        val chooser = Intent.createChooser(intent,"choose you want o send....")
        startActivity(intent)
    }
    fun next(view: View) {
        load()
    }

}