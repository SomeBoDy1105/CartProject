package com.example.cartprojects

import com.example.cartprojects.data.models.Item
import com.example.cartprojects.data.models.ProductResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject


const val myurl = "https://dummyjson.com/products"

/**
 * This function parses a JSON string into a ProductResponse object using Gson.
 * It then prints the total number of products and the details of each product.
 *
 */
fun parseJson(jsonString: String) {
    // Create a new Gson instance
    val gson = Gson()

    // Define the type of the data to parse
    val productResponseType = object : TypeToken<ProductResponse>() {}.type

    // Parse the JSON string into a ProductResponse object
    val productResponse: ProductResponse = gson.fromJson(jsonString, productResponseType)

    // Print the total number of products
    println("Total: ${productResponse.total}")

    // Loop through each product in the productResponse
    for (product in productResponse.products) {
        // Print the details of each product
        println("ID: ${product.id}, Title: ${product.title}, Description: ${product.description}, Price: ${product.price}, stock: ${product.stock}, thumbnail: ${product.thumbnail}")
    }
}


/**
 * This function makes a synchronous GET request to a specified URL and returns the response body as a string.
 *
 * @return The response body as a string. If the response body is null, an empty string is returned.
 */
fun synchronousGetRequest(): String {
    // Create a new OkHttpClient instance
    val client = OkHttpClient()

    // Build a new request, specifying the URL
    val request = Request.Builder()
        .url(myurl)
        .build()

    // Execute the request and get the response
    val response = client.newCall(request).execute()

    // Return the response body as a string, or an empty string if the response body is null
    return response.body?.string() ?: ""
}

fun parseJsonToItems(jsonString: String): List<Item> {
    val jsonObject = JSONObject(jsonString)

    val total = jsonObject.getInt("total")
    println("Total: $total")

    val products = jsonObject.getJSONArray("products")
    val items = mutableListOf<Item>()

    for (i in 0 until products.length()) {
        val product = products.getJSONObject(i)

        val id = product.getInt("id")
        val name = product.getString("title")
        val description = product.getString("description")
        val price = product.getDouble("price")
        val stock = product.getInt("stock")
        val thumbnail = product.getString("thumbnail")

        val item = Item(id, name, price, description, stock, thumbnail)
        items.add(item)

        println("ID: $id, Name: $name, Description: $description, Price: $price, Stock: $stock, Thumbnail: $thumbnail")
    }

    return items
}

fun main() {
    val result = synchronousGetRequest()
    parseJson(result)
}