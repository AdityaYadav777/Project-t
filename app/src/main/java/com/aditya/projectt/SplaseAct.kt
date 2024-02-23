package com.aditya.projectt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplaseAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splase)

        Handler().postDelayed({
            startActivity(Intent(this,LoginAct::class.java))
            finish()
        },1000)

    }
}