package com.david.hackro.products.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBannersUseCase @Inject constructor(private val repository: ProductsRepository) {

    fun invoke(): Flow<Result<List<Int>>> {
        return repository.getBanners()
    }
}