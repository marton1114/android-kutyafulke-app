package com.example.kutyafulke.presentation.ui.home.owned_dog_item.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun UnverifyButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        contentPadding = PaddingValues(0.dp),
//        border = BorderStroke(1.5.dp, Color.Red),
        modifier = modifier
            .width(40.dp)
            .height(30.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "",
//            tint = Color.Red
        )
    }
}

@Preview
@Composable
fun UnverifyButtonPreview() {
    UnverifyButton() {}
}