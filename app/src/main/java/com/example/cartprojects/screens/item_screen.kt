package com.example.cartprojects.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cartprojects.data.models.Item

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemScreen(navController: NavController, item: Item) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("CartProject") }, actions = {
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
            })
        }
    ) {
        Box() {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {
                Text(text = item.title)
                Text(text = item.description)
                Text(text = item.price.toString())
                Text(text = item.stock.toString())

            }
        }
    }
}