package com.example.kutyafulke.presentation.ui.main.components.bottom_bar.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.kutyafulke.R

@Composable
fun FloatingPopupMenu(
    onDismissRequest: () -> Unit,
    onFillTest: () -> Unit,
    onTakeAPicture: () -> Unit,
) {
    Popup(
        alignment = Alignment.BottomCenter,
        offset = IntOffset(0, -300),
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(
            focusable = true
        )
    ) {
        Card(
            modifier = Modifier
                .width(200.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onFillTest() }
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Outlined.Create,
                        contentDescription = ""
                    )
                    Text(text = stringResource(R.string.pop_up_personality_test_label))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTakeAPicture() }
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Outlined.AccountBox,
                        contentDescription = ""
                    )
                    Text(text = stringResource(R.string.pop_up_image_classification_label))
                }
            }
        }
    }
}

@Preview
@Composable
fun FloatingPopupMenuPreview() {
    FloatingPopupMenu(onDismissRequest = { /*TODO*/ }, onFillTest = { /*TODO*/ }) { }
}