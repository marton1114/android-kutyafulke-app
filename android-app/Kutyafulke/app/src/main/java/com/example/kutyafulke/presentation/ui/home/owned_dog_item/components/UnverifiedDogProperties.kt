package com.example.kutyafulke.presentation.ui.home.owned_dog_item.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import coil.compose.AsyncImage
import com.example.kutyafulke.R
import com.example.kutyafulke.data.models.OwnedDog

@Composable
fun UnverifiedDogProperties(
    ownedDog: OwnedDog,
    onNameChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onVerify: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        AsyncImage(
            model = ownedDog.imageUri,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0F) }),
            modifier = Modifier
                .fillMaxHeight()
                .padding(10.dp, 10.dp, 0.dp, 10.dp)
                .clip(shape = RoundedCornerShape(20.dp, 0.dp, 0.dp, 20.dp))
                .weight(1F)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(0.dp, 10.dp, 10.dp, 10.dp)
                .clip(shape = RoundedCornerShape(0.dp, 20.dp, 20.dp, 0.dp))
                .background(MaterialTheme.colorScheme.background)
                .weight(2F)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(3F)
                    .padding(10.dp, 0.dp, 0.dp, 0.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                CustomTextField(
                    modifier = Modifier
                        .width(150.dp)
                        .padding(0.dp, 0.dp, 0.dp, 2.5.dp),
                    label = stringResource(R.string.unverified_owned_dog_name_label),
                    value = ownedDog.dogName,
                    onValueChange = { newValue ->
                        if (newValue.length <= 10) {
                            onNameChange(newValue)
                        }
                    }
                )
                CustomTextField(
                    modifier = Modifier
                        .width(100.dp)
                        .padding(0.dp, 2.5.dp, 0.dp, 0.dp),
                    label = stringResource(R.string.unverified_owned_dog_weight_label),
                    typeLabel = "kg",
                    value = ownedDog.dogWeight,
                    onValueChange = { newValue ->
                        if (newValue.length <= 4 &&
                            (newValue.isDigitsOnly() || "." in newValue) &&
                            (newValue.count { it == '.' } <= 1)) {

                            onWeightChange(newValue)
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }

            Column(
                modifier = Modifier
                    .size(100.dp)
                    .weight(2F),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                VerifyButton(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 2.5.dp)) {
                    onVerify()
                }
                UnverifyButton(modifier = Modifier.padding(0.dp, 2.5.dp, 0.dp, 0.dp)) {
                    onRemove()
                }

            }
        }
    }
}
//
//@Preview
//@Composable
//fun UnverifiedDogPropertiesPreview() {
//    UnverifiedDogProperties(imageUri = Uri.parse(""), onVerify = {}, onRemove = {})
//}