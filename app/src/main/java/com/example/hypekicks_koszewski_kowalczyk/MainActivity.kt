package com.example.hypekicks_koszewski_kowalczyk

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hypekicks_koszewski_kowalczyk.StorefrontActivity
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, StorefrontActivity::class.java))
        finish()
    }
}