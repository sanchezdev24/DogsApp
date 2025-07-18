package com.example.dogs.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dogs.domain.model.Dog
import com.example.dogs.presentation.state.DogAction
import com.example.dogs.presentation.ui.components.EmptyState
import com.example.dogs.presentation.ui.components.ErrorMessage
import com.example.dogs.presentation.ui.components.LoadingIndicator
import com.example.dogs.presentation.viewmodel.DogViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogListScreen(
    onBackClick: () -> Unit = {},
    viewModel: DogViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = uiState.isRefreshing
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // TopAppBar
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Dogs We Love",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver"
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = {
                        viewModel.handleAction(DogAction.RefreshDogs)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Actualizar"
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface
            )
        )

        // Contenido principal
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.handleAction(DogAction.RefreshDogs) }
        ) {
            when {
                uiState.isLoading && uiState.dogs.isEmpty() -> {
                    LoadingIndicator()
                }

                uiState.error != null && uiState.dogs.isEmpty() -> {
                    ErrorMessage(
                        message = uiState.error.toString(),
                        onRetry = {
                            viewModel.handleAction(DogAction.ClearError)
                            viewModel.handleAction(DogAction.LoadDogs)
                        }
                    )
                }

                uiState.dogs.isEmpty() -> {
                    EmptyState(
                        onRefresh = { viewModel.handleAction(DogAction.RefreshDogs) }
                    )
                }

                else -> {
                    DogList(
                        dogs = uiState.dogs,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }

    // Mostrar error como Snackbar si hay datos
    LaunchedEffect(uiState.error) {
        uiState.error?.let { error ->
            if (uiState.dogs.isNotEmpty()) {
                // Aquí podrías mostrar un SnackBar
                // snackbarHostState.showSnackbar(error)
                viewModel.handleAction(DogAction.ClearError)
            }
        }
    }
}

@Composable
private fun DogList(
    dogs: List<Dog>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        items(
            items = dogs,
            key = { dog -> dog.dogName }
        ) { dog ->
            DogItem(dog = dog)
        }
    }
}