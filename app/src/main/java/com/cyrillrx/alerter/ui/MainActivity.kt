package com.cyrillrx.alerter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.annotation.ExperimentalCoilApi
import com.cyrillrx.alerter.ui.theme.AppTheme
import com.cyrillrx.alerter.widget.WatcherItem
import com.cyrillrx.alerter.widget.WatcherItemData
import okhttp3.OkHttpClient

@ExperimentalCoilApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val items = createWatcherItems()

        val client = OkHttpClient.Builder().build()

        setContent {
            AppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    AlertList(items, ::refreshItem)
                }
            }
        }
    }

    private fun refreshItem(item: WatcherItemData) {
        TODO()
    }
}

private fun createWatcherItems(): Array<WatcherItemData> = arrayOf(
    WatcherItemData(
        title = "Critical Role",
        subtitle = "TAL’DOREI CAMPAIGN SETTING REBORN",
        url = "https://shop.critrole.eu/collections/tabletop/products/taldorei-campaign-setting-reborn",
        imageUrl = "https://cdn.shopify.com/s/files/1/0598/9879/0083/products/TalDoreiRebornHero_900x.jpg?v=1642119750",
        inStock = false,
    ),
    WatcherItemData(
        title = "Critical Role",
        subtitle = "VOX MACHINA DICE SET: GM",
        url = "https://shop.critrole.eu/collections/tabletop/products/vox-machina-dice-set-gm",
        imageUrl = "https://cdn.shopify.com/s/files/1/0598/9879/0083/products/ProductPhotos-VoxMachinaDiceSetGM-White-DiceandBag-1200x_900x.jpg?v=1637171913",
        inStock = false,
    ),
    WatcherItemData(
        title = "Lego",
        subtitle = "Porte-clés Spider-Man",
        url = "https://www.lego.com/fr-fr/product/spider-man-keyring-853950",
        imageUrl = "https://www.lego.com/cdn/cs/set/assets/blt7be7a40979eec479/853950.jpg?fit=bounds&format=jpg&quality=80&width=1600&height=1600&dpr=1",
        inStock = false,
    ),
    WatcherItemData(
        title = "Critical Role",
        subtitle = "Res Arcana : Extension Perlae Imperii",
        url = "https://www.philibertnet.com/fr/sand-castle-games/98605-res-arcana-extension-perlae-imperii-850004236529.html",
        imageUrl = "https://cdn1.philibertnet.com/508047-thickbox_default/res-arcana-extension-perlae-imperii.jpg",
        inStock = false,
    ),
)

@ExperimentalCoilApi
@Composable
fun AlertList(items: Array<WatcherItemData>, onItemClicked: (WatcherItemData) -> Unit) {
    Column {
        items.forEach { item ->
            WatcherItem(
                title = item.title,
                subtitle = item.subtitle,
                imageUrl = item.imageUrl,
                inStock = item.inStock,
                onItemClicked = { onItemClicked(item) }
            )
        }
    }
}

@ExperimentalCoilApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme {
        AlertList(createWatcherItems()) {}
    }
}
