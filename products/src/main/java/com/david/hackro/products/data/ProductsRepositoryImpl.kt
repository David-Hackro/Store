package com.david.hackro.products.data

import androidx.annotation.CheckResult
import com.david.hackro.core.resultOf
import com.david.hackro.products.data.local.CategoryEntity
import com.david.hackro.products.data.local.ProductEntity
import com.david.hackro.products.data.local.ProductsDao
import com.david.hackro.products.data.local.bannerList
import com.david.hackro.products.data.local.categoryList
import com.david.hackro.products.data.remote.ProductsApi
import com.david.hackro.products.data.remote.ResponseProduct
import com.david.hackro.products.data.remote.toEntity
import com.david.hackro.products.domain.Category
import com.david.hackro.products.domain.Product
import com.david.hackro.products.domain.ProductsRepository
import com.david.hackro.products.domain.toDomain
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retryWhen
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val remoteSource: ProductsApi,
    private val localSource: ProductsDao
) : ProductsRepository {

    @CheckResult
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
                val entityList = it.map { responseProduct -> responseProduct.toEntity() }

                localSource.insertAllProducts(entityList)

                emit(Result.success(entityList.map { it.toDomain() }))

            }.onFailure {
                Timber.e("${ProductsRepositoryImpl::class.simpleName} $it")
                emit(Result.failure(it))
            }
        }

    }

    @CheckResult
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


    private fun getCategoryIcon(category: String): Int {
        return when (category) {
            "electronics" -> categoryList[0]
            "jewelery" -> categoryList[1]
            "men's clothing" -> categoryList[2]
            "women's clothing" -> categoryList[3]
            else -> categoryList[3]
        }
    }

    override fun getCategories(): Flow<Result<List<Category>>> = flow {
        val localResponse = getLocalCategories()

        if (localResponse.isSuccess && !localResponse.getOrNull().isNullOrEmpty()) {

            val response = localResponse.map { categoryList ->
                categoryList.map { categoryEntity ->
                    categoryEntity.toDomain(getCategoryIcon(categoryEntity.category))
                }
            }

            emit(response)

            getRemoteCategories().onSuccess {
                localSource.insertCategories(it.map { createCategoryEntity(it) })
            }.onFailure {
                Timber.d("${ProductsRepositoryImpl::class.simpleName} $it")
            }

        } else {
            getRemoteCategories().onSuccess {
                localSource.insertCategories(it.map { createCategoryEntity(it) })

                emit(Result.success(it.map { category ->
                    createCategoryEntity(category).toDomain(
                        getCategoryIcon(category)
                    )
                }))
            }.onFailure {
                emit(Result.failure(it))
            }
        }
    }

    override fun getFlashProducts(): Flow<Result<List<Product>>> = flow {
        val localResponse = getLocalFlashProducts()

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
                val entityList = it.map { responseProduct -> responseProduct.toEntity() }

                localSource.insertAllProducts(entityList)

                emit(Result.success(entityList.map { it.toDomain() }))

            }.onFailure {
                Timber.e("${ProductsRepositoryImpl::class.simpleName} $it")
                emit(Result.failure(it))
            }
        }
    }

    override fun getFlashProductsx(): Flow<Result<List<Category>>> = flow {
        localSource.getCategoriesx().map { itemList ->
            itemList.map { it.toDomain(getCategoryIcon(it.category)) }
        }.onEach { result ->
            if (result.isEmpty()) {
                refreshCategories()
            }
        }.map {
            emit(Result.success(it))
        }.catch {
            if (it is CancellationException) {
                throw it
            }

            emit(Result.failure(it))
        }
    }

    private suspend fun refreshCategories() {
        remoteSource
            .getCategories()
            .map { category ->
                createCategoryEntity(category)
            }
            .also {
                localSource.insertCategories(it)
            }
    }

    override fun getProductsByTextOrCategory(
        text: String,
        category: String
    ): Flow<Result<List<Product>>> = flow {

        val localResponse: Result<List<ProductEntity>> = when {
            text.isNotEmpty() && category.isNotEmpty() -> getLocalProductsByTextAndCategory(
                text,
                category
            )

            text.isNotEmpty() -> getLocalProductsByText(text)
            category.isNotEmpty() -> getLocalProductsByCategory(category)
            else -> getAllLocalProducts()
        }


        if (localResponse.isSuccess && !localResponse.getOrNull().isNullOrEmpty()) {
            val response = localResponse.map { listEntity ->
                listEntity.map { product ->
                    product.toDomain()
                }
            }

            emit(response)

        } else {
            emit(Result.failure(Exception()))
        }
    }

    override fun getBanners(): Flow<Result<List<Int>>> = flow {
        emit(Result.success(bannerList))
    }

    private fun createCategoryEntity(category: String): CategoryEntity {
        return CategoryEntity().apply {
            this.category = category
        }
    }

    private suspend fun getAllRemoteProducts(): Result<List<ResponseProduct>> = resultOf {
        remoteSource.getAllProducts()
    }

    private suspend fun getRemoteProductById(id: Int): Result<ResponseProduct> = resultOf {
        remoteSource.getProductById(id)
    }

    private suspend fun getRemoteCategories(): Result<List<String>> = resultOf {
        remoteSource.getCategories()
    }

    private suspend fun getAllLocalProducts(): Result<List<ProductEntity>> =
        resultOf { localSource.getAllProducts() }


    private suspend fun getLocalProductById(id: Int): Result<ProductEntity> =
        resultOf { localSource.getProductById(id) }


    private suspend fun getLocalCategories(): Result<List<CategoryEntity>> =
        resultOf { localSource.getCategories() }


    private suspend fun getLocalFlashProducts(): Result<List<ProductEntity>> =
        resultOf { localSource.getFlashProducts() }

    private suspend fun getLocalProductsByText(text: String): Result<List<ProductEntity>> =
        resultOf { localSource.getProductsByText(text) }

    private suspend fun getLocalProductsByTextAndCategory(
        text: String,
        category: String
    ): Result<List<ProductEntity>> =
        resultOf { localSource.getProductsByTextAndCategory(text, category) }

    private suspend fun getLocalProductsByCategory(
        category: String
    ): Result<List<ProductEntity>> =
        resultOf { localSource.getProductsByCategory(category) }
}