package com.example.cartprojects.screens//package com.example.cartprojects

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cartprojects.CardWithShape
import com.example.cartprojects.data.models.Item


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun favScreen(
    navController: NavController,
    cartItems: MutableList<Item>,
    favItems: MutableSet<Item>
) {
    var rebuildTrigger by remember { mutableStateOf(false) } // New state variable

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorite") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        favItems.clear()
                        rebuildTrigger = !rebuildTrigger
                    }) {
                        Icon(
                            Icons.Filled.Clear, contentDescription = "clear"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (favItems.isEmpty()) {
            NoItemsInFavorites()
            return@Scaffold
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                items(favItems.toList()) { item -> // Use cartItems instead of CardWithShapeProvider().values.toList()
                    CardWithShape(
                        navController,
                        item,
                        cartItems, // Pass cartItems to CardWithShape
                        favItems,
                        state = favItems.contains(item) // Pass true if the item is in favItems
                    )

                }

            }
        }
    }
    if (rebuildTrigger) { // Use the value of rebuildTrigger
        // This space intentionally left blank. It's just to make sure that rebuildTrigger is being read, so that changes to it will trigger a recomposition.
    }
}

@Composable
fun NoItemsInFavorites() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(text = "No items in favorites")
    }
}