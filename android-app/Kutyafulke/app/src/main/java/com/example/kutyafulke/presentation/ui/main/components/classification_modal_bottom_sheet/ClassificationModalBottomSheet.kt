package com.example.kutyafulke.presentation.ui.main.components.classification_modal_bottom_sheet

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.kutyafulke.R
import com.example.kutyafulke.data.models.Breed
import com.example.kutyafulke.presentation.components.ModalBottomSheetTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassificationModalBottomSheet(
    sheetState: SheetState,
    imageUri: Uri,
    breeds: List<Breed>,
    imageClassificationProbabilityList: List<Float>,
    onDismissRequest: () -> Unit,
    onBreedClick: (breedId: String) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
    ) {
        ModalBottomSheetTopBar(
            title = stringResource(R.string.image_classification_title_label),
            modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 0.dp),
            onClose = onDismissRequest
        )
        AsyncImage(
            model = imageUri.toString(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(10.dp, 10.dp, 10.dp, 10.dp)
                .height(200.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp))
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(breeds.sortedBy { -imageClassificationProbabilityList[it.id.toInt()] }) {
                breed ->

                BreedCard(
                    breed = breed,
                    percentage = imageClassificationProbabilityList[breed.id.toInt()],
                    onClick = { onBreedClick(breed.id) }
                )
            }
        }
    }
}