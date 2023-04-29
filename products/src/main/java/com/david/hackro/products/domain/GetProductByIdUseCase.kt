package com.david.hackro.products.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(private val repository: ProductsRepository) {

    fun invoke(params: Params): Flow<Result<Product>> {
        return repository.getProductById(params.id)
    }

    data class Params(val id: Int)
}