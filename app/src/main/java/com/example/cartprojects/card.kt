package com.example.cartprojects

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.cartprojects.data.models.Item
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CardWithShape(
    navController: NavController,
    item: Item,
    cartItems: MutableList<Item>,// Add cartItems parameter
    favItems: MutableSet<Item>,// Add favItems parameter
    state: Boolean


) {
    val isClicked = remember { mutableStateOf(state) }
    val snackbarHostState = remember { SnackbarHostState() } // New state for the Snackbar
    val scope = rememberCoroutineScope() // Remember a CoroutineScope

    val paddingModifier = Modifier
        .padding(10.dp)
        .aspectRatio(0.5f)
        .height(500.dp)
    Card(
        shape = RoundedCornerShape(14.dp), modifier = paddingModifier
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = rememberImagePainter(data = item.thumbnail), // Use the thumbnail URL to load the image
                contentDescription = "Product Image",
                modifier = Modifier
                    .clickable { navController.navigate("itemScreen/${item.id}") }
                    .clip(RoundedCornerShape(13.dp))
                    .height(150.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop // Optional: Crop the image if it's not a perfect square
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                item.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                item.description, style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text("Price: ${item.price}")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Stock: ${item.stock}")
                }

                IconButton(onClick = {
                    isClicked.value = !isClicked.value
                    if (isClicked.value) {
                        favItems.add(item)
                        scope.launch {
                            snackbarHostState.showSnackbar("Item added to favorites")
                        }
                    } else {
                        favItems.remove(item)
                    }
                }) {
                    if (isClicked.value) {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Favorite",
                            tint = Color.Red
                        )
                    } else {
                        Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Favorite")
                    }
                }
            }
            Box(modifier = Modifier.height(10.dp))
            Row {
                ElevatedButton(
                    onClick = {
                        // Add the item to the cart when the button is clicked
                        cartItems.add(item)
                        // Show a Snackbar
                        scope.launch {
                            snackbarHostState.showSnackbar("Item added to cart")
                        }

                    }, colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        "Add to Cart",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
    // Add a SnackbarHost to your layout
    SnackbarHost(hostState = snackbarHostState)
}
