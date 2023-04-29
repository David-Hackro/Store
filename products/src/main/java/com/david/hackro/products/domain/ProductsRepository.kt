package com.david.hackro.products.domain

import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    fun getAllProducts(): Flow<Result<List<Product>>>

    fun getProductById(id: Int): Flow<Result<Product>>
}