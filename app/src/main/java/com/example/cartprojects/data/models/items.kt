package com.example.cartprojects.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val stock: Int,
    val thumbnail: String
)

data class ProductResponse(
    val products: List<Item>,
    val total: Int
)
