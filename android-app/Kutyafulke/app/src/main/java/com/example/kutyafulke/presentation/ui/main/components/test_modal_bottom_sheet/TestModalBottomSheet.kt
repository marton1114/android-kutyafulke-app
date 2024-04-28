package com.example.kutyafulke.presentation.ui.main.components.test_modal_bottom_sheet

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kutyafulke.R
import com.example.kutyafulke.data.models.Breed
import com.example.kutyafulke.presentation.components.ModalBottomSheetTopBar
import com.example.kutyafulke.presentation.theme.Typography
import com.example.kutyafulke.presentation.ui.main.TestColorItem
import com.example.kutyafulke.presentation.ui.main.TestQuestionItem

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TestModalBottomSheet(
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    testQuestionItemList: List<TestQuestionItem>,
    testColorItemList: List<TestColorItem>,
    testClassificationProbabilityList: List<Float>,
    breeds: List<Breed>,
    onClick: () -> Unit,
    onBreedClick: (breedId: String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val primaryColor = MaterialTheme.colorScheme.primary
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
    ) {
        ModalBottomSheetTopBar(
            title = stringResource(R.string.dog_test_title_label),
            modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 0.dp),
            onClose = onDismissRequest
        )
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(10.dp, 10.dp, 10.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(10.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "1.",
                    modifier = Modifier.weight(1.0F),
                    style = Typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .weight(4.0F)
                    .padding(0.dp, 0.dp, 10.dp, 0.dp)) {
                    Text(text = stringResource(R.string.dog_test_color_question_label))
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 5.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        maxItemsInEachRow = 4,
                    ) {
                        testColorItemList.forEach { testColorItem ->
                            Canvas(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable(interactionSource, null) {
                                        testColorItem.onClick(testColorItem.valueKey)
                                    }
                                    .padding(5.dp)
                            ) {
                                val radius = size.height / 2F
                                drawCircle(testColorItem.color, radius)
                                if (testColorItem.colorSelectionMap[testColorItem.valueKey]!!) {
                                    drawCircle(
                                        color = primaryColor,
                                        radius = radius,
                                        style = Stroke(width = 10f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
            testQuestionItemList.forEachIndexed { i, testQuestionItem ->
                Row(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .padding(10.dp, 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${i + 2}.",
                        modifier = Modifier.weight(1.0F),
                        style = Typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .weight(4.0F)
                        .padding(0.dp, 0.dp, 10.dp, 0.dp)) {
                        Text(text = testQuestionItem.title)
                        Slider(
                            value = testQuestionItem.valueMap[testQuestionItem.valueKey]!!,
                            onValueChange = {
                                testQuestionItem.onValueChange(it, testQuestionItem.valueKey)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            valueRange = 1.0F..5.0F,
                            steps = 3
                        )
                    }

                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                Button(onClick = onClick) {
                    Text(text = stringResource(R.string.dog_test_button_label))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            if (breeds.isNotEmpty() and testClassificationProbabilityList.isNotEmpty()) {
                Text(text = stringResource(R.string.dog_test_result_label))
                breeds.sortedBy { -testClassificationProbabilityList[it.id.toInt()] }
                    .filter { testClassificationProbabilityList[it.id.toInt()] >= 0.009F }.forEach { breed ->
                        BreedCard(
                            breed = breed,
                            percentage = testClassificationProbabilityList[breed.id.toInt()],
                            onClick = { onBreedClick(breed.id) }
                        )
                    }
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}