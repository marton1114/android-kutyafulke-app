package com.example.kutyafulke.presentation.ui.home.owned_dog_item.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    label: String,
    typeLabel: String = "",
    value: String,
    onValueChange: (newValue: String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    cornerSize: Dp = 20.dp,
    borderWidth: Dp = 2.dp,
) {
    val maxLength = 12

    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            if (it.length <= maxLength)
                onValueChange(it)
        },
        textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.primary),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        decorationBox = { innerTextField ->
            Column(horizontalAlignment = Alignment.Start) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(36.dp)
                        .border(width = borderWidth, color = MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(cornerSize))
                        .clip(RoundedCornerShape(cornerSize))
                        .background(MaterialTheme.colorScheme.inverseOnSurface)
                ) {
                    Text(
                        text = label,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                    innerTextField()
                }
            }
        }
    )
}
