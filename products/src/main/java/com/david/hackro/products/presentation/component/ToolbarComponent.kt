package com.david.hackro.products.presentation.component

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp)) {
        var textState by remember { mutableStateOf("") }
        val lightBlue = Color(0xffe8e8e8)

        TextField(
            modifier = Modifier.fillMaxWidth(),
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
                Text("clocks, sneakers, etc..", style = TextStyle(Color(0xff7777777)))
            },
            shape = RoundedCornerShape(24.dp),
            singleLine = true,
            trailingIcon = {
                if (textState.isNotEmpty()) {
                    IconButton(onClick = {
                        textState = ""
                        onValueChange.invoke("")
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = null
                        )
                    }
                }
            }
        )
    }
}
