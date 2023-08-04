package com.david.hackro.store.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.david.hackro.store.R

@Composable
fun SearchBar(onValueChange: (String) -> Unit) {
    val focusRequester = remember { FocusRequester() }

    Column {
        var textState by remember { mutableStateOf("") }
        val lightBlue = Color(0xFFF1F1F1)

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    if (it.isFocused) {
                        // focused
                        onValueChange.invoke("")
                    }
                },
            value = textState,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = lightBlue,
                cursorColor = Color.Black,
                disabledLabelColor = lightBlue,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            onValueChange = {
                onValueChange.invoke(it)
                textState = it
            },
            placeholder = {
                Text(
                    stringResource(id = R.string.txt_search_toolbar),
                    style = TextStyle(color = colorResource(id = R.color.search_bar_background))
                )
            },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            trailingIcon = {

                val icon = if (textState.isNotEmpty()) {
                    Icons.Outlined.Close
                } else {
                    Icons.Outlined.Search
                }

                IconButton(onClick = {
                    textState = ""
                    onValueChange.invoke("")
                }
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                }
            }
        )
    }
}


@Preview
@Composable
fun toolbarComponentPreview() {
    val onValueChange: (String) -> Unit = {}
    SearchBar(onValueChange)
}