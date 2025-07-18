package com.example.dogs.presentation.state

sealed class DogAction {
    object LoadDogs : DogAction()
    object RefreshDogs : DogAction()
    object ClearError : DogAction()
}