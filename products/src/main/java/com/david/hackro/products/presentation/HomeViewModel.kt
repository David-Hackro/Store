package com.david.hackro.products.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.hackro.products.domain.Category
import com.david.hackro.products.domain.GetAllProductsUseCase
import com.david.hackro.products.domain.GetBannersUseCase
import com.david.hackro.products.domain.GetCategoriesUseCase
import com.david.hackro.products.domain.GetFlashProductsUseCase
import com.david.hackro.products.domain.GetProductByIdUseCase
import com.david.hackro.products.domain.GetProductsByTextOrCategoryUseCase
import com.david.hackro.products.domain.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getFlashProductsUseCase: GetFlashProductsUseCase,
    private val getBannersUseCase: GetBannersUseCase,
    private val getProductsByTextOrCategoryUseCase: GetProductsByTextOrCategoryUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    init {
        loadCatalog()
    }

    private fun loadCatalog() {
        getBanners()
        getCategories()
        getFlashProducts()
    }

    private fun getFlashProducts() = viewModelScope.launch {
        getFlashProductsUseCase.invoke().collect { result ->
            result.onSuccess { productList ->
                _state.update {
                    it.copy(
                        flashProducts = productList
                    )
                }
                Timber.i("")
            }.onFailure {
                it
                Timber.i("")
            }
        }
    }

    private fun getCategories() = viewModelScope.launch {
        getCategoriesUseCase.invoke().collect { result ->
            result.onSuccess { categoryList ->
                _state.update {
                    it.copy(
                        categories = categoryList
                    )
                }
                Timber.i("")
            }.onFailure {
                it
                Timber.i("")
            }
        }
    }


    private fun getBanners() = viewModelScope.launch {
        getBannersUseCase.invoke().collect { result ->
            result.onSuccess { bannerList ->
                _state.update {
                    it.copy(
                        banners = bannerList
                    )
                }
                Timber.i("")
            }.onFailure {
                it
                Timber.i("")
            }
        }
    }

    fun searchProducts(text: String = "", category: String = "") = viewModelScope.launch {

        if (text.trim().isEmpty()) {
            _state.update {
                it.copy(searchResult = listOf())
            }

        } else {
            val params = GetProductsByTextOrCategoryUseCase.Params(text, category)
            getProductsByTextOrCategoryUseCase.invoke(params).collect { result ->
                result.onSuccess { productList ->
                    _state.update {
                        it.copy(
                            searchResult = productList,
                            emptyResult = false
                        )
                    }
                    Timber.i("")
                }.onFailure {
                    it
                    Timber.i("")
                    _state.update {
                        it.copy(emptyResult = true)
                    }

                }
            }
        }
    }

    data class State(
        val categories: List<Category> = listOf(),
        val flashProducts: List<Product> = listOf(),
        val banners: List<Int> = listOf(),
        val searchResult: List<Product> = listOf(),
        val emptyResult: Boolean = false
    )
}