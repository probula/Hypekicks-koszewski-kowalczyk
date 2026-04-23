package com.example.hypekicks_koszewski_kowalczyk

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class AdminPanelAcivity : AppCompatActivity() {

    private lateinit var etId: EditText
    private lateinit var etBrand: EditText
    private lateinit var etModelName: EditText
    private lateinit var etReleaseYear: EditText
    private lateinit var etResellPrice: EditText
    private lateinit var etImageUrl: EditText
    private lateinit var btnAdd: Button

    private lateinit var listView: ListView
    private val listText = mutableListOf<String>()
    private lateinit var listAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_panel)

        etId = findViewById(R.id.etId)
        etBrand = findViewById(R.id.etBrand)
        etModelName = findViewById(R.id.etModelName)
        etReleaseYear = findViewById(R.id.etReleaseYear)
        etResellPrice = findViewById(R.id.etResellPrice)
        etImageUrl = findViewById(R.id.etImageUrl)
        btnAdd = findViewById(R.id.btnAddToDb)

        listView = findViewById(R.id.lvSneakers)
        listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listText)
        listView.adapter = listAdapter


    }
}