package com.david.hackro.products.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFlashProductsUseCase @Inject constructor(private val repository: ProductsRepository) {

    fun invoke(): Flow<Result<List<Product>>> {
        return repository.getFlashProducts()
    }
}