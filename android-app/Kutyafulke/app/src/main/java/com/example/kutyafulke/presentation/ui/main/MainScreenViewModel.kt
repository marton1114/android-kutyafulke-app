package com.example.kutyafulke.presentation.ui.main

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kutyafulke.data.models.Response
import com.example.kutyafulke.data.repositories.interfaces.AuthenticationRepository
import com.example.kutyafulke.data.repositories.interfaces.BreedsRepository
import com.example.kutyafulke.domain.use_cases.AddOwnedDogUseCase
import com.example.kutyafulke.ml.ImageModel
import com.example.kutyafulke.ml.TestModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Objects
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalMaterial3Api::class)
class MainScreenViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val breedsRepository: BreedsRepository,
    private val addOwnedDogUseCase: AddOwnedDogUseCase
): ViewModel() {
    var uiState by mutableStateOf(MainScreenUiState())
        private set

    fun onEvent(event: MainScreenUiEvent) {
        when (event) {
            is MainScreenUiEvent.LogOutEvent -> {
                logout()
            }
            is MainScreenUiEvent.AddOwnedDogEvent -> {
                addDog(event.imageUri, event.breedId)
            }
            is MainScreenUiEvent.UpdateProvidedFileUriEvent -> {
                val currentTimeInMillis = LocalDateTime.now()
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli()

                val file = File.createTempFile(
                    currentTimeInMillis.toString(),
                    ".jpg",
                    event.context.externalCacheDir
                )

                uiState = uiState.copy(
                    providedFileUri = FileProvider.getUriForFile(
                        Objects.requireNonNull(event.context),
                        event.context.packageName + ".provider",
                        file
                    )
                )
            }
            is MainScreenUiEvent.ChangePopupVisibilityEvent -> {
                uiState = uiState.copy(isPopupVisible = !uiState.isPopupVisible)
            }
            is MainScreenUiEvent.ChangeTestBottomSheetVisibilityEvent -> {
                uiState = uiState.copy(isTestBottomSheetVisible = !uiState.isTestBottomSheetVisible)
            }
            is MainScreenUiEvent.ChangeClassificationBottomSheetVisibilityEvent -> {
                uiState = uiState.copy(
                    isClassificationBottomSheetVisible = !uiState.isClassificationBottomSheetVisible
                )
            }
            is MainScreenUiEvent.ProcessTakenPictureEvent -> {
                if (uiState.providedFileUri.path?.isNotEmpty() == true) {
                    val imageProcessor = ImageProcessor.Builder()
                        .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
                        .add(NormalizeOp(0.0F, 255.0F))
                        .build()
                    val imageSource = ImageDecoder.createSource(event.context.contentResolver, uiState.providedFileUri)
                    val image = ImageDecoder.decodeBitmap(imageSource).copy(Bitmap.Config.ARGB_8888, true)
                    var tensorImage = TensorImage(DataType.FLOAT32)
                    tensorImage.load(image)
                    tensorImage = imageProcessor.process(tensorImage)

                    lateinit var model: ImageModel
                    try {
                        model = ImageModel.newInstance(event.context)
                        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
                        inputFeature0.loadBuffer(tensorImage.buffer)
                        val outputFeature0 = model.process(inputFeature0).outputFeature0AsTensorBuffer

                        uiState = uiState.copy(
                            imageClassificationProbabilityList = outputFeature0.floatArray.toList(),
                            isClassificationBottomSheetVisible = true
                        )
                        getBreeds()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        model.close()
                    }
                }
            }
            is MainScreenUiEvent.ProcessCompletedTestEvent -> {
                lateinit var model: TestModel
                try {
                    model = TestModel.newInstance(event.context)

                    val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 22), DataType.FLOAT32)

                    val inputData = uiState.sliderValues.values.toList().plus(uiState.colorValues
                        .values.map { if (it) 1.0F else 0.0F }).toFloatArray()

                    inputFeature0.loadArray(inputData)

                    val outputs = model.process(inputFeature0)
                    val outputFeature0 = outputs.outputFeature0AsTensorBuffer

                    uiState = uiState.copy(
                        testClassificationProbabilityList  = outputFeature0.floatArray.toList(),
                    )

                    getBreeds()
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    model.close()
                }

            }
            is MainScreenUiEvent.ChangeSliderValueEvent -> {
                var newSliderValues = uiState.sliderValues.toMutableMap()
                newSliderValues[event.valueKey] = event.value
                uiState = uiState.copy(
                    sliderValues = newSliderValues.toMap()
                )
            }
            is MainScreenUiEvent.ChangeColorSelectionValueEvent -> {
                var newColorValues = uiState.colorValues.toMutableMap()
                newColorValues[event.valueKey] = ! newColorValues[event.valueKey]!!
                uiState = uiState.copy(
                    colorValues = newColorValues.toMap()
                )
            }
        }
    }

    private fun addDog(uri: Uri, breedId: String) = viewModelScope.launch {
        addOwnedDogUseCase.invoke(uri, breedId).collect { response ->
            when (response) {
                is Response.Success -> {}
                is Response.Failure -> {}
                else -> {}
            }
        }
    }

    private fun getBreeds() = viewModelScope.launch {
        breedsRepository.getBreeds().collect { response ->
            when(response) {
                is Response.Success -> {
                    uiState = uiState.copy(
                        breeds = response.data,
                    )
                }
                is Response.Failure -> {}
                else -> {}
            }
        }
    }
    
    private fun logout() = authenticationRepository.logoutUser()
}