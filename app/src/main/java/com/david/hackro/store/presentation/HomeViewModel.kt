package com.david.hackro.store.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.hackro.products.domain.Category
import com.david.hackro.products.domain.GetAllProductsUseCase
import com.david.hackro.products.domain.GetBannersUseCase
import com.david.hackro.products.domain.GetCategoriesUseCase
import com.david.hackro.products.domain.GetFlashProductsUseCase
import com.david.hackro.products.domain.GetFlashProductsxUseCase
import com.david.hackro.products.domain.GetProductByIdUseCase
import com.david.hackro.products.domain.GetProductsByTextOrCategoryUseCase
import com.david.hackro.products.domain.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getFlashProductsUseCase: GetFlashProductsUseCase,
    private val getFlashProductsxUseCase: GetFlashProductsxUseCase,
    private val getBannersUseCase: GetBannersUseCase,
    private val getProductsByTextOrCategoryUseCase: GetProductsByTextOrCategoryUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()
    private var category = ""

    init {
        loadCatalog()
    }

    private fun loadCatalog() {
        getBanners()
        getCategories()
        getFlashProducts()
        getFlashProductsx()
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

    private fun getFlashProductsx() = viewModelScope.launch {
        getFlashProductsxUseCase.invoke().collect { result ->
            result.onSuccess {

            }.onFailure {

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


    fun searchProducts(text: String = "") = viewModelScope.launch {

        _state.update {
            it.copy(isSearchActive = true)
        }

        if (text.trim().isEmpty() && category.isEmpty()) {
            getAllProducts()
        } else {
            val params = GetProductsByTextOrCategoryUseCase.Params(text, category)
            getProductsByTextOrCategoryUseCase.invoke(params).collect { result ->
                result.onSuccess { productList ->
                    _state.update {
                        it.copy(
                            searchResult = productList,
                        )
                    }
                    Timber.i("")
                }.onFailure {
                    it
                    Timber.i("")
                    _state.update {
                        it.copy(searchResult = listOf())
                    }

                }
            }
        }
    }

    fun openCategoryResults(category: String) = viewModelScope.launch {
        _state.update {
            it.copy(isCategoryResultActive = true)
        }

        val params = GetProductsByTextOrCategoryUseCase.Params("", category)
        getProductsByTextOrCategoryUseCase.invoke(params).collect { result ->
            result.onSuccess { productList ->
                _state.update {
                    it.copy(
                        searchResult = productList,
                    )
                }
                Timber.i("")
            }.onFailure {
                it
                Timber.i("")
                _state.update {
                    it.copy(searchResult = listOf())
                }

            }
        }
    }

    private fun getAllProducts() = viewModelScope.launch {
        getAllProductsUseCase.invoke().collect {
            it.onSuccess { result ->
                _state.update {
                    it.copy(
                        isSearchActive = true,
                        searchResult = result
                    )
                }
            }.onFailure {
                _state.update {
                    it.copy(searchResult = listOf())
                }
            }
        }
    }

    fun closeSearchResults() {
        category = ""
        _state.update {
            it.copy(isSearchActive = false, searchResult = listOf(), isCategoryResultActive = false)
        }
    }

    data class State(
        val categories: List<Category> = listOf(),
        val flashProducts: List<Product> = listOf(),
        val productLists: List<List<Product>> = listOf(),
        val banners: List<Int> = listOf(),
        val searchResult: List<Product> = listOf(),
        val isSearchActive: Boolean = false,
        val isCategoryResultActive: Boolean = false,
    )
}