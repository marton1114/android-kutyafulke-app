package com.example.kutyafulke.presentation.ui.breeds.components.breed_modal_bottom_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.kutyafulke.presentation.theme.Typography

@Composable
fun BreedStatCard(
    breedStatItem: BreedStatItem,
    modifier: Modifier = Modifier,
    padding: Dp = 5.dp,
    cornerSize: Dp = 20.dp,
    height: Dp = 16.dp,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, padding),
        verticalAlignment = Alignment.Bottom
    ) {
        Icon(
            imageVector = breedStatItem.icon,
            contentDescription = "",
            modifier = Modifier.size(height * 3)
        )
        Column(
            modifier = Modifier
                .padding(padding, 0.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = breedStatItem.title,
                style = Typography.titleMedium,
            )
            LinearProgressIndicator(
                progress = breedStatItem.data.toFloat().div(5.0F),
                modifier = Modifier
                    .padding(0.dp, padding)
                    .fillMaxWidth()
                    .height(height)
                    .clip(RoundedCornerShape(cornerSize))
            )

        }
    }
}

@Preview
@Composable
fun BreedStatCardPreview() {
    BreedStatCard(
        breedStatItem = BreedStatItem(
            Icons.Filled.Info,
            "Test",
            0.4
        )
    )
}