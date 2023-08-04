package com.david.hackro.products.domain

import com.david.hackro.products.data.local.ProductEntity

data class Dictionary(val categoryId: String, val itemProducts: List<Product>)
data class Product(
    val id: Int? = null,
    val image: String? = null,
    val price: Double? = null,
    val rating: Rating? = null,
    val description: String? = null,
    val title: String? = null,
    val category: String? = null
)

data class Rating(
    val rate: Any? = null,
    val count: Int? = null
)

fun ProductEntity.toDomain() = Product(
    id = id,
    image = image,
    price = price,
    rating = Rating(rate, count),
    description = description,
    title = title,
    category = category
)