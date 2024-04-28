package com.example.kutyafulke.presentation.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kutyafulke.R
import com.example.kutyafulke.presentation.components.DropdownItem
import com.example.kutyafulke.presentation.components.TopAppBarWithAccountIcon
import com.example.kutyafulke.presentation.theme.DogBlack
import com.example.kutyafulke.presentation.theme.DogBrown
import com.example.kutyafulke.presentation.theme.DogCream
import com.example.kutyafulke.presentation.theme.DogGray
import com.example.kutyafulke.presentation.theme.DogRed
import com.example.kutyafulke.presentation.theme.DogWhite
import com.example.kutyafulke.presentation.theme.DogYellow
import com.example.kutyafulke.presentation.ui.main.components.bottom_bar.CustomBottomBar
import com.example.kutyafulke.presentation.ui.main.components.bottom_bar.components.FloatingPopupMenu
import com.example.kutyafulke.presentation.ui.main.components.classification_modal_bottom_sheet.ClassificationModalBottomSheet
import com.example.kutyafulke.presentation.ui.main.components.test_modal_bottom_sheet.TestModalBottomSheet
import com.example.kutyafulke.presentation.ui.main.navigation.navhosts.MainNavSubTree
import com.example.kutyafulke.presentation.ui.main.navigation.routes.NavNodeRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val uiState = viewModel.uiState
    val context = LocalContext.current
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            viewModel.onEvent(MainScreenUiEvent.ProcessTakenPictureEvent(context))
        }
    }
    LaunchedEffect(uiState.providedFileUri) {
        if (uiState.providedFileUri != Uri.EMPTY) {
            cameraLauncher.launch(uiState.providedFileUri)
        }
    }
    val innerNavHostController = rememberNavController()
    val dropdownItems = listOf(
        DropdownItem(
            text = stringResource(R.string.log_out_label),
            onClick = {
                viewModel.onEvent(MainScreenUiEvent.LogOutEvent)
                navHostController.navigate(NavNodeRoute.AUTH) {
                    popUpTo(NavNodeRoute.ROOT) {
                        inclusive = true
                    }
                }
            }
        )
    )
    val permissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { }
    )

    val testQuestionItemList = listOf(
        TestQuestionItem(
            title = stringResource(R.string.dog_test_size_question_label),
            valueMap = uiState.sliderValues,
            valueKey = "size",
        ){ changedValue, valueKey ->
            viewModel.onEvent(MainScreenUiEvent.ChangeSliderValueEvent(changedValue, valueKey))
        },
        TestQuestionItem(
            title = stringResource(R.string.dog_test_feeding_question_label),
            valueMap = uiState.sliderValues,
            valueKey = "feeding",
        ){ changedValue, valueKey ->
            viewModel.onEvent(MainScreenUiEvent.ChangeSliderValueEvent(changedValue, valueKey))
        },
        TestQuestionItem(
            title = stringResource(R.string.dog_test_hair_length_question_label),
            valueMap = uiState.sliderValues,
            valueKey = "hairLength",
        ){ changedValue, valueKey ->
            viewModel.onEvent(MainScreenUiEvent.ChangeSliderValueEvent(changedValue, valueKey))
        },
        TestQuestionItem(
            title = stringResource(R.string.dog_test_hair_texture_question_label),
            valueMap = uiState.sliderValues,
            valueKey = "hairTexture",
        ){ changedValue, valueKey ->
            viewModel.onEvent(MainScreenUiEvent.ChangeSliderValueEvent(changedValue, valueKey))
        },
        TestQuestionItem(
            title = stringResource(R.string.dog_test_grooming_question_label),
            valueMap = uiState.sliderValues,
            valueKey = "grooming",
        ){ changedValue, valueKey ->
            viewModel.onEvent(MainScreenUiEvent.ChangeSliderValueEvent(changedValue, valueKey))
        },
        TestQuestionItem(
            title = stringResource(R.string.dog_test_shedding_question_label),
            valueMap = uiState.sliderValues,
            valueKey = "shedding",
        ){ changedValue, valueKey ->
            viewModel.onEvent(MainScreenUiEvent.ChangeSliderValueEvent(changedValue, valueKey))
        },
        TestQuestionItem(
            title = stringResource(R.string.dog_test_walking_question_label),
            valueMap = uiState.sliderValues,
            valueKey = "walking",
        ){ changedValue, valueKey ->
            viewModel.onEvent(MainScreenUiEvent.ChangeSliderValueEvent(changedValue, valueKey))
        },
        TestQuestionItem(
            title = stringResource(R.string.dog_test_playfulness_question_label),
            valueMap = uiState.sliderValues,
            valueKey = "playfulness",
        ){ changedValue, valueKey ->
            viewModel.onEvent(MainScreenUiEvent.ChangeSliderValueEvent(changedValue, valueKey))
        },
        TestQuestionItem(
            title = stringResource(R.string.dog_test_intelligence_question_label),
            valueMap = uiState.sliderValues,
            valueKey = "intelligence",
        ){ changedValue, valueKey ->
            viewModel.onEvent(MainScreenUiEvent.ChangeSliderValueEvent(changedValue, valueKey))
        },
        TestQuestionItem(
            title = stringResource(R.string.dog_test_kid_tolerance_question_label),
            valueMap = uiState.sliderValues,
            valueKey = "kidTolerance",
        ){ changedValue, valueKey ->
            viewModel.onEvent(MainScreenUiEvent.ChangeSliderValueEvent(changedValue, valueKey))
        },
        TestQuestionItem(
            title = stringResource(R.string.dog_test_cat_tolerance_question_label),
            valueMap = uiState.sliderValues,
            valueKey = "catTolerance",
        ){ changedValue, valueKey ->
            viewModel.onEvent(MainScreenUiEvent.ChangeSliderValueEvent(changedValue, valueKey))
        },
        TestQuestionItem(
            title = stringResource(R.string.dog_test_health_problems_question_label),
            valueMap = uiState.sliderValues,
            valueKey = "healthProblems",
        ){ changedValue, valueKey ->
            viewModel.onEvent(MainScreenUiEvent.ChangeSliderValueEvent(changedValue, valueKey))
        },
        TestQuestionItem(
            title = stringResource(R.string.dog_test_loudness_question_label),
            valueMap = uiState.sliderValues,
            valueKey = "loudness",
        ){ changedValue, valueKey ->
            viewModel.onEvent(MainScreenUiEvent.ChangeSliderValueEvent(changedValue, valueKey))
        },
        TestQuestionItem(
            title = stringResource(R.string.dog_test_independence_question_label),
            valueMap = uiState.sliderValues,
            valueKey = "independence",
        ){ changedValue, valueKey ->
            viewModel.onEvent(MainScreenUiEvent.ChangeSliderValueEvent(changedValue, valueKey))
        },
        TestQuestionItem(
            title = stringResource(R.string.dog_test_deffensiveness_question_label),
            valueMap = uiState.sliderValues,
            valueKey = "defensiveness",
        ){ changedValue, valueKey ->
            viewModel.onEvent(MainScreenUiEvent.ChangeSliderValueEvent(changedValue, valueKey))
        },
    )

    val testColorItemList = listOf(
        TestColorItem(
            color = DogBlack,
            colorSelectionMap = uiState.colorValues,
            valueKey = "black"
        ) {
          viewModel.onEvent(MainScreenUiEvent.ChangeColorSelectionValueEvent(it))
        },
        TestColorItem(
            color = DogBrown,
            colorSelectionMap = uiState.colorValues,
            valueKey = "brown"
        ) {
            viewModel.onEvent(MainScreenUiEvent.ChangeColorSelectionValueEvent(it))
        },
        TestColorItem(
            color = DogCream,
            colorSelectionMap = uiState.colorValues,
            valueKey = "cream"
        ) {
            viewModel.onEvent(MainScreenUiEvent.ChangeColorSelectionValueEvent(it))
        },
        TestColorItem(
            color = DogGray,
            colorSelectionMap = uiState.colorValues,
            valueKey = "gray"
        ) {
            viewModel.onEvent(MainScreenUiEvent.ChangeColorSelectionValueEvent(it))
        },
        TestColorItem(
            color = DogRed,
            colorSelectionMap = uiState.colorValues,
            valueKey = "red"
        ) {
            viewModel.onEvent(MainScreenUiEvent.ChangeColorSelectionValueEvent(it))
        },
        TestColorItem(
            color = DogWhite,
            colorSelectionMap = uiState.colorValues,
            valueKey = "white"
        ) {
            viewModel.onEvent(MainScreenUiEvent.ChangeColorSelectionValueEvent(it))
        },
        TestColorItem(
            color = DogYellow,
            colorSelectionMap = uiState.colorValues,
            valueKey = "yellow"
        ) {
            viewModel.onEvent(MainScreenUiEvent.ChangeColorSelectionValueEvent(it))
        },
    )

    if (uiState.isClassificationBottomSheetVisible) {
        ClassificationModalBottomSheet(
            sheetState = uiState.testSheetState,
            imageUri = uiState.providedFileUri,
            breeds = uiState.breeds,
            imageClassificationProbabilityList = uiState.imageClassificationProbabilityList,
            onDismissRequest = {
                viewModel.onEvent(MainScreenUiEvent.ChangeClassificationBottomSheetVisibilityEvent)
            },
        ) { breedId ->
            viewModel.onEvent(
                MainScreenUiEvent.AddOwnedDogEvent(breedId, uiState.providedFileUri)
            )
            viewModel.onEvent(MainScreenUiEvent.ChangeClassificationBottomSheetVisibilityEvent)
        }
    }

    if (uiState.isTestBottomSheetVisible) {
        TestModalBottomSheet(
            onDismissRequest = {
                viewModel.onEvent(MainScreenUiEvent.ChangeTestBottomSheetVisibilityEvent)
            },
            sheetState = uiState.testSheetState,
            testQuestionItemList = testQuestionItemList,
            testColorItemList = testColorItemList,
            testClassificationProbabilityList = uiState.testClassificationProbabilityList,
            breeds = uiState.breeds,
            onClick = { viewModel.onEvent(MainScreenUiEvent.ProcessCompletedTestEvent(context)) },
        ) { breedId ->
            viewModel.onEvent(
                MainScreenUiEvent.AddOwnedDogEvent(breedId, Uri.EMPTY)
            )
            viewModel.onEvent(MainScreenUiEvent.ChangeTestBottomSheetVisibilityEvent)
        }
    }

    if (uiState.isPopupVisible) {
        FloatingPopupMenu(
            onDismissRequest = { viewModel.onEvent(MainScreenUiEvent.ChangePopupVisibilityEvent) },
            onFillTest = {
                viewModel.onEvent(MainScreenUiEvent.ChangePopupVisibilityEvent)
                viewModel.onEvent(MainScreenUiEvent.ChangeTestBottomSheetVisibilityEvent)
            },
            onTakeAPicture = {
                when (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)) {
                    PackageManager.PERMISSION_GRANTED -> {
                        viewModel.onEvent(MainScreenUiEvent.ChangePopupVisibilityEvent)
                        viewModel.onEvent(MainScreenUiEvent.UpdateProvidedFileUriEvent(context))
                    }
                    else -> {
                        permissionResultLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
            }
        )
    }


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        topBar = { TopAppBarWithAccountIcon(stringResource(R.string.main_screen_title_label), dropdownItems) },
        bottomBar = {
            CustomBottomBar(
                navHostController = innerNavHostController,
                onFloatingActionButtonClick = {
                    viewModel.onEvent(MainScreenUiEvent.ChangePopupVisibilityEvent)
                }
            )
        },
        content = {
            Surface(
                modifier = Modifier
                    .padding(
                        it.calculateStartPadding(LayoutDirection.Ltr),
                        it.calculateTopPadding(),
                        it.calculateEndPadding(LayoutDirection.Ltr),
                        it.calculateBottomPadding() - 20.dp
                    )
            ) {
                MainNavSubTree(navHostController = innerNavHostController)
            }
        }
    )
}

data class TestColorItem(
    val color: Color,
    val colorSelectionMap: Map<String, Boolean>,
    val valueKey: String,
    val onClick: (String) -> Unit,
)

data class TestQuestionItem(
    val title: String,
    val valueMap: Map<String, Float>,
    val valueKey: String,
    val onValueChange: (Float, String) -> Unit,
)
