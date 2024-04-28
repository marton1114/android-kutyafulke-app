package com.example.kutyafulke.presentation.ui.home.owned_dog_item.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.kutyafulke.R
import com.example.kutyafulke.data.models.OwnedDog
import com.example.kutyafulke.presentation.theme.Typography

@Composable
fun WalkingComponent(
    ownedDog: OwnedDog,
    lastWeeklyWalkingTimeResetLabel: String,
    isLocked: Boolean,
    onUnlock: () -> Unit,
    onLock: () -> Unit,
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
            text = stringResource(R.string.walking_component_title_label),
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        )
        Row(modifier = Modifier.height(height)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight().weight(3.2625F)
            ) {
                if (ownedDog.traveledWeeklyDistance < ownedDog.neededWeeklyDistance) {
                    Box {
                        LinearProgressIndicator(
                            progress = ownedDog.traveledWeeklyDistance
                                .div(ownedDog.neededWeeklyDistance).coerceIn(0.0F, 1.0F),
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center)
                                .height(45.dp)
                                .clip(RoundedCornerShape(20.dp))
                        )
                        Text(
                            text = lastWeeklyWalkingTimeResetLabel,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(7.5.dp, 5.dp),
                            style = Typography.bodySmall
                        )
                        Text(
                            text = "%.0f/%.0f m".format(
                                ownedDog.traveledWeeklyDistance,
                                ownedDog.neededWeeklyDistance
                            ),
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(7.5.dp, 5.dp),
                            style = Typography.bodyLarge
                        )
                    }
                } else {
                    if (!isLocked) {
                        onLock()
                    }
                    Box {
                        Text(
                            text = stringResource(R.string.walking_component_done_label),
                            modifier = Modifier.align(Alignment.Center),
                            style = Typography.bodyLarge
                        )
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight().weight(0.7375F)
            ) {
                if (ownedDog.traveledWeeklyDistance < ownedDog.neededWeeklyDistance) {
                    IconButton(onClick = if (isLocked) onUnlock else onLock) {
                        if (isLocked) {
                            Icon(imageVector = Icons.Filled.Lock, contentDescription = "")
                        } else {
                            Icon(imageVector = Icons.Outlined.Lock, contentDescription = "")
                        }
                    }
                } else { Icon(imageVector = Icons.Filled.Done, contentDescription = "") }
            }
        }
    }
}

@Preview
@Composable
fun WalkingComponentPreview() {
    WalkingComponent(ownedDog = OwnedDog(lastWalkingResetTime = 999999999999999999, neededWeeklyDistance = 10F), "2023. 10. 12.", isLocked = true, {}, {})
}