package com.example.dogs.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogs.domain.usecase.GetDogsUseCase
import com.example.dogs.presentation.state.DogAction
import com.example.dogs.presentation.state.DogUiState
import com.example.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(
    private val getDogsUseCase: GetDogsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DogUiState())
    val uiState: StateFlow<DogUiState> = _uiState.asStateFlow()

    init {
        handleAction(DogAction.LoadDogs)
    }

    fun handleAction(action: DogAction) {
        when (action) {
            is DogAction.LoadDogs -> loadDogs()
            is DogAction.RefreshDogs -> refreshDogs()
            is DogAction.ClearError -> clearError()
        }
    }

    private fun loadDogs() {
        viewModelScope.launch {
            getDogsUseCase().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            dogs = resource.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = resource.message
                        )
                    }
                }
            }
        }
    }

    private fun refreshDogs() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isRefreshing = true)
                getDogsUseCase.refreshDogs()
                _uiState.value = _uiState.value.copy(isRefreshing = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isRefreshing = false,
                    error = e.message
                )
            }
        }
    }

    private fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}