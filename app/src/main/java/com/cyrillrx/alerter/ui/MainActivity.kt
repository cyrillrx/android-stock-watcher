package com.cyrillrx.alerter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import coil.annotation.ExperimentalCoilApi
import com.cyrillrx.alerter.model.WatchedProduct

@ExperimentalCoilApi
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val initialProducts = createProductToWatch()
        viewModel.setup(initialProducts)

        setContent {
            MainScreen(viewModel)
        }

        viewModel.updateProducts(this)
    }

    companion object {
        private fun createProductToWatch(): List<WatchedProduct> {
            val criticalRoleValidator = { htmlAsString: String -> htmlAsString.contains("http://schema.org/InStock") }
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
    }
}

