package com.example.kutyafulke.presentation.ui.main.components.test_modal_bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.kutyafulke.data.models.Breed
import com.example.kutyafulke.presentation.theme.Typography

@Composable
fun BreedCard(
    breed: Breed,
    percentage: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    padding: Dp = 5.dp,
    cornerSize: Dp = 20.dp
) {
    Row(
        modifier = modifier
            .width(300.dp)
            .height(86.dp)
            .padding(padding, 0.dp, padding.times(2), padding)
            .clip(shape = RoundedCornerShape(cornerSize))
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "%.2f%%".format(percentage * 100),
            modifier = Modifier
                .padding(padding, padding, 0.dp, padding)
                .clip(shape = RoundedCornerShape(cornerSize, 0.dp, 0.dp, cornerSize))
                .weight(1F),
            textAlign = TextAlign.Center,
            style = Typography.titleMedium
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
                style = Typography.titleMedium,
                modifier = Modifier
                    .padding(padding, 0.dp)
            )
            Text(
                text = "(${breed.englishName})",
                style = Typography.titleSmall,
                modifier = Modifier
                    .padding(padding, 0.dp)
            )
        }

    }
}

@Preview
@Composable
fun BreedCardPreview() {
    BreedCard(
        breed = Breed(
            hungarianName = "Puli",
            englishName = "Puli",
            imageUri = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/af/Csalfa_Sommer_07_10_A.jpg/800px-Csalfa_Sommer_07_10_A.jpg"
        ),
        0.5F,
        onClick = {  })
}