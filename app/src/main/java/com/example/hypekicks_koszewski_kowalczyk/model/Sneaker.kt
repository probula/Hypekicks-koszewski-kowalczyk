package com.example.hypekicks_koszewski_kowalczyk.model

import java.io.Serializable

data class Sneaker(
    var id: String = "",
    var brand: String = "",
    var modelName: String = "",
    var releaseYear: Long = 0,
    var resellPrice: Long = 0,
    var imageUrl: String = ""
) : Serializable