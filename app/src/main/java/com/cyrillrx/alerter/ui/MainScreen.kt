package com.cyrillrx.alerter.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.annotation.ExperimentalCoilApi
import com.cyrillrx.alerter.ui.theme.AppTheme
import com.cyrillrx.alerter.widget.UiProduct
import com.cyrillrx.alerter.widget.WatcherItem

@ExperimentalCoilApi
@Composable
fun MainScreen(viewModel: MainViewModel) {
    MainScreen(viewModel.uiState)
}

@ExperimentalCoilApi
@Composable
fun MainScreen(uiState: MainScreenState) {
    AppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            if (uiState.isLoading) {
                Loader()
            } else {
                ProductList(uiState.products)
            }
        }
    }
}

@Composable
private fun Loader() {
    CircularProgressIndicator(
        Modifier.wrapContentSize(Alignment.Center)
    )
}

@ExperimentalCoilApi
@Composable
private fun ProductList(uiItems: List<UiProduct>) {
    LazyColumn {
        items(uiItems) { item ->
            WatcherItem(
                title = item.title,
                subtitle = item.subtitle,
                imageUrl = item.imageUrl,
                inStock = item.inStock,
                onItemClicked = item.onClicked,
            )
        }
    }
}

@ExperimentalCoilApi
@Preview(showBackground = true)
@Composable
fun MainScreenLoadingPreview() {
    MainScreen(MainScreenState(emptyList(), true))
}

@ExperimentalCoilApi
@Preview
@Composable
fun MainScreenPreview() {
    val uiProducts = listOf(
        UiProduct(
            title = "Critical Role",
            subtitle = "TAL’DOREI CAMPAIGN SETTING REBORN",
            imageUrl = "https://cdn.shopify.com/s/files/1/0598/9879/0083/products/TalDoreiRebornHero_900x.jpg?v=1642119750",
            url = "",
            inStock = false,
            onClicked = {},
        ),
        UiProduct(
            title = "Critical Role",
            subtitle = "VOX MACHINA DICE SET: GM",
            imageUrl = "https://cdn.shopify.com/s/files/1/0598/9879/0083/products/ProductPhotos-VoxMachinaDiceSetGM-White-DiceandBag-1200x_900x.jpg?v=1637171913",
            url = "",
            inStock = false,
            onClicked = {},
        ),
    )
    val uiState = MainScreenState(uiProducts, false)
    MainScreen(uiState)
}
