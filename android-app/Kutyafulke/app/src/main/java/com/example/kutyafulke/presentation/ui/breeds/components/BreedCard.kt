package com.example.kutyafulke.presentation.ui.breeds.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.kutyafulke.data.models.Breed
import com.example.kutyafulke.presentation.theme.Typography

@Composable
fun BreedCard(
    breed: Breed,
    modifier: Modifier = Modifier,
    padding: Dp = 10.dp,
    cornerSize: Dp = 20.dp,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(padding, 0.dp, padding, padding)
            .clip(shape = RoundedCornerShape(cornerSize))
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        AsyncImage(
            model = breed.imageUri,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .padding(padding, padding, 0.dp, padding)
                .clip(shape = RoundedCornerShape(cornerSize, 0.dp, 0.dp, cornerSize))
                .weight(1F)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, padding, padding, padding)
                .clip(shape = RoundedCornerShape(0.dp, cornerSize, cornerSize, 0.dp))
                .background(MaterialTheme.colorScheme.background)
                .weight(2F),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = breed.hungarianName,
                style = Typography.titleLarge,
                modifier = Modifier
                    .padding(padding, 0.dp)
            )
            Text(
                text = "(${breed.englishName})",
                style = Typography.titleMedium,
                modifier = Modifier
                    .padding(padding, 0.dp)
            )
        }

    }
}