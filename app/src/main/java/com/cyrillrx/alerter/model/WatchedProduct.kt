package com.cyrillrx.alerter.model

class WatchedProduct(
    val url: String,
    val validator: (String) -> Boolean,
    val title: String,
    val subtitle: String,
    val imageUrl: String,
)
