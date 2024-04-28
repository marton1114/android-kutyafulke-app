package com.example.kutyafulke.presentation.ui.home.owned_dog_item.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.kutyafulke.R
import com.example.kutyafulke.presentation.components.ModalBottomSheetTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsModalBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    toggleButtonSettingsItems: List<ToggleButtonSettingsItem>,
    iconButtonSettingsItems: List<IconButtonSettingsItem>,
    padding: Dp = 10.dp,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        dragHandle = null,
        sheetState = sheetState
    ) {
        ModalBottomSheetTopBar(
            title = stringResource(R.string.settings_title_label),
            modifier = Modifier.padding(padding, padding, padding, 0.dp),
            onClose = onDismissRequest
        )

        Divider()
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            toggleButtonSettingsItems.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, padding / 2),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = item.label, modifier = Modifier.weight(1.0F))
                    Switch(
                        checked = item.checked,
                        onCheckedChange = { item.onCheckedChange() }
                    )
                }
            }

            iconButtonSettingsItems.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, padding / 2),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = item.label, modifier = Modifier.weight(1.0F))
                    IconButton(
                        onClick = item.onClick
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

data class ToggleButtonSettingsItem(
    val label: String,
    val checked: Boolean,
    val onCheckedChange: () -> Unit,
)

data class IconButtonSettingsItem(
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit,
)