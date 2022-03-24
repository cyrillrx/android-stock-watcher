package com.cyrillrx.alerter.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyrillrx.alerter.model.WatchedProduct
import com.cyrillrx.alerter.validator.HtmlFetcher
import com.cyrillrx.alerter.widget.UiProduct
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val products: MutableList<WatchedProduct> = ArrayList()

    var uiState by mutableStateOf(MainScreenState(emptyList(), isLoading = true))

    fun setup(products: List<WatchedProduct>) {
        this.products.apply {
            clear()
            addAll(products)
        }
    }

    fun updateProducts(context: Context) {
        if (products.isEmpty()) return

        viewModelScope.launch {
            uiState = MainScreenState(emptyList(), isLoading = true)
            val uiProducts = products.map { it.toUIWatcherItem(context) }
            uiState = MainScreenState(uiProducts, isLoading = false)
        }
    }

    companion object {
        private const val TAG = "MainViewModel"

        private suspend fun WatchedProduct.toUIWatcherItem(context: Context): UiProduct {
            val htmlAsText = HtmlFetcher(url).getText()

            return UiProduct(
                title = title,
                subtitle = subtitle,
                imageUrl = imageUrl,
                url = url,
                inStock = validator(htmlAsText),
                onClicked = { openUrl(context, url) },
            )
        }

        private fun openUrl(context: Context, url: String) {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Log.e(TAG, "Could not open url: $url")
            }
        }
    }
}
