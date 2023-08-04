package com.david.hackro.store

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.david.hackro.store.presentation.HomeViewModel
import com.david.hackro.store.presentation.component.BottomNavigationBar
import com.david.hackro.store.presentation.component.FlashSellGrid
import com.david.hackro.store.presentation.component.ProductsGrid
import com.david.hackro.store.presentation.component.SearchBar
import com.david.hackro.store.presentation.component.categoryComponent
import com.david.hackro.store.presentation.ui.theme.StoreTheme
import com.google.android.material.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    val onValueChange: (String) -> Unit = {

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            StoreTheme {
                Bundle()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Scaffold(
                        bottomBar = { BottomNavigationBar() },
                        topBar = {
                            toolbar(onValueChange)
                        },
                        content = { padding ->
                            Box(modifier = Modifier.padding(padding)) {
                                Home(homeViewModel)
                            }
                        }

                    )
                }
            }
        }
    }
}


@Composable
fun toolbar(onValueChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .padding(10.dp, 10.dp, 10.dp, 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier
                .padding(0.dp, 0.dp, 10.dp, 0.dp)
                .size(50.dp)
                .background(Color.White)
                .shadow(1.dp)
                .border(
                    1.dp,
                    Color.Transparent,
                    shape = RoundedCornerShape(32.dp),
                ),
            onClick = { }
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = null
            )
        }
        SearchBar(onValueChange)
    }
}


/*
setContent {
            StoreTheme {
                Bundle()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Scaffold(
                        bottomBar = { BottomNavigationBar() },
                        content = { padding ->
                            Box(modifier = Modifier.padding(padding)) {
                                Home(homeViewModel)
                            }
                        }

                    )
                }
            }
        }
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Home(viewModel: HomeViewModel) {
    val uiState by viewModel.state.collectAsState()

    val focusManager = LocalFocusManager.current

    val onValueChange: (String) -> Unit = { text ->
        viewModel.searchProducts(text)
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    val onCategoryClicked: (String) -> Unit = { category ->
        viewModel.openCategoryResults(category)
    }

    val closeSearchResults: () -> Unit = {
        viewModel.closeSearchResults()
        keyboardController?.hide()
        focusManager.clearFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White),
    ) {

        categoryComponent(uiState, onCategoryClicked)

        Spacer(Modifier.size(8.dp))
        Divider(modifier = Modifier.height(0.5.dp).fillMaxWidth().background(colorResource(id = com.david.hackro.store.R.color.divider)))
        Spacer(Modifier.size(16.dp))

        FlashSellGrid(uiState)
        Spacer(Modifier.size(16.dp))
        FlashSellGrid(uiState)
        Spacer(Modifier.size(16.dp))
        FlashSellGrid(uiState)
    }
}

@Preview
@Composable
fun mainScreen() {
    val onValueChange: (String) -> Unit = {

    }

    StoreTheme {
        toolbar(onValueChange)

    }
}