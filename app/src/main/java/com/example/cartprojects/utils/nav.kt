package com.example.cartprojects.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cartprojects.data.models.Item
import com.example.cartprojects.screens.CartScreen
import com.example.cartprojects.screens.Homepage
import com.example.cartprojects.screens.ItemScreen
import com.example.cartprojects.screens.favScreen
import com.example.cartprojects.screens.fetchItems
import com.example.cartprojects.screens.loadingState
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RootGraph() {
    val navController = rememberNavController()
    var loading by remember { mutableStateOf(true) }
    var items by remember { mutableStateOf(emptyList<Item>()) }
    var cartItems by remember { mutableStateOf(mutableListOf<Item>()) } // New cart items list
    var favItems by remember { mutableStateOf(mutableSetOf<Item>()) } // New fav items list

    // Get a SystemUiController instance
    val systemUiController = rememberSystemUiController()
    // Set the status bar color
    systemUiController.setStatusBarColor(Color.Black.copy(0.2f), darkIcons = false)
    // Set the navigation bar color
    systemUiController.setNavigationBarColor(Color.Black.copy(0.5f), darkIcons = false)

    LaunchedEffect(Unit) {
        launch(Dispatchers.IO) {
            items = fetchItems()
            withContext(Dispatchers.Main) { // Switch back to the main thread to update the UI
                loading = false
            }
        }
    }


    ProvideWindowInsets {
        val insets = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.systemBars,
            applyStart = true,
            applyEnd = true,
            applyTop = true,
            applyBottom = true
        )

        NavHost(navController, startDestination = "home", modifier = Modifier.padding(insets)) {
            composable("home") {
                if (loading) loadingState() else Homepage(navController, items, cartItems, favItems)
            }
            composable("cartScreen") { CartScreen(navController, cartItems, favItems) }
            composable("favScreen") { favScreen(navController, cartItems, favItems) }
            composable("loadingScreen") { loadingState() }
            composable("itemPage/{itemId}") { backStackEntry -> // Add itemPage route
                val itemId = backStackEntry.arguments?.getString("itemId")?.toIntOrNull()
                val item = items.find { it.id == itemId }
                if (item != null) {
                    ItemScreen(navController, item) // Display ItemPage if item is found
                } else {
                    ItemScreen(
                        navController,
                        items[0]
                    ) // Display the first item if item is not found
                }
            }
        }
    }
}