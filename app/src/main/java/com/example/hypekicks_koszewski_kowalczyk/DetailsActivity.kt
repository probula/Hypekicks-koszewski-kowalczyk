package com.example.hypekicks_koszewski_kowalczyk

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.hypekicks_koszewski_kowalczyk.model.Sneaker
import java.text.NumberFormat
import java.util.Locale

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SNEAKER = "extra_sneaker"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val image = findViewById<ImageView>(R.id.ivSneakerLarge)
        val tvBrand = findViewById<TextView>(R.id.tvBrand)
        val tvModel = findViewById<TextView>(R.id.tvModel)
        val tvPrice = findViewById<TextView>(R.id.tvPrice)
        val btnBack = findViewById<Button>(R.id.btnBack)

        val sneaker = getSneakerFromIntent()
        if (sneaker == null) {
            finish()
            return
        }

        tvBrand.text = sneaker.brand
        tvModel.text = sneaker.modelName

        val priceFormatted = NumberFormat.getCurrencyInstance(Locale("pl", "PL")).format(sneaker.resellPrice)
        tvPrice.text = priceFormatted

        Glide.with(this)
            .load(sneaker.imageUrl)
            .placeholder(android.R.drawable.progress_horizontal)
            .into(image)

        btnBack.setOnClickListener { finish() }
    }

    private fun getSneakerFromIntent(): Sneaker? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(EXTRA_SNEAKER, Sneaker::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(EXTRA_SNEAKER) as? Sneaker
        }
    }
}

