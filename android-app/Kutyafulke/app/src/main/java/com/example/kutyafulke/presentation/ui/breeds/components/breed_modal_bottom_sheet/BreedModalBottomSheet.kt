package com.example.kutyafulke.presentation.ui.breeds.components.breed_modal_bottom_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.kutyafulke.R
import com.example.kutyafulke.data.models.Breed
import com.example.kutyafulke.data.models.SurveyStats

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedModalBottomSheet(
    sheetState: SheetState,
    data: SurveyStats,
    breed: Breed,
    onDismissRequest: () -> Unit,
    imageHeight: Dp = 200.dp,
    padding: Dp = 10.dp,
) {
    val breedStatItems = listOf(
        BreedStatItem(
            icon = Icons.Filled.Info,
            title = stringResource(R.string.size_label),
            data = data.size
        ),
        BreedStatItem(
            icon = Icons.Filled.Info,
            title = stringResource(R.string.feeding_label),
            data = data.feeding
        ),
        BreedStatItem(
            icon = Icons.Filled.Info,
            title = stringResource(R.string.hair_length_label),
            data = data.hairLength
        ),
        BreedStatItem(
            icon = Icons.Filled.Info,
            title = stringResource(R.string.hair_texture_label),
            data = data.hairTexture
        ),
        BreedStatItem(
            icon = Icons.Filled.Info,
            title = stringResource(R.string.grooming_label),
            data = data.grooming
        ),
        BreedStatItem(
            icon = Icons.Filled.Info,
            title = stringResource(R.string.shedding_label),
            data = data.shedding
        ),
        BreedStatItem(
            icon = Icons.Filled.Info,
            title = stringResource(R.string.walking_label),
            data = data.walking
        ),
        BreedStatItem(
            icon = Icons.Filled.Info,
            title = stringResource(R.string.playfulness_label),
            data = data.playfulness
        ),
        BreedStatItem(
            icon = Icons.Filled.Info,
            title = stringResource(R.string.intelligence_label),
            data = data.intelligence
        ),
        BreedStatItem(
            icon = Icons.Filled.Info,
            title = stringResource(R.string.kid_tolerance_label),
            data = data.kidTolerance
        ),
        BreedStatItem(
            icon = Icons.Filled.Info,
            title = stringResource(R.string.cat_tolerance_label),
            data = data.catTolerance
        ),
        BreedStatItem(
            icon = Icons.Filled.Info,
            title = stringResource(R.string.health_problems_label),
            data = data.healthProblems
        ),
        BreedStatItem(
            icon = Icons.Filled.Info,
            title = stringResource(R.string.loudness_label),
            data = data.loudness
        ),
        BreedStatItem(
            icon = Icons.Filled.Info,
            title = stringResource(R.string.independence_label),
            data = data.independence
        ),
        BreedStatItem(
            icon = Icons.Filled.Info,
            title = stringResource(R.string.defensiveness_label),
            data = data.defensiveness
        )
    )

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        dragHandle = null,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(padding)
        ) {
            TopBar(
                mainLabel = breed.hungarianName,
                secondaryLabel = breed.englishName,
                onClose = onDismissRequest
            )
            Divider()
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = breed.imageUri,
                    modifier = Modifier
                        .height(imageHeight)
                        .clip(RoundedCornerShape(20.dp)),
                    contentDescription = "Breed image",
                    contentScale = ContentScale.FillWidth
                )
                ColorsCard(
                    icon = Icons.Filled.Info,
                    label = stringResource(R.string.color_combinations_label),
                    sideNote = stringResource(R.string.color_combinations_side_note_label),
                    colorMaps = data.colors
                )

                breedStatItems.forEach { breedStatItem ->
                    BreedStatCard(breedStatItem)
                }
            }
        }
    }
}

data class BreedStatItem(
    val icon: ImageVector,
    val title: String,
    val data: Double
)