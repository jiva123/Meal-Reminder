package com.example.mealreminder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import kotlinx.android.synthetic.main.splash_pg.*

class Splash_pg : AppCompatActivity() {


    private val splashTimeout = 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_pg)
        var an: ImageView = iconid;

        Handler().postDelayed({
            val intent = Intent(this@Splash_pg, login_pg::class.java)
            startActivity(intent)
            finish()
        }, splashTimeout.toLong())

        var anm: Animation = AnimationUtils.loadAnimation(this, R.anim.anim);
        an.startAnimation(anm);

    }
    override fun onPause() {
        super.onPause()
        finish()
    }
}
