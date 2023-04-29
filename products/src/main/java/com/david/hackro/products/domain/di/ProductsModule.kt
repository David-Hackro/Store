package com.david.hackro.products.domain.di

import com.david.hackro.products.data.ProductsRepositoryImpl
import com.david.hackro.products.data.remote.ProductsApi
import com.david.hackro.products.domain.GetAllProductsUseCase
import com.david.hackro.products.domain.GetProductByIdUseCase
import com.david.hackro.products.domain.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [ProductsModule.BindRepository::class])
@InstallIn(SingletonComponent::class)
object ProductsModule {


    @Provides
    @Singleton
    fun provideAdviceApi(retrofit: Retrofit): ProductsApi {
        return retrofit.create(ProductsApi::class.java)
    }


    @Provides
    @Singleton
    fun provideGetAllProductsUseCase(repository: ProductsRepository): GetAllProductsUseCase {
        return GetAllProductsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetProductByIdUseCase(repository: ProductsRepository): GetProductByIdUseCase {
        return GetProductByIdUseCase(repository)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindRepository {
        @Binds
        @Singleton
        fun bindRepository(productsRepository: ProductsRepositoryImpl): ProductsRepository
    }
}