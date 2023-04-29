package com.david.hackro.products.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

private const val VERSION_PRODUCTS_DB = 1

@Database(
    entities = [ProductEntity::class], version = VERSION_PRODUCTS_DB
)
abstract class ProductsDatabase : RoomDatabase() {
    abstract fun ProductsDao(): ProductsDao
}