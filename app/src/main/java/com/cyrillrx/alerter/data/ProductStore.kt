package com.cyrillrx.alerter.data

import com.cyrillrx.alerter.model.WatchedProduct

class ProductStore {
    fun get(): List<WatchedProduct> = PRODUCTS

    companion object {
        private val PRODUCTS = createProductToWatch()

        private fun createProductToWatch(): List<WatchedProduct> {
            val criticalRoleValidator = { htmlAsString: String -> htmlAsString.contains("http://schema.org/InStock") }
            val legoValidator = { htmlAsString: String -> !htmlAsString.contains("rupture de stock") }
            val philibertValidator = { htmlAsString: String -> !htmlAsString.contains("plus en stock") }

            return listOf(
                WatchedProduct(
                    url = "https://shop.critrole.eu/collections/tabletop/products/taldorei-campaign-setting-reborn",
                    validator = criticalRoleValidator,
                    title = "Critical Role - EU",
                    subtitle = "TAL’DOREI CAMPAIGN SETTING REBORN",
                    imageUrl = "https://cdn.shopify.com/s/files/1/0598/9879/0083/products/TalDoreiRebornHero_900x.jpg?v=1642119750",
                ),
                WatchedProduct(
                    url = "https://shop.critrole.eu/collections/tabletop/products/vox-machina-dice-set-gm",
                    validator = criticalRoleValidator,
                    title = "Critical Role - EU",
                    subtitle = "VOX MACHINA DICE SET: GM",
                    imageUrl = "https://cdn.shopify.com/s/files/1/0598/9879/0083/products/ProductPhotos-VoxMachinaDiceSetGM-White-DiceandBag-1200x_900x.jpg?v=1637171913",
                ),
                WatchedProduct(
                    url = "https://shop.critrole.com/collections/tabletop/products/taldorei-campaign-setting-reborn",
                    validator = criticalRoleValidator,
                    title = "Critical Role - US",
                    subtitle = "TAL’DOREI CAMPAIGN SETTING REBORN",
                    imageUrl = "https://cdn.shopify.com/s/files/1/0598/9879/0083/products/TalDoreiRebornHero_900x.jpg?v=1642119750",
                ),
                WatchedProduct(
                    url = "https://shop.critrole.com/collections/tabletop/products/vox-machina-dice-set-gm",
                    validator = criticalRoleValidator,
                    title = "Critical Role - US",
                    subtitle = "VOX MACHINA DICE SET: GM",
                    imageUrl = "https://cdn.shopify.com/s/files/1/0598/9879/0083/products/ProductPhotos-VoxMachinaDiceSetGM-White-DiceandBag-1200x_900x.jpg?v=1637171913",
                ),
                WatchedProduct(
                    url = "https://shop.critrole.com/products/critical-role-call-of-the-netherdeep",
                    validator = criticalRoleValidator,
                    title = "Critical Role - US",
                    subtitle = "CRITICAL ROLE: CALL OF THE NETHERDEEP",
                    imageUrl = "https://cdn.shopify.com/s/files/1/0052/4809/0172/products/DnD_CallOfTheNetherDeep_ProductPhotoMock_720x.jpg?v=1646158581",
                ),
                WatchedProduct(
                    url = "https://shop.critrole.com/collections/tabletop/products/critical-role-vox-machina-heroes-of-whitestone-1000-piece-jigsaw-puzzle",
                    validator = criticalRoleValidator,
                    title = "Critical Role - US",
                    subtitle = "CRITICAL ROLE: VOX MACHINA -- HEROES OF WHITESTONE 1000-PIECE JIGSAW PUZZLE",
                    imageUrl = "https://cdn.shopify.com/s/files/1/0052/4809/0172/products/Critical-Role_Heroes-of-Whitestone_1k_PZ_3dbt_Web-1200_720x.jpg?v=1637613262",
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
                WatchedProduct(
                    url = "https://www.philibertnet.com/fr/iello/94798-the-crew-mission-sous-marine-3760175518324.html",
                    validator = philibertValidator,
                    title = "Philibert",
                    subtitle = "The Crew : Mission Sous-Marine",
                    imageUrl = "https://cdn1.philibertnet.com/495745-thickbox_default/the-crew-mission-sous-marine.jpg",
                ),
            )
        }
    }
}
