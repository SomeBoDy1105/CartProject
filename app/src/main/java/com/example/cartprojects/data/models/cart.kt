package com.example.cartprojects.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class cart(
    @PrimaryKey(autoGenerate = true)
    val cartitems: List<Item>
)

@Entity
data class fav(
    @PrimaryKey(autoGenerate = true)
    val favitems: Set<Item>
)