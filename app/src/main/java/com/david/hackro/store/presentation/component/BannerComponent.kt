package com.david.hackro.store.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.david.hackro.store.presentation.HomeViewModel

@Composable
fun BannerComponent(uiState: HomeViewModel.State) {
    if (uiState.banners.isNullOrEmpty()) return

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(1.5.dp),
    ) {

        items(items = uiState.banners) {
            BannerItem(it)
        }
    }
}


@Composable
fun BannerItem(resourceId: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 10.dp,
        shape = RoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp)
    ) {
        Image(
            painter = painterResource(resourceId),
            contentScale = ContentScale.Crop,
            contentDescription = "",
            modifier = Modifier.fillMaxWidth().height(200.dp)
                .clickable {
                    //onRefreshAdviceListener.invoke()
                })
    }
}
