package com.david.hackro.products.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.david.hackro.products.domain.Product
import com.david.hackro.products.presentation.HomeViewModel


@Composable
fun ProductsGrid(state: HomeViewModel.State) {
    if (state.categories.isEmpty()) return

    Column(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(8.dp, 0.dp, 8.dp, 0.dp)) {

        Text(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            textAlign = TextAlign.Left,
            text = "Results",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                shadow = Shadow(
                    color = Color.Black
                )
            )
        )

        if (state.emptyResult) {
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "EMPY RESULTS",
                    style = TextStyle(
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        shadow = Shadow(
                            color = Color.Black
                        )
                    )
                )
            }
        } else {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth(),
                columns = GridCells.Fixed(3),
            ) {
                items(items = state.searchResult) { product ->
                    ProductItem(product)
                }
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductItem(item: Product) {
    Column(
        modifier = Modifier.wrapContentSize().padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            modifier = Modifier
                .clickable { },
            elevation = 10.dp,
            shape = RoundedCornerShape(10.dp)
        ) {
            GlideImage(
                model = item.image,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(90.dp)
                    .clickable {
                        //onRefreshAdviceListener.invoke()
                    },
                contentDescription = ""
            )
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = item.title.toString(),
            maxLines = 3
        )

    }
}