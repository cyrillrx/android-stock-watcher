package com.cyrillrx.alerter.ui

import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.cyrillrx.alerter.data.ProductStore
import com.cyrillrx.alerter.model.WatchedProduct
import com.cyrillrx.alerter.validator.HtmlFetcher
import com.cyrillrx.alerter.widget.UiProduct
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val products: MutableList<WatchedProduct> = ArrayList(ProductStore().get())

    var uiState by mutableStateOf(MainScreenState(emptyList(), isLoading = true))

    init {
        updateProducts(application)
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

        private suspend fun WatchedProduct.toUIWatcherItem(context: Context): UiProduct = UiProduct(
            title = title,
            subtitle = subtitle,
            imageUrl = imageUrl,
            url = url,
            inStock = isInStock(),
            onClicked = { openUrl(context, url) },
        )

        private suspend fun WatchedProduct.isInStock(): Boolean {
            val htmlAsText = HtmlFetcher(url).getText()
            return validator(htmlAsText)
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
