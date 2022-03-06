package com.cyrillrx.alerter.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import com.cyrillrx.alerter.validator.HtmlFetcher
import com.cyrillrx.alerter.widget.UIWatcherItem
import com.cyrillrx.alerter.widget.WatcherItem
import kotlinx.coroutines.runBlocking

@ExperimentalCoilApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val items = runBlocking { createItemsToWatch().map { it.toUIWatcherItem() } }

        setContent {
            AppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    AlertList(items, ::refreshItem)
                }
            }
        }
    }

    private fun refreshItem(item: UIWatcherItem) {
        val url = item.url
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.e(TAG, "Could not open url: $url")
        }
    }

    companion object {
        private const val TAG = "MainActivity"

        private suspend fun WatchedItem.toUIWatcherItem(): UIWatcherItem {
            val htmlAsText = HtmlFetcher(url).getText()

            return UIWatcherItem(
                title = title,
                subtitle = subtitle,
                imageUrl = imageUrl,
                url = url,
                inStock = validator(htmlAsText),
            )
        }
    }
}

private fun createItemsToWatch(): List<WatchedItem> {
    val criticalRoleValidator = { htmlAsString: String -> !htmlAsString.contains("Sold Out") }
    val legoValidator = { htmlAsString: String -> !htmlAsString.contains("rupture de stock") }
    val philibertValidator = { htmlAsString: String -> !htmlAsString.contains("plus en stock") }

    return listOf(
        WatchedItem(
            url = "https://shop.critrole.eu/collections/tabletop/products/taldorei-campaign-setting-reborn",
            validator = criticalRoleValidator,
            title = "Critical Role",
            subtitle = "TAL’DOREI CAMPAIGN SETTING REBORN",
            imageUrl = "https://cdn.shopify.com/s/files/1/0598/9879/0083/products/TalDoreiRebornHero_900x.jpg?v=1642119750",
        ),
        WatchedItem(
            url = "https://shop.critrole.eu/collections/tabletop/products/vox-machina-dice-set-gm",
            validator = criticalRoleValidator,
            title = "Critical Role",
            subtitle = "VOX MACHINA DICE SET: GM",
            imageUrl = "https://cdn.shopify.com/s/files/1/0598/9879/0083/products/ProductPhotos-VoxMachinaDiceSetGM-White-DiceandBag-1200x_900x.jpg?v=1637171913",
        ),
        WatchedItem(
            url = "https://www.lego.com/fr-fr/product/spider-man-keyring-853950",
            validator = legoValidator,
            title = "Lego",
            subtitle = "Porte-clés Spider-Man",
            imageUrl = "https://www.lego.com/cdn/cs/set/assets/blt7be7a40979eec479/853950.jpg?fit=bounds&format=jpg&quality=80&width=1600&height=1600&dpr=1",
        ),
        WatchedItem(
            url = "https://www.philibertnet.com/fr/sand-castle-games/98605-res-arcana-extension-perlae-imperii-850004236529.html",
            validator = philibertValidator,
            title = "Philibert",
            subtitle = "Res Arcana : Extension Perlae Imperii",
            imageUrl = "https://cdn1.philibertnet.com/508047-thickbox_default/res-arcana-extension-perlae-imperii.jpg",
        ),
    )
}

private fun createUIWatcherItems(): List<UIWatcherItem> = listOf(
    UIWatcherItem(
        title = "Critical Role",
        subtitle = "TAL’DOREI CAMPAIGN SETTING REBORN",
        imageUrl = "https://cdn.shopify.com/s/files/1/0598/9879/0083/products/TalDoreiRebornHero_900x.jpg?v=1642119750",
        url = "",
        inStock = false,
    ),
    UIWatcherItem(
        title = "Critical Role",
        subtitle = "VOX MACHINA DICE SET: GM",
        imageUrl = "https://cdn.shopify.com/s/files/1/0598/9879/0083/products/ProductPhotos-VoxMachinaDiceSetGM-White-DiceandBag-1200x_900x.jpg?v=1637171913",
        url = "",
        inStock = false,
    ),
)

@ExperimentalCoilApi
@Composable
fun AlertList(items: List<UIWatcherItem>, onItemClicked: (UIWatcherItem) -> Unit) {
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
        AlertList(createUIWatcherItems()) {}
    }
}
