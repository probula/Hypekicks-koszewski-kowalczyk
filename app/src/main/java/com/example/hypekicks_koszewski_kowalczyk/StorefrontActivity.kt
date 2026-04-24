package com.example.hypekicks_koszewski_kowalczyk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.GridView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.hypekicks_koszewski_kowalczyk.model.Sneaker
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlin.jvm.java

class StorefrontActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private val list = mutableListOf<Sneaker>()
    private lateinit var adapter: SneakerAdapter


    private val fullList = mutableListOf<Sneaker>()
    private val filteredList = mutableListOf<Sneaker>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storefront)

        val gridView = findViewById<GridView>(R.id.gridView)

        adapter = SneakerAdapter(this, filteredList)
        gridView.adapter = adapter

        db.collection("sneakers")
            .get()
            .addOnSuccessListener { result ->
                fullList.clear()
                filteredList.clear()

                for (doc in result) {
                    val sneaker = doc.toObject(Sneaker::class.java)
                    fullList.add(sneaker)
                }
                filteredList.addAll(fullList)
                adapter.notifyDataSetChanged()
            }
        val searchView = findViewById<SearchView>(R.id.searchView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                val text = newText?.lowercase() ?: ""

                filteredList.clear()

                for (s in fullList) {
                    if (s.modelName.lowercase().contains(text)) {
                        filteredList.add(s)
                    }
                }

                adapter.notifyDataSetChanged()
                return true
            }
        })
        val btn = findViewById<Button>(R.id.btnAdminPanel)
        btn.setOnClickListener {
            val intent = Intent(this, AdminPanelAcivity::class.java)
            startActivity(intent)
        }
    }
}