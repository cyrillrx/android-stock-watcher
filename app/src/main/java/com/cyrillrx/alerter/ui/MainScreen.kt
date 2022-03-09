package com.cyrillrx.alerter.ui

import android.content.Context
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.cyrillrx.alerter.ui.theme.AppTheme
import com.cyrillrx.alerter.widget.UiProduct
import com.cyrillrx.alerter.widget.WatcherItem
import java.util.Date
import java.util.Locale

@ExperimentalCoilApi
@Composable
fun MainScreen(viewModel: MainViewModel) {
    MainScreen(viewModel.uiState, viewModel::updateProducts)
}

@ExperimentalCoilApi
@Composable
fun MainScreen(uiState: MainScreenState, updateProducts: (Context) -> Unit) {
    AppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {

            val products = uiState.products
            when {
                uiState.isLoading -> Loader()
                products.isEmpty() -> EmptyState()
                else -> DefaultContent(products, updateProducts)
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

@Composable
private fun EmptyState() {
    Text(text = "No products", modifier = Modifier.wrapContentSize(Alignment.Center))
}

@ExperimentalCoilApi
@Composable
private fun DefaultContent(products: List<UiProduct>, updateProducts: (Context) -> Unit) {
    val context = LocalContext.current

    Column {
        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        Text(text = "Last update: $time", Modifier.padding(16.dp))
        ProductList(products, Modifier.weight(1f))
        Button(
            onClick = { updateProducts(context) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "refresh")
        }
    }
}

@ExperimentalCoilApi
@Composable
private fun ProductList(products: List<UiProduct>, modifier: Modifier = Modifier) {
    LazyColumn(modifier) {
        items(products) { product ->
            WatcherItem(
                title = product.title,
                subtitle = product.subtitle,
                imageUrl = product.imageUrl,
                inStock = product.inStock,
                onItemClicked = product.onClicked,
            )
        }
    }
}

@ExperimentalCoilApi
@Preview(showBackground = true)
@Composable
fun MainScreenLoadingPreview() {
    MainScreen(MainScreenState(emptyList(), true)) {}
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
    MainScreen(uiState) {}
}
