package com.david.hackro.products.data.remote

import com.david.hackro.products.data.local.ProductEntity
import com.google.gson.annotations.SerializedName

data class ResponseProduct(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("image")
    val image: String? = null,

    @SerializedName("price")
    val price: Double? = null,

    @SerializedName("rating")
    val rating: Rating? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("category")
    val category: String? = null
)

data class Rating(

    @SerializedName("rate")
    var rate: Double? = null,

    @SerializedName("count")
    var count: Int? = null
)


fun ResponseProduct.toEntity() = ProductEntity().apply {
    id = this@toEntity.id
    image = this@toEntity.image
    price = this@toEntity.price
    rate = this@toEntity.rating?.rate
    count = this@toEntity.rating?.count
    description = this@toEntity.description
    title = this@toEntity.title
    category = this@toEntity.category
}