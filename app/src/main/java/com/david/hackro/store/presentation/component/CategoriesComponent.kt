package com.david.hackro.store.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.david.hackro.products.domain.Category
import com.david.hackro.store.R
import com.david.hackro.store.presentation.HomeViewModel

/*@Composable
fun CategoryGrid(state: HomeViewModel.State, onCategoryClicked: (String) -> Unit) {
    if (state.categories.isEmpty()) return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp, 0.dp, 8.dp, 0.dp)
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            textAlign = TextAlign.Left,
            text = "Categories",
            style = TextStyle(
                fontSize = 20.sp, fontWeight = FontWeight.Bold, shadow = Shadow(
                    color = Color.Black
                )
            )
        )

        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(3),
        ) {
            items(items = state.categories) { category ->
                CategoryItem(category, onCategoryClicked)
            }
        }
    }
}*/

/*
@Composable
fun CategoryItem(item: Category, onCategoryClicked: (String) -> Unit) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(5.dp)
            .clickable {
                onCategoryClicked.invoke(item.category)
            },
    ) {
        Card(
            elevation = 10.dp, shape = RoundedCornerShape(10.dp),
        ) {
            Image(
                painter = painterResource(item.categoryIcon),
                contentScale = ContentScale.Crop,
                contentDescription = "",
                modifier = Modifier.size(90.dp)
            )
        }

        Text(
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, text = item.category
        )

    }
}
*/

@Composable
fun categoryComponent(state: HomeViewModel.State, onCategoryClicked: (String) -> Unit) {
    LazyHorizontalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 66.dp),
        rows = GridCells.Fixed(1),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(all = 10.dp)
    ) {

        this.items(state.categories) { category ->
            CategoryItemx(category)
        }
    }
}

@Composable
fun CategoryItemx(item: Category) {
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .clickable { },
        elevation = 2.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp),
        ) {

            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(60.dp)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp))
                    .background(colorResource(id = R.color.search_bar_backgrounddd)),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(id = item.categoryIcon),
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )
            }

            Text(
                text = item.category,
                modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun preview() {
    val category = Category("Headset", R.drawable.cart)
    val state = HomeViewModel.State(categories = listOf(category, category, category))
    val onCategoryClicked: (String) -> Unit = { category ->

    }

    categoryComponent(state, onCategoryClicked)
}