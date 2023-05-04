package com.david.hackro.products.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.david.hackro.products.R
import com.david.hackro.products.domain.Category
import com.david.hackro.products.presentation.component.BannerComponent
import com.david.hackro.products.presentation.component.CategoryGrid
import com.david.hackro.products.presentation.component.FlashSellGrid
import com.david.hackro.products.presentation.component.ProductsGrid
import com.david.hackro.products.presentation.component.TopBar
import com.david.hackro.products.presentation.ui.theme.StoreTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home(homeViewModel)
                }
            }
        }
    }
}

@Composable
fun Home(viewModel: HomeViewModel) {
    val uiState by viewModel.state.collectAsState()
    val onValueChange: (String) -> Unit = { text ->
        viewModel.searchProducts(text)
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        if (uiState.searchResult.isEmpty()) {
            BannerComponent(uiState)
            Spacer(Modifier.size(8.dp))
        }
        TopBar(onValueChange)
        Spacer(Modifier.size(16.dp))
        if (uiState.searchResult.isNotEmpty()) {

            ProductsGrid(uiState)
        } else {

            CategoryGrid(uiState)
            Spacer(Modifier.size(16.dp))
            FlashSellGrid(uiState)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StoreTheme {
        val categories =
            HomeViewModel.State(
                listOf(
                    Category("Fashion", R.drawable.electronics),
                    Category("Home", R.drawable.jewelry),
                    Category("Watch", R.drawable.mens),
                    Category("Bags", R.drawable.womens)
                )
            )


        //Home()
    }
}