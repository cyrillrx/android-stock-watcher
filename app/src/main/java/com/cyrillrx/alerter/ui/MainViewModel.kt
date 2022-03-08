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
    var uiState by mutableStateOf(MainScreenState(emptyList()))

    fun updateProducts(products: List<WatchedProduct>) {
        viewModelScope.launch {
            val uiProducts = products.map { it.toUIWatcherItem() }
            uiState = MainScreenState(uiProducts)
        }
    }

    fun onProductClicked(context: Context, item: UiProduct) {
        openUrl(context, item.url)
    }

    companion object {
        private const val TAG = "MainViewModel"

        private suspend fun WatchedProduct.toUIWatcherItem(): UiProduct {
            val htmlAsText = HtmlFetcher(url).getText()

            return UiProduct(
                title = title,
                subtitle = subtitle,
                imageUrl = imageUrl,
                url = url,
                inStock = validator(htmlAsText),
            )
        }

        fun openUrl(context: Context, url: String) {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Log.e(TAG, "Could not open url: $url")
            }
        }
    }
}
