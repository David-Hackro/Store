package com.david.hackro.products.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.hackro.products.domain.GetAllProductsUseCase
import com.david.hackro.products.domain.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {


    init {
        loadCatalog()
    }

    private fun loadCatalog() {
        getAllProducts()
    }

    private fun getAllProducts() = viewModelScope.launch {
        getAllProductsUseCase.invoke().collect { result ->
            result.onSuccess {
                it
            }.onFailure {
                it
            }
        }
    }
}