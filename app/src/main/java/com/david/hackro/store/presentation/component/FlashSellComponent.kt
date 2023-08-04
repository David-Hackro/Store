package com.david.hackro.store.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.david.hackro.products.domain.Product
import com.david.hackro.store.R
import com.david.hackro.store.presentation.HomeViewModel

@Composable
fun FlashSellGrid(state: HomeViewModel.State) {
    if (state.flashProducts.isNullOrEmpty()) return

    Column(modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp)) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left,
            text = "Flash Sell",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                shadow = Shadow(
                    color = Color.Black
                )
            )
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(all = 10.dp)
        ) {

            items(items = state.flashProducts) {
                FlshSellItem(it)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FlshSellItem(item: Product) {
    /* Column(
         modifier = Modifier
             .padding(10.dp)
             .fillMaxWidth()
             .height(90.dp),
         horizontalAlignment = Alignment.CenterHorizontally,
         //verticalArrangement = Arrangement.Center,
     ) {
         Card(
             modifier = Modifier
                 .clickable { },
             elevation = 10.dp,
             shape = RoundedCornerShape(10.dp)
         ) {
             Row {
                 Column {

                     GlideImage(
                         model = item.image,
                         contentScale = ContentScale.Crop,
                         modifier = Modifier
                             .fillMaxHeight()
                             .width(90.dp)
                             .clickable {
                                 //onRefreshAdviceListener.invoke()
                             },
                         contentDescription = ""
                     )

                     *//*                    Image(
                                            painter = painterResource(R.mipmap.jewelery),
                                            contentScale = ContentScale.Crop,
                                            contentDescription = "",
                                            modifier = Modifier.fillMaxHeight().width(90.dp)
                                                .clickable {
                                                    //onRefreshAdviceListener.invoke()
                                                })*//*
                }

                Spacer(Modifier.size(8.dp))

                Column(modifier = Modifier.width(150.dp)) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Left,
                        maxLines = 2,
                        text = item.title.toString()
                    )

                    Spacer(Modifier.size(2.dp))


                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Left,
                        text = "$${item.price} MXN"
                    )

                    Spacer(Modifier.size(2.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxHeight()
                    ) {

                        Image(
                            painter = painterResource(android.R.drawable.star_off),
                            contentScale = ContentScale.Crop,
                            contentDescription = "",
                            modifier = Modifier.size(16.dp)
                        )

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Left,
                            text = item.rating?.rate.toString()
                        )
                    }
                }
            }
        }
    }*/


    Column(
        modifier = Modifier
            .width(150.dp),
    ) {

        Card(
            modifier = Modifier
                .size(150.dp),
            elevation = 2.dp,
            shape = RoundedCornerShape(10.dp)
        ) {

            Box(
                modifier = Modifier.background(color = colorResource(id = R.color.search_bar_backgroundd)),
                contentAlignment = Alignment.Center,
            ) {
                GlideImage(
                    model = item.image,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .fillMaxHeight()
                        .size(90.dp)

                        .clickable {
                            //onRefreshAdviceListener.invoke()
                        },
                    contentDescription = ""
                )
            }
        }

        Row(Modifier.height(25.dp)) {
            Text(text = item.title.toString(),
                modifier = Modifier.width(100.dp),
                textAlign = TextAlign.Left,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)
            Text(
                text = "$${item.price.toString()}",
                modifier = Modifier.width(50.dp),
                textAlign = TextAlign.Left,
                maxLines = 2,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(Modifier.size(2.dp))
        Row {
            Text(text = item.description.toString(),
                maxLines = 2,
                fontWeight = FontWeight.Light)
        }
    }
}

@Preview
@Composable
fun previewsh() {
    val product = Product(
        id = 1,
        image = "https://purepng.com/public/uploads/large/purepng.com-mens-polo-shirtpolo-shirtcottongarmentsfebricmenskolargrey-1421526391680vyoyr.png",
        price = 10.0,
        description = "description temporal",
        title = "polo men",
        category = "men"

    )

    FlshSellItem(product)
}