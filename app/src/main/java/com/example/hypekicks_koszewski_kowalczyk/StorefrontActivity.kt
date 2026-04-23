package com.example.hypekicks_koszewski_kowalczyk

import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.example.hypekicks_koszewski_kowalczyk.model.Sneaker
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlin.jvm.java

class StorefrontActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private val list = mutableListOf<Sneaker>()
    private lateinit var adapter: SneakerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storefront)

        val gridView = findViewById<GridView>(R.id.gridView)

        adapter = SneakerAdapter(this, list)
        gridView.adapter = adapter

        db.collection("sneakers")
            .get()
            .addOnSuccessListener { result ->
                list.clear()

                for (doc in result) {
                    val sneaker = doc.toObject(Sneaker::class.java)
                    list.add(sneaker)
                }

                adapter.notifyDataSetChanged()
            }
    }
}