package com.david.hackro.products.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ProductsDao {

    @Upsert
    suspend fun insertAllProducts(productList: List<ProductEntity>)

    @Upsert
    suspend fun insertProduct(product: ProductEntity)

    @Query("SELECT * from ProductEntity")
    suspend fun getAllProducts(): List<ProductEntity>

    @Query("SELECT * from ProductEntity WHERE id=:id LIMIT 1")
    suspend fun getProductById(id: Int): ProductEntity

}