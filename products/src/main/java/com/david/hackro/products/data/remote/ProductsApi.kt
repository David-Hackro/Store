package com.david.hackro.products.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsApi {


    @GET("products")
    suspend fun getAllProducts(): List<ResponseProduct>


    @GET("products")
    suspend fun getProductById(@Query("per_page") id: Int): ResponseProduct


    @GET("products/categories")
    suspend fun getCategories(): List<String>

}