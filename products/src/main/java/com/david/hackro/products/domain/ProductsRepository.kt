package com.david.hackro.products.domain

import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    fun getAllProducts(): Flow<Result<List<Product>>>

    fun getProductById(id: Int): Flow<Result<Product>>

    fun getCategories(): Flow<Result<List<Category>>>

    fun getFlashProducts(): Flow<Result<List<Product>>>

    fun getFlashProductsx(): Flow<Result<List<Category>>>

    fun getProductsByTextOrCategory(text: String, category: String): Flow<Result<List<Product>>>

    fun getBanners(): Flow<Result<List<Int>>>
}