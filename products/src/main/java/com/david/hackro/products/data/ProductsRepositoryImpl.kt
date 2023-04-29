package com.david.hackro.products.data

import com.david.hackro.core.resultOf
import com.david.hackro.products.data.local.ProductEntity
import com.david.hackro.products.data.local.ProductsDao
import com.david.hackro.products.data.remote.ProductsApi
import com.david.hackro.products.data.remote.ResponseProduct
import com.david.hackro.products.data.remote.toEntity
import com.david.hackro.products.domain.Product
import com.david.hackro.products.domain.ProductsRepository
import com.david.hackro.products.domain.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val remoteSource: ProductsApi,
    private val localSource: ProductsDao
) :
    ProductsRepository {

    override fun getAllProducts(): Flow<Result<List<Product>>> = flow {
        val localResponse = getAllLocalProducts()

        if (localResponse.isSuccess && !localResponse.getOrNull().isNullOrEmpty()) {
            val response = localResponse.map { listEntity ->
                listEntity.map { product ->
                    product.toDomain()
                }
            }

            emit(response)

            getAllRemoteProducts().onSuccess {
                val entityList = it.map { responseProduct ->
                    responseProduct.toEntity()
                }

                localSource.insertAllProducts(entityList)
            }.onFailure {
                Timber.e("${ProductsRepositoryImpl::class.simpleName} $it")
            }

        } else {

            getAllRemoteProducts().onSuccess {
                val entityList = it.map { responseProduct ->
                    responseProduct.toEntity()
                }

                localSource.insertAllProducts(entityList)

                emit(Result.success(entityList.map { it.toDomain() }))

            }.onFailure {
                Timber.e("${ProductsRepositoryImpl::class.simpleName} $it")
                emit(Result.failure(it))
            }
        }

    }

    override fun getProductById(id: Int): Flow<Result<Product>> = flow {
        val localResponse = getLocalProductById(id)

        if (localResponse.isSuccess) {
            emit(localResponse.map { it.toDomain() })

            getRemoteProductById(id).onSuccess { remoteResponse ->
                localSource.insertProduct(remoteResponse.toEntity())
            }.onFailure {
                Timber.d("${ProductsRepositoryImpl::class.simpleName} $it")
            }

        } else {
            getRemoteProductById(id).onSuccess { remoteResponse ->
                val entity = remoteResponse.toEntity()

                emit(Result.success(entity.toDomain()))

                localSource.insertProduct(entity)
            }.onFailure {
                Timber.d("${ProductsRepositoryImpl::class.simpleName} $it")
            }
        }
    }

    private suspend fun getAllRemoteProducts(): Result<List<ResponseProduct>> = resultOf {
        remoteSource.getAllProducts()
    }

    private suspend fun getRemoteProductById(id: Int): Result<ResponseProduct> = resultOf {
        remoteSource.getProductById(id)
    }

    private suspend fun getAllLocalProducts(): Result<List<ProductEntity>> =
        resultOf { localSource.getAllProducts() }


    private suspend fun getLocalProductById(id: Int): Result<ProductEntity> =
        resultOf { localSource.getProductById(id) }

}

