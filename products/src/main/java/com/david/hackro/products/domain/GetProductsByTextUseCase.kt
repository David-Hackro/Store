package com.david.hackro.products.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsByTextOrCategoryUseCase @Inject constructor(private val repository: ProductsRepository) {

    fun invoke(params: Params): Flow<Result<List<Product>>> {
        return repository.getProductsByTextOrCategory(params.text, params.category)
    }

    data class Params(val text: String, val category: String)
}