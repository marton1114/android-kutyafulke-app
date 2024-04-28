package com.example.kutyafulke.presentation.ui.home.owned_dog_item.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.kutyafulke.R
import com.example.kutyafulke.data.models.OwnedDog
import com.example.kutyafulke.presentation.theme.Typography

@Composable
fun VerifiedDogProperties(
    ownedDog: OwnedDog,
    onSettingsButtonClick: () -> Unit
) {
    val actualWeight = ownedDog.dogWeight.toFloat()
    val optimalWeight = ownedDog.averageDogWeight.toFloat()
    Row(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ownedDog.imageUri,
            contentDescription = "",
            contentScale = ContentScale.Crop,
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
                .fillMaxHeight()
                .padding(0.dp, 10.dp, 10.dp, 10.dp)
                .clip(shape = RoundedCornerShape(0.dp, 20.dp, 20.dp, 0.dp))
                .background(MaterialTheme.colorScheme.background)
                .weight(2F)
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp, 0.dp)
                    .weight(2F)
            ) {
                Text(text = stringResource(R.string.verified_owned_dog_name_label), style = Typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary)
                Text(
                    text = ownedDog.dogName,
                    style = Typography.titleLarge
                )
                Spacer(modifier = Modifier.size(5.dp))
                Text(text = stringResource(R.string.verified_owned_dog_weight_label), style = Typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary)
                Text(
                    text = "%.1f kg (${
                        if (actualWeight > optimalWeight) 
                            "+" 
                        else 
                            ""
                    }%.1f)".format(actualWeight, (actualWeight - optimalWeight)),
                    style = Typography.titleMedium
                )
                Spacer(modifier = Modifier.size(5.dp))
            }
            IconButton(
                onClick = onSettingsButtonClick,
                modifier = Modifier
                    .weight(1F)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Dog settings",
                    modifier = Modifier
                        .size(60.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun VerifiedDogPropertiesPreview() {
    VerifiedDogProperties(OwnedDog(dogName = "Buksi", dogWeight = "40.0",
        averageDogWeight = "10.0"), {})
}