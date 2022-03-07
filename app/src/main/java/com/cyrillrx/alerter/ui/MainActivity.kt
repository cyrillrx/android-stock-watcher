package com.cyrillrx.alerter.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.annotation.ExperimentalCoilApi
import com.cyrillrx.alerter.model.WatchedProduct
import com.cyrillrx.alerter.ui.theme.AppTheme
import com.cyrillrx.alerter.widget.UiProduct
import com.cyrillrx.alerter.widget.WatcherItem

@ExperimentalCoilApi
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen(viewModel)
        }

        val initialProducts = createProductToWatch()
        viewModel.updateProducts(initialProducts)
    }
}

private fun createProductToWatch(): List<WatchedProduct> {
    val criticalRoleValidator = { htmlAsString: String -> !htmlAsString.contains("Sold Out") }
    val legoValidator = { htmlAsString: String -> !htmlAsString.contains("rupture de stock") }
    val philibertValidator = { htmlAsString: String -> !htmlAsString.contains("plus en stock") }

    return listOf(
        WatchedProduct(
            url = "https://shop.critrole.eu/collections/tabletop/products/taldorei-campaign-setting-reborn",
            validator = criticalRoleValidator,
            title = "Critical Role",
            subtitle = "TAL’DOREI CAMPAIGN SETTING REBORN",
            imageUrl = "https://cdn.shopify.com/s/files/1/0598/9879/0083/products/TalDoreiRebornHero_900x.jpg?v=1642119750",
        ),
        WatchedProduct(
            url = "https://shop.critrole.eu/collections/tabletop/products/vox-machina-dice-set-gm",
            validator = criticalRoleValidator,
            title = "Critical Role",
            subtitle = "VOX MACHINA DICE SET: GM",
            imageUrl = "https://cdn.shopify.com/s/files/1/0598/9879/0083/products/ProductPhotos-VoxMachinaDiceSetGM-White-DiceandBag-1200x_900x.jpg?v=1637171913",
        ),
        WatchedProduct(
            url = "https://www.lego.com/fr-fr/product/spider-man-keyring-853950",
            validator = legoValidator,
            title = "Lego",
            subtitle = "Porte-clés Spider-Man",
            imageUrl = "https://www.lego.com/cdn/cs/set/assets/blt7be7a40979eec479/853950.jpg?fit=bounds&format=jpg&quality=80&width=1600&height=1600&dpr=1",
        ),
        WatchedProduct(
            url = "https://www.philibertnet.com/fr/sand-castle-games/98605-res-arcana-extension-perlae-imperii-850004236529.html",
            validator = philibertValidator,
            title = "Philibert",
            subtitle = "Res Arcana : Extension Perlae Imperii",
            imageUrl = "https://cdn1.philibertnet.com/508047-thickbox_default/res-arcana-extension-perlae-imperii.jpg",
        ),
    )
}

private fun createUIWatcherItems(): List<UiProduct> = listOf(
    UiProduct(
        title = "Critical Role",
        subtitle = "TAL’DOREI CAMPAIGN SETTING REBORN",
        imageUrl = "https://cdn.shopify.com/s/files/1/0598/9879/0083/products/TalDoreiRebornHero_900x.jpg?v=1642119750",
        url = "",
        inStock = false,
    ),
    UiProduct(
        title = "Critical Role",
        subtitle = "VOX MACHINA DICE SET: GM",
        imageUrl = "https://cdn.shopify.com/s/files/1/0598/9879/0083/products/ProductPhotos-VoxMachinaDiceSetGM-White-DiceandBag-1200x_900x.jpg?v=1637171913",
        url = "",
        inStock = false,
    ),
)

@ExperimentalCoilApi
@Composable
fun MainScreen(viewModel: MainViewModel) {
    MainScreen(viewModel.uiState.products, viewModel::onItemClicked)
}

@ExperimentalCoilApi
@Composable
fun MainScreen(uiItems: List<UiProduct>, onItemClicked: (Context, UiProduct) -> Unit) {
    val context = LocalContext.current
    AppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            LazyColumn {
                items(uiItems) { item ->
                    WatcherItem(
                        title = item.title,
                        subtitle = item.subtitle,
                        imageUrl = item.imageUrl,
                        inStock = item.inStock,
                        onItemClicked = { onItemClicked(context, item) }
                    )
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen(createUIWatcherItems()) { _, _ -> }
}
