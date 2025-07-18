package com.example.dogs.presentation.state

import com.example.dogs.domain.model.Dog

data class DogUiState(
    val dogs: List<Dog> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false
)