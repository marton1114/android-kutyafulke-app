package com.example.kutyafulke.presentation.ui.home.owned_dog_item.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.kutyafulke.R
import com.example.kutyafulke.data.models.OwnedDog
import com.example.kutyafulke.presentation.theme.Typography

@Composable
fun HealthComponent(
    isCounting: Boolean,
    nextPillDateLabel: String,
    ownedDog: OwnedDog,
    onConfirmAddPill: Boolean,
    onConfirmRemovePill: Boolean,
    onIncrementPill: () -> Unit,
    onDecrementPill: () -> Unit,
    onGivePill: () -> Unit,
    padding: Dp = 10.dp,
    cornerSize: Dp = 20.dp,
    height: Dp = 75.dp,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(padding, 0.dp, padding, padding)
            .clip(shape = RoundedCornerShape(cornerSize))
            .background(MaterialTheme.colorScheme.background)
            .padding(padding)
    ) {
        Text(
            text = stringResource(R.string.health_component_title_label),
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        )
        Row(
            modifier = Modifier
                .height(height),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(3.2625F),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!isCounting) {
                    IconButton(onClick = onIncrementPill, enabled = onConfirmAddPill) {
                        Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = "")
                    }
                    Text(text = stringResource(
                        R.string.daily_pill_number_label,
                        ownedDog.neededPills
                    ), modifier = Modifier.weight(1F), style = Typography.bodyLarge,
                        textAlign = TextAlign.Center)
                    IconButton(onClick = onDecrementPill, enabled = onConfirmRemovePill) {
                        Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = "")
                    }
                } else {
                    Text(text = nextPillDateLabel, modifier = Modifier.weight(1F), style = Typography.bodyLarge,
                        textAlign = TextAlign.Center)
                }
            }
            IconButton(onClick = onGivePill, modifier = Modifier.weight(0.7375F), enabled = !isCounting) {
                Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "")
            }
        }
    }
}

@Preview
@Composable
fun HealthComponentPreview() {
    HealthComponent(false, "2023. 10. 14. 13:14", OwnedDog(), true, true, {}, {}, {})
}