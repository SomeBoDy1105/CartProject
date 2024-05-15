package com.example.cartprojects.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cartprojects.CardWithShape
import com.example.cartprojects.data.models.Item
import com.example.cartprojects.parseJsonToItems
import com.example.cartprojects.synchronousGetRequest

fun fetchItems(): List<Item> {
    return parseJsonToItems(synchronousGetRequest())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Homepage(
    navController: NavController,
    itemsList: List<Item>,
    cartItems: MutableList<Item>,
    favItems: MutableSet<Item>
) {


    Scaffold(
        topBar = {
            TopAppBar(title = { Text("CartProject") },
                actions = {
                    IconButton(onClick = { navController.navigate("cartScreen") }) {
                        Icon(
                            Icons.Filled.ShoppingCart, contentDescription = "shopping cart"
                        )
                    }
                    IconButton(onClick = { navController.navigate("favScreen") }) {
                        Icon(
                            Icons.Filled.Favorite, contentDescription = "Fav icon"
                        )
                    }
                },
                )
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(.5.dp),
            horizontalArrangement = Arrangement.spacedBy(.5.dp)
        ) {
            items(itemsList) { item ->
                CardWithShape(
                    navController,
                    item,
                    cartItems, // Pass cartItems to CardWithShape
                    favItems,  // pass favItems to CardWithShape
                    state = favItems.contains(item) // Pass true if the item is in favItems
                )
            }
        }
    }
}
