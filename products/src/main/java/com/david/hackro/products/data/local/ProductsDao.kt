package com.david.hackro.products.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import java.util.concurrent.Flow

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

    @Query("SELECT * from CategoryEntity")
    suspend fun getCategories(): List<CategoryEntity>

    @Query("SELECT * from CategoryEntity")
    fun getCategoriesx(): kotlinx.coroutines.flow.Flow<List<CategoryEntity>>

    @Upsert
    suspend fun insertCategories(productList: List<CategoryEntity>)


    @Query("SELECT * from ProductEntity ORDER BY RANDOM() LIMIT 5")
    suspend fun getFlashProducts(): List<ProductEntity>

    @Query("SELECT * from ProductEntity WHERE title  LIKE '%' || :text || '%'")
    suspend fun getProductsByText(text: String): List<ProductEntity>

    @Query("SELECT * from ProductEntity WHERE title  LIKE '%' || :text || '%' AND category=:category")
    suspend fun getProductsByTextAndCategory(text: String, category: String): List<ProductEntity>

    @Query("SELECT * from ProductEntity WHERE category=:category")
    suspend fun getProductsByCategory(category: String): List<ProductEntity>


}