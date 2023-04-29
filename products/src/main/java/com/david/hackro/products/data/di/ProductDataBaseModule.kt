package com.david.hackro.products.data.di

import android.content.Context
import androidx.room.Room
import com.david.hackro.products.data.local.ProductsDao
import com.david.hackro.products.data.local.ProductsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val PRODUCTS_DB = "products_db"

@Module
@InstallIn(SingletonComponent::class)
object ProductDataBaseModule {

    @Singleton
    @Provides
    fun provideProductsDataBase(@ApplicationContext context: Context): ProductsDatabase {
        return Room.databaseBuilder(context, ProductsDatabase::class.java, PRODUCTS_DB).build()
    }

    @Singleton
    @Provides
    fun provideProductsDao(database: ProductsDatabase): ProductsDao {
        return database.ProductsDao()
    }

}