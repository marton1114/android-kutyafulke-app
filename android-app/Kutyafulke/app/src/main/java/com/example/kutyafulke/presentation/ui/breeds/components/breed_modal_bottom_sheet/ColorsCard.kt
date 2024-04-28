package com.example.kutyafulke.presentation.ui.breeds.components.breed_modal_bottom_sheet

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.kutyafulke.presentation.theme.DogBlack
import com.example.kutyafulke.presentation.theme.DogBrown
import com.example.kutyafulke.presentation.theme.DogCream
import com.example.kutyafulke.presentation.theme.DogGray
import com.example.kutyafulke.presentation.theme.DogRed
import com.example.kutyafulke.presentation.theme.DogWhite
import com.example.kutyafulke.presentation.theme.DogYellow
import com.example.kutyafulke.presentation.theme.Typography

@Composable
fun ColorsCard(
    icon: ImageVector,
    label: String,
    sideNote: String,
    colorMaps: List<Map<String, Boolean>>,
    modifier: Modifier = Modifier,
    padding: Dp = 5.dp,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, padding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "",
            modifier = Modifier.size(48.dp).align(Alignment.Bottom)
        )
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row {
                Text(text = label, style = Typography.titleMedium, modifier = Modifier)
                Text(text = " (${sideNote})", style = Typography.titleSmall, modifier = Modifier)
            }
            Spacer(modifier = Modifier.height(padding / 2))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(padding / 2)
            ) {
                colorMaps.forEach { colorMap ->
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.background)
                            .padding(padding),
                        horizontalArrangement = Arrangement.spacedBy(padding / 2)
                    ) {
                        colorMap.forEach { (color, shouldDraw) ->
                            if (shouldDraw) {
                                DrawCircle(
                                    color = when (color) {
                                        "black" -> { DogBlack }
                                        "brown" -> { DogBrown }
                                        "cream" -> { DogCream }
                                        "gray" -> { DogGray }
                                        "red" -> { DogRed }
                                        "white" -> { DogWhite }
                                        "yellow" -> { DogYellow }
                                        else -> { Color.Transparent }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun DrawCircle(color: Color) {
    Canvas(
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(color)
    ) {}
}